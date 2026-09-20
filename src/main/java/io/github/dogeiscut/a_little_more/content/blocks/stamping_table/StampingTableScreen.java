package io.github.dogeiscut.a_little_more.content.blocks.stamping_table;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import io.github.dogeiscut.a_little_more.content.blocks.stamping_table.StampingTablePatternFaceRenderer.CubeView;
import io.github.dogeiscut.a_little_more.content.blocks.stamping_table.StampingTablePatternFaceRenderer.FaceFrame;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static io.github.dogeiscut.a_little_more.content.blocks.stamping_table.StampingTableLayout.*;

public class StampingTableScreen extends AbstractContainerScreen<StampingTableMenu> {

    private static final Component EDIT = Component.translatable("gui.a_little_more.stamping_table.edit");
    private static final Component EDITING = Component.translatable("gui.a_little_more.stamping_table.editing");
    private static final Component FLIP_PREVIEW = Component.translatable("gui.a_little_more.stamping_table.flip_preview");

    private static final float CUBE_HALF_WIDTH = 0.7071F;
    private static final float CUBE_HALF_HEIGHT = 0.8165F;

    private static final int GRID_ICON_BASE = 0xB0B0B0;
    private static final int GRID_ICON_PATTERN = 0xFFFFFF;
    private static final int SELECTED_BORDER = 0xFFF0B429;
    private static final int GIZMO_COLOR = 0xFFCC2222;

    private final Map<StampingTableAction, Button> actionButtons = new EnumMap<>(StampingTableAction.class);
    private final Map<Direction, Button> editButtons = new EnumMap<>(Direction.class);

    private ItemStack patternBlockHint = ItemStack.EMPTY;
    private ItemStack dyeHint = ItemStack.EMPTY;
    private ItemStack patternItemHint = ItemStack.EMPTY;

    private CubeView view = CubeView.FRONT;
    private int startRow;
    private float scrollOffs;
    private boolean scrolling;

    public StampingTableScreen(@NotNull StampingTableMenu menu, @NotNull Inventory inventory, @NotNull Component title) {
        super(menu, inventory, title);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY = TITLE_Y;
        this.inventoryLabelX = INVENTORY_X - 1;
        this.inventoryLabelY = INVENTORY_LABEL_Y;

        this.patternBlockHint = new ItemStack(ALMBlocks.PATTERN_BLOCK.get());
        this.dyeHint = new ItemStack(Items.WHITE_DYE);
        this.patternItemHint = new ItemStack(Items.CREEPER_BANNER_PATTERN);

        this.actionButtons.clear();
        this.editButtons.clear();

        StampingTableAction[][] rows = {
                {StampingTableAction.FLIP_HORIZONTAL, StampingTableAction.FLIP_VERTICAL},
                {StampingTableAction.ROTATE_CLOCKWISE, StampingTableAction.ROTATE_COUNTERCLOCKWISE},
                {StampingTableAction.COPY_FACE, StampingTableAction.PASTE_FACE},
        };
        for (int row = 0; row < rows.length; row++) {
            for (int column = 0; column < rows[row].length; column++) {
                this.addActionButton(rows[row][column],
                        this.leftPos + ACTION_BUTTON_X + column * (ACTION_BUTTON_WIDTH + ACTION_BUTTON_GAP),
                        this.topPos + ACTION_BUTTON_Y + row * (ACTION_BUTTON_HEIGHT + ACTION_BUTTON_GAP),
                        ACTION_BUTTON_WIDTH);
            }
        }
        this.addActionButton(StampingTableAction.REMOVE_FACE,
                this.leftPos + ACTION_BUTTON_X,
                this.topPos + ACTION_BUTTON_Y + rows.length * (ACTION_BUTTON_HEIGHT + ACTION_BUTTON_GAP),
                ACTION_BUTTON_WIDTH * 2 + ACTION_BUTTON_GAP);

        for (Direction direction : Direction.values()) {
            Button button = Button.builder(EDIT, pressed -> this.sendButton(StampingTableMenu.BUTTON_EDIT_FACE_BASE + direction.get3DDataValue()))
                    .bounds(0, 0, EDIT_BUTTON_END_WIDTH, EDIT_BUTTON_HEIGHT)
                    .tooltip(Tooltip.create(faceName(direction)))
                    .build();
            this.editButtons.put(direction, this.addRenderableWidget(button));
        }

        this.addRenderableWidget(Button.builder(FLIP_PREVIEW, pressed -> this.view = this.view.opposite())
                .bounds(this.leftPos + CUBE_CENTER_X - FLIP_PREVIEW_WIDTH / 2, this.topPos + FLIP_PREVIEW_Y,
                        FLIP_PREVIEW_WIDTH, EDIT_BUTTON_HEIGHT)
                .build());

        this.updateButtons();
    }

    private void addActionButton(@NotNull StampingTableAction action, int x, int y, int width) {
        Button button = Button.builder(Component.translatable(action.translationKey()), pressed -> this.sendButton(action.buttonId()))
                .bounds(x, y, width, ACTION_BUTTON_HEIGHT)
                .tooltip(Tooltip.create(Component.translatable(action.translationKey() + ".tooltip")))
                .build();
        this.actionButtons.put(action, this.addRenderableWidget(button));
    }

    private void sendButton(int id) {
        if (this.minecraft != null && this.minecraft.gameMode != null) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, id);
        }
    }

    private static @NotNull Component faceName(@NotNull Direction direction) {
        return Component.translatable("gui.a_little_more.stamping_table.face." + direction.getName());
    }

    private void updateButtons() {
        ItemStack input = this.menu.getPatternBlockStack();
        boolean hasInput = !input.isEmpty();
        Direction editing = this.menu.getEditingFace();
        boolean hasFace = hasInput && StampingTableMenu.getFaces(input).hasFace(editing);

        for (Map.Entry<StampingTableAction, Button> entry : this.actionButtons.entrySet()) {
            entry.getValue().active = switch (entry.getKey()) {
                case PASTE_FACE -> hasInput && this.menu.hasClipboard();
                default -> hasFace;
            };
        }

        float centerX = this.leftPos + CUBE_CENTER_X;
        float centerY = this.topPos + CUBE_CENTER_Y;
        for (Direction direction : Direction.values()) {
            Button button = this.editButtons.get(direction);
            button.visible = this.view.shows(direction);
            if (!button.visible) {
                continue;
            }

            boolean current = direction == editing;
            button.active = !current;
            button.setMessage(current ? EDITING : EDIT);

            int x;
            int y;
            int width;
            if (direction == this.view.endFace) {
                width = EDIT_BUTTON_END_WIDTH;
                x = Math.round(centerX - width / 2.0F);
                y = direction == Direction.UP
                        ? Math.round(centerY - CUBE_SCALE * CUBE_HALF_HEIGHT) - EDIT_BUTTON_GAP - EDIT_BUTTON_HEIGHT
                        : Math.round(centerY + CUBE_SCALE * CUBE_HALF_HEIGHT) + EDIT_BUTTON_GAP;
            } else {
                width = EDIT_BUTTON_SIDE_WIDTH;
                FaceFrame face = StampingTablePatternFaceRenderer.cubeFace(this.view, direction, centerX, centerY, CUBE_SCALE);
                y = Math.round(face.y(0.5F, 0.5F)) - EDIT_BUTTON_HEIGHT / 2;
                x = direction == this.view.leftFace
                        ? Math.round(centerX - CUBE_SCALE * CUBE_HALF_WIDTH) - EDIT_BUTTON_GAP - width
                        : Math.round(centerX + CUBE_SCALE * CUBE_HALF_WIDTH) + EDIT_BUTTON_GAP;
            }
            button.setWidth(width);
            button.setX(x);
            button.setY(y);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.updateButtons();
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        drawPanel(graphics, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
        for (Slot slot : this.menu.slots) {
            drawSlotFrame(graphics, this.leftPos + slot.x - 1, this.topPos + slot.y - 1);
        }

        this.renderSlotHint(graphics, this.menu.getSlot(0), this.patternBlockHint);
        this.renderSlotHint(graphics, this.menu.getSlot(1), this.dyeHint);
        this.renderSlotHint(graphics, this.menu.getSlot(2), this.patternItemHint);

        this.renderPatternGrid(graphics, mouseX, mouseY);
        this.renderFacePreview(graphics);

        StampingTablePatternFaceRenderer.drawCube(graphics, this.previewFaces(), this.view,
                this.leftPos + CUBE_CENTER_X, this.topPos + CUBE_CENTER_Y, CUBE_SCALE, this.menu.getEditingFace());
        this.renderAxisGizmo(graphics);
    }

    private void renderSlotHint(@NotNull GuiGraphics graphics, @NotNull Slot slot, @NotNull ItemStack hint) {
        if (slot.hasItem()) {
            return;
        }
        int x = this.leftPos + slot.x;
        int y = this.topPos + slot.y;
        graphics.renderFakeItem(hint, x, y);
        graphics.fill(RenderType.guiOverlay(), x, y, x + 16, y + 16, 0xB08B8B8B);
    }

    private boolean arePatternsLocked() {
        ItemStack input = this.menu.getPatternBlockStack();
        return !input.isEmpty() && !StampingTableMenu.getFaces(input).hasFace(this.menu.getEditingFace());
    }

    private @NotNull PatternBlockFaces previewFaces() {
        ItemStack result = this.menu.getResultStack();
        if (!result.isEmpty()) {
            return StampingTableMenu.getFaces(result);
        }
        return StampingTableMenu.getFaces(this.menu.getPatternBlockStack());
    }

    private void renderFacePreview(@NotNull GuiGraphics graphics) {
        int x = this.leftPos + PREVIEW_X;
        int y = this.topPos + PREVIEW_Y;
        graphics.fill(x - 2, y - 2, x + PREVIEW_SIZE + 2, y + PREVIEW_SIZE + 2, 0xFF000000);
        StampingTablePatternFaceRenderer.drawFace(graphics, FaceFrame.square(x, y, PREVIEW_SIZE),
                this.previewFaces().getFace(this.menu.getEditingFace()).orElse(null), 1.0F);
    }

    private void renderPatternGrid(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        List<Holder<PatternBlockPattern>> patterns = this.menu.getSelectablePatterns();
        int hidden = hiddenRows(patterns.size());
        this.startRow = Math.min(this.startRow, hidden);
        if (hidden == 0) {
            this.scrollOffs = 0.0F;
        }

        drawInset(graphics, this.leftPos + GRID_X - 2, this.topPos + GRID_Y - 2, GRID_WIDTH + 4, GRID_HEIGHT + 4);

        int selected = this.menu.getSelectedPatternIndex();
        int hovered = this.arePatternsLocked() ? -1 : this.patternIndexAt(mouseX, mouseY);
        for (int row = 0; row < GRID_VISIBLE_ROWS; row++) {
            for (int column = 0; column < GRID_COLUMNS; column++) {
                int index = (this.startRow + row) * GRID_COLUMNS + column;
                if (index >= patterns.size()) {
                    continue;
                }
                int x = this.leftPos + GRID_X + column * GRID_PITCH;
                int y = this.topPos + GRID_Y + row * GRID_PITCH;

                int border = index == selected ? SELECTED_BORDER : index == hovered ? 0xFFFFFFFF : 0xFF373737;
                graphics.fill(x, y, x + GRID_CELL, y + GRID_CELL, border);
                StampingTablePatternFaceRenderer.drawPatternIcon(graphics, patterns.get(index).value(),
                        x + 2, y + 2, GRID_CELL - 4, GRID_ICON_BASE, GRID_ICON_PATTERN);
            }
        }

        if (this.arePatternsLocked()) {
            graphics.fill(this.leftPos + GRID_X - 1, this.topPos + GRID_Y - 1,
                    this.leftPos + GRID_X + GRID_WIDTH + 1, this.topPos + GRID_Y + GRID_HEIGHT + 1, 0xA0C6C6C6);
        }

        int barX = this.leftPos + SCROLLBAR_X;
        int barY = this.topPos + GRID_Y;
        graphics.fill(barX, barY, barX + SCROLLBAR_WIDTH, barY + GRID_HEIGHT, 0xFF373737);
        int handleY = barY + (int) ((GRID_HEIGHT - SCROLLBAR_HANDLE_HEIGHT) * this.scrollOffs);
        int handleColor = hidden > 0 ? 0xFFC6C6C6 : 0xFF7A7A7A;
        graphics.fill(barX, handleY, barX + SCROLLBAR_WIDTH, handleY + SCROLLBAR_HANDLE_HEIGHT, 0xFF000000);
        graphics.fill(barX + 1, handleY + 1, barX + SCROLLBAR_WIDTH - 1, handleY + SCROLLBAR_HANDLE_HEIGHT - 1, handleColor);
    }

    // will probably want to change the look of this at some point tbh but this will do for now
    private void renderAxisGizmo(@NotNull GuiGraphics graphics) {
        float centerX = this.leftPos + GIZMO_X;
        float centerY = this.topPos + GIZMO_Y;

        for (Direction direction : new Direction[]{this.view.endFace, this.view.leftFace, this.view.rightFace}) {
            float[] screen = this.view.screenDirection(direction);
            float tipX = centerX + screen[0] * GIZMO_ARM;
            float tipY = centerY + screen[1] * GIZMO_ARM;

            StampingTablePatternFaceRenderer.drawLine(graphics, centerX, centerY, tipX, tipY, 1.5F, GIZMO_COLOR);
            double angle = Math.atan2(screen[1], screen[0]) + Math.PI;
            for (double spread : new double[]{0.5, -0.5}) {
                StampingTablePatternFaceRenderer.drawLine(graphics, tipX, tipY,
                        tipX + (float) Math.cos(angle + spread) * 4.0F, tipY + (float) Math.sin(angle + spread) * 4.0F,
                        1.5F, GIZMO_COLOR);
            }

            Component name = faceName(direction);
            int width = this.font.width(name);
            float anchorX = tipX + screen[0] * 2.0F;
            float anchorY = tipY + screen[1] * 2.0F;
            int textX = Math.round(screen[0] < -0.3F ? anchorX - width : screen[0] > 0.3F ? anchorX : anchorX - width / 2.0F);
            int textY = Math.round(screen[1] < -0.5F ? anchorY - 9.0F : screen[1] > 0.5F ? anchorY + 1.0F : anchorY - 4.0F);
            graphics.drawString(this.font, name, textX, textY, GIZMO_COLOR, false);
        }
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        int index = this.patternIndexAt(mouseX, mouseY);
        if (index >= 0 && this.arePatternsLocked()) {
            graphics.renderTooltip(this.font, Component.translatable("gui.a_little_more.stamping_table.dye_face_first"), mouseX, mouseY);
        } else if (index >= 0) {
            PatternBlockPattern pattern = this.menu.getSelectablePatterns().get(index).value();
            // it'd probably make more sense to use a generic pattern name without the color but whatever
            graphics.renderTooltip(this.font, Component.translatable(pattern.translationKey() + ".white"), mouseX, mouseY);
        }
    }

    private static int hiddenRows(int patternCount) {
        int rows = (patternCount + GRID_COLUMNS - 1) / GRID_COLUMNS;
        return Math.max(0, rows - GRID_VISIBLE_ROWS);
    }

    private int patternIndexAt(double mouseX, double mouseY) {
        double relativeX = mouseX - (this.leftPos + GRID_X);
        double relativeY = mouseY - (this.topPos + GRID_Y);
        if (relativeX < 0 || relativeY < 0) {
            return -1;
        }

        int column = (int) (relativeX / GRID_PITCH);
        int row = (int) (relativeY / GRID_PITCH);
        if (column >= GRID_COLUMNS || row >= GRID_VISIBLE_ROWS) {
            return -1;
        }
        if (relativeX - column * GRID_PITCH >= GRID_CELL || relativeY - row * GRID_PITCH >= GRID_CELL) {
            return -1;
        }

        int index = (this.startRow + row) * GRID_COLUMNS + column;
        return index < this.menu.getSelectablePatterns().size() ? index : -1;
    }

    private boolean isOverScrollbar(double mouseX, double mouseY) {
        int x = this.leftPos + SCROLLBAR_X;
        int y = this.topPos + GRID_Y;
        return mouseX >= x && mouseX < x + SCROLLBAR_WIDTH && mouseY >= y && mouseY < y + GRID_HEIGHT;
    }

    private void scrollBarTo(double mouseY) {
        int hidden = hiddenRows(this.menu.getSelectablePatterns().size());
        if (hidden == 0) {
            return;
        }
        float trackTop = this.topPos + GRID_Y + SCROLLBAR_HANDLE_HEIGHT / 2.0F;
        float trackLength = GRID_HEIGHT - SCROLLBAR_HANDLE_HEIGHT;
        this.scrollOffs = Mth.clamp((float) (mouseY - trackTop) / trackLength, 0.0F, 1.0F);
        this.startRow = (int) (this.scrollOffs * hidden + 0.5F);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int index = this.patternIndexAt(mouseX, mouseY);
            if (index >= 0) {
                if (this.arePatternsLocked()) {
                    return true;
                }
                if (this.minecraft != null) {
                    this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                }
                this.sendButton(StampingTableMenu.BUTTON_SELECT_PATTERN_BASE + index);
                return true;
            }
            if (this.isOverScrollbar(mouseX, mouseY)) {
                this.scrolling = true;
                this.scrollBarTo(mouseY);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling) {
            this.scrollBarTo(mouseY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int hidden = hiddenRows(this.menu.getSelectablePatterns().size());
        if (hidden > 0) {
            this.startRow = Mth.clamp(this.startRow - (int) Math.signum(scrollY), 0, hidden);
            this.scrollOffs = (float) this.startRow / hidden;
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    //TODO: actual background asset(s)

    private static void drawPanel(@NotNull GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0xFF000000);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0xFFFFFFFF);
        graphics.fill(x + 3, y + 3, x + width - 1, y + height - 1, 0xFF555555);
        graphics.fill(x + 3, y + 3, x + width - 3, y + height - 3, 0xFFC6C6C6);
    }

    private static void drawInset(@NotNull GuiGraphics graphics, int x, int y, int width, int height) {
        graphics.fill(x, y, x + width, y + height, 0xFFFFFFFF);
        graphics.fill(x, y, x + width - 1, y + height - 1, 0xFF373737);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0xFF8B8B8B);
    }

    private static void drawSlotFrame(@NotNull GuiGraphics graphics, int x, int y) {
        drawInset(graphics, x, y, 18, 18);
    }
}

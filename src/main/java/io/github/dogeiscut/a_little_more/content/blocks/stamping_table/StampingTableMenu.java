package io.github.dogeiscut.a_little_more.content.blocks.stamping_table;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Face;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Layer;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Orientation;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockItem;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockPattern;
import io.github.dogeiscut.a_little_more.registry.ALMBlocks;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import io.github.dogeiscut.a_little_more.registry.ALMMenuTypes;
import io.github.dogeiscut.a_little_more.registry.ALMTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StampingTableMenu extends AbstractContainerMenu {

    public static final int MAX_LAYERS_PER_FACE = 8;

    public static final int BUTTON_SELECT_PATTERN_BASE = 0;
    public static final int BUTTON_EDIT_FACE_BASE = 1000;
    public static final int BUTTON_ACTION_BASE = 2000;

    private static final int PATTERN_BLOCK_SLOT = 0;
    private static final int DYE_SLOT = 1;
    private static final int PATTERN_ITEM_SLOT = 2;
    private static final int RESULT_SLOT = 3;
    private static final int INVENTORY_START = 4;
    private static final int HOTBAR_START = 31;
    private static final int SLOTS_END = 40;

    private final @NotNull ContainerLevelAccess access;
    private final HolderLookup.@NotNull RegistryLookup<PatternBlockPattern> patternLookup;

    private final @NotNull Container inputContainer;
    private final @NotNull Container resultContainer;
    private final @NotNull Slot patternBlockSlot;
    private final @NotNull Slot dyeSlot;
    private final @NotNull Slot patternItemSlot;
    private final @NotNull Slot resultSlot;

    private final DataSlot selectedPattern = DataSlot.standalone();
    private final DataSlot editingFace = DataSlot.standalone();
    private final DataSlot clipboardPresent = DataSlot.standalone();

    private @NotNull List<Holder<PatternBlockPattern>> selectablePatterns = List.of();
    private @Nullable Face clipboard;
    private @NotNull Runnable slotUpdateListener = () -> {
    };

    public StampingTableMenu(int containerId, @NotNull Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public StampingTableMenu(int containerId, @NotNull Inventory inventory, @NotNull ContainerLevelAccess access) {
        super(ALMMenuTypes.STAMPING_TABLE.get(), containerId);
        this.access = access;
        this.patternLookup = inventory.player.registryAccess().lookupOrThrow(PatternBlockPattern.REGISTRY_KEY);

        this.inputContainer = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                StampingTableMenu.this.slotsChanged(this);
                StampingTableMenu.this.slotUpdateListener.run();
            }
        };
        this.resultContainer = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                super.setChanged();
                StampingTableMenu.this.slotUpdateListener.run();
            }
        };

        this.patternBlockSlot = this.addSlot(new Slot(this.inputContainer, 0,
                StampingTableLayout.PATTERN_BLOCK_SLOT_X, StampingTableLayout.PATTERN_BLOCK_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PatternBlockItem;
            }
        });
        this.dyeSlot = this.addSlot(new Slot(this.inputContainer, 1,
                StampingTableLayout.DYE_SLOT_X, StampingTableLayout.DYE_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }
        });
        this.patternItemSlot = this.addSlot(new Slot(this.inputContainer, 2,
                StampingTableLayout.PATTERN_ITEM_SLOT_X, StampingTableLayout.PATTERN_ITEM_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof BannerPatternItem;
            }
        });
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 0,
                StampingTableLayout.RESULT_SLOT_X, StampingTableLayout.RESULT_SLOT_Y) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                StampingTableMenu.this.patternBlockSlot.remove(1);
                StampingTableMenu.this.dyeSlot.remove(1);
                StampingTableMenu.this.access.execute((level, pos) -> level.playSound(
                        null, pos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F));
                super.onTake(player, stack);
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(inventory, column + row * 9 + 9,
                        StampingTableLayout.INVENTORY_X + column * 18, StampingTableLayout.INVENTORY_Y + row * 18));
            }
        }
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(inventory, column, StampingTableLayout.INVENTORY_X + column * 18, StampingTableLayout.HOTBAR_Y));
        }

        this.addDataSlot(this.selectedPattern).set(-1);
        this.addDataSlot(this.editingFace).set(Direction.UP.get3DDataValue());
        this.addDataSlot(this.clipboardPresent).set(0);

        this.selectablePatterns = computeSelectablePatterns(ItemStack.EMPTY);
    }

    public static @NotNull PatternBlockFaces getFaces(@NotNull ItemStack stack) {
        return stack.getOrDefault(ALMDataComponents.PATTERN_BLOCK_FACES, PatternBlockFaces.EMPTY);
    }

    private static boolean isSamePattern(@NotNull Holder<PatternBlockPattern> a, @NotNull Holder<PatternBlockPattern> b) {
        return a.unwrapKey().isPresent() ? a.unwrapKey().equals(b.unwrapKey()) : a.equals(b);
    }

    public @NotNull List<Holder<PatternBlockPattern>> getSelectablePatterns() {
        return this.selectablePatterns;
    }

    public int getSelectedPatternIndex() {
        return this.selectedPattern.get();
    }

    public @NotNull Direction getEditingFace() {
        return Direction.from3DDataValue(this.editingFace.get());
    }

    public boolean hasClipboard() {
        return this.clipboardPresent.get() != 0;
    }

    public @NotNull ItemStack getPatternBlockStack() {
        return this.patternBlockSlot.getItem();
    }

    public @NotNull ItemStack getResultStack() {
        return this.resultSlot.getItem();
    }

    public void registerUpdateListener(@NotNull Runnable listener) {
        this.slotUpdateListener = listener;
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int id) {
        if (id >= BUTTON_ACTION_BASE) {
            StampingTableAction action = StampingTableAction.byIndex(id - BUTTON_ACTION_BASE);
            return action != null && this.performAction(action);
        }
        if (id >= BUTTON_EDIT_FACE_BASE) {
            int face = id - BUTTON_EDIT_FACE_BASE;
            if (face >= Direction.values().length) {
                return false;
            }
            this.editingFace.set(face);
            this.setupResultSlot();
            return true;
        }
        if (id >= BUTTON_SELECT_PATTERN_BASE && id < this.selectablePatterns.size()) {
            this.selectedPattern.set(this.selectedPattern.get() == id ? -1 : id);
            this.setupResultSlot();
            return true;
        }
        return false;
    }

    private boolean performAction(@NotNull StampingTableAction action) {
        ItemStack input = this.patternBlockSlot.getItem();
        if (input.isEmpty()) {
            return false;
        }

        Direction direction = this.getEditingFace();
        PatternBlockFaces faces = getFaces(input);
        Optional<Face> existing = faces.getFace(direction);

        switch (action) {
            case COPY_FACE -> {
                if (existing.isEmpty()) {
                    return false;
                }
                this.clipboard = existing.get();
                this.clipboardPresent.set(1);
                return true;
            }
            case PASTE_FACE -> {
                if (this.clipboard == null) {
                    return false;
                }
                this.setFaces(input, faces.withFace(direction, this.clipboard));
                StampingTableMenu.this.access.execute((level, pos) -> level.playSound(
                        null, pos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F));
                return true;
            }
            case REMOVE_FACE -> {
                if (existing.isEmpty()) {
                    return false;
                }
                this.setFaces(input, faces.withoutFace(direction));
                StampingTableMenu.this.access.execute((level, pos) -> level.playSound(
                        null, pos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F));
                return true;
            }
            default -> {
                if (existing.isEmpty()) {
                    return false;
                }
                Face face = existing.get();
                Orientation orientation = action.apply(face.orientation());
                this.setFaces(input, faces.withFace(direction, new Face(face.baseColor(), orientation, face.layers())));
                return true;
            }
        }
    }

    private void setFaces(@NotNull ItemStack stack, @NotNull PatternBlockFaces faces) {
        stack.set(ALMDataComponents.PATTERN_BLOCK_FACES, faces);
        this.patternBlockSlot.setChanged();
    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        if (container == this.inputContainer) {
            this.refreshSelectablePatterns();
            this.setupResultSlot();
        }
    }

    private @Nullable Holder<PatternBlockPattern> getSelectedPattern() {
        int index = this.selectedPattern.get();
        return index >= 0 && index < this.selectablePatterns.size() ? this.selectablePatterns.get(index) : null;
    }

    private @NotNull List<Holder<PatternBlockPattern>> computeSelectablePatterns(@NotNull ItemStack patternItem) {
        List<Holder<PatternBlockPattern>> patterns = new ArrayList<>();
        this.patternLookup.get(ALMTags.PatternBlockPatterns.NO_ITEM_REQUIRED)
                .ifPresent(set -> set.forEach(patterns::add));

        if (patternItem.getItem() instanceof BannerPatternItem bannerPatternItem) {
            this.patternLookup.get(ALMTags.PatternBlockPatterns.forBannerPatternTag(bannerPatternItem.getBannerPattern()))
                    .ifPresent(set -> set.forEach(holder -> {
                        if (!patterns.contains(holder)) {
                            patterns.add(holder);
                        }
                    }));
        }
        return List.copyOf(patterns);
    }

    private void refreshSelectablePatterns() {
        Holder<PatternBlockPattern> previous = this.getSelectedPattern();
        this.selectablePatterns = this.computeSelectablePatterns(this.patternItemSlot.getItem());

        int index = -1;
        if (previous != null) {
            for (int i = 0; i < this.selectablePatterns.size(); i++) {
                if (isSamePattern(this.selectablePatterns.get(i), previous)) {
                    index = i;
                    break;
                }
            }
        }
        this.selectedPattern.set(index);
    }

    private void setupResultSlot() {
        ItemStack input = this.patternBlockSlot.getItem();
        ItemStack dye = this.dyeSlot.getItem();

        ItemStack result = ItemStack.EMPTY;
        if (!input.isEmpty() && dye.getItem() instanceof DyeItem dyeItem) {
            Direction direction = this.getEditingFace();
            PatternBlockFaces faces = getFaces(input);
            Optional<Face> existing = faces.getFace(direction);

            if (existing.isEmpty()) {
                result = input.copyWithCount(1);
                result.set(ALMDataComponents.PATTERN_BLOCK_FACES,
                        faces.withFace(direction, new Face(dyeItem.getDyeColor(), Orientation.R0_NONE, List.of())));
            } else {
                Holder<PatternBlockPattern> pattern = this.getSelectedPattern();
                Face face = existing.get();
                if (pattern != null && face.layers().size() < MAX_LAYERS_PER_FACE) {
                    List<Layer> layers = new ArrayList<>(face.layers());
                    layers.add(new Layer(pattern, dyeItem.getDyeColor()));

                    result = input.copyWithCount(1);
                    result.set(ALMDataComponents.PATTERN_BLOCK_FACES,
                            faces.withFace(direction, new Face(face.baseColor(), face.orientation(), layers)));
                }
            }
        }

        if (!ItemStack.matches(result, this.resultSlot.getItem())) {
            this.resultContainer.setItem(0, result);
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            original = stack.copy();

            if (index == RESULT_SLOT) {
                if (!this.moveItemStackTo(stack, INVENTORY_START, SLOTS_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, original);
            } else if (index != DYE_SLOT && index != PATTERN_BLOCK_SLOT && index != PATTERN_ITEM_SLOT) {
                if (stack.getItem() instanceof PatternBlockItem) {
                    if (!this.moveItemStackTo(stack, PATTERN_BLOCK_SLOT, PATTERN_BLOCK_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof DyeItem) {
                    if (!this.moveItemStackTo(stack, DYE_SLOT, DYE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.getItem() instanceof BannerPatternItem) {
                    if (!this.moveItemStackTo(stack, PATTERN_ITEM_SLOT, PATTERN_ITEM_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= INVENTORY_START && index < HOTBAR_START) {
                    if (!this.moveItemStackTo(stack, HOTBAR_START, SLOTS_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= HOTBAR_START && index < SLOTS_END
                        && !this.moveItemStackTo(stack, INVENTORY_START, HOTBAR_START, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, INVENTORY_START, SLOTS_END, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == original.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stack);
        }
        return original;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, ALMBlocks.STAMPING_TABLE.get());
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.inputContainer));
    }
}

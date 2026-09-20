package io.github.dogeiscut.a_little_more.content.blocks.stamping_table;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public enum StampingTableAction {
    FLIP_HORIZONTAL("flip_h", Orientation::flippedHorizontally),
    FLIP_VERTICAL("flip_v", Orientation::flippedVertically),
    ROTATE_CLOCKWISE("rotate_cw", Orientation::rotatedClockwise),
    ROTATE_COUNTERCLOCKWISE("rotate_ccw", Orientation::rotatedCounterClockwise),
    COPY_FACE("copy", null),
    PASTE_FACE("paste", null),
    REMOVE_FACE("remove_face", null);

    private final String translationSuffix;
    private final @Nullable UnaryOperator<Orientation> orientationChange;

    StampingTableAction(String translationSuffix, @Nullable UnaryOperator<Orientation> orientationChange) {
        this.translationSuffix = translationSuffix;
        this.orientationChange = orientationChange;
    }

    public static @Nullable StampingTableAction byIndex(int index) {
        StampingTableAction[] actions = values();
        return index >= 0 && index < actions.length ? actions[index] : null;
    }

    public @NotNull String translationKey() {
        return "gui.a_little_more.stamping_table." + translationSuffix;
    }

    public boolean changesOrientation() {
        return orientationChange != null;
    }

    public @NotNull Orientation apply(@NotNull Orientation orientation) {
        return orientationChange == null ? orientation : orientationChange.apply(orientation);
    }

    public int buttonId() {
        return StampingTableMenu.BUTTON_ACTION_BASE + ordinal();
    }
}

package io.github.dogeiscut.a_little_more.content.blocks.stamping_table;

public final class StampingTableLayout {

    private StampingTableLayout() {
    }

    public static final int WIDTH = 424;
    public static final int HEIGHT = 246;

    public static final int TITLE_Y = 5;

    public static final int PATTERN_BLOCK_SLOT_X = 9;
    public static final int PATTERN_BLOCK_SLOT_Y = 16;
    public static final int DYE_SLOT_X = 9;
    public static final int DYE_SLOT_Y = 38;
    public static final int PATTERN_ITEM_SLOT_X = 9;
    public static final int PATTERN_ITEM_SLOT_Y = 60;

    public static final int RESULT_SLOT_X = 373;
    public static final int RESULT_SLOT_Y = 41;

    public static final int INVENTORY_X = (WIDTH - 9 * 18) / 2 + 1;
    public static final int INVENTORY_LABEL_Y = 156;
    public static final int INVENTORY_Y = 168;
    public static final int HOTBAR_Y = 226;

    public static final int GRID_X = 32;
    public static final int GRID_Y = 16;
    public static final int GRID_COLUMNS = 7;
    public static final int GRID_VISIBLE_ROWS = 3;
    public static final int GRID_CELL = 20;
    public static final int GRID_PITCH = 22;
    public static final int GRID_WIDTH = GRID_COLUMNS * GRID_PITCH - (GRID_PITCH - GRID_CELL);
    public static final int GRID_HEIGHT = GRID_VISIBLE_ROWS * GRID_PITCH - (GRID_PITCH - GRID_CELL);
    public static final int SCROLLBAR_X = GRID_X + GRID_WIDTH + 4;
    public static final int SCROLLBAR_WIDTH = 8;
    public static final int SCROLLBAR_HANDLE_HEIGHT = 15;

    public static final int PREVIEW_X = 32;
    public static final int PREVIEW_Y = 90;
    public static final int PREVIEW_SIZE = 48;

    public static final int ACTION_BUTTON_X = 88;
    public static final int ACTION_BUTTON_Y = 90;
    public static final int ACTION_BUTTON_WIDTH = 60;
    public static final int ACTION_BUTTON_HEIGHT = 14;
    public static final int ACTION_BUTTON_GAP = 2;

    public static final int CUBE_CENTER_X = 288;
    public static final int CUBE_CENTER_Y = 83;
    public static final float CUBE_SCALE = 38.0F;
    public static final int EDIT_BUTTON_HEIGHT = 14;
    public static final int EDIT_BUTTON_END_WIDTH = 60; // top and bottom faces
    public static final int EDIT_BUTTON_SIDE_WIDTH = 44; // left and right faces
    public static final int EDIT_BUTTON_GAP = 3;
    public static final int FLIP_PREVIEW_WIDTH = 70;
    public static final int FLIP_PREVIEW_Y = 134;

    public static final int GIZMO_X = 363;
    public static final int GIZMO_Y = 128;
    public static final float GIZMO_ARM = 11.0F;
}

package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public final class PatternBlockFaceFrames {

    private static final float[][][] BASIS = new float[6][][];

    static {
        BASIS[Direction.UP.get3DDataValue()] = basis(0, 1, 0, 1, 0, 0, 0, 0, 1);
        BASIS[Direction.DOWN.get3DDataValue()] = basis(0, 0, 1, 1, 0, 0, 0, 0, -1);
        BASIS[Direction.NORTH.get3DDataValue()] = basis(1, 1, 0, -1, 0, 0, 0, -1, 0);
        BASIS[Direction.SOUTH.get3DDataValue()] = basis(0, 1, 1, 1, 0, 0, 0, -1, 0);
        BASIS[Direction.WEST.get3DDataValue()] = basis(0, 1, 0, 0, 0, 1, 0, -1, 0);
        BASIS[Direction.EAST.get3DDataValue()] = basis(1, 1, 1, 0, 0, -1, 0, -1, 0);
    }

    private PatternBlockFaceFrames() {
    }

    public static float[][] basis(@NotNull Direction direction) {
        return BASIS[direction.get3DDataValue()];
    }

    public static float[] right(@NotNull Direction direction) {
        return basis(direction)[1];
    }

    public static float[] down(@NotNull Direction direction) {
        return basis(direction)[2];
    }

    private static float[][] basis(float ox, float oy, float oz, float rx, float ry, float rz,
                                   float dx, float dy, float dz) {
        return new float[][]{{ox, oy, oz}, {rx, ry, rz}, {dx, dy, dz}};
    }
}

package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Face;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces.Orientation;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public final class PatternBlockFlip {

    public static final int TINT_OFFSET = 1000;

    private static final Direction[] SOURCE = new Direction[6];
    private static final int[] QUARTER_TURNS = new int[6];

    static {
        for (Direction shown : Direction.values()) {
            float[] turnedNormal = turn(shown.getStepX(), shown.getStepY(), shown.getStepZ());
            Direction source = Direction.getNearest(turnedNormal[0], turnedNormal[1], turnedNormal[2]);
            SOURCE[shown.get3DDataValue()] = source;

            float[] turnedRight = turn(PatternBlockFaceFrames.right(source));
            float alongRight = dot(turnedRight, PatternBlockFaceFrames.right(shown));
            float alongDown = dot(turnedRight, PatternBlockFaceFrames.down(shown));
            QUARTER_TURNS[shown.get3DDataValue()] =
                    alongRight > 0.5F ? 0 : alongDown > 0.5F ? 1 : alongRight < -0.5F ? 2 : 3;
        }
    }

    private PatternBlockFlip() {
    }

    public static @NotNull Direction source(@NotNull Direction shown) {
        return SOURCE[shown.get3DDataValue()];
    }

    public static @NotNull PatternBlockFaces flipped(@NotNull PatternBlockFaces faces) {
        Map<Direction, Face> result = new EnumMap<>(Direction.class);
        for (Direction shown : Direction.values()) {
            int index = shown.get3DDataValue();
            faces.getFace(SOURCE[index]).ifPresent(face -> {
                Orientation orientation = face.orientation();
                for (int i = 0; i < QUARTER_TURNS[index]; i++) {
                    orientation = orientation.rotatedClockwise();
                }
                result.put(shown, new Face(face.baseColor(), orientation, face.layers()));
            });
        }
        return new PatternBlockFaces(result);
    }

    private static float[] turn(float @NotNull [] v) {
        return turn(v[0], v[1], v[2]);
    }

    private static float[] turn(float x, float y, float z) {
        return new float[]{z, -y, x};
    }

    private static float dot(float @NotNull [] a, float @NotNull [] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }
}

package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record PatternBlockFaces(Map<Direction, Face> faces) {

    public static final PatternBlockFaces EMPTY = new PatternBlockFaces(Map.of());

    public PatternBlockFaces {
        faces = Map.copyOf(faces);

    }

    public Optional<Face> getFace(Direction direction) {
        return Optional.ofNullable(faces.get(direction));
    }

    public boolean hasFace(Direction direction) {
        return faces.containsKey(direction);
    }

    public PatternBlockFaces withFace(Direction direction, Face face) {
        Map<Direction, Face> copy = new EnumMap<>(Direction.class);
        copy.putAll(faces);
        copy.put(direction, face);
        return new PatternBlockFaces(copy);
    }

    public PatternBlockFaces withoutFace(Direction direction) {
        if (!faces.containsKey(direction)) {
            return this;
        }
        Map<Direction, Face> copy = new EnumMap<>(Direction.class);
        copy.putAll(faces);
        copy.remove(direction);
        return new PatternBlockFaces(copy);
    }

    public static final Codec<PatternBlockFaces> CODEC = Codec.unboundedMap(Direction.CODEC, Face.CODEC)
            .xmap(PatternBlockFaces::new, PatternBlockFaces::faces);

    public static final StreamCodec<RegistryFriendlyByteBuf, PatternBlockFaces> STREAM_CODEC =
            ByteBufCodecs.<RegistryFriendlyByteBuf, Direction, Face, Map<Direction, Face>>map(
                    i -> new EnumMap<>(Direction.class),
                    Direction.STREAM_CODEC,
                    Face.STREAM_CODEC
            ).map(PatternBlockFaces::new, PatternBlockFaces::faces);

    public enum Orientation implements StringRepresentable {
        R0_NONE("r0", 0, false),
        R90_NONE("r90", 90, false),
        R180_NONE("r180", 180, false),
        R270_NONE("r270", 270, false),
        R0_FLIP_H("r0_flip", 0, true),
        R90_FLIP_H("r90_flip", 90, true),
        R180_FLIP_H("r180_flip", 180, true),
        R270_FLIP_H("r270_flip", 270, true);

        public static final Codec<Orientation> CODEC = StringRepresentable.fromEnum(Orientation::values);

        public static final StreamCodec<ByteBuf, Orientation> STREAM_CODEC = ByteBufCodecs.fromCodecTrusted(CODEC);

        private final String name;
        private final int rotation;
        private final boolean flip;

        Orientation(String name, int rotation, boolean flip) {
            this.name = name;
            this.rotation = rotation;
            this.flip = flip;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        public int getRotation() {
            return rotation;
        }

        public boolean isFlip() {
            return flip;
        }

        public static Orientation of(int rotation, boolean flip) {
            int normalized = Math.floorMod(rotation, 360);
            for (Orientation orientation : values()) {
                if (orientation.rotation == normalized && orientation.flip == flip) {
                    return orientation;
                }
            }
            throw new IllegalArgumentException("Unsupported orientation: " + rotation + " flip=" + flip);
        }

        public Orientation rotatedClockwise() {
            return of(rotation + 90, flip);
        }

        public Orientation rotatedCounterClockwise() {
            return of(rotation - 90, flip);
        }

        public Orientation flippedHorizontally() {
            return of(-rotation, !flip);
        }

        public Orientation flippedVertically() {
            return of(180 - rotation, !flip);
        }
    }

    public record Layer(Holder<PatternBlockPattern> pattern, DyeColor color) {

        public static final Codec<Layer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                PatternBlockPattern.CODEC.fieldOf("pattern").forGetter(Layer::pattern),
                DyeColor.CODEC.fieldOf("color").forGetter(Layer::color)
        ).apply(instance, Layer::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Layer> STREAM_CODEC = StreamCodec.composite(
                PatternBlockPattern.STREAM_CODEC, Layer::pattern,
                DyeColor.STREAM_CODEC, Layer::color,
                Layer::new
        );
    }

    public record Face(DyeColor baseColor, Orientation orientation, List<Layer> layers) {

        public Face {
            layers = List.copyOf(layers);
        }

        public static final Codec<Face> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                DyeColor.CODEC.fieldOf("base_color").forGetter(Face::baseColor),
                Orientation.CODEC.fieldOf("orientation").forGetter(Face::orientation),
                Layer.CODEC.listOf().fieldOf("layers").forGetter(Face::layers)
        ).apply(instance, Face::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Face> STREAM_CODEC = StreamCodec.composite(
                DyeColor.STREAM_CODEC, Face::baseColor,
                Orientation.STREAM_CODEC, Face::orientation,
                Layer.STREAM_CODEC.apply(ByteBufCodecs.list()), Face::layers,
                Face::new
        );
    }
}

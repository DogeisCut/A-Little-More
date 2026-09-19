package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

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

    public static final Codec<PatternBlockFaces> CODEC = Codec.unboundedMap(Direction.CODEC, Face.CODEC)
            .xmap(PatternBlockFaces::new, PatternBlockFaces::faces);

    public static final StreamCodec<RegistryFriendlyByteBuf, PatternBlockFaces> STREAM_CODEC =
            ByteBufCodecs.<RegistryFriendlyByteBuf, Direction, Face, Map<Direction, Face>>map(
                    i -> new EnumMap<>(Direction.class),
                    Direction.STREAM_CODEC,
                    Face.STREAM_CODEC
            ).map(PatternBlockFaces::new, PatternBlockFaces::faces);

    public enum Orientation implements StringRepresentable {
        R0_NONE("r0_none", 0, false, false),
        R90_NONE("r90_none", 90, false, false),
        R180_NONE("r180_none", 180, false, false),
        R270_NONE("r270_none", 270, false, false),
        R0_FLIP_H("r0_flip_h", 0, true, false),
        R90_FLIP_H("r90_flip_h", 90, true, false),
        R180_FLIP_H("r180_flip_h", 180, true, false),
        R270_FLIP_H("r270_flip_h", 270, true, false);

        public static final Codec<Orientation> CODEC = StringRepresentable.fromEnum(Orientation::values);

        public static final StreamCodec<ByteBuf, Orientation> STREAM_CODEC = ByteBufCodecs.fromCodecTrusted(CODEC);

        private final String name;
        public final int rotation;
        public final boolean flipHorizontal;
        public final boolean flipVertical;

        Orientation(String name, int rotation, boolean flipHorizontal, boolean flipVertical) {
            this.name = name;
            this.rotation = rotation;
            this.flipHorizontal = flipHorizontal;
            this.flipVertical = flipVertical;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
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

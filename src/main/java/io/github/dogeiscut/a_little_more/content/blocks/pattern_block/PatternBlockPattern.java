package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record PatternBlockPattern(ResourceLocation assetId, String translationKey) {

    public static final ResourceKey<Registry<PatternBlockPattern>> REGISTRY_KEY =
            ResourceKey.createRegistryKey(ALittleMore.id("pattern_block_pattern"));

    public static final Codec<PatternBlockPattern> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(PatternBlockPattern::assetId),
            Codec.STRING.fieldOf("translation_key").forGetter(PatternBlockPattern::translationKey)
    ).apply(instance, PatternBlockPattern::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PatternBlockPattern> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, PatternBlockPattern::assetId,
            ByteBufCodecs.STRING_UTF8, PatternBlockPattern::translationKey,
            PatternBlockPattern::new
    );

    public static final Codec<Holder<PatternBlockPattern>> CODEC =
            net.minecraft.resources.RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PatternBlockPattern>> STREAM_CODEC =
            ByteBufCodecs.holder(REGISTRY_KEY, DIRECT_STREAM_CODEC);
}
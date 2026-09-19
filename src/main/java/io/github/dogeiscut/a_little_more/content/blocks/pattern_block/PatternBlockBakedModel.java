package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMModelProperties;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class PatternBlockBakedModel implements BakedModel {

    private final TextureAtlasSprite baseSprite;
    private final TextureAtlasSprite emptyBaseSprite; // Base sprite used when face has no data
    private final Function<ResourceLocation, TextureAtlasSprite> spriteLookup;

    private final Map<CacheKey, List<BakedQuad>> quadCache = new ConcurrentHashMap<>();

    public PatternBlockBakedModel(
            TextureAtlasSprite baseSprite,
            TextureAtlasSprite emptyBaseSprite,
            Function<ResourceLocation, TextureAtlasSprite> spriteLookup
    ) {
        this.baseSprite = baseSprite;
        this.emptyBaseSprite = emptyBaseSprite;
        this.spriteLookup = spriteLookup;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, @NotNull RandomSource random) {
        return getQuads(state, direction, random, ModelData.EMPTY, null);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(
            @Nullable BlockState state,
            @Nullable Direction side,
            @NotNull RandomSource rand,
            @NotNull ModelData extraData,
            @Nullable RenderType renderType
    ) {
        if (side == null) {
            return List.of();
        }

        PatternBlockFaces faces = extraData.get(ALMModelProperties.PATTERN_BLOCK_FACES);
        if (faces == null) {
            faces = PatternBlockFaces.EMPTY;
        }

        CacheKey key = new CacheKey(side, faces, renderType);
        PatternBlockFaces finalFaces = faces;
        return quadCache.computeIfAbsent(key, k -> buildQuads(side, finalFaces, renderType));
    }

    private List<BakedQuad> buildQuads(Direction side, PatternBlockFaces faces, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();

        boolean wantBase = renderType == null || renderType.equals(RenderType.solid());
        boolean wantPatterns = renderType == null || renderType.equals(RenderType.translucent());

        Optional<PatternBlockFaces.Face> faceOpt = faces.getFace(side);

        if (wantBase) {
            if (faceOpt.isPresent()) {
                int baseTint = side.get3DDataValue() * 100;
                quads.add(PatternBlockQuadBuilder.baseFaceQuad(side, baseSprite, baseTint));
            } else {
                quads.add(PatternBlockQuadBuilder.baseFaceQuad(side, emptyBaseSprite, -1));
            }
        }

        if (wantPatterns && faceOpt.isPresent()) {
            PatternBlockFaces.Face face = faceOpt.get();
            List<PatternBlockFaces.Layer> layers = face.layers();

            for (int i = 0; i < layers.size(); i++) {
                PatternBlockFaces.Layer layer = layers.get(i);
                TextureAtlasSprite sprite = spriteFor(layer.pattern().value());
                if (sprite == null) continue;

                int layerTint = (side.get3DDataValue() * 100) + i + 1;
                quads.add(PatternBlockQuadBuilder.layerFaceQuad(side, sprite, face.orientation(), layerTint));
            }
        }

        return quads;
    }

    @Nullable
    private TextureAtlasSprite spriteFor(PatternBlockPattern pattern) {
        ResourceLocation assetId = pattern.assetId();
        TextureAtlasSprite sprite = spriteLookup.apply(assetId);
        if (sprite == null) {
            ALittleMore.LOGGER.warn("Pattern block missing texture atlas entry for pattern '{}'", assetId);
        }
        return sprite;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return emptyBaseSprite;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return emptyBaseSprite;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.of(RenderType.solid(), RenderType.translucent());
    }

    private record CacheKey(Direction side, PatternBlockFaces faces, @Nullable RenderType renderType) {}
}
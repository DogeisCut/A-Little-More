package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
import io.github.dogeiscut.a_little_more.registry.ALMModelProperties;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
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

    private static final int MAX_CACHED_ITEM_MODELS = 512;

    private final TextureAtlasSprite baseSprite;
    private final TextureAtlasSprite emptyBaseSprite;
    private final Function<ResourceLocation, TextureAtlasSprite> spriteLookup;
    private final ItemTransforms transforms;

    private final Map<CacheKey, List<BakedQuad>> quadCache;
    private final Map<ItemKey, PatternBlockBakedModel> itemModels;

    private final @Nullable PatternBlockFaces itemFaces;
    private final int tintOffset;
    private final boolean flipped;
    private final @Nullable PatternBlockBakedModel flippedView;

    public PatternBlockBakedModel(
            TextureAtlasSprite baseSprite,
            TextureAtlasSprite emptyBaseSprite,
            Function<ResourceLocation, TextureAtlasSprite> spriteLookup,
            ItemTransforms transforms
    ) {
        this.baseSprite = baseSprite;
        this.emptyBaseSprite = emptyBaseSprite;
        this.spriteLookup = spriteLookup;
        this.transforms = transforms;
        this.quadCache = new ConcurrentHashMap<>();
        this.itemModels = new ConcurrentHashMap<>();
        this.itemFaces = null;
        this.tintOffset = 0;
        this.flipped = false;
        this.flippedView = new PatternBlockBakedModel(this, true);
    }

    private PatternBlockBakedModel(@NotNull PatternBlockBakedModel main, boolean flipped) {
        this.baseSprite = main.baseSprite;
        this.emptyBaseSprite = main.emptyBaseSprite;
        this.spriteLookup = main.spriteLookup;
        this.transforms = main.transforms;
        this.quadCache = main.quadCache;
        this.itemModels = main.itemModels;
        this.itemFaces = null;
        this.tintOffset = 0;
        this.flipped = flipped;
        this.flippedView = null;
    }

    private PatternBlockBakedModel(@NotNull PatternBlockBakedModel view, @Nullable PatternBlockFaces faces, int tintOffset) {
        this.baseSprite = view.baseSprite;
        this.emptyBaseSprite = view.emptyBaseSprite;
        this.spriteLookup = view.spriteLookup;
        this.transforms = view.transforms;
        this.quadCache = view.quadCache;
        this.itemModels = view.itemModels;
        this.itemFaces = faces;
        this.tintOffset = tintOffset;
        this.flipped = view.flipped;
        this.flippedView = null;
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext context, @NotNull PoseStack poseStack, boolean leftHand) {
        transforms.getTransform(context).apply(leftHand, poseStack);

        if (flippedView != null && context == ItemDisplayContext.GUI && PatternBlockFlipKey.isHeld()) {
            return flippedView;
        }
        return this;
    }

    @Override
    public @NotNull List<BakedModel> getRenderPasses(@NotNull ItemStack stack, boolean fabulous) {
        if (itemFaces != null) {
            return List.of(this);
        }

        PatternBlockFaces faces = stack.getOrDefault(ALMDataComponents.PATTERN_BLOCK_FACES, PatternBlockFaces.EMPTY);
        ItemKey key = new ItemKey(faces, flipped);
        PatternBlockBakedModel model = itemModels.get(key);
        if (model == null) {
            if (itemModels.size() >= MAX_CACHED_ITEM_MODELS) {
                itemModels.clear();
            }
            model = flipped
                    ? new PatternBlockBakedModel(this, PatternBlockFlip.flipped(faces), PatternBlockFlip.TINT_OFFSET)
                    : new PatternBlockBakedModel(this, faces, 0);
            itemModels.put(key, model);
        }
        return List.of(model);
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return transforms;
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
            faces = itemFaces != null ? itemFaces : PatternBlockFaces.EMPTY;
        }

        CacheKey key = new CacheKey(side, faces, renderType, tintOffset);
        PatternBlockFaces finalFaces = faces;
        return quadCache.computeIfAbsent(key, k -> buildQuads(side, finalFaces, renderType, tintOffset));
    }

    private @NotNull List<BakedQuad> buildQuads(@NotNull Direction side, @NotNull PatternBlockFaces faces, @Nullable RenderType renderType, int tintOffset) {
        List<BakedQuad> quads = new ArrayList<>();

        boolean wantBase = renderType == null || renderType.equals(RenderType.solid());
        boolean wantPatterns = renderType == null || renderType.equals(RenderType.translucent());

        Optional<PatternBlockFaces.Face> faceOpt = faces.getFace(side);

        if (wantBase) {
            if (faceOpt.isPresent()) {
                int baseTint = tintOffset + side.get3DDataValue() * 100;
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

                int layerTint = tintOffset + (side.get3DDataValue() * 100) + i + 1;
                quads.add(PatternBlockQuadBuilder.layerFaceQuad(side, sprite, face.orientation(), layerTint));
            }
        }

        return quads;
    }

    @Nullable
    private TextureAtlasSprite spriteFor(@NotNull PatternBlockPattern pattern) {
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

    private record CacheKey(Direction side, PatternBlockFaces faces, @Nullable RenderType renderType, int tintOffset) {
    }

    private record ItemKey(PatternBlockFaces faces, boolean flipped) {
    }
}
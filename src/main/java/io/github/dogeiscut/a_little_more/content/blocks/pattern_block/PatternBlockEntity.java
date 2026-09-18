package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlockEntities;
import io.github.dogeiscut.a_little_more.registry.ALMModelProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class PatternBlockEntity extends BlockEntity {
    public static final int MAX_PATTERNS = 8;
    private static final String TAG_PATTERNS = "patterns";
    private DyeColor baseColor;
    private BannerPatternLayers patterns = BannerPatternLayers.EMPTY;

    public PatternBlockEntity(BlockPos pos, BlockState blockState) {
        super(ALMBlockEntities.PATTERN_BLOCK_ENTITY.get(), pos, blockState);
        this.patterns = BannerPatternLayers.EMPTY;
        this.baseColor = ((PatternBlock) blockState.getBlock()).getColor();
    }

    public PatternBlockEntity(BlockPos pos, BlockState blockState, DyeColor baseColor) {
        this(pos, blockState);
        this.baseColor = baseColor;
    }
    public void fromItem(ItemStack stack, DyeColor color) {
        this.baseColor = color;
        this.applyComponentsFromItemStack(stack);
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder()
                .with(ALMModelProperties.BASE_COLOR, baseColor)
                .with(ALMModelProperties.BANNER_PATTERN_LAYERS, patterns)
                .build();
    }

    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.patterns.equals(BannerPatternLayers.EMPTY)) {
            tag.put("patterns", BannerPatternLayers.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), this.patterns).getOrThrow());
        }
    }

    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("patterns")) {
            BannerPatternLayers.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), tag.get("patterns")).resultOrPartial((p_331289_) -> ALittleMore.LOGGER.error("Failed to parse pattern block patterns: '{}'", p_331289_)).ifPresent((p_332632_) -> this.patterns = p_332632_);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    public BannerPatternLayers getPatterns() {
        return this.patterns;
    }

    public ItemStack getItem() {
        ItemStack itemstack = new ItemStack(PatternBlock.byColor(this.baseColor));
        itemstack.applyComponents(this.collectComponents());
        return itemstack;
    }

    public DyeColor getBaseColor() {
        return this.baseColor;
    }

    protected void applyImplicitComponents(BlockEntity.@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.patterns = componentInput.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
    }

    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.BANNER_PATTERNS, this.patterns);
    }

    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("patterns");
    }
}

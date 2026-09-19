package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMBlockEntities;
import io.github.dogeiscut.a_little_more.registry.ALMDataComponents;
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
    private static final String TAG_FACES = "faces";
    private PatternBlockFaces faces = PatternBlockFaces.EMPTY;

    public PatternBlockEntity(BlockPos pos, BlockState blockState) {
        super(ALMBlockEntities.PATTERN_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void setFaces(PatternBlockFaces faces) {
        this.faces = faces;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            this.requestModelDataUpdate();
        }
    }

    public PatternBlockFaces getFaces() {
        return this.faces;
    }

    @Override
    public @NotNull ModelData getModelData() {
        return ModelData.builder()
                .with(ALMModelProperties.PATTERN_BLOCK_FACES, this.faces)
                .build();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.faces.equals(PatternBlockFaces.EMPTY)) {
            PatternBlockFaces.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), this.faces)
                    .resultOrPartial(error -> ALittleMore.LOGGER.error("Failed to encode pattern block faces: '{}'", error))
                    .ifPresent(nbt -> tag.put(TAG_FACES, nbt));
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(TAG_FACES)) {
            PatternBlockFaces.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), tag.get(TAG_FACES))
                    .resultOrPartial(error -> ALittleMore.LOGGER.error("Failed to parse pattern block faces: '{}'", error))
                    .ifPresent(parsed -> this.faces = parsed);
        } else {
            this.faces = PatternBlockFaces.EMPTY;
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(this.getBlockState().getBlock());
        itemStack.applyComponents(this.collectComponents());
        return itemStack;
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.faces = componentInput.getOrDefault(ALMDataComponents.PATTERN_BLOCK_FACES, PatternBlockFaces.EMPTY);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(ALMDataComponents.PATTERN_BLOCK_FACES, this.faces);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove(TAG_FACES);
    }
}

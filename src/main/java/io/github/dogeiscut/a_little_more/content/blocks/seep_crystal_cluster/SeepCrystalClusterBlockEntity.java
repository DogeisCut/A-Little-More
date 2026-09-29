package io.github.dogeiscut.a_little_more.content.blocks.seep_crystal_cluster;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.blocks.pattern_block.PatternBlockFaces;
import io.github.dogeiscut.a_little_more.registry.ALMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SeepCrystalClusterBlockEntity extends BlockEntity {
    private ItemStack contents = ItemStack.EMPTY;

    public SeepCrystalClusterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ALMBlockEntities.SEEP_CRYSTAL_CLUSTER.get(), pos, blockState);
    }

    public ItemStack getContents() {
        return contents;
    }

    public void setContents(ItemStack contents) {
        this.contents = contents == null ? ItemStack.EMPTY : contents;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (!getContents().isEmpty()) {
            tag.put("Contents", getContents().save(registries));
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Contents", CompoundTag.TAG_COMPOUND)) {
            this.setContents(ItemStack.parse(registries, tag.getCompound("Contents")).orElse(ItemStack.EMPTY));
        } else {
            this.setContents(ItemStack.EMPTY);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void onDataPacket(net.minecraft.network.@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket pkt, HolderLookup.@NotNull Provider registries) {
        super.onDataPacket(net, pkt, registries);
        CompoundTag tag = pkt.getTag();
        this.loadAdditional(tag, registries);
    }
}

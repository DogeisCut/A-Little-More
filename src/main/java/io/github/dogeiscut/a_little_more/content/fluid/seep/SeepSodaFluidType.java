package io.github.dogeiscut.a_little_more.content.fluid.seep;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.fluid.BaseFluidType;
import io.github.dogeiscut.a_little_more.registry.ALMSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SoundActions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class SeepSodaFluidType extends SeepFluidType {
    public SeepSodaFluidType() {
        super("block.a_little_more.seep_soda", 0xAFFFFFFF);
    }
}
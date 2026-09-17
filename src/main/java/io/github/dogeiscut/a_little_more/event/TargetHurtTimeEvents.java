package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class TargetHurtTimeEvents {
    // TODO: figure out how to update the red flash entities do when hit

    @SubscribeEvent
    public static void onLivingEntityPostDamage(LivingDamageEvent.@NotNull Post event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            AttributeMap attributes = attacker.getAttributes();
            AttributeInstance instance = attributes.getInstance(ALMAttributes.TARGET_HURT_TIME);

            if (instance != null) {
                LivingEntity target = event.getEntity();
                double multiplier = instance.getValue();

                target.hurtDuration = (int) ((target.hurtDuration * multiplier) + 0.5);
                target.hurtTime = (int) ((target.hurtTime * multiplier) + 0.5);
                target.invulnerableTime = (int) ((target.invulnerableTime * multiplier) + 0.5);
            }
        }
    }


}

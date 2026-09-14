package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = ALittleMore.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TargetHurtTimeEvents {

    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            if (!event.has(type, ALMAttributes.TARGET_HURT_TIME)) {
                event.add(type, ALMAttributes.TARGET_HURT_TIME);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityPostDamage(LivingDamageEvent.Post event) {
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

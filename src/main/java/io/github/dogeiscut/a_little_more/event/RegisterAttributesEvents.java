package io.github.dogeiscut.a_little_more.event;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.content.mobs.animals.opossum.OpossumEntity;
import io.github.dogeiscut.a_little_more.registry.ALMEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ALittleMore.MOD_ID)
public class RegisterAttributesEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ALMEntities.OPOSSUM.get(), OpossumEntity.createAttributes());
    }
}

package io.github.dogeiscut.a_little_more.content.mobs.animals.opossum;

import io.github.dogeiscut.a_little_more.ALittleMore;
import io.github.dogeiscut.a_little_more.registry.ALMModelLayerLocations;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OpossumRenderer extends MobRenderer<OpossumEntity, OpossumModel<OpossumEntity>> {
    public OpossumRenderer(EntityRendererProvider.@NotNull Context context) {
        super(context, new OpossumModel<>(context.bakeLayer(ALMModelLayerLocations.OPOSSUM)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OpossumEntity opossumEntity) {
        return ALittleMore.id("textures/entity/opossum/opossum.png");
    }
}

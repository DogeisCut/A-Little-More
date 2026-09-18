package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.dogeiscut.a_little_more.ALittleMore;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import org.jetbrains.annotations.NotNull;

public class PatternBlockGeometryLoader implements IGeometryLoader<PatternBlockUnbakedGeometry> {
    public static final PatternBlockGeometryLoader INSTANCE = new PatternBlockGeometryLoader();
    public static final ResourceLocation ID = ALittleMore.id("pattern_block");

    @Override
    public @NotNull PatternBlockUnbakedGeometry read(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new PatternBlockUnbakedGeometry();
    }
}

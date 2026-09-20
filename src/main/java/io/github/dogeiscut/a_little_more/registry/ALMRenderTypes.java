package io.github.dogeiscut.a_little_more.registry;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class ALMRenderTypes extends RenderType {
    public ALMRenderTypes(@NotNull String name, @NotNull VertexFormat format, VertexFormat.@NotNull Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, @NotNull Runnable setupState, @NotNull Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
}

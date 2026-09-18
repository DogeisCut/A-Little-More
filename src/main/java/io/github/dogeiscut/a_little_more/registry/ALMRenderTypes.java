package io.github.dogeiscut.a_little_more.registry;

import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.VertexFormat;

public class ALMRenderTypes extends RenderType {
    public ALMRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
}

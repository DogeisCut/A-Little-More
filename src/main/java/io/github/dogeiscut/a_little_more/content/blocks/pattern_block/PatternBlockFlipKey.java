package io.github.dogeiscut.a_little_more.content.blocks.pattern_block;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public final class PatternBlockFlipKey {

    public static final KeyMapping FLIP = new KeyMapping(
            "key.a_little_more.flip_pattern_blocks",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_SHIFT,
            "key.categories.a_little_more"
    );

    private PatternBlockFlipKey() {
    }

    public static boolean isHeld() {
        if (FLIP.isUnbound()) {
            return false;
        }

        InputConstants.Key key = FLIP.getKey();
        long window = Minecraft.getInstance().getWindow().getWindow();
        return switch (key.getType()) {
            case KEYSYM -> InputConstants.isKeyDown(window, key.getValue());
            case MOUSE -> GLFW.glfwGetMouseButton(window, key.getValue()) == GLFW.GLFW_PRESS;
            default -> FLIP.isDown();
        };
    }
}

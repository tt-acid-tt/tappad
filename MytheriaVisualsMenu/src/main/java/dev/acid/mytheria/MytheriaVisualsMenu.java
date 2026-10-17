package dev.acid.mytheria;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class MytheriaVisualsMenu implements ClientModInitializer {
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mytheria_visuals_menu.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.mytheria_visuals_menu"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen instanceof MytheriaMenuScreen) {
                    client.setScreen(null);
                } else if (client.currentScreen == null) {
                    client.setScreen(new MytheriaMenuScreen());
                }
            }
        });
    }
}

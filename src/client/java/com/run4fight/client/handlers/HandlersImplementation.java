package com.run4fight.client.handlers;

import com.run4fight.client.overlay.BuffOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

public class HandlersImplementation {

    public static void register(ActionBarHandler actionBarHandler) {
        ClientTickEvents.END_CLIENT_TICK.register(
                actionBarHandler::onTick
        );
    }

    public static void registerOverlay(BuffOverlay buffOverlay) {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CHAT,
                BuffOverlay.ID,
                buffOverlay::render
        );
    }
}

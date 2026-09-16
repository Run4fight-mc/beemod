package com.run4fight.client.core.overlay;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

import java.util.ArrayList;
import java.util.List;

/**
 * The single place overlays are registered.
 *
 * <p>One {@link #register} call attaches the overlay to the HUD and adds
 * it to the BeeMod config screen's feature list - there is nothing else
 * to wire up.
 */
public final class OverlayRegistry {

    private static final List<Overlay> OVERLAYS = new ArrayList<>();

    private OverlayRegistry() {
    }

    public static void register(Overlay overlay) {
        OVERLAYS.add(overlay);

        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CHAT,
                overlay.getId(),
                overlay::render
        );
    }

    /** Every registered overlay, in registration order. */
    public static List<Overlay> all() {
        return List.copyOf(OVERLAYS);
    }
}

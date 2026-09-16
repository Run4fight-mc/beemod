package com.run4fight.client.core.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class HandlersImplementation {

    public static void register(ActionBarHandler actionBarHandler) {
        ClientTickEvents.END_CLIENT_TICK.register(
                actionBarHandler::onTick
        );
    }
}

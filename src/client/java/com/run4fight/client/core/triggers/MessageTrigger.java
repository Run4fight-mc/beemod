package com.run4fight.client.core.triggers;

import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.model.CooldownModel;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class MessageTrigger {

    private final CooldownModel model;
    private final TriggerHandler handler;

    public MessageTrigger(CooldownModel model, TriggerHandler handler) {
        this.model = model;
        this.handler = handler;
    }

    public void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String text = message.getString();

            if (model.matchesBreak(text)) {
                model.disable();
                handler.onBroken(model);
                return;
            }

            if (model.matchesComplete(text)) {
                handler.onCompleted(model);
                return;
            }

            if (model.matchesTrigger(text)) {
                model.trigger();
                handler.onTriggered(model);
            }
        });
    }

    public CooldownModel getModel() { return model; }
}
package com.run4fight.client.core.handlers;

import com.run4fight.client.core.triggers.CooldownData;
import com.run4fight.client.core.triggers.MessageTrigger;
import com.run4fight.client.core.triggers.TriggerRegistry;
import com.run4fight.client.model.CooldownModel;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class TriggerHandler {

    private final TriggerRegistry registry = new TriggerRegistry();

    public void register() {
        registerCooldown("wealth_clock", "[ⓘ] The wealth clock powered up!", 3_600_000L);
    }

    private void registerCooldown(String id, String target, long durationMs) {
        CooldownModel model = new CooldownModel(id, target, durationMs);

        CooldownData.getEndMs(id).ifPresent(model::restore);

        MessageTrigger trigger = new MessageTrigger(model, this);
        registry.add(trigger);
        trigger.register();
    }

    public void onTriggered(CooldownModel model) {
        CooldownData.save(model);
    }

    public TriggerRegistry getRegistry() { return registry; }
}
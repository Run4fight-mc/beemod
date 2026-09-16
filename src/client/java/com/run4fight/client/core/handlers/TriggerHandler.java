package com.run4fight.client.core.handlers;

import com.run4fight.client.config.ChatTriggers;
import com.run4fight.client.core.triggers.CooldownData;
import com.run4fight.client.core.triggers.MessageTrigger;
import com.run4fight.client.core.triggers.TriggerRegistry;
import com.run4fight.client.model.CooldownModel;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.util.regex.Pattern;

public class TriggerHandler {

    private final TriggerRegistry registry = new TriggerRegistry();

    public void register() {
        for (ChatTriggers trigger : ChatTriggers.values()) {
            registerCooldown(
                    trigger.getCooldownName(),
                    trigger.getTriggerMessage(),
                    trigger.getBreakMessage(),
                    trigger.getCompleteMessage(),
                    trigger.getDurationMs()
            );
        }
    }

    private void registerCooldown(
            String id,
            Pattern triggerMessage,
            Pattern breakMessage,
            Pattern completeMessage,
            long durationMs
    ) {
        CooldownModel model = new CooldownModel(
                id,
                triggerMessage,
                breakMessage,
                completeMessage,
                durationMs
        );

        CooldownData.getEndMs(id).ifPresent(model::restore);

        MessageTrigger trigger = new MessageTrigger(model, this);
        registry.add(trigger);
        trigger.register();
    }

    public void onBroken(CooldownModel model) {
        model.reset();
    }

    public void onCompleted(CooldownModel model) {
        model.complete();
    }

    public void onTriggered(CooldownModel model) {
        CooldownData.save(model);
    }

    public TriggerRegistry getRegistry() { return registry; }
}
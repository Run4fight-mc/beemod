package com.run4fight.client.core.handlers;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.config.ChatTriggers;
import com.run4fight.client.config.ChatTriggerSettings;
import com.run4fight.client.core.triggers.CooldownData;
import com.run4fight.client.core.triggers.MessageTrigger;
import com.run4fight.client.core.triggers.TriggerRegistry;
import com.run4fight.client.model.CooldownModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;

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

        MessageTrigger trigger = new MessageTrigger(
                model,
                this
        );

        registry.add(trigger);
        trigger.register();
    }

    public void onBroken(CooldownModel model) {
        model.reset();
    }

    public void onCompleted(CooldownModel model) {
        model.complete();

        ChatTriggerSettings settings = getSettings(model.getId());

        if (settings.isSoundOnEnd()) {
            playBell();
        }
    }

    public void onTriggered(CooldownModel model) {
        ChatTriggerSettings settings = getSettings(model.getId());

        if (settings.isSoundOnStart()) {
            playBell();
        }

        CooldownData.save(model);
    }

    private ChatTriggerSettings getSettings(String id) {
        for (ChatTriggers trigger : ChatTriggers.values()) {
            if (trigger.getCooldownName().equals(id)) {
                return BeeModConfig.get().chatTrigger(trigger);
            }
        }

        return new ChatTriggerSettings(false, false, false);
    }

    public boolean isEnabled(String id) {
        return getSettings(id).isEnabled();
    }

    public TriggerRegistry getRegistry() {
        return registry;
    }

    private void playBell() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null) {
            client.player.playSound(
                    SoundEvents.BLOCK_BELL_USE,
                    4.0f,
                    0.5f
            );
        }
    }
}
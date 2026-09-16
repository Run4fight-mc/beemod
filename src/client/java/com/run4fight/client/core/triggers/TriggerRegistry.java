package com.run4fight.client.core.triggers;

import com.run4fight.client.model.CooldownModel;
import java.util.*;

public class TriggerRegistry {

    private final List<MessageTrigger> triggers = new ArrayList<>();

    public void add(MessageTrigger trigger) { triggers.add(trigger); }

    /** Every registered cooldown, active or not - used to build config options. */
    public List<CooldownModel> getAll() {
        return triggers.stream()
                .map(MessageTrigger::getModel)
                .toList();
    }

    public List<CooldownModel> getActiveCooldowns() {
        return triggers.stream()
                .map(MessageTrigger::getModel)
                .filter(CooldownModel::isActive)
                .toList();
    }
}
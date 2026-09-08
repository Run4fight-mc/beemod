package com.run4fight.client.model;

import java.util.List;

public class BuffModel {
    private final char icon;
    private final String name;
    private final String duration;
    private final double stacks;
    private final List<EffectModel> effects;

    public BuffModel(
            char icon,
            String name,
            String duration,
            double stacks,
            List<EffectModel> effects) {
        this.icon = icon;
        this.name = name;
        this.duration = duration;
        this.stacks = stacks;
        this.effects = effects;
    }

    public char getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public double getStacks() {
        return stacks;
    }

    public List<EffectModel> getEffects() {
        return effects;
    }
}


package com.run4fight.client.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum AlertSound {
    BELL(
            "Bell",
            SoundEvents.BLOCK_BELL_USE
    ),
    PLING(
            "Pling",
            SoundEvents.BLOCK_NOTE_BLOCK_PLING.value()
    ),
    EXPERIENCE(
            "Experience",
            SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP
    ),
    AMETHYST(
            "Amethyst",
            SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME
    ),
    TOTEM(
            "Totem",
            SoundEvents.ITEM_TOTEM_USE
    ),
    ENDER_DRAGON_GROWL(
            "Dragon Growl",
            SoundEvents.ENTITY_ENDER_DRAGON_GROWL
    ),
    LEVEL_UP(
            "Level Up",
            SoundEvents.ENTITY_PLAYER_LEVELUP
    );

    private final String displayName;
    private final SoundEvent sound;

    AlertSound(String displayName, SoundEvent sound) {
        this.displayName = displayName;
        this.sound = sound;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public void play() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null) {
            client.player.playSound(
                    sound,
                    1.0f,
                    1.0f
            );
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
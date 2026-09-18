package com.run4fight.client.config;

public class ChatTriggerSettings {
    private boolean enabled;
    private boolean soundOnStart;
    private boolean soundOnEnd;
    private AlertSound sound;


    public ChatTriggerSettings(
            boolean enabled,
            boolean soundOnStart,
            boolean soundOnEnd
    ) {
        this(
                enabled,
                soundOnStart,
                soundOnEnd,
                AlertSound.BELL
        );
    }

    public ChatTriggerSettings(
            boolean enabled,
            boolean soundOnStart,
            boolean soundOnEnd,
            AlertSound sound
    ) {
        this.enabled = enabled;
        this.soundOnStart = soundOnStart;
        this.soundOnEnd = soundOnEnd;
        this.sound = sound;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSoundOnStart() {
        return soundOnStart;
    }

    public void setSoundOnStart(boolean soundOnStart) {
        this.soundOnStart = soundOnStart;
    }

    public boolean isSoundOnEnd() {
        return soundOnEnd;
    }

    public void setSoundOnEnd(boolean soundOnEnd) {
        this.soundOnEnd = soundOnEnd;
    }

    public AlertSound getSound() {
        return sound != null ? sound : AlertSound.BELL;
    }

    public void setSound(AlertSound sound) {
        this.sound = sound != null ? sound : AlertSound.BELL;
    }
}
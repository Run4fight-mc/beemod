package com.run4fight.client.config;

public class ChatTriggerSettings {

    private boolean enabled;
    private boolean soundOnStart;
    private boolean soundOnEnd;

    public ChatTriggerSettings(
            boolean enabled,
            boolean soundOnStart,
            boolean soundOnEnd
    ) {
        this.enabled = enabled;
        this.soundOnStart = soundOnStart;
        this.soundOnEnd = soundOnEnd;
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
}
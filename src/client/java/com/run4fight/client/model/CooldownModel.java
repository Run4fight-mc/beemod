package com.run4fight.client.model;

public class CooldownModel {
    private final String id;
    private final String targetMessage;
    private final long durationMs;
    private volatile long endMs = 0L;
    private volatile long lastTrigger = 0L;

    private static final long MIN_TRIGGER_INTERVAL = 5000L; // 5s


    public CooldownModel(String id, String targetMessage, long durationMs) {
        this.id = id;
        this.targetMessage = targetMessage;
        this.durationMs = durationMs;
    }

    public void trigger() {
        long now = System.currentTimeMillis();
        if (now - lastTrigger < MIN_TRIGGER_INTERVAL) {
            return;
        }
        lastTrigger = now;
        this.endMs = now + durationMs;
    }

    public void restore(long endMs) {
        this.endMs = endMs;
    }

    public long getRemainingMs() {
        return Math.max(0, endMs - System.currentTimeMillis());

    }

    public boolean isActive() { return getRemainingMs() > 0; }
    public String getId() { return id; }
    public String getTargetMessage() { return targetMessage; }
}
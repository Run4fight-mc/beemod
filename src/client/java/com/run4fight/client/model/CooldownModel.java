package com.run4fight.client.model;

import java.util.regex.Pattern;

public class CooldownModel {
    private final String id;
    private final Pattern triggerMessage;
    private final Pattern breakMessage;
    private final Pattern completeMessage;
    private final long durationMs;

    private volatile long endMs = 0L;
    private volatile long lastTrigger = 0L;

    private static final long MIN_TRIGGER_INTERVAL = 5000L; // 5s

    public CooldownModel(
            String id,
            Pattern triggerMessage,
            Pattern breakMessage,
            Pattern completeMessage,
            long durationMs
    ) {
        this.id = id;
        this.triggerMessage = triggerMessage;
        this.breakMessage = breakMessage;
        this.completeMessage = completeMessage;
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

    public void disable() {
        this.endMs = 0L;
    }

    public void restore(long endMs) {
        this.endMs = endMs;
    }

    public long getRemainingMs() {
        return Math.max(0, endMs - System.currentTimeMillis());
    }

    public boolean isActive() {
        return getRemainingMs() > 0;
    }

    public boolean matchesTrigger(String message) {
        return !triggerMessage.pattern().isEmpty()
                && triggerMessage.matcher(message).find();
    }

    public boolean matchesBreak(String message) {
        return !breakMessage.pattern().isEmpty()
                && breakMessage.matcher(message).find();
    }

    public boolean matchesComplete(String message) {
        return !completeMessage.pattern().isEmpty()
                && completeMessage.matcher(message).find();
    }

    public String getId() {
        return id;
    }

    public Pattern getTriggerMessage() {
        return triggerMessage;
    }

    public Pattern getBreakMessage() {
        return breakMessage;
    }

    public Pattern getCompleteMessage() {
        return completeMessage;
    }

    public void reset() {
        endMs = System.currentTimeMillis() + durationMs;
    }

    public void complete() {
        endMs = 0L;
    }
}
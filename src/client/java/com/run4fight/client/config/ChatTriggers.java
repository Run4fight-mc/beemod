package com.run4fight.client.config;

import java.util.regex.Pattern;

public enum ChatTriggers {
    WEALTH_CLOCK(
            "wealth_clock",
            "\\[ⓘ] The wealth clock powered up!",
            "",
            "",
            3_600_000L
    ),

    RED_FIELD_BOOST(
            "red_field_boost",
            "\\[ⓘ] Boosted "+RegexData.RED_FIELD.getRegex()+" field",
            "",
            "",
            3_600_000L
    ),

    BLUE_FIELD_BOOST(
            "blue_field_boost",
            "\\[ⓘ] Boosted "+RegexData.BLUE_FIELD.getRegex()+" field",
            "",
            "",
            3_600_000L
    ),

    MOUNTAIN_TOP_BOOST(
            "mountain_top_boost",
            "\\[ⓘ] Boosted "+RegexData.WHITE_FIELD.getRegex()+" field",
            "",
            "",
            3_600_000L
    ),

    TOTEM_BEE_DESPAWN(
            "totem_bee_despawn",
            "\\[!] A totem has spawned\\.\\.\\.",
            "\\[!] A totem bee was found by "+RegexData.PLAYER_NAME.getRegex(),
            "\\[!] \\+\\d bee totem \\(from totem bee\\)",
            300_000L
    ),

    LAPIS_BEE_DESPAWN(
            "lapis_bee_despawn",
            "\\[!] An (EPIC )?ore has spawned\\.\\.\\.",
            "\\[!] A (GIFTED )?hungry lapis bee was found by "+RegexData.PLAYER_NAME.getRegex(),
            "\\[!] \\+1 (epic )?lapis \\(from (epic )?hungry lapis bee\\)",
            300_000L
    );

    private final String cooldownName;
    private final Pattern triggerMessage;
    private final Pattern breakMessage;
    private final Pattern completeMessage;
    private final long durationMs;

    ChatTriggers(
            String cooldownName,
            String triggerMessage,
            String breakMessage,
            String completeMessage,
            long durationMs
    ) {
        this.cooldownName = cooldownName;
        this.triggerMessage = Pattern.compile(triggerMessage);
        this.breakMessage = Pattern.compile(breakMessage);
        this.completeMessage = Pattern.compile(completeMessage);
        this.durationMs = durationMs;
    }

    public String getCooldownName() {
        return cooldownName;
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

    public long getDurationMs() {
        return durationMs;
    }
}
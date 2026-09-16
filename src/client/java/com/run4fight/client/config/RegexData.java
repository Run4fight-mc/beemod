package com.run4fight.client.config;

import java.util.regex.Pattern;

public enum RegexData {
    //used for common used regex to make it easier to use
    PLAYER_NAME("PLAYER_NAME", "[a-zA-Z0-9_]{2,16}$"),
    RED_FIELD("RED_FIELD", "(strawberry|rose|mushroom)"),
    BLUE_FIELD("BLUE_FIELD", "(pine tree|bamboo|blue flower)"),
    WHITE_FIELD("WHITE_FIELD", "(dandelion|sunflower|pumpkin|spider|pineapple|cactus)");


    private final String regexName;
    private final String regex;


    RegexData(
            String regexName,
            String regex
    ) {
        this.regexName = regexName;
        this.regex = regex;
    }

    public String getRegexName() {
        return regexName;
    }

    public String getRegex() {
        return regex;
    }

    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}

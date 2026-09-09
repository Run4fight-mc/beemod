package com.run4fight.client.core.font;

public enum SymbolIcons {
    COLON_10(":", '\uE085'),
    DOT_0(".", '\uE081'),
    X_0("x", '\uE084'),
    TDOT_14(".", '\uE0AA'),
    TPLUS_14("+", '\uE0AB'),
    TPERCENT_14("%", '\uE0AC'),

    TDOT_17(".", '\uE0D1'),
    TPLUS_17("+", '\uE0D2'),
    TPERCENT_17("%", '\uE0D3'),


    TDOT_20(".", '\uE0F8'),
    TPLUS_20("+", '\uE0F9'),
    TPERCENT_20("%", '\uE0FA');

    private final char icon;
    private final String character;

    SymbolIcons(String character, char icon) {
        this.icon = icon;
        this.character = character;
    }

    public char getIcon() {
        return icon;
    }

    public String getCharacter() {
        return character;
    }

}



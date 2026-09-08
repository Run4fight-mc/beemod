package com.run4fight.client.font;

public enum SpaceIcons {
    SPACE_1(" ",'\uE00C'),
    SPACE_2(" ",'\uE00B'),
    SPACE_3("-",'\uE00D'),
    SPACE_4("_",'\uE00E'),
    SPACE_5(" ",'\uE00F');


    public final char icon;
    private final String character;

    SpaceIcons(String character, char icon) {
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

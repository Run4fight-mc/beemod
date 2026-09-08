package com.run4fight.client.font;

public enum DigitIcons {
    //CHARACTER_HEIGHT("character", 'icon'),
    NUMBER_0_10("0", '\uE06F'),
    NUMBER_9_10("9", '\uE070'),
    NUMBER_8_10("8", '\uE071'),
    NUMBER_2_10("2", '\uE072'),
    NUMBER_4_10("4", '\uE073'),
    NUMBER_3_10("3", '\uE074'),
    NUMBER_7_10("7", '\uE075'),
    NUMBER_1_10("1", '\uE076'),
    NUMBER_6_10("6", '\uE077'),
    NUMBER_5_10("5", '\uE078'),

    NUMBER_0_0("0", '\uE079'),
    NUMBER_9_0("9", '\uE07A'),
    NUMBER_8_0("8", '\uE07B'),
    NUMBER_7_0("7", '\uE07C'),
    NUMBER_4_0("4", '\uE07D'),
    NUMBER_3_0("3", '\uE07E'),
    NUMBER_2_0("2", '\uE07F'),
    NUMBER_1_0("1", '\uE080'),
    NUMBER_6_0("6", '\uE082'),
    NUMBER_5_0("5", '\uE083'),

    T0_14("0", '\uE0A0'),
    T1_14("1", '\uE0A1'),
    T2_14("2", '\uE0A2'),
    T3_14("3", '\uE0A3'),
    T4_14("4", '\uE0A4'),
    T5_14("5", '\uE0A5'),
    T6_14("6", '\uE0A6'),
    T7_14("7", '\uE0A7'),
    T8_14("8", '\uE0A8'),
    T9_14("9", '\uE0A9'),

    T0_17("0", '\uE0C7'),
    T1_17("1", '\uE0C8'),
    T2_17("2", '\uE0C9'),
    T3_17("3", '\uE0CA'),
    T4_17("4", '\uE0CB'),
    T5_17("5", '\uE0CC'),
    T6_17("6", '\uE0CD'),
    T7_17("7", '\uE0CE'),
    T8_17("8", '\uE0CF'),
    T9_17("9", '\uE0D0'),


    T0_20("0", '\uE0EE'),
    T1_20("1", '\uE0EF'),
    T2_20("2", '\uE0F0'),
    T3_20("3", '\uE0F1'),
    T4_20("4", '\uE0F2'),
    T5_20("5", '\uE0F3'),
    T6_20("6", '\uE0F4'),
    T7_20("7", '\uE0F5'),
    T8_20("8", '\uE0F6'),
    T9_20("9", '\uE0F7');

    private final char icon;
    private final String character;

    DigitIcons(String character, char icon) {
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

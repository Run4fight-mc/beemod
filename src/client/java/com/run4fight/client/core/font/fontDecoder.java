package com.run4fight.client.core.font;

import java.util.HashMap;
import java.util.Map;

public class fontDecoder {

    private static final Map<Character, String> ICON_MAP = buildIconMap();


    private static Map<Character, String> buildIconMap() {
        Map<Character, String> iconMap = new HashMap<>();

        for (SpaceIcons icon : SpaceIcons.values()) {
            iconMap.put(icon.getIcon(), icon.getCharacter());
        }

//        for (BuffIcons icon : BuffIcons.values()) {
//            iconMap.put(icon.getIcon(), icon.getBuffName());
//        }

        for (DigitIcons icon : DigitIcons.values()) {
            iconMap.put(icon.getIcon(), icon.getCharacter());
        }

        for (TextIcons icon : TextIcons.values()) {
            iconMap.put(icon.getIcon(), icon.getCharacter());
        }

        for (SymbolIcons icon : SymbolIcons.values()) {
            iconMap.put(icon.getIcon(), icon.getCharacter());
        }

        return iconMap;
    }

    public static String decode(String input) {
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            result.append(ICON_MAP.getOrDefault(c, String.valueOf(c)));
        }

        return result.toString();
    }
}

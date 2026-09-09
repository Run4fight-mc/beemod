package com.run4fight.client.core.parser;
import com.run4fight.client.core.font.BuffIcons;
import com.run4fight.client.model.BuffModel;
import com.run4fight.client.model.EffectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.run4fight.client.core.font.BuffIcons.BUFF_ICON_MAP;
import static com.run4fight.client.core.font.fontDecoder.decode;

public class ActionBarParser {
    public static List<BuffModel> getBuffs(String rawBuffs) {
        String decodedBuffs = decode(rawBuffs);
        String buffIconClass = fetchAllBuffIcons();

        Pattern buffPattern = Pattern.compile(
                "(" + buffIconClass + ")(.*?)" +
                        "(?=" + buffIconClass + "|$)"
        );

        Pattern headerPattern = Pattern.compile(
                "^-?(?<duration>\\d{2}:\\d{2})\\s*_\\s*x(?<stacks>\\d+(?:\\.\\d+)?)"
        );

        Pattern effectPattern = Pattern.compile(
                "(?<value>[+x]\\s*" +
                        "\\d(?:\\s*\\d)*" +
                        "(?:\\s*\\.\\s*\\d(?:\\s*\\d)*)?" +
                        "\\s*%?)" +
                        "\\s*" +
                        "(?<name>.*?)" +
                        "(?=[+x]\\s*" +
                        "\\d(?:\\s*\\d)*" +
                        "(?:\\s*\\.\\s*\\d(?:\\s*\\d)*)?" +
                        "\\s*%?|$)"
        );

        List<BuffModel> buffs = new ArrayList<>();

        Matcher matcher = buffPattern.matcher(decodedBuffs);

        while (matcher.find()) {
            char icon = matcher.group(1).charAt(0);
            String description = matcher.group(2).trim();

            Matcher headerMatcher = headerPattern.matcher(description);

            if (!headerMatcher.find()) {
                continue;
            }

            String name = BUFF_ICON_MAP.get(icon);
            String duration = headerMatcher.group("duration");
            double stacks = Double.parseDouble(headerMatcher.group("stacks"));

            List<EffectModel> effects = new ArrayList<>();

            String effectsText = description.substring(headerMatcher.end()).trim();

            Matcher effectMatcher = effectPattern.matcher(effectsText);

            while (effectMatcher.find()) {
                String value = effectMatcher.group("value")
                        .replaceAll("\\s+", "");

                String effectName = effectMatcher.group("name")
                        .trim()
                        .replaceAll(" {5,}", "\u0000")
                        .replaceAll(" ", "")
                        .replace("\u0000", " ");

                EffectModel effect = new EffectModel(
                        value,
                        effectName
                );

                effects.add(effect);
            }

            BuffModel buff = new BuffModel(
                    icon,
                    name,
                    duration,
                    stacks,
                    effects
            );

            buffs.add(buff);
        }
        return buffs;
    }



    private static String fetchAllBuffIcons() {
        StringBuilder result = new StringBuilder("[");

        for (BuffIcons icon : BuffIcons.values()) {
            result.append(icon.getIcon());
        }

        result.append("]");

        return result.toString();
    }
}


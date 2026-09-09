package com.run4fight.client.core.font;

import java.util.HashMap;
import java.util.Map;

public enum BuffIcons {
    BAMBOO_ICON("bamboo icon", '\uE10B'),
    BLUEEXTRACT("blue extract", '\uE015'),
    BLUEFLOWER("blue pollen", '\uE023'),
    BLUEFLOWER_ICON("blueflower field", '\uE107'),
    BLUEFLOWER_ICON2("blueflower field", '\uE118'),
    CACTUS_ICON("cactus field", '\uE111'),
    CLOVER_ICON("clover field", '\uE108'),
    COCONUT_ICON("coconut field", '\uE114'),
    COOKED_BEEF("cooked beef", '\uE01B'),
    COOKED_CHICKEN("cooked chicken", '\uE01A'),
    COOKED_PORKCHOP("cooked porkchop", '\uE01C'),
    CROWN("crown", '\uE01F'),
    DANDELION_ICON("dandelion field", '\uE104'),
    DANDELION_ICON2("dandelion field", '\uE115'),
    ENZYMES("enzymes", '\uE018'),
    FLAMEPOWER("flame power", '\uE025'),
    FOCUS("focus", '\uE01E'),
    GLUE("glue", '\uE016'),
    GUMMYMASK("gummy mask", '\uE013'),
    HIGHFIVE("highfive", '\uE02B'),
    JUMP("jump", '\uE027'),
    KINDLE("kindle", '\uE02E'),
    LANDINGZONE("landingzone", '\uE028'),
    MOUNTAIN_ICON("mountain field", '\uE112'),
    MUSHROOM_ICON("mushroom field", '\uE106'),
    MUSHROOM_ICON2("mushroom field", '\uE117'),
    OIL("oil", '\uE017'),
    PEPPER_ICON("pepper field", '\uE113'),
    PINEAPPLE_ICON("pineapple field", '\uE10C'),
    PINE_ICON("pine field", '\uE110'),
    PURPLEFLOWER("purple pollen", '\uE026'),
    PURPLEPOTION("purple potion", '\uE100'),
    REDEXTRACT("red extract", '\uE014'),
    REDFLOWER("red pollen", '\uE022'),
    ROASTDINNER("roast dinner", '\uE02F'),
    ROSE_ICON("rose field", '\uE10F'),
    SHAMROCK("shamrock", '\uE02A'),
    SPIDER_ICON("spider field", '\uE10A'),
    SPEED("speed", '\uE01D'),
    STACHE("stache", '\uE020'),
    STEVE("steve", '\uE02D'),
    STRAWBERRY_ICON("strawberry field", '\uE109'),
    STUMP_ICON("stump field", '\uE10D'),
    SUNFLOWER_ICON("sunflower field", '\uE105'),
    SUNFLOWER_ICON2("sunflower field", '\uE116'),
    WRATH("wrath", '\uE021'),
    TOXICZONE("toxic zone", '\uE029'),
    TOTEM("totem", '\uE019'),
    UPDATEORB("update orb", '\uE101'),
    WEALTHCLOCK("wealth clock", '\uE02C'),
    WHITEFLOWER("white flower", '\uE024');

    private final String buffName;
    private final char icon;

    public static final Map<Character, String> BUFF_ICON_MAP = fetchBuffIconMap();

    BuffIcons(String buffName, char icon) {
        this.icon = icon;
        this.buffName = buffName;
    }

    public char getIcon() {
        return icon;
    }

    public String getBuffName() {
        return buffName;
    }

    private static Map<Character, String> fetchBuffIconMap() {
        Map<Character, String> map = new HashMap<>();

        for (BuffIcons icon : BuffIcons.values()) {
            map.put(icon.getIcon(), icon.getBuffName());
        }
        return map;
    }

//    public static BuffIcons fromIcon(char icon) {
//        for (BuffIcons type : values()) {
//            if (type.icon() == icon) {
//                return type;
//            }
//        }
//
//        return null;
//    }
//
//    public static BuffIcons fromName(String name) {
//        for (BuffIcons type : values()) {
//            if (type.buffName.equalsIgnoreCase(name)) {
//                return type;
//            }
//        }
//
//        return null;
//    }
}

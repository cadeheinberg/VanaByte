package me.cade.vanabyte.Fighters.Enums;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum KitType {
    AIRBENDER("Airbender",
            0,
            new WeaponType[]{WeaponType.AIRBENDER_SWORD},
            ChatColor.WHITE,
            Color.fromRGB(255, 255, 255)),
    BESERKER("Beserker",
            1,
            new WeaponType[]{WeaponType.BESERKER_AXE},
            ChatColor.LIGHT_PURPLE,
            Color.fromRGB(150, 0, 255)),
    SCORCH("Scorch",
            2,
            new WeaponType[]{WeaponType.SHOTTY_SHOTGUN, WeaponType.THROWING_TNT},
            ChatColor.YELLOW,
            Color.fromRGB(255, 255, 0)),
    GOBLIN("Goblin",
            3,
            new WeaponType[]{WeaponType.GOBLIN_BOW, WeaponType.GOBLIN_SWORD, WeaponType.GOBLIN_ARROW},
            ChatColor.GREEN,
            Color.fromRGB(77, 255, 0)),
    IGOR("Igor",
            4,
            new WeaponType[]{WeaponType.IGORS_TRIDENT}, ChatColor.RED,
            Color.fromRGB(255, 15, 99)),
    SUMMO("Summo",
            5,
            new WeaponType[]{WeaponType.SUMO_STICK}, ChatColor.BLUE,
            Color.fromRGB(8, 111, 255)),
    GRIEF("Grief",
            6,
            new WeaponType[]{WeaponType.GRIEF_SWORD}, ChatColor.AQUA,
            Color.fromRGB(0, 0, 0)),
    UNKNOWN_KIT("unknown",
            -1,
            new WeaponType[]{WeaponType.UNKNOWN_WEAPON}, null,
            null);

    private final String name;
    private final int kitID;
    private final WeaponType[] weaponTypes;
    private final ChatColor textColor;
    private final Color armorColor;

    private KitType(String name, int kitID, WeaponType[] weaponTypes, ChatColor textColor, Color armorColor){
        this.name = name;
        this.kitID = kitID;
        this.weaponTypes = weaponTypes;
        this.textColor = textColor;
        this.armorColor = armorColor;
    }

    public String getKitNameUncolored() {
        return this.name;
    }

    public String getKitNameColored() {
        return this.name;
    }

    public int getKitID() {
        return kitID;
    }

    public ChatColor getTextColor() {
        return textColor;
    }

    public Color getArmorColor() {
        return armorColor;
    }

    public WeaponType[] getWeaponTypes() {
        return weaponTypes;
    }

    public static KitType getKitTypeFromKitID(int kitID){
        for (KitType kitType : KitType.values()){
            if(kitType.getKitID() == kitID){
                return kitType;
            }
        }
        return KitType.UNKNOWN_KIT;
    }
}

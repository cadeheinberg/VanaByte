package me.cade.vanabyte.Fighters.Enums;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;

public enum KitType {
    AIRBENDER("Airbender",
            "K000",
            new WeaponType[]{WeaponType.AIRBENDER_SWORD},
            new Location(VanaByte.hub, -1043.5, 195.3, -111.5, 135, 0),
            ChatColor.WHITE,
            Color.fromRGB(255, 255, 255)),
    BESERKER("Beserker",
            "K001",
            new WeaponType[]{WeaponType.BESERKER_AXE},
            new Location(VanaByte.hub, -1045.5, 195.3, -108.5, 135, 0),
            ChatColor.LIGHT_PURPLE,
            Color.fromRGB(150, 0, 255)),
    SCORCH("Scorch",
            "K002",
            new WeaponType[]{WeaponType.SHOTTY_SHOTGUN, WeaponType.THROWING_TNT},
            new Location(VanaByte.hub, -1048.5, 195.3, -106.5, 135, 0),
            ChatColor.YELLOW,
            Color.fromRGB(255, 255, 0)),
    GOBLIN("Goblin",
            "K003",
            new WeaponType[]{WeaponType.GOBLIN_BOW, WeaponType.GOBLIN_SWORD, WeaponType.GOBLIN_ARROW},
            new Location(VanaByte.hub, -1052.5, 195.3, -105.5, 180, 0),
            ChatColor.GREEN,
            Color.fromRGB(77, 255, 0)),
    IGOR("Igor",
            "K004",
            new WeaponType[]{WeaponType.IGORS_TRIDENT},
            new Location(VanaByte.hub, -1056.5, 195.3, -106.5, -135, 0),
            ChatColor.RED,
            Color.fromRGB(255, 15, 99)),
    SUMMO("Summo",
            "K005",
            new WeaponType[]{WeaponType.SUMO_STICK},
            new Location(VanaByte.hub, -1059.5, 195.3, -108.5, -135, 0),
            ChatColor.BLUE,
            Color.fromRGB(8, 111, 255)),
    GRIEF("Grief",
            "K006",
            new WeaponType[]{WeaponType.GRIEF_SWORD},
            new Location(VanaByte.hub, -1061.5, 195.3, -111.5, -135, 0),
            ChatColor.AQUA,
            Color.fromRGB(0, 0, 0));

    private final String name;
    private final String kitID;
    private final WeaponType[] weaponTypes;
    private final ChatColor textColor;
    private final Color armorColor;
    private final Location selectorLocation;

    private KitType(String name, String kitID, WeaponType[] weaponTypes, Location selectorLocation, ChatColor textColor, Color armorColor){
        if(kitID.length() != 4){
            throw new RuntimeException(name + "s Kit ID length is not 4");
        }
        this.name = name;
        this.kitID = kitID;
        this.weaponTypes = weaponTypes;
        this.selectorLocation = selectorLocation;
        this.textColor = textColor;
        this.armorColor = armorColor;
    }

    public String getKitNameUncolored() {
        return this.name;
    }

    public String getKitNameColored() {
        return this.name;
    }

    public String getKitID() {
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

    public static KitType getKitTypeFromKitID(String kitID){
        for (KitType kitType : KitType.values()){
            if(kitType.getKitID().equals(kitID)){
                return kitType;
            }
        }
        return null;
    }

    public Location getSelectorLocation() {
        return selectorLocation;
    }

}

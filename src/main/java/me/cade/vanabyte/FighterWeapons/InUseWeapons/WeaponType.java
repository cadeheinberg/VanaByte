package me.cade.vanabyte.FighterWeapons.InUseWeapons;


import org.bukkit.entity.EntityType;

public enum WeaponType {
    AIRBENDER_SWORD("airbender sword"),
    BESERKER_AXE("beserker axe"),
    SHOTTY_SHOTGUN("shotty shotgun"),
    GOBLIN_BOW("goblin bow"),
    GOBLIN_SWORD("goblin sword"),
    GOBLIN_ARROW("goblin arrow"),
    IGORS_TRIDENT("igors trident"),
    SUMO_STICK("sumo stick"),
    GRIEF_SWORD("grief sword"),
    PARACHUTE("parachute"),
    THROWING_TNT("throwing TNT"),
    UNKNOWN_WEAPON("unknown");

    private final String name;

    private WeaponType(String name){
        this.name = name;
    }


    public static WeaponType fromName(String name) {
        for (WeaponType type : WeaponType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return WeaponType.UNKNOWN_WEAPON;
    }

    private String getName() {
        return this.name;
    }

}

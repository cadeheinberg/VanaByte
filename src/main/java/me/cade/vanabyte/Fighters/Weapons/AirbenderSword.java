package me.cade.vanabyte.Fighters.Weapons;

import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.FighterKits.FighterKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class AirbenderSword {

    static final String weaponDrop = "Gust of Wind";
    static final String weaponRightClick = "Use Shield";
    static final ChatColor weaponNameColor = ChatColor.WHITE;
    static final String weaponName = weaponNameColor + "Airbender Sword";
    private Material material = null;
    private int durationTicks = -1;
    private int rechargeTicks = -1;
    private double specialDamage = -1;
    private double meleeDamage = -1;
    private double projectileDamage = -1;
    private int cooldownTicks = -1;
    private FighterKitManager fighterKitManager;
    public AirbenderSword(FighterKitManager fkit) {
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 7 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.durationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.rechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.cooldownTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
    }
    public AirbenderSword(){
        this.durationTicks = 200;
        this.rechargeTicks = 50;
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.cooldownTicks = 0;
        this.material = Material.IRON_SWORD;
    }

}

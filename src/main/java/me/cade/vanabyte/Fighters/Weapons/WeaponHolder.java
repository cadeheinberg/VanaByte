package me.cade.vanabyte.Fighters.Weapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.FighterKit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class WeaponHolder {
    static final String weaponDrop = null;
    static final String weaponRightClick = null;
    static final ChatColor weaponNameColor = null;
    static final String weaponName = null;
    private FighterKit fKit = null;
    private Player player = null;
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Weapon weapon = null;
    private WeaponAbility weaponAbility = null;
    public WeaponHolder(){}
    public WeaponHolder(Fighter fighter){
        this.fighterKitManager = fighter.getFighterKitManager();
        this.fKit = fighter.getFKit();
        this.player = fighter.getPlayer();
        this.weaponAbility = new WeaponAbility(fighter, this);
    }
    //Return False - when not primary weapon and when cooldown is on primary weapon
    //Return True  - when it is primary weapon and cooldown successfully set
    public boolean doRightClick() {
        if (player.getCooldown(this.getMaterial()) > 0) {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
            return false;
        }
        player.setCooldown(this.getMaterial(), this.getRightClickCooldownTicks());
        return true;
    }

    //Returns True if there is no cooldown and ability for either
    // the Fighter Kit or special item can be activated
    //
    //Returns False if there is a cooldown or not a Fighter Kits item
    public boolean doDrop() {
            if (this.player.getCooldown(FighterKitManager.cooldownMaterial) > 0
                    || this.player.getCooldown(FighterKitManager.cooldownMaterial) > 0) {
                this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
                return false;
            }
            return true;
    }

    public void activateSpecial() {
        this.getWeaponAbility().startAbilityDuration();
    }

    public void deActivateSpecial() {
        //pass
    }

    public int getRightClickCooldownTicks() { return rightClickCooldownTicks;}
    public Material getMaterial() {
        return material;
    }

    public String getWeaponDrop() {
        return weaponDrop;
    }

    public String getWeaponRightClick() {
        return weaponRightClick;
    }

    public String getWeaponName() {return weaponName;}

    public int getAbilityDurationTicks() {
        return abilityDurationTicks;
    }

    public int getAbilityRechargeTicks() {
        return abilityRechargeTicks;
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    public double getMeleeDamage() {
        return meleeDamage;
    }

    public double getSpecialDamage() {
        return specialDamage;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public WeaponAbility getWeaponAbility() {
        return weaponAbility;
    }

    public ChatColor getWeaponNameColor(){return weaponNameColor;}
}

package me.cade.vanabyte.Fighters.Weapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class W1_BeserkerAxe extends WeaponHolder {
    final String weaponDrop = "Speed/Jump/Dig Boost";
    final String weaponRightClick = "Super Leap";
    final ChatColor weaponNameColor = ChatColor.LIGHT_PURPLE;
    final String weaponName = weaponNameColor + "Beserker Axe";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W1_BeserkerAxe(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 7 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 80 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.IRON_AXE;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public W1_BeserkerAxe(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 80;
        this.material = Material.IRON_AXE;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }

    private void doBoosterJump() {
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 8, 1);
        Vector currentDirection = this.player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(1.7, 1.7, 1.7));
        this.player.setVelocity(currentDirection);
    }
    @Override
    public boolean doRightClick() {
        if (super.doRightClick()) {
            this.doBoosterJump();
            return true;
        }
        return false;
    }
    @Override
    public boolean doDrop() {
        if (!super.doDrop()){
            return false;
        }
        this.activateSpecial();
        return true;
    }
    @Override
    public void activateSpecial() {
        super.activateSpecial();
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, this.abilityDurationTicks, 0));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, this.abilityDurationTicks, 3));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, this.abilityDurationTicks, 0));
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    @Override
    public String getWeaponDrop() {
        return weaponDrop;
    }

    @Override
    public String getWeaponRightClick() {
        return weaponRightClick;
    }

    @Override
    public String getWeaponName() {return weaponName;}

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public int getRightClickCooldownTicks() {
        return rightClickCooldownTicks;
    }

    @Override
    public int getAbilityDurationTicks() {
        return abilityDurationTicks;
    }

    @Override
    public int getAbilityRechargeTicks() {
        return abilityRechargeTicks;
    }

    @Override
    public double getProjectileDamage() {
        return projectileDamage;
    }

    @Override
    public double getMeleeDamage() {
        return meleeDamage;
    }

    @Override
    public double getSpecialDamage() {
        return specialDamage;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public ChatColor getWeaponNameColor(){return weaponNameColor;}
}

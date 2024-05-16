package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.FighterProjectile;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;

public class W4_IgorsTrident extends WeaponHolder {
    final String weaponDrop = "Explosive Tridents";
    final String weaponRightClick = "Throw Trident";
    final ChatColor weaponNameColor = ChatColor.RED;
    final String weaponName = weaponNameColor + "Igors Trident";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W4_IgorsTrident(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 5 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.TRIDENT;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public W4_IgorsTrident(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 10;
        this.specialDamage = 10;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 5;
        this.material = Material.TRIDENT;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    @Override
    public boolean doRightClick() {
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
        this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    public boolean doThrowTrident(Trident trident) {
        if (player.getCooldown(this.getMaterial()) > 0) {
            return false;
        }
        if(this.getRightClickCooldownTicks() > 0){
            player.setCooldown(this.getMaterial(), this.getRightClickCooldownTicks());
        }
        if (super.getWeaponAbility().isAbilityActive()) {
            trident.setFireTicks(1000);
        }
        FighterProjectile.addMetadataToProjectile(trident);
        this.player.getInventory().remove(this.getWeapon().getWeaponItem());
        player.getInventory().addItem(this.getWeapon().getWeaponItem());
        trident.setShooter(player);
        return true;
    }

    //Returns the amount of damage to do to the player
    public double doTridentHitEntity(LivingEntity victim, Trident trident) {
        if (trident.getFireTicks() > 0) {
            Location local = victim.getLocation();
            local.setY(local.getY() - 0.5);
            CreateExplosion.doAnExplosion(this.player, local, 0.7, this.specialDamage, true);
            //explosion will deal special damage ^^
            return 0;
        }
        else{
            return this.getProjectileDamage();
        }
    }

    public void doTridentHitGround(Location location, Trident trident) {
        if (trident.getFireTicks() > 0) {
            CreateExplosion.doAnExplosion(this.player, location, 0.7, this.specialDamage, true);
        }
        trident.remove();
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

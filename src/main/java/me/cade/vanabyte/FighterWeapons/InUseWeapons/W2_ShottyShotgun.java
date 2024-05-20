package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.FighterProjectile;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class W2_ShottyShotgun extends WeaponHolder {

    static final String weaponDrop = "Flaming Bullets";
    static final String weaponRightClick = "Shoot Shotgun";
    static final ChatColor weaponNameColor = ChatColor.YELLOW;
    static final String weaponName = weaponNameColor + "Scorch Shotgun";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W2_ShottyShotgun(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 20 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.IRON_SHOVEL;
        this.weapon = new Weapon(WeaponType.SHOTTY_SHOTGUN, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public W2_ShottyShotgun(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 0;
        this.material = Material.IRON_SHOVEL;
        this.weapon = new Weapon(WeaponType.SHOTTY_SHOTGUN, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }

    @Override
    public boolean doRightClick() {
        if (!super.doRightClick()) {
            return false;
        }
        this.shootSnowballs();
        this.doShotgunRecoil(-0.42);
        return true;
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
        this.player.setCooldown(this.getMaterial(), 0);
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    public double doSnowballHitEntity(LivingEntity victim, Snowball snowball) {
        if (snowball.getFireTicks() > 0) {
            victim.setFireTicks(50);
            return this.getSpecialDamage();
        }
        return this.getProjectileDamage();
    }

    public void doSnowballHitGround(Location location, Snowball snowball) {
        if (snowball.getFireTicks() > 0) {

        }
    }
    private void doShotgunRecoil(Double power) {
        Vector currentDirection = this.player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        this.player.setVelocity(currentDirection);
    }

    public void shootSnowballs() {
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);
        ArrayList<Snowball> snowBalls = new ArrayList<Snowball>();
        for (int i = 0; i < 6; i++){
            snowBalls.add(this.player.launchProjectile(Snowball.class));
            Random random = new Random();
            snowBalls.get(i).setVelocity(snowBalls.get(i).getVelocity().add(new Vector(random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25))));
            FighterProjectile.addMetadataToProjectile(snowBalls.get(i));
            snowBalls.get(i).setShooter(this.player);
            if (super.getWeaponAbility().isAbilityActive()) {
                snowBalls.get(i).setFireTicks(1000);
            }
        }
    }

    @Override
    public int getRightClickCooldownTicks() {
        return rightClickCooldownTicks;
    }

    @Override
    public Material getMaterial() {
        return material;
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
    public String getWeaponName() {
        return weaponName;
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

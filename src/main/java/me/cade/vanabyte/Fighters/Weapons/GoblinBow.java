package me.cade.vanabyte.Fighters.Weapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.FighterProjectile;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class GoblinBow extends WeaponHolder{

    final String weaponDrop = "Barrage of Poison";
    final String weaponRightClick = "Shoot Bow";
    final ChatColor weaponNameColor = ChatColor.GREEN;
    final String weaponName = weaponNameColor + "Goblin Bow";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public GoblinBow(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 7 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.BOW;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
        this.weapon.applyWeaponEnchantment(Enchantment.ARROW_INFINITE, 1);
    }
    public GoblinBow(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 0;
        this.material = Material.BOW;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
        this.weapon.applyWeaponEnchantment(Enchantment.ARROW_INFINITE, 1);
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

    public double doArrowHitEntity(LivingEntity victim, Arrow arrow) {
        // create your own form of knockback
        if (victim instanceof Player) {
            Fighter.get(player).fighterDismountParachute();
        }
        if (arrow.getFireTicks() > 0) {
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 2));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 2));
            return this.getSpecialDamage();
        }
        return projectileDamage;
    }

    public boolean doArrowShoot(Arrow arrow, double force) {
        if (this.player.getCooldown(this.getMaterial()) > 0) {
            return false;
        }
        this.player.setCooldown(this.getMaterial(), this.getRightClickCooldownTicks());
        if (super.getWeaponAbility().isAbilityActive()) {
            arrow.setFireTicks(1000);
            doArrowBarrage(this.player, arrow, force);
        }
        return true;
    }

    public static void doArrowBarrage(Player player, Arrow arrow, double force) {
        if (force > 0.75) {
            Arrow arrow1 = player.launchProjectile(Arrow.class);
            arrow1.setVelocity(arrow.getVelocity().add(new Vector(0, 0.25, 0)));
            arrow1.setFireTicks(2000);
            arrow1.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow1);

            Arrow arrow2 = player.launchProjectile(Arrow.class);
            arrow2.setVelocity(arrow.getVelocity().add(new Vector(0, -0.25, 0)));
            arrow2.setFireTicks(2000);
            arrow2.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow2);

            Arrow arrow3 = player.launchProjectile(Arrow.class);
            arrow3.setVelocity(arrow.getVelocity().add(new Vector(0.25, 0, 0)));
            arrow3.setFireTicks(2000);
            arrow3.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow3);

            Arrow arrow4 = player.launchProjectile(Arrow.class);
            arrow4.setVelocity(arrow.getVelocity().add(new Vector(-0.25, 0, 0)));
            arrow4.setFireTicks(2000);
            arrow4.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow4);

            Arrow arrow5 = player.launchProjectile(Arrow.class);
            arrow5.setVelocity(arrow.getVelocity().add(new Vector(0, 0, 0.25)));
            arrow5.setFireTicks(2000);
            arrow5.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow5);

            Arrow arrow6 = player.launchProjectile(Arrow.class);
            arrow6.setVelocity(arrow.getVelocity().add(new Vector(0, 0, -0.25)));
            arrow6.setFireTicks(2000);
            arrow6.setShooter(player);
            FighterProjectile.addMetadataToProjectile(arrow6);
            return;
        }
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

package me.cade.vanabyte.Fighters.Weapons;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class GriefSword extends WeaponHolder {

    final String weaponDrop = "Invisibility & Health Steal";
    final String weaponRightClick = "Use Shield";
    final ChatColor weaponNameColor = ChatColor.AQUA;
    final String weaponName = weaponNameColor + "Grief Sword";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public GriefSword(Fighter fighter) {
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
        this.material = Material.NETHERITE_SWORD;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public GriefSword(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 0;
        this.material = Material.NETHERITE_SWORD;
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
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, this.abilityDurationTicks, 0));
        this.makeInvisible(this.player);
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
        VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.HEART, ParticleStyle.fromName("swords"));
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
        this.makeVisible(player);
        VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleEffect.HEART);
    }
    public void doStealHealth() {
        if (super.getWeaponAbility().isAbilityActive()) {
            double combined = this.player.getHealth() + 1.5;
            if (combined > 20) {
                this.player.setHealth(20);
            } else {
                this.player.setHealth(combined);
            }
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
        }
    }
    public static void makeInvisible(Player player) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.FEET, new ItemStack(Material.AIR));
        }
    }

    public static void makeVisible(Player player) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, player.getEquipment().getHelmet());
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, player.getEquipment().getChestplate());
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, player.getEquipment().getLeggings());
            p.sendEquipmentChange(player, EquipmentSlot.FEET,  player.getEquipment().getBoots());
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

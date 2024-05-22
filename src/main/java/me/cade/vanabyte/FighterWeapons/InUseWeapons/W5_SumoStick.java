package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class W5_SumoStick extends WeaponHolder {

    final String weaponDrop = "Ground Pound";
    final String weaponRightClick = "Toss Enemy";
    final ChatColor weaponNameColor = ChatColor.BLUE;
    final String weaponName = weaponNameColor + "Sumo Stick";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W5_SumoStick(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 15 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.STICK;
        this.weapon = new Weapon(WeaponType.SUMO_STICK, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
        this.weapon.applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
    }
    public W5_SumoStick(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 0;
        this.material = Material.STICK;
        this.weapon = new Weapon(WeaponType.SUMO_STICK, this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
        this.weapon.applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
    }

    @Override
    public boolean doRightClick() {
        return true;
    }
    @Override
    public boolean doDrop() {
        if (!super.doDrop()){
            return false;
        }
        if(fighter.getFighterTaskManager().getGroundPoundTask() != 0){
            return false;
        }
        this.activateSpecial();
        return true;
    }
    @Override
    public void activateSpecial() {
        super.activateSpecial();
        doJump(this.player, 1.4, Fighter.get(this.player));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, this.abilityDurationTicks, 0));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, this.abilityDurationTicks, 1));
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
        stopListening(fighter);
    }

    public void doJump(Player player, Double power, Fighter pFight) {
        Location local = player.getLocation();
        local.setPitch(-60);
        Vector currentDirection = local.getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        player.setVelocity(currentDirection);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
            @Override
            public void run() {
                listenForFall(player, pFight);
            }
        }, 5);
    }

    public void listenForFall(Player player, Fighter fighter) {
        fighter.getFighterTaskManager().setGroundPoundTask(new BukkitRunnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                if (player == null) {
                    stopListening(fighter);
                    return;
                }
                if (!player.isOnline()) {
                    stopListening(fighter);
                    return;
                }
                if (player.isDead()) {
                    stopListening(fighter);
                    return;
                }
                if (player.isOnGround()) {
                    stopListening(fighter);
                    doGroundHit(player, player.getLocation(), 0.3);
                    return;
                }
                if (player.isSneaking()) {
                    launchPlayerDown(player, 1.5, fighter);
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
    }

    public static void launchPlayerDown(Player player, Double power, Fighter pFight) {
        Location local = player.getLocation();
        local.setPitch(80);
        Vector currentDirection = local.getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        player.setVelocity(currentDirection);
    }

    public static void stopListening(Fighter fighter) {
        Bukkit.getScheduler().cancelTask(fighter.getFighterTaskManager().getGroundPoundTask());
        fighter.getFighterTaskManager().setGroundPoundTask(0);
    }

    // make this freeze players also
    public void doGroundHit(Player shooter, Location location, double power) {
        CreateExplosion.doAnExplosion(shooter, location, 0.7, this.getSpecialDamage(), true, weapon.getWeaponTypeFromItemStack());
    }

    public void doPickUp(LivingEntity rightClicked) {
        if (player.getPassengers() == null) {
            return;
        }
        if (player.getPassengers().size() >= 1) {
            return;
        }
        if (rightClicked instanceof Player) {
            if (((Player) rightClicked).isSneaking()) {
                return;
            }
        }
        if (player.getCooldown(this.getMaterial()) > 0) {
            return;
        }
        player.setCooldown(this.getMaterial(), this.getRightClickCooldownTicks());
        player.addPassenger(rightClicked);
    }

    public void doThrow(Player killer, LivingEntity victim) {
        killer.eject();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location playerLocation = killer.getLocation();
                if (playerLocation.getPitch() < -60) {
                    playerLocation.setPitch((float) -60.0);
                }
                Vector currentDirection = playerLocation.getDirection().normalize();
                currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
                victim.setVelocity(currentDirection);
            }
        }, 2);
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

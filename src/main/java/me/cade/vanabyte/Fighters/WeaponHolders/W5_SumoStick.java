package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class W5_SumoStick extends WeaponHolder {

    public W5_SumoStick(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
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
        CreateExplosion.doAnExplosion(shooter, location, 0.7, this.getSpecialDamage(), true, this.weaponType);
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
}

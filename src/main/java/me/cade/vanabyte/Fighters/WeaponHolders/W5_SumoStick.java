package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.PVP.CreateExplosion;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class W5_SumoStick extends WeaponHolder {

    private final Player player;

    public W5_SumoStick(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        player = fighter.getPlayer();
    }

    @Override
    public boolean doLeftClick(PlayerInteractEvent e){
        if(super.doLeftClick(e)){
            if (e.getPlayer().getPassengers() != null && e.getPlayer().getPassengers().size() > 1) {
                this.doThrow(e.getPlayer(), (LivingEntity) e.getPlayer().getPassengers().get(0));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doRightClick(PlayerInteractEvent e) {
        // super checks if there is a cooldown on material,
        if(super.doRightClick(e)){
            //do specific stuff here, apply cooldown?
            return true;
        }
        return false;
    }

    @Override
    public boolean doRightClickEntity(PlayerInteractEntityEvent e) {
        // super checks if there is a cooldown on material
        if(super.doRightClickEntity(e)){
            //do specific stuff here
            if(e.getRightClicked() instanceof LivingEntity){
                if (player.getPassengers() == null) {
                    return false;
                }
                if (player.getPassengers().size() >= 1) {
                    return false;
                }
                if (e.getRightClicked() instanceof Player) {
                    if (((Player) e.getRightClicked()).isSneaking()) {
                        return false;
                    }
                }
                this.doPickUp((LivingEntity) e.getRightClicked());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doDrop(PlayerDropItemEvent e) {
        if (super.doDrop(e)){
            if(Fighter.get(player).getFighterTaskManager().getGroundPoundTask() != 0){
                return false;
            }
            this.activateSpecial();
            return true;
        }
        return false;
    }

    @Override
    public boolean doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        if(super.doMeleeAttack(e, killer, victim)){
            if (killer.getPassengers() == null) {
                return false;
            }
            if (killer.getPassengers().size() < 1) {
                return false;
            }
            if (!(killer.getPassengers().get(0).equals(victim))) {
                return false;
            }
            this.doThrow((Player) e.getDamager(), (LivingEntity) e.getEntity());
            return true;
        }
        return false;
    }

    @Override
    public boolean activateSpecial() {
        if(super.activateSpecial()){
            doJump(player, 1.4, Fighter.get(this.player));
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, super.getAbilityDurationTicks(), 0));
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, super.getAbilityDurationTicks(), 1));
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean deActivateSpecial() {
        if(super.deActivateSpecial()){
            this.stopListening(player);
            return true;
        }
        return false;
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
                    stopListening(player);
                    return;
                }
                if (!player.isOnline()) {
                    stopListening(player);
                    return;
                }
                if (player.isDead()) {
                    stopListening(player);
                    return;
                }
                if (player.isOnGround()) {
                    stopListening(player);
                    doGroundHit(player, player.getLocation(), 0.3);
                    return;
                }
                if (player.isSneaking()) {
                    launchPlayerDown(player, 1.5, fighter);
                }
            }
        }.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
    }

    public void launchPlayerDown(Player player, Double power, Fighter pFight) {
        Location local = player.getLocation();
        local.setPitch(80);
        Vector currentDirection = local.getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        player.setVelocity(currentDirection);
    }

    public void stopListening(Player player) {
        Bukkit.getScheduler().cancelTask(Fighter.get(player).getFighterTaskManager().getGroundPoundTask());
        Fighter.get(player).getFighterTaskManager().setGroundPoundTask(0);
    }

    // make this freeze players also
    public void doGroundHit(Player shooter, Location location, double power) {
        CreateExplosion.doAnExplosion(shooter, location, 0.7, this.getSpecialDamage(), true, super.getWeaponType());
    }

    public void doPickUp(LivingEntity rightClicked) {
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

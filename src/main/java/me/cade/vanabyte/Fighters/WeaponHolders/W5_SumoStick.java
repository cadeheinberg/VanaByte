package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class W5_SumoStick extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.SUMO_STICK;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);
    private final double knockBackLevel = fighter.getDoubleFromWeaponType(weaponType, 3);

    private final int basePickUpCooldown = fighter.getTickFromWeaponType(weaponType, 4);
    private final int abilityOnPickUpCooldown = fighter.getTickFromWeaponType(weaponType, 5);

    private final double baseThrowPower = fighter.getDoubleFromWeaponType(weaponType, 6);
    private final double abilityOnThrowPower = fighter.getDoubleFromWeaponType(weaponType, 7);

    private final double sumoSlamExplosionDamage = fighter.getDoubleFromWeaponType(weaponType, 8);
    private final double sumoSlamExplosionPower = fighter.getDoubleFromWeaponType(weaponType, 9);
    private final double sumoJumpPower = fighter.getDoubleFromWeaponType(weaponType, 10);

    public W5_SumoStick(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                basePickUpCooldown,
                abilityDuration,
                abilityRecharge);
        if((int) knockBackLevel >= 0){
            weapon.applyWeaponUnsafeEnchantment(Enchantment.KNOCKBACK, (int) knockBackLevel);
        }
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        if (player.getPassengers().isEmpty()) {
            return;
        }
        if (player.getPassengers().get(0).equals(victim)) {
            this.doThrow(victim);
            return;
        }
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doLeftClick(PlayerInteractEvent e){
        if (player.getPassengers().isEmpty()) {
            return;
        }
        this.doThrow((LivingEntity) e.getPlayer().getPassengers().get(0));
    }

    @Override
    public void doRightClickEntity(PlayerInteractEntityEvent e) {
        if (!super.checkAndSetMainCooldown(basePickUpCooldown, abilityOnPickUpCooldown)) {
            return;
        }
        VanaByte.sendConsoleMessageBad("pickup", "0");
        if (e.getRightClicked() instanceof LivingEntity) {
            VanaByte.sendConsoleMessageBad("pickup", "1");
            //if you have passengers you cant add more
            if (!player.getPassengers().isEmpty()) {
                return;
            }
            VanaByte.sendConsoleMessageBad("pickup", "2");
            if (e.getRightClicked() instanceof Player) {
                if (((Player) e.getRightClicked()).isSneaking()) {
                    return;
                }
            }
            VanaByte.sendConsoleMessageBad("pickup", "3");
            this.doPickUp((LivingEntity) e.getRightClicked());
        }
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        if(Fighter.get(player).getFighterTaskManager().getGroundPoundTask() != 0){
            return;
        }
        this.doJump();
//        this.player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, abilityDuration, 0));
//        this.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, abilityDuration, 1));
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
    }

    public void doJump() {
        Location local = player.getLocation();
        local.setPitch(-60);
        Vector currentDirection = local.getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(sumoJumpPower, sumoJumpPower, sumoJumpPower));
        player.setVelocity(currentDirection);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
            @Override
            public void run() {
                listenForFall(player, fighter);
            }
        }, 2);
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
                    doGroundHit(player.getLocation());
                    return;
                }
                if (player.isSneaking()) {
                    launchPlayerDown(player, 5.0, fighter);
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
    public void doGroundHit(Location location) {
        createAnExplosion(location, sumoSlamExplosionDamage, sumoSlamExplosionPower);
    }

    public void doPickUp(LivingEntity rightClicked) {
        player.addPassenger(rightClicked);
    }

    public void doThrow(LivingEntity victim) {
        player.eject();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(VanaByte.getInstance(), new Runnable() {
            @Override
            public void run() {
                Location playerLocation = player.getLocation();
                if (playerLocation.getPitch() < -60) {
                    playerLocation.setPitch((float) -60.0);
                }
                Vector currentDirection = playerLocation.getDirection().normalize();
                if(abilityOnThrowPower >= 0 && weaponAbility.isAbilityActive()){
                    currentDirection = currentDirection.multiply(new Vector(abilityOnThrowPower, abilityOnThrowPower, abilityOnThrowPower));
                }else{
                    currentDirection = currentDirection.multiply(new Vector(baseThrowPower, baseThrowPower, baseThrowPower));
                }
                victim.setVelocity(currentDirection);
            }
        }, 2);
    }
}

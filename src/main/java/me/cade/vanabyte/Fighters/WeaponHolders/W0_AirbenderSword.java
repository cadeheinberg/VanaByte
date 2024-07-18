package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class W0_AirbenderSword extends WeaponHolder {

    private final Player player;

    public W0_AirbenderSword(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        this.player = fighter.getPlayer();
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim);
    }

    @Override
    public void doRightClick(PlayerInteractEvent e) {
        if(!super.checkAndSetMainCooldown()){
            return;
        }
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown()){
            return;
        }
        this.gustOfWindSpell();
        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, statBundle.abilityDuration, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, statBundle.abilityDuration, 1));
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
    }

    public void gustOfWindSpell() {
        Location playerLocation = super.player.getLocation();
        if (playerLocation.getPitch() > 49) {
            launchPlayer(super.player, statBundle.getBasePower2());
            return;
        }
        Location origin = super.player.getEyeLocation();
        Vector direction = super.player.getLocation().getDirection();
        double dX = direction.getX();
        double dY = direction.getY();
        double dZ = direction.getZ();
        playerLocation.setPitch((float) -30.0);
        double power = statBundle.getBasePower1();
        int range = 3;
        ArrayList<Integer> hitList = new ArrayList<Integer>();
        for (int j = 2; j < range; j++) {
            origin = origin.add(dX * j, dY * j, dZ * j);
            super.player.spawnParticle(Particle.DUST, origin, 100, 0.5, 0.75, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
            ArrayList<Entity> entityList = (ArrayList<Entity>) super.player.getWorld().getNearbyEntities(origin, 2.5, 2.5,
                    2.5);
            for (Entity entity : entityList) {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "1 Living Entity Found");
                if (!(entity instanceof LivingEntity)) {
                    continue;
                } else if (hitList.contains(((LivingEntity) entity).getEntityId())) {
                    continue;
                } else if (super.player.getName().equals(((LivingEntity) entity).getName())) {
                    continue;
                }
                if(entity instanceof Player) {
                    if(((Player) entity).getGameMode() == GameMode.CREATIVE) {
                        return;
                    }
                }
                super.trackWeaponDamage((LivingEntity) entity);
                ((LivingEntity) entity).damage(statBundle.getBaseDamage1());
                Vector currentDirection = playerLocation.getDirection().normalize();
                currentDirection = currentDirection.multiply(new Vector(power, power, power));
                entity.setVelocity(currentDirection);
                hitList.add(((LivingEntity) entity).getEntityId());
            }
            origin = origin.subtract(dX * j, dY * j, dZ * j);
        }
    }

    private static void launchPlayer(Player player, Double power) {
        player.spawnParticle(Particle.DUST, player.getLocation(), 100, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        player.setVelocity(currentDirection);
    }

}

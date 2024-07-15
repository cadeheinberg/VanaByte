package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Damaging.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class W0_AirbenderSword extends WeaponHolder {

    public W0_AirbenderSword(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
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
        this.gustOfWindSpell();
        super.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, super.getAbilityDurationTicks(), 1));
        super.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, super.getAbilityDurationTicks(), 1));
        super.getPlayer().playSound(super.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
    }

    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }

    public void gustOfWindSpell() {
        Location playerLocation = super.getPlayer().getLocation();
        if (playerLocation.getPitch() > 49) {
            launchPlayer(super.getPlayer(), -1.5);
            return;
        }
        Location origin = super.getPlayer().getEyeLocation();
        Vector direction = super.getPlayer().getLocation().getDirection();
        double dX = direction.getX();
        double dY = direction.getY();
        double dZ = direction.getZ();
        playerLocation.setPitch((float) -30.0);
        int range = 13;
        double power = 2.8;
        ArrayList<Integer> hitList = new ArrayList<Integer>();
        for (int j = 2; j < range; j++) {
            origin = origin.add(dX * j, dY * j, dZ * j);
            super.getPlayer().spawnParticle(Particle.DUST, origin, 100, 0.5, 0.75, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
            ArrayList<Entity> entityList = (ArrayList<Entity>) super.getPlayer().getWorld().getNearbyEntities(origin, 2.5, 2.5,
                    2.5);
            for (Entity entity : entityList) {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "1 Living Entity Found");
                if (!(entity instanceof LivingEntity)) {
                    continue;
                } else if (hitList.contains(((LivingEntity) entity).getEntityId())) {
                    continue;
                } else if (super.getPlayer().getName().equals(((LivingEntity) entity).getName())) {
                    continue;
                }
                if(entity instanceof Player) {
                    if(((Player) entity).getGameMode() == GameMode.CREATIVE) {
                        return;
                    }
                }
                VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent(super.getPlayer(), entity, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, DamageSource.builder(DamageType.EXPLOSION).build(), super.getSpecialDamage()), super.getWeaponType()));
                ((LivingEntity) entity).damage(super.getSpecialDamage());
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

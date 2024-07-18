package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class W0_AirbenderSword extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.AIRBENDER_SWORD;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final double gustEnemyPower = fighter.getDoubleFromWeaponType(weaponType, 3);
    private final double gustEnemyDamage = fighter.getDoubleFromWeaponType(weaponType, 4);
    private final double gustSelfPower = fighter.getDoubleFromWeaponType(weaponType, 5);

    public W0_AirbenderSword(Fighter fighter) {
        super(WEAPON_TYPE);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                -1,
                abilityDuration,
                abilityDuration);
        super.player = fighter.getPlayer();
        super.weaponAbility = new WeaponAbility(fighter, this);
        super.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        this.gustOfWindSpell();
//        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, abilityDuration, 1));
//        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, abilityRecharge, 1));
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
    }

    private void gustOfWindSpell() {
        Location playerLocation = super.player.getLocation();
        if (gustSelfPower >= 0 && playerLocation.getPitch() > 49) {
            player.spawnParticle(Particle.DUST, player.getLocation(), 100, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
            Vector currentDirection = player.getLocation().getDirection().normalize();
            currentDirection = currentDirection.multiply(new Vector(gustSelfPower, gustSelfPower, gustSelfPower));
            player.setVelocity(currentDirection);
            return;
        }
        Location origin = super.player.getEyeLocation();
        Vector direction = super.player.getLocation().getDirection();
        double dX = direction.getX();
        double dY = direction.getY();
        double dZ = direction.getZ();
        playerLocation.setPitch((float) -30.0);
        double power = gustEnemyPower;
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
                super.trackWeaponDamage((LivingEntity) entity, gustEnemyDamage);
                ((LivingEntity) entity).damage(gustEnemyDamage);
                Vector currentDirection = playerLocation.getDirection().normalize();
                currentDirection = currentDirection.multiply(new Vector(power, power, power));
                entity.setVelocity(currentDirection);
                hitList.add(((LivingEntity) entity).getEntityId());
            }
            origin = origin.subtract(dX * j, dY * j, dZ * j);
        }
    }

}

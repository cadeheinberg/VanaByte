package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Permissions.SafeZone;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CreateExplosion {

	public static void doAnExplosion(Player shooter, Location location, double power, double damage,
			boolean confusion) {
		if(!SafeZone.inHub(location.getWorld())){
			location.getWorld().createExplosion(location, 6F, false, true, shooter);
		}
		location.getWorld().spawnParticle(Particle.EXPLOSION, location.getX(), location.getY() + 2,
				location.getZ(), 2);
		location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
		for (Entity ent : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
			if (!(ent instanceof LivingEntity)) {
				continue;
			}
			Location upShoot = ent.getLocation();
			if (ent.isOnGround()) {
				upShoot.setY(upShoot.getY() + 1);
			}
			if(ent instanceof Player) {
				if(((Player) ent).getGameMode() == GameMode.CREATIVE) {
					return;
				}
			}
			Vector currentDirection = upShoot.toVector().subtract(location.toVector());
			currentDirection = currentDirection.multiply(new Vector(power, power, power));
			ent.setVelocity(currentDirection);
			if (((LivingEntity) ent) != shooter) {
				((LivingEntity) ent).damage(damage, shooter);
				if (confusion) {
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 120, 2));
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 120, 2));
				}
			}
		}
	}
//	EntityDamageByEntityEvent damage =
//			new EntityDamageByEntityEvent(killer, victim, EntityDamageEvent.DamageCause.ENTITY_ATTACK, amount);
//					Bukkit.getServer().getPluginManager().callEvent(damage);
}

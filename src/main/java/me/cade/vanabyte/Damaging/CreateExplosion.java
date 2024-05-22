package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Damaging.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import me.cade.vanabyte.NPCS.RealEntities.RealLivingEntity;
import me.cade.vanabyte.Permissions.SafeZone;
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

public class CreateExplosion {

	public static void doAnExplosion(Player shooter, Location location, double power, double damage, boolean confusion, WeaponType weaponType) {
		if(!SafeZone.inHub(location.getWorld())){
			location.getWorld().createExplosion(location, 4F, false, true, shooter);
//			just create your own explosion to break blocks cause spigots is bad
		}
		location.getWorld().spawnParticle(Particle.EXPLOSION, location.getX(), location.getY() + 2,
				location.getZ(), 2);
		location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
		for (Entity ent : location.getWorld().getNearbyEntities(location, 4, 4, 4)) {
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
				VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent(shooter, ent, EntityDamageEvent.DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.EXPLOSION).build(), damage), weaponType));
				((LivingEntity) ent).damage(damage);
				if (confusion) {
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 120, 2));
					((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 120, 2));
				}
			}
		}
	}
}

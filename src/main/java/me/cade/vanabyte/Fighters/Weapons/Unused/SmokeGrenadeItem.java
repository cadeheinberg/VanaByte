package me.cade.vanabyte.Fighters.Weapons.Unused;

import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SmokeGrenadeItem extends WeaponHolder {
	
	public static void doSmokeGrenade(Player player) {
		Location location = player.getLocation();
		location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location.getX(), location.getY() + 2,
			      location.getZ(), 10, 5.0, 5.0, 5.0, 0.3);
	}

}

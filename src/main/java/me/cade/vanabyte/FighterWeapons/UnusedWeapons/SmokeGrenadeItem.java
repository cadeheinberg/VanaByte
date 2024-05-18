package me.cade.vanabyte.FighterWeapons.UnusedWeapons;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponHolder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class SmokeGrenadeItem extends WeaponHolder {
	
	public static void doSmokeGrenade(Player player) {
		Location location = player.getLocation();
		location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location.getX(), location.getY() + 2,
			      location.getZ(), 10, 5.0, 5.0, 5.0, 0.3);
	}

}

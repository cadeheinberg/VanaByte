package me.cade.vanabyte.SpecialItems;

import me.cade.vanabyte.Damaging.CreateExplosion;
import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.SafeZone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class SpecialItemsListener implements Listener {

	@EventHandler
	public void onDismount(EntityDismountEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDismounted().getType() == EntityType.CHICKEN) {
				Fighter.get((Player) e.getEntity()).fighterDismountParachute();
			}
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		if (e.getEntity() instanceof TNTPrimed) {
			// If its a creeper or something natural ignore it
			return;
		}
		if(!e.getEntity().hasMetadata("thrower")){
			// If its a normal TNT and not Fighter produced ignore it
			return;
		}
		e.setCancelled(true);
		for (Block b : e.blockList()) {
			if (b.getType() == Material.PACKED_ICE) {
				b.breakNaturally();
			}
		}
		e.blockList().clear();
	}
	
	@EventHandler
	public void onExplodePrime(ExplosionPrimeEvent e) {
		if(!(e.getEntity() instanceof TNTPrimed)){
			return;
		}
		if(!e.getEntity().hasMetadata("thrower")){
			// If its a normal TNT and not Fighter produced ignore it
			return;
		}
		e.setCancelled(true);
		// Create the explosion
		Player killer = (Player) Bukkit.getPlayer(e.getEntity().getMetadata("thrower").get(0).asString());
		if (killer != null) {
			CreateExplosion.doAnExplosion(killer, e.getEntity().getLocation(), 1.6, ThrowingTNTItem.getDamage(), false);;
		}
	}

}

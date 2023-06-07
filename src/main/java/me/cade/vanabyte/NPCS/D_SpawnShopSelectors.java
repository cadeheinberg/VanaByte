package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class D_SpawnShopSelectors {
	
	private static Location[] locations = {
			new Location(VanaByte.hub, -1073.5, 193.5, -110.5, -120, 0),
			new Location(VanaByte.hub, -1077.5, 193.5, -113.5, -100, 0),
			new Location(VanaByte.hub, -1077.5, 193.5, -119.5, -80, 0),
			new Location(VanaByte.hub, -1073.5, 193.5, -122.5, -60, 0)
	};
	
	private static String[] names = {
			ChatColor.AQUA + "" + ChatColor.BOLD + "Global Modifiers",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Killstreaks",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Perks",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Special Items"
	};
	
	private static EntityType[] types = {
			EntityType.BLAZE,
			EntityType.WITHER_SKELETON,
			EntityType.IRON_GOLEM,
			EntityType.VILLAGER
	};
	
	private static D1_LivingEntity[] selectors = new D1_LivingEntity[4];
	
	public static void spawn() {
		for(int i = 0; i<4;i++) {
			selectors[i] = new D1_LivingEntity(types[i], 
					names[i], locations[i], null, null, null);
		}
	    
	}
	

}

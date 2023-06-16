package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class D_SpawnShopSelectors {
	
	private static Location[] locations = {
			new Location(VanaByte.hub, -1063.5, 195, -118.5, -45, 0),
			new Location(VanaByte.hub, -1061.5, 195, -120.5, -45, 0),
			new Location(VanaByte.hub, -1043.5, 195, -120.5, 45, 0),
			new Location(VanaByte.hub, -1041.5, 195, -118.5, 45, 0),
	};
	
	private static String[] names = {
			ChatColor.AQUA + "" + ChatColor.BOLD + "Kit Upgrades",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Enhancement Perks",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Custom Crafting",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Starting Packs",
	};
	
	private static EntityType[] types = {
			EntityType.SNOWMAN,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
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

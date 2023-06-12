package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class D_SpawnShopSelectors {
	
	private static Location[] locations = {
			new Location(VanaByte.hub, -1063.5, 195, -120.5, 0, 0),
			new Location(VanaByte.hub, -1041.5, 195, -120.5, 0, 0),
	};
	
	private static String[] names = {
			ChatColor.AQUA + "" + ChatColor.BOLD + "Shop & Upgrades",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Custom Crafting",
	};
	
	private static EntityType[] types = {
			EntityType.SNOWMAN,
			EntityType.VILLAGER
	};
	
	private static D1_LivingEntity[] selectors = new D1_LivingEntity[2];
	
	public static void spawn() {
		for(int i = 0; i<2;i++) {
			selectors[i] = new D1_LivingEntity(types[i], 
					names[i], locations[i], null, null, null);
		}
	    
	}
	

}

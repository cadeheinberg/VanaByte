package me.cade.vanabyte.NPCS.RealEntities;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class RealLivingEntity {

	private static int spawnAmount = 2;

	private static String[] names = {
			ChatColor.GOLD + "" + ChatColor.BOLD + "Join Anarchy",
			ChatColor.GOLD + "" + ChatColor.BOLD + "Que for Vana's Royale",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Kit Upgrades",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Enhancement Perks",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Custom Crafting",
			ChatColor.AQUA + "" + ChatColor.BOLD + "Starting Packs",
	};
    private static Location[] locations = {
            new Location(VanaByte.hub, -1042.5, 195.1, -119.5, 45, 0),
			new Location(VanaByte.hub, -1062.5, 195.1, -119.5, -45, 0),
			new Location(VanaByte.hub, -1063.5, 195, -118.5, -45, 0),
			new Location(VanaByte.hub, -1061.5, 195, -120.5, -45, 0),
			new Location(VanaByte.hub, -1043.5, 195, -120.5, 45, 0),
			new Location(VanaByte.hub, -1041.5, 195, -118.5, 45, 0),
    };
    private static EntityType[] types = {
            EntityType.WOLF,
			EntityType.CAT,
			EntityType.SNOW_GOLEM,
			EntityType.VILLAGER,
			EntityType.VILLAGER,
			EntityType.VILLAGER
    };
	private static RealLivingEntity[] selectors = new RealLivingEntity[spawnAmount];
    private String name;
    private Location location;
    private EntityType type;
    private org.bukkit.entity.LivingEntity entity;
    public RealLivingEntity(EntityType type, String name, Location location) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.entity = (org.bukkit.entity.LivingEntity) location.getWorld().spawnEntity(location, type);
        this.entity.setSilent(true);
        this.entity.setAI(false);
        this.entity.setCustomName(name);
        this.entity.setCustomNameVisible(true);
        this.entity.setRemoveWhenFarAway(false);
    }

    public String getName() {
        return this.name;
    }

    public EntityType getType() {
        return this.type;
    }

    public org.bukkit.entity.LivingEntity getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }

    public static RealLivingEntity[] getSelectors() {
        return selectors;
    }

    public static void spawnAll() {
		for (int i = 0; i < spawnAmount; i++) {
			selectors[i] = new RealLivingEntity(types[i],
					names[i], locations[i]);
			if(i == 1){
				//vana
				Cat vana = (Cat) selectors[i].getEntity();
				vana.setCatType(Cat.Type.BRITISH_SHORTHAIR);
				vana.setCollarColor(DyeColor.MAGENTA);
			}
		}
	}
}

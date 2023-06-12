package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class D_SpawnGameSelectors {

  private static Location[] locations = {
          new Location(VanaByte.hub, -1062.5, 197, -138.5, -135, 0),
  };

  private static String[] names = {
          ChatColor.AQUA + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "Join Anarchy",
  };

  private static EntityType[] types = {
          EntityType.IRON_GOLEM,
  };

  private static D1_LivingEntity[] selectors = new D1_LivingEntity[1];

  public static void spawn() {
    for(int i = 0; i<1;i++) {
      selectors[i] = new D1_LivingEntity(types[i],
              names[i], locations[i], null, null, null);
    }

  }

}

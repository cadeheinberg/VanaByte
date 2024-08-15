package me.cade.vanabyte.Permissions;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Location;
import org.bukkit.World;

public class SafeZone {

  public static boolean safeZone(Location location) {
    if(location.getY() > 186 && inHub(location.getWorld())) {
      return true;
    }
    return false;
  }

  public static boolean ladderZone(Location location) {
    if(location.getX() > -1060 && location.getX() < -1045 && location.getZ() > -161 && location.getZ() < -146) {
      return true;
    }
    return false;
  }

  public static boolean inHub(World world){
    if (world == VanaByte.hub){
      return true;
    }
    return false;
  }

  public static boolean inAnarchy(World world){
    if (world == VanaByte.anarchyWorld){
      return true;
    }
    return false;
  }
  
}

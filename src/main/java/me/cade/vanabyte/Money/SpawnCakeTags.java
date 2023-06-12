package me.cade.vanabyte.Money;

import me.cade.vanabyte.NPCS.D1_ArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class SpawnCakeTags {

  private static UUID iceCake;
  private static UUID sandCake;
  private static UUID grassCake;
  private static final String genericName = ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + "" + "9" + " min";

  public static void makeIceCake(String time) {
    Location local = new Location(VanaByte.hub, -1014.5, 67, -143.5);
    D1_ArmorStand stand = new D1_ArmorStand(genericName, local, 0, false, true, false);
    iceCake = stand.getStand().getUniqueId();
  }

  public static void makeSandCake(String time) {
    Location local = new Location(VanaByte.hub, -1057.5, 67, -101.5);
    D1_ArmorStand stand = new D1_ArmorStand(genericName, local, 0, false, true, false);
    sandCake = stand.getStand().getUniqueId();
  }

  public static void makeGrassCake(String time) {
    Location local = new Location(VanaByte.hub, -1092.5, 67, -93.5);
    D1_ArmorStand stand = new D1_ArmorStand(genericName, local, 0, false, true, false);
    grassCake = stand.getStand().getUniqueId();
  }

  public static void updateTagNames(int timeBetweenDropSeconds) {
    String minLeft = "" + timeBetweenDropSeconds;
    
    //Ice Cake
    if (Bukkit.getEntity(iceCake) == null) {
      makeIceCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + minLeft + "s");
    }
    ((ArmorStand) Bukkit.getEntity(iceCake)).setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + minLeft + "s");
    
    //Sand Cake
    if (Bukkit.getEntity(sandCake) == null) {
      makeSandCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + minLeft + "s");
    }
    ((ArmorStand) Bukkit.getEntity(sandCake)).setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + minLeft + "s");
    
    //Grass Cake
    if (Bukkit.getEntity(grassCake) == null) {
      makeGrassCake(ChatColor.AQUA + "" + ChatColor.BOLD + "Cake In: " + ChatColor.WHITE + ""
        + minLeft + "s");
    }
    ((ArmorStand) Bukkit.getEntity(grassCake)).setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD
      + "Cake In: " + ChatColor.WHITE + "" + minLeft + "s");
  }

}

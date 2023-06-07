package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class D_SpawnGameSelectors {
  
  private static ChatColor a = ChatColor.AQUA;;
  private static ChatColor b = ChatColor.BOLD;
  
  public static String[] names;
  
  public static void spawnGameSelectors() {
    String p = a + "" + b + "";
    names = new String[4];
    Location[] locations = new Location[4];
    
    names[0] = p + "Man Hunt";
    locations[0] = new Location(VanaByte.hub, -1031.5, 193.5, -110.5);

    names[1] = p + "Battle Royale";
    locations[1] = new Location(VanaByte.hub, -1027.5, 193.5, -113.5);
    
    names[2] = p + "Capture The Flag";
    locations[2] = new Location(VanaByte.hub, -1027.5, 193.5, -119.5);
    
    names[3] = p + "Gun Game";
    locations[3] = new Location(VanaByte.hub, -1031.5, 193.5, -122.5);

    D1_ArmorStand killStreak = new D1_ArmorStand(names[0], locations[0], 120, false, false);
    killStreak.equipDiamondArmor();

    D1_ArmorStand armorUpgrade = new D1_ArmorStand(names[1], locations[1], 100, false, false);
    armorUpgrade.equipIronArmor();;
    
    D1_ArmorStand levelManager = new D1_ArmorStand(names[2], locations[2], 80, false, false);
    levelManager.equipGoldArmor();
    
    D1_ArmorStand agilityUpgrade = new D1_ArmorStand(names[3], locations[3], 60, false, false);
    agilityUpgrade.equipChainArmor();
  }

}

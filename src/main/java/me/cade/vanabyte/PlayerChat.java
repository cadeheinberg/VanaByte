package me.cade.vanabyte;

import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
  
  @EventHandler
  public void chatEvent(AsyncPlayerChatEvent e) {
    e.setCancelled(true);
    tellPlayerMessageToAll(calculatePlayerMessage(e.getPlayer(), e.getMessage()));
  }
  
  
  public static String calculatePlayerMessage(Player player, String note) {
	  Fighter pFight = Fighter.get(player);
	  int numLevel = pFight.getPlayerLevel();
	  String message = "";
	  String levelPre = "";
	  levelPre = ChatColor.LIGHT_PURPLE + "" + numLevel;
    if (player.hasPermission("seven.owner")) {
      message = levelPre + ChatColor.RED + "" + ChatColor.BOLD + " " + "OWNER" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else if (player.hasPermission("seven.admin")) {
      message = levelPre + ChatColor.GOLD + "" + ChatColor.BOLD + " " + "ADMIN" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else if (player.hasPermission("seven.builder")) {
      message = levelPre + ChatColor.GREEN + "" + ChatColor.BOLD + " " + "BUILDER" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    }  else if (player.hasPermission("seven.vip")) {
      message = levelPre + ChatColor.AQUA + "" + ChatColor.BOLD + " " + "VIP" + ChatColor.WHITE
        + " " + player.getName() + ": " + ChatColor.GRAY + note;
    } else {
      message =
        levelPre + ChatColor.GRAY + " " + player.getName() + ": " + ChatColor.GRAY + note;
    }
    return message;
  }

  
  public static void tellPlayerMessageToAll(String note) {
    for (Player inWorld : Bukkit.getOnlinePlayers()) {
      inWorld.sendMessage(note);
    }
  }
  
  public static void tellPlayerMessageToAllInWorld(World w, String note) {
    for (Player inWorld : w.getPlayers()) {
      inWorld.sendMessage(note);
    }
  }
  
}

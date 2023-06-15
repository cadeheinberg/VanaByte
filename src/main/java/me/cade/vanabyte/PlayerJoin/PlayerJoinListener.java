package me.cade.vanabyte.PlayerJoin;

import me.cade.vanabyte.*;
import me.cade.vanabyte.Holograms.Abstraction.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.lang.reflect.InvocationTargetException;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    player.teleport(VanaByte.hubSpawn);
    new Fighter(player);
    for(Player online : Bukkit.getOnlinePlayers()) {
      online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 8, 1);
    }
    player.sendMessage("Hologram creating");
    Location location = player.getLocation();
    location.setY(location.getY() + 1.5);
    Hologram h = VanaByte.getHologramManager().createHologram(location, "welcome_" + player.getUniqueId());
    if(h == null){
      h = VanaByte.getHologramManager().getHologram("welcome_" + player.getUniqueId());
    }
    if(h == null){
      player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Welcome " + player.getName());
      return;
    }
    try {
      if(h.getLine(0) == null){
        h.addLine(ChatColor.GREEN + "" + ChatColor.BOLD + "Welcome " + player.getName());
      }else{
        h.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "Welcome " + player.getName());
      }
        h.showTo(player);
    } catch (InvocationTargetException ex) {
      throw new RuntimeException(ex);
    }
    player.sendMessage("Hologram created");
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Fighter.get(e.getPlayer()).fighterLeftServer();
  }
  
  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    e.setRespawnLocation(VanaByte.hubSpawn);
    Fighter.get(e.getPlayer()).fighterRespawn();
  }
  
}

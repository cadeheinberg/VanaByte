package me.cade.vanabyte;

import me.cade.vanabyte.*;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Permissions.SafeZone;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    player.teleport(VanaByte.hubSpawn);
    new Fighter(player);
    for(Player online : Bukkit.getOnlinePlayers()) {
      online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 8, 1);
    }
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    if(Fighter.get(e.getPlayer()) != null){
      Fighter.get(e.getPlayer()).fighterLeftServer();
    }
  }
  
  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    e.setRespawnLocation(VanaByte.hubSpawn);
    if(Fighter.get(e.getPlayer()) != null){
      Fighter.get(e.getPlayer()).fighterRespawn();
    }
  }

  @EventHandler
  public void onWorldChange(PlayerChangedWorldEvent e){
    if(SafeZone.inHub(e.getPlayer().getWorld())){
      if(Fighter.get(e.getPlayer()) != null){
        Fighter.get(e.getPlayer()).fighterChangeWorld();
      }
    }
  }
  
}

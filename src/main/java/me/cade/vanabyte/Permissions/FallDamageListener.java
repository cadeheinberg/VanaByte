package me.cade.vanabyte.Permissions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FallDamageListener implements Listener {

  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (e.getCause() == DamageCause.FALL) {
      if(e.getEntity() instanceof Player) {
        e.setCancelled(true);
      }
    }
  }
  
}

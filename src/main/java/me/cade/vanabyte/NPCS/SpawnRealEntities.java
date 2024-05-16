package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.NPCS.RealEntities.ArmorStand;
import me.cade.vanabyte.NPCS.RealEntities.LivingEntity;
import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SpawnRealEntities {

  public static void spawnNPCS() {
    ArmorStand.spawnAll();
    LivingEntity.spawnAll();
  }
  
  public static void removeAllNpcs() {
    for( Entity e : VanaByte.hub.getEntities()) {
      if(e instanceof Player) {
        continue;
      }else {
        e.remove();
      }
    }
  }

}

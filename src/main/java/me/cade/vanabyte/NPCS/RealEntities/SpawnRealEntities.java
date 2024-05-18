package me.cade.vanabyte.NPCS.RealEntities;

import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SpawnRealEntities {

  public static void spawnNPCS() {
    MyArmorStand.spawnAll();
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

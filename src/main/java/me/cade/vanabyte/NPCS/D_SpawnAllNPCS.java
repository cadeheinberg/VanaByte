package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class D_SpawnAllNPCS {

  public static void spawnNPCS() {
    D_SpawnKitSelectors.spawnKitSelectors();
    SpawnShowdownSelector.spawn();
    //D_SpawnGameSelectors.spawnGameSelectors();
    //D_SpawnShopSelectors.spawn();
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

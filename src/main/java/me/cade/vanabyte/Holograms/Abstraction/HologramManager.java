package me.cade.vanabyte.Holograms.Abstraction;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramManager {

  private final Map<String, Hologram> hologramMap = new HashMap<>();
  private final HologramFactory hologramFactory;

  public HologramManager(HologramFactory hologramFactory) {
    this.hologramFactory = hologramFactory;
  }

  public Hologram getHologram(String hologramID) {
    return hologramMap.get(hologramID);
  }

  public Hologram createHologram(Location location, String name) {
    if(hologramMap.containsKey(name)) {
      return null;
    }
    Hologram hologram = hologramFactory.createHologram(location, name);
    hologramMap.put(name, hologram);
    return hologram;
  }

  public void deleteHologram(String hologramID) throws InvocationTargetException {
    Hologram hologram = hologramMap.remove(hologramID);
    if (hologram == null) {
      return;
    }
    for (Player p : Bukkit.getOnlinePlayers()){
      hologram.hideFrom(p);
    }
  }

  public void initPlayer(Player player) {
    // This could be optimized by mapping holograms to Worlds or even Chunks
    // But for simplicity's sake we will just show a player all holograms
    for (Hologram hologram : hologramMap.values()) {
      try {
        hologram.showTo(player);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public List<String> getHologramNames() {
    return List.copyOf(hologramMap.keySet());
  }

}

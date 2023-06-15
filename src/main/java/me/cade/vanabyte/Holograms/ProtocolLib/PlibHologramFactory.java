package me.cade.vanabyte.Holograms.ProtocolLib;

import me.cade.vanabyte.Holograms.Abstraction.Hologram;
import me.cade.vanabyte.Holograms.Abstraction.HologramFactory;
import org.bukkit.Location;

public class PlibHologramFactory implements HologramFactory {
  @Override
  public Hologram createHologram(Location location, String hologramName) {
    return new PlibHologram(location, hologramName);
  }
}

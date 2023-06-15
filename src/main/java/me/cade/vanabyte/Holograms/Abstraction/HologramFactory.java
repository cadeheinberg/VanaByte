package me.cade.vanabyte.Holograms.Abstraction;

import org.bukkit.Location;

public interface HologramFactory {

  Hologram createHologram(Location location, String hologramName);

}

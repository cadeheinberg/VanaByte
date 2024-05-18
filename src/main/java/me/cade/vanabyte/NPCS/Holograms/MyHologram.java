package me.cade.vanabyte.NPCS.Holograms;

import me.cade.vanabyte.PlayerJoinListener;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyHologram {

    private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);
    private Location location = null;
    private List<ArmorStand> hologramLines = null;
    private List<UUID> viewers = null;

    protected MyHologram(Location inLocation, List<String> displayLines){
        hologramLines = new ArrayList<>();
        //Bukkit.getServer().getOnlinePlayers().forEach(player -> {player.sendMessage("hologram: " + inLocation.getX() + " " + inLocation.getY() + " " + inLocation.getZ());});
        for(int i = 0; i < displayLines.size(); i++){
            ArmorStand armorStand = (org.bukkit.entity.ArmorStand) VanaByte.hub.spawnEntity(inLocation.clone().subtract(0,.25*(i),0), EntityType.ARMOR_STAND);
            armorStand.setCustomName(displayLines.get(i));
            armorStand.setCustomNameVisible(true);
            armorStand.setInvisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStand.setInvulnerable(true);
            hologramLines.add(armorStand);
        }
        this.location = inLocation;
        viewers = new ArrayList<>();
    }

    protected void setText(List<String> displayLines){
        if(displayLines.isEmpty()){
            return;
        }
        else if(displayLines.size() == this.hologramLines.size()){
            //loop through exiting and rename
            for(int i=0; i < displayLines.size(); i++){
                hologramLines.get(i).setCustomName(displayLines.get(i));
            }
        }else{
            //remove old lines and create new ones
            for(ArmorStand hologramLine : hologramLines){
                hologramLine.remove();
            }
            for(String line : displayLines){
                ArmorStand armorStand = (org.bukkit.entity.ArmorStand) VanaByte.hub.spawnEntity(this.location, EntityType.ARMOR_STAND);
                armorStand.setCustomName(line);
                armorStand.setCustomNameVisible(true);
                armorStand.setInvisible(true);
                armorStand.setGravity(false);
                armorStand.setMarker(true);
                armorStand.setInvulnerable(true);
                hologramLines.add(armorStand);
            }
        }
    }

    protected void ShowTo(Player player){
        for(ArmorStand armorStand : hologramLines){
            player.showEntity(plugin, armorStand);
        }
        viewers.add(player.getUniqueId());
    }

    protected void hideFrom(Player player){
        for(ArmorStand armorStand : hologramLines){
            player.hideEntity(plugin, armorStand);
        }
    }

    protected boolean hasViewer(UUID uuid){
        return viewers.contains(uuid);
    }

}

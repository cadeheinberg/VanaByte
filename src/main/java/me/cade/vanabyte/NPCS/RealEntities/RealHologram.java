package me.cade.vanabyte.NPCS.RealEntities;

import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealHologram {

    private static int spawnAmount = 1;

    private static String[] names = {
            ChatColor.YELLOW + "Upgrades",
    };
    private static Location[] locations = {
            new Location(VanaByte.hub, -1052.5, 195.2, -112.5, 0, 0)
    };

    private static RealHologram[] selectors = new RealHologram[spawnAmount];

    public static void spawnAll() {
        for (int i = 0; i < spawnAmount; i++) {
            selectors[i] = new RealHologram(locations[i], Arrays.asList(names[i]));
        }
    }

    private Location location = null;
    private List<ArmorStand> hologramLines = null;

    protected RealHologram(Location inLocation, List<String> displayLines){
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

}

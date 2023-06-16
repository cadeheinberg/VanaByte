package me.cade.vanabyte.NPCS.ProtocolHolograms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PHologram {

    //String - key to retrieve PHologram object
    //PHologram - the object holding hologram details
    private static final Map<String, PHologram> hologramMap = new HashMap<>();

    private Location location;

    PSingleLineOfHologram headNode;

    private Player player;

    public PHologram(String key, Location location, String displayText){
        this.location = location;
        // Could be safer but this is probably fine
        headNode = new PSingleLineOfHologram(location, displayText);
        this.hologramMap.put(key, this);
    }

    public void addLine(String displayText){
        PSingleLineOfHologram current = this.headNode;
        //get a line where current.nextLine is null, add the new line here
        int i = 1;
        while(current.getNextLine() != null){
            current = current.getNextLine();
            i++;
        }
        PSingleLineOfHologram nextLine = new PSingleLineOfHologram(this.location.clone().add(0, - (0.25 * i), 0), displayText);
        current.setNextLine(nextLine);;
    }

    public boolean setLine(int lineOfHologram, String displayText){
        PSingleLineOfHologram toUpdate = this.headNode;
        for (int i = 0; i < lineOfHologram; i++){
            if(toUpdate == null){
                return false;
            }
            toUpdate = toUpdate.getNextLine();
        }
        toUpdate.setLineDisplayText(displayText);
        toUpdate.refreshDisplayText(this.player);
        return true;
    }

    public void showHologramToPlayer(Player player){
        PSingleLineOfHologram current = headNode;
        while(current != null){
            current.showTo(player);
            current = current.getNextLine();
        }
        this.player = player;
    }

    public static Map<String, PHologram> getHologramMap() {
        return hologramMap;
    }
}

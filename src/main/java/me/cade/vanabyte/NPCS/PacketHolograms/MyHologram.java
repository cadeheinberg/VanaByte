package me.cade.vanabyte.NPCS.PacketHolograms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MyHologram {

    //String - key to retrieve PHologram object
    //PHologram - the object holding hologram details
    private static final Map<String, MyHologram> hologramMap = new HashMap<>();

    private Location location;

    protected MyHologramLine headNode;

    private Player player;

    public MyHologram(String key, Location location, String displayText){
        this.location = location;
        // Could be safer but this is probably fine
        headNode = new MyHologramLine(location, displayText);
        this.hologramMap.put(key, this);
    }

    public void addLine(String displayText){
        MyHologramLine current = this.headNode;
        //get a line where current.nextLine is null, add the new line here
        int i = 1;
        while(current.getNextLine() != null){
            current = current.getNextLine();
            i++;
        }
        MyHologramLine nextLine = new MyHologramLine(this.location.clone().add(0, - (0.25 * i), 0), displayText);
        current.setNextLine(nextLine);;
    }

    public boolean setLine(int lineOfHologram, String displayText){
        MyHologramLine toUpdate = this.headNode;
        for (int i = 0; i < lineOfHologram; i++){
            if(toUpdate == null){
                return false;
            }
            toUpdate = toUpdate.getNextLine();
        }
        toUpdate.setLineDisplayText(displayText);
        return true;
    }

    public void showHologramToPlayer(Player player){
        MyHologramLine current = headNode;
        while(current != null){
            current.showTo(player);
            current = current.getNextLine();
        }
        this.player = player;
    }

    public static Map<String, MyHologram> getHologramMap() {
        return hologramMap;
    }
}

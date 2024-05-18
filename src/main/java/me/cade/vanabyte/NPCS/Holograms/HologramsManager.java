package me.cade.vanabyte.NPCS.Holograms;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.NPCS.RealEntities.MyArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HologramsManager {

    private Player player = null;
    private Fighter fighter = null;
    private MyHologram[] kitHolograms = {null, null, null, null, null, null, null};
    private MyHologram welcomeHologram = null;
    private static HashMap<String, MyHologram> hologramMap = new HashMap<>();

    public HologramsManager(Player player, Fighter fighter) {
        this.player = player;
        this.fighter = fighter;
    }

    public void fighterJoined() {
        //show personal welcome holograms
        this.refreshMyWelecomeHologram();
        //show personal kit holograms
        for (int i = 0; i < kitHolograms.length; i++) {
            this.refreshMyKitHolograms(i);
        }
        //hide everyone elses holgrams
        this.hideOtherPlayersHolograms();
    }

    public void fighterChangedWorld() {
        //show personal holograms
        this.refreshMyWelecomeHologram();
        //hide everyone elses holgrams
        this.hideOtherPlayersHolograms();
    }

    public void fighterDied() {
    }

    public void fighterKilled() {
        this.refreshMyWelecomeHologram();
    }

    public void fighterRespawned() {
        this.refreshMyWelecomeHologram();
    }

    public void fighterLeftServer() {
    }

    public void refreshMyWelecomeHologram() {
        List<String> text = Arrays.asList(ChatColor.AQUA + "" + ChatColor.BOLD + "Welcome " + player.getName(), "You have " + fighter.getKills() + " kills!!!", "You have " + fighter.getDeaths() + " deaths");
        if (hologramMap.containsKey("welcome_" + player.getUniqueId())) {
            welcomeHologram = hologramMap.get("welcome_" + player.getUniqueId());
            welcomeHologram.setText(text);
        } else {
            welcomeHologram = new MyHologram(VanaByte.hubSpawn.clone().add(0, 1.5, 0), text);
            welcomeHologram.ShowTo(this.player);
            hologramMap.put("welcome_" + player.getUniqueId(), welcomeHologram);
            for (Player other : Bukkit.getServer().getOnlinePlayers()) {
                if(other != player){
                    welcomeHologram.hideFrom(other);
                }
            }
        }
    }

    public void refreshMyKitHolograms(int kitID) {
        String locked = ChatColor.RED + "1 Kit Key Needed";
        if (fighter.getFighterKitManager().getUnlockedKit(kitID) > 0) {
            locked = ChatColor.GREEN + "Unlocked";
        }
        String q_special = "  Drop-Item: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponDrop() + "  ";
        String rc_special = "  Right-Click: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponRightClick() + "  ";
        List<String> text = Arrays.asList(locked, q_special, rc_special);
        if (hologramMap.containsKey("kit0" + kitID + "_" + player.getUniqueId())) {
            kitHolograms[kitID] = hologramMap.get("kit0" + kitID + "_" + player.getUniqueId());
            kitHolograms[kitID].setText(text);
        } else {
            //Bukkit.getServer().getOnlinePlayers().forEach(player -> {player.sendMessage("hologram: " + kitID);});
            kitHolograms[kitID] = new MyHologram(MyArmorStand.getLocationOfSelector(kitID).clone().add(0, 2.50, 0), text);
            kitHolograms[kitID].ShowTo(this.player);
            hologramMap.put("kit0" + kitID + "_" + player.getUniqueId(), kitHolograms[kitID]);
            for (Player other : Bukkit.getServer().getOnlinePlayers()) {
                if(other != player){
                    kitHolograms[kitID].hideFrom(other);
                }
            }
        }
    }

    public void hideOtherPlayersHolograms() {
        for (MyHologram hologram : hologramMap.values()) {
            if (!hologram.hasViewer(this.player.getUniqueId())) {
                hologram.hideFrom(this.player);
            }
        }
    }
}


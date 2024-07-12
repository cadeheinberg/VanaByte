package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.NPCS.RealEntities.MyArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class HologramManager {

    private Player player = null;
    private Fighter fighter = null;
    private Hologram[] kitHolograms = {null, null, null, null, null, null, null};
    private Hologram welcomeHologram = null;

    public HologramManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    public void fighterJoined(){
        if(player.getWorld() == VanaByte.hub){
            this.respawnKitHolograms();
            this.respawnWelcomeHologram();
        }
    }

    public void fighterDied(){
        //updated with respawn?
    }

    public void fighterKilled(){
        if(player.getWorld() == VanaByte.hub){
            this.respawnWelcomeHologram();
        }
    }

    public void fighterLeftServer(){
        this.despawnHubHolograms();
    }

    public void fighterChangedWorld(World from){
        if(from == VanaByte.hub){
            this.despawnHubHolograms();
        }else{
            this.respawnKitHolograms();
            this.respawnWelcomeHologram();
        }
    }

    public void fighterRespawned(){
        if(player.getWorld() == VanaByte.hub){
            this.respawnKitHolograms();
            this.respawnWelcomeHologram();
        }
    }

    public void fighterPurchasedKit(){
        if(player.getWorld() == VanaByte.hub){
            this.respawnKitHolograms();
        }
    }

    private void despawnHubHolograms(){
        Bukkit.getConsoleSender().sendMessage("despawning all holograms");
        if(welcomeHologram != null){
            welcomeHologram.removeFromServer();
            welcomeHologram = null;
        }
        for(int kitID = 0; kitID < kitHolograms.length; kitID++){
            if(kitHolograms[kitID] != null) {
                kitHolograms[kitID].removeFromServer();
                kitHolograms[kitID] = null;
            }
        }
    }

    private void respawnKitHolograms(){
        Bukkit.getConsoleSender().sendMessage("respawning kit holograms");
        for (int kitID = 0; kitID < kitHolograms.length; kitID++) {
            if (kitHolograms[kitID] == null) {
                kitHolograms[kitID] = new Hologram(MyArmorStand.getLocationOfSelector(kitID).clone().add(0, 2.50, 0),false);
            }
            if (kitHolograms[kitID] == null) {
                player.sendMessage("HologramManager Error: kits");
            }
            else{
                String locked = ChatColor.RED + " Free For Beta ";
                if (fighter.getFighterKitManager().getUnlockedKit(kitID) > 0) {
                    locked = ChatColor.GREEN + " Unlocked ";
                }
                kitHolograms[kitID].setDisplayText(locked + "\n"
                        + ChatColor.WHITE + " Special: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponDrop()  + " \n"
                        + ChatColor.WHITE + " Main: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponRightClick() + " ");
                kitHolograms[kitID].showTo(this.player);
            }
        }
    }

    private void respawnWelcomeHologram(){
        Bukkit.getConsoleSender().sendMessage("respawning welcome holograms");
        if(welcomeHologram == null) {
            welcomeHologram = new Hologram(VanaByte.hubSpawn.clone().add(0, 1.5, 0), false);
        }
        if(welcomeHologram == null){
            player.sendMessage("HologramManager Error: welcome");
        }else{
            welcomeHologram.setDisplayText(ChatColor.AQUA + " Welcome " + player.getName() + " \n"
                    + ChatColor.WHITE + " You have " + fighter.getKills() + " kills!!! \n"
                    + ChatColor.WHITE + " You have " + fighter.getDeaths() + " deaths :( ");
            welcomeHologram.showTo(player);
        }
    }

}


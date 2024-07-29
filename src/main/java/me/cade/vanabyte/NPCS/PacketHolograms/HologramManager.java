package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HologramManager {

    private Player player = null;
    private Fighter fighter = null;
    private final Hologram[] kitHolograms;
    private Hologram welcomeHologram;

    public HologramManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
        kitHolograms = new Hologram[KitType.values().length];
        welcomeHologram = null;
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
        int i = 0;
        for(KitType kitType : KitType.values()){
            if (kitHolograms[i] == null) {
                kitHolograms[i] = new Hologram(kitType.getSelectorLocation().clone().add(0, 2.50, 0),false);
                if (kitHolograms[i] == null) {
                    player.sendMessage("HologramManager Error: kits");
                    return;
                }
            }
            String locked = ChatColor.RED + " Free For Beta ";
            if (fighter.getFighterMYSQLManager().hasUnlockedKitType(kitType)) {
                locked = ChatColor.GREEN + " Unlocked ";
            }
            kitHolograms[i].setDisplayText(locked + "\n"
                    + ChatColor.WHITE + " Special: " + kitType.getWeaponTypes()[0].getWeaponDrop() + " \n"
                    + ChatColor.WHITE + " Main: " + kitType.getWeaponTypes()[0].getWeaponRightClick() + " ");
            kitHolograms[i].showTo(this.player);
            i++;
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


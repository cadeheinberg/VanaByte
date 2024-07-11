package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.NPCS.RealEntities.MyArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    private Player player = null;
    private Fighter fighter = null;
    private MyHologramDisplay[] kitHolograms = {null, null, null, null, null, null, null};
    private MyHologramDisplay welcomeHologram = null;

    public HologramManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    public void fighterJoined(){
        this.spawnKitHolograms();
        this.spawnWelcomeHologram();
    }

    public void fighterDied(){}

    public void fighterKilled(){
        this.spawnWelcomeHologram();
    }

    public void fighterLeftServer(){}

    public void fighterChangedWorld(){
        this.spawnKitHolograms();
        this.spawnWelcomeHologram();
    }

    public void fighterRespawned(){
        this.spawnKitHolograms();
        this.spawnWelcomeHologram();}

    public void fighterPurchasedKit(){
        this.spawnKitHolograms();
        this.spawnWelcomeHologram();
    }

    public void spawnKitHolograms(){
        for (int kitID = 0; kitID < kitHolograms.length; kitID++) {
            String locked = ChatColor.RED + "1 Kit Key Needed";
            if (fighter.getFighterKitManager().getUnlockedKit(kitID) > 0) {
                locked = ChatColor.GREEN + "Unlocked";
            }
            String q_special = "  Drop-Item: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponDrop() + "  ";
            String rc_special = "  Right-Click: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponRightClick() + "  ";
            if (kitHolograms[kitID] != null) {
                kitHolograms[kitID].setDisplayText(locked + "\n" + q_special + "\n"  + rc_special);
                kitHolograms[kitID].showTo(this.player);
            }else{
                kitHolograms[kitID] = new MyHologramDisplay(MyArmorStand.getLocationOfSelector(kitID).clone().add(0, 2.50, 0),
                        locked + "\n" + q_special + "\n"  + rc_special,
                        false);
                kitHolograms[kitID].showTo(this.player);
            }
        }
    }

    protected void spawnWelcomeHologram(){
        if(welcomeHologram != null){
            welcomeHologram.setDisplayText("You have " + fighter.getKills() + " kills!!! \n" +
                    "\"You have \" + fighter.getDeaths() + \" deaths :(\"");
            welcomeHologram.showTo(player);
        }else{
            welcomeHologram = new MyHologramDisplay(VanaByte.hubSpawn.clone().add(0 , 1.5, 0),
                    ChatColor.AQUA + "" + ChatColor.BOLD + "Welcome " + player.getName() + "\n" +
                            "\"You have \" + fighter.getKills() + \" kills!!!\" \n" +
                            "\"You have \" + fighter.getDeaths() + \" deaths :(\"",
                    false);
            welcomeHologram.showTo(player);
        }
    }
}


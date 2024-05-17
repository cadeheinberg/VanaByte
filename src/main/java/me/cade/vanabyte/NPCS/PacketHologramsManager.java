package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.NPCS.PacketHolograms.MyHologram;
import me.cade.vanabyte.NPCS.RealEntities.ArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PacketHologramsManager {

    private Player player = null;
    private Fighter fighter = null;
    private MyHologram[] kitHolograms = {null, null, null, null, null, null, null};
    private MyHologram welcomeHologram = null;

    public PacketHologramsManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    public void fighterJoined(){
        this.spawnHolograms();
    }

    public void fighterDied(){

    }

    public void fighterLeftServer(){

    }

    public void fighterChangedWorld(){
        this.spawnHolograms();
    }

    public void fighterRespawned(){
    }
    public void spawnHolograms(){
        this.findExistingHologramsForPlayer();
        this.refreshWelcomeHologram();
        for (int i = 0; i < kitHolograms.length; i++) {
            this.refreshKitHolograms(i);
        }
    }

    public void findExistingHologramsForPlayer(){
        if(MyHologram.getHologramMap().containsKey("welcome_" + player.getUniqueId())){
            welcomeHologram = MyHologram.getHologramMap().get("welcome_" + player.getUniqueId());
        }
        for (int i = 0; i < Fighter.getNumberOfKits(); i++){
            if(MyHologram.getHologramMap().containsKey("kit0" + i + "_" + player.getUniqueId())){
                kitHolograms[i] = MyHologram.getHologramMap().get("kit0" + i + "_" + player.getUniqueId());
            }
        }
    }

    public void refreshWelcomeHologram(){
        if(welcomeHologram != null){
            welcomeHologram.setLine(1, "You have " + fighter.getKills() + " kills!!!");
            welcomeHologram.setLine(2,"You have " + fighter.getDeaths() + " deaths :(");
            welcomeHologram.showHologramToPlayer(player);
        }else{
            welcomeHologram = new MyHologram("welcome_" + player.getUniqueId(), VanaByte.hubSpawn.clone().add(0 , 1.5, 0), ChatColor.AQUA + "" + ChatColor.BOLD + "Welcome " + player.getName());
            welcomeHologram.addLine("You have " + fighter.getKills() + " kills!!!");
            welcomeHologram.addLine("You have " + fighter.getDeaths() + " deaths :(");
            welcomeHologram.showHologramToPlayer(player);
        }
    }

    public void refreshKitHolograms(int kitID) {
        String locked = ChatColor.RED + "1 Kit Key Needed";
        if (fighter.getFighterKitManager().getUnlockedKit(kitID) > 0) {
            locked = ChatColor.GREEN + "Unlocked";
        }
        String q_special = "  Drop-Item: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponDrop() + "  ";
        String rc_special = "  Right-Click: " + fighter.getFighterKitManager().getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponRightClick() + "  ";

        if (kitHolograms[kitID] != null) {
            kitHolograms[kitID].setLine(0, locked);
            kitHolograms[kitID].setLine(1, q_special);
            kitHolograms[kitID].setLine(2, rc_special);
            kitHolograms[kitID].showHologramToPlayer(this.player);
        }else{
            kitHolograms[kitID] = new MyHologram("kit0" + kitID + "_" + player.getUniqueId(), ArmorStand.getLocationOfSelector(kitID).clone().add(0, 2.50, 0), locked);
            kitHolograms[kitID].addLine(q_special);
            kitHolograms[kitID].addLine(rc_special);
            kitHolograms[kitID].showHologramToPlayer(this.player);
        }
    }
}


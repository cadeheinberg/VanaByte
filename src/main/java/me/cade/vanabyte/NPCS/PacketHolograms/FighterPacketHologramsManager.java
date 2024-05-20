package me.cade.vanabyte.NPCS.PacketHolograms;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.NPCS.GUIs.GUIManager;
import me.cade.vanabyte.NPCS.RealEntities.MyArmorStand;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FighterPacketHologramsManager {

    private Player player = null;
    private Fighter fighter = null;
    private MyHologram[] kitHolograms = {null, null, null, null, null, null, null};
    private MyHologram welcomeHologram = null;

    public FighterPacketHologramsManager(Player player, Fighter fighter){
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
        for (int i = 0; i < Fighter.getNumberOfKits(); i++){
            if(MyHologram.getHologramMap().containsKey("kit0" + i + "_" + player.getUniqueId())){
                kitHolograms[i] = MyHologram.getHologramMap().get("kit0" + i + "_" + player.getUniqueId());
            }
        }
        for (int kitID = 0; kitID < kitHolograms.length; kitID++) {
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
                kitHolograms[kitID] = new MyHologram("kit0" + kitID + "_" + player.getUniqueId(), MyArmorStand.getLocationOfSelector(kitID).clone().add(0, 2.50, 0), locked);
                kitHolograms[kitID].addLine(q_special);
                kitHolograms[kitID].addLine(rc_special);
                kitHolograms[kitID].showHologramToPlayer(this.player);
            }
        }
    }

    protected void spawnWelcomeHologram(){
        if(MyHologram.getHologramMap().containsKey("welcome_" + player.getUniqueId())){
            welcomeHologram = MyHologram.getHologramMap().get("welcome_" + player.getUniqueId());
        }
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
}


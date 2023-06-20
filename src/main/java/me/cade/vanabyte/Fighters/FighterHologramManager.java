package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.NPCS.D_SpawnKitSelectors;
import me.cade.vanabyte.NPCS.ProtocolHolograms.PHologram;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FighterHologramManager {

    private Player player = null;
    private Fighter fighter = null;
    private PHologram[] kitHolograms = {null, null, null, null, null, null, null};
    private PHologram welcomeHologram = null;

    protected FighterHologramManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    protected void fighterJoined(){
        this.spawnHolograms();
    }

    protected void fighterDied(){

    }

    protected void fighterLeftServer(){

    }

    protected void fighterChangedWorld(){
        this.spawnHolograms();
    }

    protected void fighterRespawned(){
        //this.resetSpecialAbility();
    }
    public void spawnHolograms(){
        this.findExistingHologramsForPlayer();
        this.refreshWelcomeHologram();
        for (int i = 0; i < kitHolograms.length; i++) {
            this.refreshKitHolograms(i);
        }
    }

    public void findExistingHologramsForPlayer(){
        if(PHologram.getHologramMap().containsKey("welcome_" + player.getUniqueId())){
            welcomeHologram = PHologram.getHologramMap().get("welcome_" + player.getUniqueId());
        }
        for (int i = 0; i < Fighter.getNumberOfKits(); i++){
            if(PHologram.getHologramMap().containsKey("kit0" + i + "_" + player.getUniqueId())){
                kitHolograms[i] = PHologram.getHologramMap().get("kit0" + i + "_" + player.getUniqueId());
            }
        }
    }

    public void refreshWelcomeHologram(){
        if(welcomeHologram != null){
            welcomeHologram.setLine(1, "You have " + fighter.getKills() + " kills!!!");
            welcomeHologram.setLine(2,"You have " + fighter.getDeaths() + " deaths :(");
            welcomeHologram.showHologramToPlayer(player);
        }else{
            welcomeHologram = new PHologram("welcome_" + player.getUniqueId(), VanaByte.hubSpawn.clone().add(0 , 1.5, 0), ChatColor.AQUA + "" + ChatColor.BOLD + "Welcome " + player.getName());
            welcomeHologram.addLine("You have " + fighter.getKills() + " kills!!!");
            welcomeHologram.addLine("You have " + fighter.getDeaths() + " deaths :(");
            welcomeHologram.showHologramToPlayer(player);
        }
    }

    public void refreshKitHolograms(int kitID) {
        String locked = ChatColor.RED + "1 Kit Key Needed";
        if (fighter.fighterKitManager.getUnlockedKit(kitID) > 0) {
            locked = ChatColor.GREEN + "Unlocked";
        }
        String q_special = "  Drop-Item: " + fighter.fighterKitManager.getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponDrop() + "  ";
        String rc_special = "  Right-Click: " + fighter.fighterKitManager.getFkitsNoPlayer()[kitID].getWeaponHolders().get(0).getWeaponRightClick() + "  ";

        if (kitHolograms[kitID] != null) {
            kitHolograms[kitID].setLine(0, locked);
            kitHolograms[kitID].setLine(1, q_special);
            kitHolograms[kitID].setLine(2, rc_special);
            kitHolograms[kitID].showHologramToPlayer(this.player);
        }else{
            kitHolograms[kitID] = new PHologram("kit0" + kitID + "_" + player.getUniqueId(), D_SpawnKitSelectors.getLocationOfSelector(kitID).clone().add(0, 2.50, 0), locked);
            kitHolograms[kitID].addLine(q_special);
            kitHolograms[kitID].addLine(rc_special);
            kitHolograms[kitID].showHologramToPlayer(this.player);
        }
    }
}

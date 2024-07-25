package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.MySQL.DatabaseTable;
import me.cade.vanabyte.MySQL.FighterTable;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FighterMYSQLManager {

    private Player player = null;
    private Fighter fighter = null;
    private FighterTable fighterTable;
    private FighterTable unlockedKitsTable;
    private FighterTable[] weaponTables = new FighterTable[DatabaseTable.getWeaponTables().length];

    private final int[] INDEX_unlockedKits = new int[7];
    private HashMap<WeaponType, Double[]> INDEX_weaponStats = new HashMap<>();

    private Double[] buildStatTable(Double[][] STATS_FROM_BLUEPRINT, Integer[] UPGRADES_FROM_BLUEPRINT){
        //Integer[X] = Y means that upgrade Double[X][Y] has been unlocked. First level of each unlocked here.
        //Integer[X] = -1 means that this skill has not been unlocked at all
        Double[] outputStats = new Double[UPGRADES_FROM_BLUEPRINT.length];
        for(int X = 0; X < UPGRADES_FROM_BLUEPRINT.length; X++){
            if(UPGRADES_FROM_BLUEPRINT[X] < 0){
                //skill has not been unlocked at all
                outputStats[X] = -1.0;
            }else{
                int Y = UPGRADES_FROM_BLUEPRINT[X];
                if(Y >= STATS_FROM_BLUEPRINT[X].length || STATS_FROM_BLUEPRINT[X][Y] < 0){
                    //somehow unlocked an invalid stat, just use base and report issue
                    outputStats[X] = STATS_FROM_BLUEPRINT[X][0];
                    VanaByte.sendConsoleMessageBad("FighterMYSQLManager.java", "invalid stat tried to be used");
                }else{
                    outputStats[X] = STATS_FROM_BLUEPRINT[X][Y];
                }
            }
        }
        return outputStats;
    }
    //fill stats to use for kits and weapons later
    private void statsDoerThing(){
        //if player is not in the mysql table
        //build "stats" using WeaponType blueprint
        for(int i = 0; i < WeaponType.values().length; i++){
            if(WeaponType.values()[i] == WeaponType.UNKNOWN_WEAPON){
                continue;
            }
            FIGHTER_STATS.put(WeaponType.values()[i], buildStatTable(WeaponType.values()[i].getStatTable().getStats(), WeaponType.values()[i].getStatTable().getUpgradeLevels()));
        }
    }

    protected FighterMYSQLManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    //If the stat is a -1 it wont upload anything
    protected boolean uploadFighter() {
        boolean success = VanaByte.databaseManager.addPlayerToDatabaseIfNotExist(player.getUniqueId(), player.getName());
        if(!success) return false;
        success = VanaByte.databaseManager.uploadFighterTable(fighterTable, player.getUniqueId(), player.getName());
        if(!success) return false;
        success = VanaByte.databaseManager.uploadFighterTable(unlockedKitsTable, player.getUniqueId(), player.getName());
        if(!success) return false;
        for(int i = 0; i < DatabaseTable.getWeaponTables().length; i++){
            success = VanaByte.databaseManager.uploadFighterTable(weaponTables[i], player.getUniqueId(), player.getName());
            if(!success) return false;
        }
        return true;
    }

    private boolean downloadFighter() {
        boolean success = VanaByte.databaseManager.addPlayerToDatabaseIfNotExist(player.getUniqueId(), player.getName());
        if(!success) return false;
        fighterTable = VanaByte.databaseManager.downloadFighterTable(DatabaseTable.getFighterTable(), player.getUniqueId());
        if(fighterTable == null) return false;
        unlockedKitsTable = VanaByte.databaseManager.downloadFighterTable(DatabaseTable.getUnlockedKitsTable(), player.getUniqueId());
        if(unlockedKitsTable == null) return false;
        for(int i = 0; i < DatabaseTable.getWeaponTables().length; i++){
            weaponTables[i] = VanaByte.databaseManager.downloadFighterTable(DatabaseTable.getWeaponTables()[i], player.getUniqueId());
            if(weaponTables[i] == null) return false;
        }
        return true;
    }

    protected void fighterJoined(){
        this.downloadFighter();
    }

    protected void fighterLeftServer(){
        this.uploadFighter();
    }

    protected void fighterChangedWorld(){

    }

    protected void fighterDied(){

    }

    protected void fighterRespawned(){

    }

    protected void deleteMeFromDatabase(){
//        VanaByte.databaseManager.deletePlayerFromTable(this.player);
        //player.kickPlayer("Your stats have been cleared");
    }

    public boolean getUnlockedKit(KitType kitType) {
        return INDEX_unlockedKits[kitID] == 1;
    }

    public void setUnlockedKit(KitType kit) {
        fighter.fighterPurchasedKit();
        INDEX_unlockedKits[kitID] = 1;
    }

    public HashMap<WeaponType, Double[]> getFIGHTER_STATS() {
        return INDEX_weaponStats;
    }

}

package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FighterMYSQLManager {

    private Player player = null;
    private Fighter fighter = null;
    private final int[] unlockedKits = new int[7];
    private HashMap<WeaponType, Double[]> FIGHTER_STATS = new HashMap<>();

    private Double[] buildStatTable(Double[][] STATS_FROM_BLUEPRINT, Integer[] UPGRADES_FROM_BLUEPRINT){
//        //Integer[X] = Y means that upgrade Double[X][Y] has been unlocked. First level of each unlocked here.
//        //Integer[X] = -1 means that this skill has not been unlocked at all
//        Double[] outputStats = new Double[UPGRADES_FROM_BLUEPRINT.length];
//        for(int X = 0; X < UPGRADES_FROM_BLUEPRINT.length; X++){
//            if(UPGRADES_FROM_BLUEPRINT[X] < 0){
//                //skill has not been unlocked at all
//                outputStats[X] = -1.0;
//            }else{
//                int Y = UPGRADES_FROM_BLUEPRINT[X];
//                if(Y >= STATS_FROM_BLUEPRINT[X].length || STATS_FROM_BLUEPRINT[X][Y] < 0){
//                    //somehow unlocked an invalid stat, just use base and report issue
//                    outputStats[X] = STATS_FROM_BLUEPRINT[X][0];
//                    VanaByte.sendConsoleMessageBad("FighterMYSQLManager.java", "invalid stat tried to be used");
//                }else{
//                    outputStats[X] = STATS_FROM_BLUEPRINT[X][Y];
//                }
//            }
//        }
//        return outputStats;
        return null;
    }
    //fill stats to use for kits and weapons later
    private void statsDoerThing(){
//        //if player is not in the mysql table
//        //build "stats" using WeaponType blueprint
//        for(int i = 0; i < WeaponType.values().length; i++){
//            if(WeaponType.values()[i] == WeaponType.UNKNOWN_WEAPON){
//                continue;
//            }
//            FIGHTER_STATS.put(WeaponType.values()[i], buildStatTable(WeaponType.values()[i].getStatTable().getStats(), WeaponType.values()[i].getStatTable().getUpgradeLevels()));
//        }
    }

    protected FighterMYSQLManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    protected void fighterJoined(){
//        this.addPlayerToDatabases();
//        this.initiateMySQLDownloads();
//        this.statsDoerThing();
    }

    protected void fighterDied(){

    }

    protected void fighterLeftServer(){
        this.uploadFighter();
    }

    protected void fighterChangedWorld(){

    }

    protected void fighterRespawned(){
        //this.resetSpecialAbility();
    }

    protected void addPlayerToDatabases(){
//        if (!VanaByte.databaseManager.playerExists(player)) {
//            VanaByte.databaseManager.addScore(player);
//        }
//        initiateMySQLDownloads();
    }

    protected void initiateMySQLDownloads() {
//        if (VanaByte.databaseManager.playerExists(player)) {
//            this.downloadFighter_Hub();
//            this.updateName();
//        } else {
//            //error has occurred, player should have been added
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Hub download");
//            player.kickPlayer("MySQL error, contact server owners");
//            return;
//        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighter_Hub() {
//        fighter.setCakes(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[2]));
//        fighter.setFighterLevel(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[3]));
//        fighter.setFighterXP(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[4]));
//        fighter.setKitID(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[5]));
//        fighter.setKills(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[6]));
//        fighter.setKillStreak(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[7]));
//        fighter.setDeaths(VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[8]));
//
//        for(int i = 0; i < this.unlockedKits.length; i++){
//            this.unlockedKits[i] = VanaByte.databaseManager.getStat(player, VanaByte.databaseManager.column[9 + i]);
//        }
    }

    //If the stat is a -1 it wont upload anything
    protected void uploadFighter() {
//        if (VanaByte.databaseManager.playerExists(player)) {
//            this.uploadFighter_Hub();
//        }
    }

    protected void deleteMeFromDatabase(){
//        VanaByte.databaseManager.deletePlayerFromTable(this.player);
        //player.kickPlayer("Your stats have been cleared");
    }

    private void uploadFighter_Hub(){
//        if (VanaByte.databaseManager == null){
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Hub upload");
//            player.kickPlayer("MySQL error, contact server owners");
//        }
//        if(fighter.getCakes() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[2], fighter.getCakes());
//        }
//        if(fighter.getFighterLevel() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[3], fighter.getFighterLevel());
//        }
//        if(fighter.getFighterXP() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[4], fighter.getFighterXP());
//        }
//        if(fighter.getKitID() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[5], fighter.getKitID());
//        }
//        if(fighter.getKills() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[6], fighter.getKills());
//        }
//        if(fighter.getKillStreak() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[7], fighter.getKillStreak());
//        }
//        if(fighter.getDeaths() != -1){
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[8], fighter.getDeaths());
//        }
//        for(int i = 0; i < this.unlockedKits.length; i++){
//            if(this.unlockedKits[i] == -1){
//                continue;
//            }
//            VanaByte.databaseManager.setStat(player.getUniqueId().toString(), VanaByte.databaseManager.column[9 + i], this.unlockedKits[i]);
//        }
    }


    private void updateName() {
//        VanaByte.databaseManager.updateName(player, VanaByte.databaseManager.column[1], player.getName());
    }

    public boolean getUnlockedKit(KitType kitType) {
        //todo
        return true;
    }

    public void setUnlockedKit(KitType kit) {
        //todo
    }

    public HashMap<WeaponType, Double[]> getFIGHTER_STATS() {
        return FIGHTER_STATS;
    }
}

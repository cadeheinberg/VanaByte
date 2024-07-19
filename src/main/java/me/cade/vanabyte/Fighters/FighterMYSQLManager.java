package me.cade.vanabyte.Fighters;

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
                    VanaByte.sendConsoleError("FighterMYSQLManager.java", "invalid stat tried to be used");
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
            FIGHTER_STATS.put(WeaponType.values()[i], buildStatTable(WeaponType.values()[i].getStatBundle().getStats(), WeaponType.values()[i].getStatBundle().getUpgradeLevels()));
        }

        //if player is in the mysql
        //check if the WeaponType stats structure matches whats stored in mysql
        //if not, the mysql will need to be restructured somehow
    }

    protected FighterMYSQLManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    protected void fighterJoined(){
        this.addPlayerToDatabases();
        this.initiateMySQLDownloads();
        this.statsDoerThing();
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
        if (!VanaByte.mySQL_Hub.playerExists(player)) {
            VanaByte.mySQL_Hub.addScore(player);
        }
//        if (!VanaByte.mySQL_upgrades.playerExists(player)) {
//            VanaByte.mySQL_upgrades.addScore(player);
//        }
//        if (!VanaByte.mySQL_royale.playerExists(player)) {
//            VanaByte.mySQL_royale.addScore(player);
//        }
        initiateMySQLDownloads();
    }
    protected void initiateMySQLDownloads() {
        if (VanaByte.mySQL_Hub.playerExists(player)) {
            this.downloadFighter_Hub();
            this.updateName();
        } else {
            //error has occurred, player should have been added
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Hub download");
            player.kickPlayer("MySQL error, contact server owners");
            return;
        }
//        if (VanaByte.mySQL_upgrades.playerExists(player)) {
//            this.downloadFighter_Upgrades();
//        } else {
//            //error has occurred, player should have been added
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Upgrades download");
//            player.kickPlayer("MySQL error, contact server owners");
//            return;
//        }
//        if (VanaByte.mySQL_royale.playerExists(player)) {
//            this.downloadFighter_Royale();
//        } else {
//            //error has occurred, player should have been added
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Royale download");
//            player.kickPlayer("MySQL error, contact server owners");
//            return;
//        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighter_Hub() {
        fighter.setCakes(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[2]));
        fighter.setFighterLevel(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[3]));
        fighter.setFighterXP(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[4]));
        fighter.setKitID(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[5]));
        fighter.setKills(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[6]));
        fighter.setKillStreak(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[7]));
        fighter.setDeaths(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[8]));

        for(int i = 0; i < this.unlockedKits.length; i++){
            this.unlockedKits[i] = VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[9 + i]);
        }
    }

    //If the stat is a -1 it wont upload anything
    protected void uploadFighter() {
        if (VanaByte.mySQL_Hub.playerExists(player)) {
            this.uploadFighter_Hub();
        }
//        if (VanaByte.mySQL_upgrades.playerExists(player)) {
//            this.UploadFighter_Upgrades();
//        }
//        if (VanaByte.mySQL_royale.playerExists(player)) {
//            this.uploadFighter_Royale();
//        }
    }

    protected void deleteMeFromDatabase(){
        VanaByte.mySQL_Hub.deletePlayerFromTable(this.player);
        VanaByte.mySQL_royale.deletePlayerFromTable(this.player);
        VanaByte.mySQL_upgrades.deletePlayerFromTable(this.player);
        //player.kickPlayer("Your stats have been cleared");
    }

    private void uploadFighter_Hub(){
        if (VanaByte.mySQL_Hub == null){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Hub upload");
            player.kickPlayer("MySQL error, contact server owners");
        }
        if(fighter.getCakes() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[2], fighter.getCakes());
        }
        if(fighter.getFighterLevel() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[3], fighter.getFighterLevel());
        }
        if(fighter.getFighterXP() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[4], fighter.getFighterXP());
        }
        if(fighter.getKitID() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[5], fighter.getKitID());
        }
        if(fighter.getKills() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[6], fighter.getKills());
        }
        if(fighter.getKillStreak() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[7], fighter.getKillStreak());
        }
        if(fighter.getDeaths() != -1){
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[8], fighter.getDeaths());
        }
        for(int i = 0; i < this.unlockedKits.length; i++){
            if(this.unlockedKits[i] == -1){
                continue;
            }
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[9 + i], this.unlockedKits[i]);
        }
    }

//    private void uploadFighter_Royale(){
//        if (VanaByte.mySQL_royale == null){
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Royale upload");
//            player.kickPlayer("MySQL error, contact server owners");
//        }
//        //pass
//    }
//
//    private void UploadFighter_Upgrades(){
//        if (VanaByte.mySQL_upgrades == null){
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Upgrades upload");
//            player.kickPlayer("MySQL error, contact server owners");
//        }
//        for(int i = 1; i <= fighter.fighterKitManager.getKitUpgrades().length; i++){
//            if(fighter.fighterKitManager.getKitUpgradesRaw(i-1) == -1){
//                continue;
//            }
//            VanaByte.mySQL_upgrades.setStat(player.getUniqueId().toString(), VanaByte.mySQL_upgrades.column[i], fighter.fighterKitManager.getKitUpgradesRaw(i-1));
//        }
//    }

    private void updateName() {
        VanaByte.mySQL_Hub.updateName(player, VanaByte.mySQL_Hub.column[1], player.getName());
    }

    public boolean getUnlockedKit(int kitID) {
        return unlockedKits[kitID] == 1;
    }

    public void setUnlockedKit(int kitID) {
        fighter.fighterPurchasedKit();
        unlockedKits[kitID] = 1;
    }

    public HashMap<WeaponType, Double[]> getFIGHTER_STATS() {
        return FIGHTER_STATS;
    }
}

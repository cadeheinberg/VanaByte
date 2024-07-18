package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FighterMYSQLManager {

    private Player player = null;
    private Fighter fighter = null;
    private final int[] unlockedKits = new int[7];

    //when giving a weapon say beserker axe, use its kit ID to index FIGHTER_STATS[KIT_ID][Y] where Y
    //is the specific stat you need from the BLUEPRINT. Here we only store the highest unlocked stat in a vector

    //FIGHTER_UNLOCKS will be incrememnted upon purchases, and stored in the MYSQL database, probably store
    //a single array per kit instead of entire [][] thing. in case you make changes to a kit
    //it will probably be easier to fix players using old stat tables. man this is alot of work.
    private Double[][] FIGHTER_STATS = new Double[WeaponType.values().length - 1][];
    private Integer[][] FIGHTER_UNLOCKS = new Integer[WeaponType.values().length - 1][];

    private Double[] buildStatTable(int i, Double[][] STATS_FROM_BLUEPRINT, Integer[] UNLOCKED_FROM_BLUEPRINT){
        //Integer[X] = Y means that upgrade Double[X][Y] has been unlocked. First level of each unlocked here.
        //Integer[X] = -1 means that this skill has not been unlocked at all
        Double[] outputStats = new Double[UNLOCKED_FROM_BLUEPRINT.length];
        Integer[] outputUnlocks = new Integer[UNLOCKED_FROM_BLUEPRINT.length];
        for(int X = 0; X < UNLOCKED_FROM_BLUEPRINT.length; X++){
            if(UNLOCKED_FROM_BLUEPRINT[X] <= 0){
                //skill has not been unlocked at all
                outputStats[X] = -1.0;
            }else{
                int Y = UNLOCKED_FROM_BLUEPRINT[X];
                if(STATS_FROM_BLUEPRINT[X][Y] <= 0){
                    //somehow unlocked an invalid stat, just use base and report issue
                    outputStats[X] = STATS_FROM_BLUEPRINT[X][0];
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CADE1: FighterMYSQLManager.java");
                }else{
                    outputStats[X] = STATS_FROM_BLUEPRINT[X][Y];
                }
            }
        }
        FIGHTER_STATS[i] = outputStats;
        FIGHTER_UNLOCKS[i] = UNLOCKED_FROM_BLUEPRINT;
        return outputStats;
    }
    //fill stats to use for kits and weapons later
    private void statsDoerThing(){
        //if player is not in the mysql table
        //build "stats" using WeaponType blueprint
        for(int i = 0; i < FIGHTER_STATS.length; i++){
            buildStatTable(i, WeaponType.values()[i].getStats(), WeaponType.values()[i].getUnlocked());
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

}

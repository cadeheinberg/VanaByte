package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FighterMYSQLManager {

    private Player player = null;
    private Fighter fighter = null;

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
        if (!VanaByte.mySQL_upgrades.playerExists(player)) {
            VanaByte.mySQL_upgrades.addScore(player);
        }
        if (!VanaByte.mySQL_royale.playerExists(player)) {
            VanaByte.mySQL_royale.addScore(player);
        }
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
        if (VanaByte.mySQL_upgrades.playerExists(player)) {
            this.downloadFighter_Upgrades();
        } else {
            //error has occurred, player should have been added
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Upgrades download");
            player.kickPlayer("MySQL error, contact server owners");
            return;
        }
        if (VanaByte.mySQL_royale.playerExists(player)) {
            this.downloadFighter_Royale();
        } else {
            //error has occurred, player should have been added
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Royale download");
            player.kickPlayer("MySQL error, contact server owners");
            return;
        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighter_Hub() {
        fighter.setCakes(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[2]));
        fighter.setFighterLevel(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[3]));
        fighter.setFighterXP(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[4]));
        fighter.fighterKitManager.setKitID(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[5]));
        fighter.setKills(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[6]));
        fighter.setKillStreak(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[7]));
        fighter.setDeaths(VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[8]));

        for(int i = 0; i < fighter.fighterKitManager.getUnlockedKits().length; i++){
            fighter.fighterKitManager.setUnlockedKit(i, VanaByte.mySQL_Hub.getStat(player, VanaByte.mySQL_Hub.column[9 + i]));
        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighter_Upgrades(){
        for(int i = 1; i <= fighter.fighterKitManager.getKitUpgrades().length; i++){
            fighter.fighterKitManager.setKitUpgradesRaw(i-1, VanaByte.mySQL_upgrades.getStat(player, VanaByte.mySQL_upgrades.column[i]));
        }
    }

    protected void downloadFighter_Royale(){
        //pass
    }

    //If the stat is a -1 it wont upload anything
    protected void uploadFighter() {
        this.uploadFighter_Hub();
        this.UploadFighter_Upgrades();
        this.uploadFighter_Royale();
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
        for(int i = 0; i < fighter.fighterKitManager.getUnlockedKits().length; i++){
            if(fighter.fighterKitManager.getUnlockedKit(0 + i) == -1){
                continue;
            }
            VanaByte.mySQL_Hub.setStat(player.getUniqueId().toString(), VanaByte.mySQL_Hub.column[9 + i], fighter.fighterKitManager.getUnlockedKit(0 + i));
        }
    }

    private void uploadFighter_Royale(){
        if (VanaByte.mySQL_royale == null){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Royale upload");
            player.kickPlayer("MySQL error, contact server owners");
        }
        //pass
    }

    private void UploadFighter_Upgrades(){
        if (VanaByte.mySQL_upgrades == null){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL_Upgrades upload");
            player.kickPlayer("MySQL error, contact server owners");
        }
        for(int i = 1; i <= fighter.fighterKitManager.getKitUpgrades().length; i++){
            if(fighter.fighterKitManager.getKitUpgradesRaw(i-1) == -1){
                continue;
            }
            VanaByte.mySQL_upgrades.setStat(player.getUniqueId().toString(), VanaByte.mySQL_upgrades.column[i], fighter.fighterKitManager.getKitUpgradesRaw(i-1));
        }
    }

    private void updateName() {
        VanaByte.mySQL_Hub.updateName(player, VanaByte.mySQL_Hub.column[1], player.getName());
    }

}

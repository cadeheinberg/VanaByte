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

    protected void addPlayerToDatabases(){
        if (!VanaByte.mysql.playerExists(player)) {
            VanaByte.mysql.addScore(player);
        }
        if (!VanaByte.mySQL_upgrades.playerExists(player)) {
            VanaByte.mySQL_upgrades.addScore(player);
        }
        initiateMySQLDownloads();
    }
    protected void initiateMySQLDownloads() {
        if (VanaByte.mysql.playerExists(player)) {
            this.downloadFighter();
            this.updateName();
        } else {
            //error has occurred, player should have been added
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL");
            Bukkit.getServer().shutdown();
            return;
        }
        if (VanaByte.mySQL_upgrades.playerExists(player)) {
            this.downloadFighterUpgrades();
        } else {
            //error has occurred, player should have been added
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something wrong with MySQL");
            Bukkit.getServer().shutdown();
            return;
        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighter() {
        fighter.fighterKitManager.setKitID(VanaByte.mysql.getStat(player, VanaByte.mysql.column[2]));
        //fighter.fighterKitManager.setKitIndex(VanaByte.mysql.getStat(player, VanaByte.mysql.column[3]));
        fighter.setPlayerLevel(VanaByte.mysql.getStat(player, VanaByte.mysql.column[4]));
        fighter.setKills(VanaByte.mysql.getStat(player, VanaByte.mysql.column[5]));
        fighter.setKillStreak(VanaByte.mysql.getStat(player, VanaByte.mysql.column[6]));
        fighter.setDeaths(VanaByte.mysql.getStat(player, VanaByte.mysql.column[7]));
        fighter.setCakes(VanaByte.mysql.getStat(player, VanaByte.mysql.column[8]));
		//this.setExp(VanaByte.mysql.getStat(player, VanaByte.mysql.column[9]));
        for(int i = 0; i < fighter.fighterKitManager.getUnlockedKits().length; i++){
            fighter.fighterKitManager.setUnlockedKit(i, VanaByte.mysql.getStat(player, VanaByte.mysql.column[10 + i]));
        }
    }

    //Downloads negative -1s if it couldnt get the stat
    protected void downloadFighterUpgrades(){
        for(int i = 1; i <= fighter.fighterKitManager.getKitUpgrades().length; i++){
            fighter.fighterKitManager.setKitUpgradesRaw(i-1, VanaByte.mySQL_upgrades.getStat(player, VanaByte.mySQL_upgrades.column[i]));
        }
    }

    //If the stat is a -1 it wont upload anything
    protected void uploadFighter() {
        if(fighter.fighterKitManager.getKitID() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[2], fighter.fighterKitManager.getKitID());
        }
//        if(this.getKitIndex() != -1){
//            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[3], this.getKitIndex());
//        }
        if(fighter.getPlayerLevel() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[4], fighter.getPlayerLevel());
        }
        if(fighter.getKills() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[5], fighter.getKills());
        }
        if(fighter.getKillStreak() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[6], fighter.getKillStreak());
        }
        if(fighter.getDeaths() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[7], fighter.getDeaths());
        }
        if(fighter.getCakes() != -1){
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[8], fighter.getCakes());
        }
//		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[9], this.getExp());
        for(int i = 0; i < fighter.fighterKitManager.getUnlockedKits().length; i++){
            if(fighter.fighterKitManager.getUnlockedKit(0 + i) == -1){
                continue;
            }
            VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[10 + i], fighter.fighterKitManager.getUnlockedKit(0 + i));
        }
        for(int i = 1; i <= fighter.fighterKitManager.getKitUpgrades().length; i++){
            if(fighter.fighterKitManager.getKitUpgradesRaw(i-1) == -1){
                continue;
            }
            VanaByte.mySQL_upgrades.setStat(player.getUniqueId().toString(), VanaByte.mySQL_upgrades.column[i], fighter.fighterKitManager.getKitUpgradesRaw(i-1));
        }
    }

    private void updateName() {
        VanaByte.mysql.updateName(player, VanaByte.mysql.column[1], player.getName());
    }

}

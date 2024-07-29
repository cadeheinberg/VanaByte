package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Enums.StatRow;
import me.cade.vanabyte.Fighters.Enums.StatTable;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.MySQL.DatabaseTable;
import me.cade.vanabyte.MySQL.FighterColumn;
import me.cade.vanabyte.MySQL.FighterTable;
import me.cade.vanabyte.VanaByte;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FighterMYSQLManager {

    private Player player = null;
    private Fighter fighter = null;
    private FighterTable fighterTable;
    private FighterTable unlockedKitsTable;
    private FighterTable[] weaponTables = new FighterTable[DatabaseTable.getWeaponTables().length];

    private HashMap<KitType, Boolean> INDEX_unlockedKits = new HashMap<>();
    private HashMap<WeaponType, Double[]> INDEX_weaponStats = new HashMap<>();

    private boolean buildINDEX_weaponStats(){
        //Integer[X] = Y means that upgrade Double[X][Y] has been unlocked. First level of each unlocked here.
        //Integer[X] = -1 means that this skill has not been unlocked at all
        for (int i = 0; i < weaponTables.length; i++){
            Double[] outputStats = new Double[weaponTables[i].getFighterColumns().size()];
            WeaponType weaponType = WeaponType.getWeaponTypeFromID(weaponTables[i].getDatabaseTable().getTableName());
            if(weaponType == null) return false;
            for(int X = 0; X < outputStats.length; X++){
                FighterColumn fColumn = weaponTables[i].getFighterColumns().get(X);
                String fColumnName = fColumn.getDatabaseColumn().getColumnName();
                StatRow statRow = weaponType.getStatTable().getStatRowWithName(fColumnName);
                if(statRow == null) continue;
                int statRowIndex = weaponType.getStatTable().getStatRowIndexWithName(fColumnName);
                if(statRowIndex == -1) return false;
                int databaseUpgradeLevel = fColumn.getValueInt();
                if(databaseUpgradeLevel < 0){
                    //skill has not been unlocked at all
                    outputStats[statRowIndex] = -1.0;
                }else{
                    if(databaseUpgradeLevel >= statRow.getStats().length || statRow.getStats()[databaseUpgradeLevel] < 0){
                        //somehow unlocked an invalid stat, just use base and report issue
                        outputStats[X] = statRow.getStats()[0];
                        VanaByte.sendConsoleMessageBad("FighterMYSQLManager.java", "invalid stat tried to be used");
                        return false;
                    }else{
                        outputStats[X] = statRow.getStats()[databaseUpgradeLevel];
                    }
                }
            }
            INDEX_weaponStats.put(weaponType, outputStats);
        }
        return true;
    }

    private boolean buildINDEX_unlockedKits(){
        for(FighterColumn fighterColumn : unlockedKitsTable.getFighterColumns()){
            KitType kitType = KitType.getKitTypeFromKitID(fighterColumn.getValueString());
            if(kitType != null){
                INDEX_unlockedKits.put(kitType, true);
            }
        }
        return true;
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
        if(!buildINDEX_weaponStats()){
            VanaByte.sendConsoleMessageBad("FighterMYSQLManager.java", "error with buildINDEX_weaponStats()");
        }
        if(!buildINDEX_unlockedKits()){
            VanaByte.sendConsoleMessageBad("FighterMYSQLManager.java", "error with buildINDEX_unlockedKits()");
        }
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

    public boolean hasUnlockedKitType(KitType kitType) {
        if(INDEX_unlockedKits.get(kitType) == null || INDEX_unlockedKits.get(kitType) == false){
            return false;
        }
        return true;
    }

    public void setHasUnlockedKitType(KitType kitType) {
        fighter.fighterPurchasedKit();
        unlockedKitsTable.addFighterColumn(new FighterColumn(DatabaseTable.getUnlockedKitsTable(), DatabaseTable.getUnlockedKitsTable().getDatabaseColumns()[1], -1, kitType.getKitID()));
        INDEX_unlockedKits.put(kitType, true);
    }

    public HashMap<WeaponType, Double[]> getFIGHTER_STATS() {
        return INDEX_weaponStats;
    }

}

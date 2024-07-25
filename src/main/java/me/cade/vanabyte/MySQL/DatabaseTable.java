package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.Fighters.Enums.StatRow;
import me.cade.vanabyte.Fighters.Enums.StatTable;
import me.cade.vanabyte.Fighters.Enums.WeaponType;

import java.util.List;

public class DatabaseTable {

    private static List<DatabaseTable> allTables;

    private final String tableName;
    private final DatabaseColumn[] databaseColumns;

    private static DatabaseTable fighterTable = null;
    private static DatabaseTable unlockedKitsTable = null;
    private static DatabaseTable[] weaponTables = null;

    public DatabaseTable(String tableName, DatabaseColumn[] databaseColumns){
        this.tableName = tableName;
        this.databaseColumns = databaseColumns;
    }

    public String getTableName() {
        return tableName;
    }

    public DatabaseColumn[] getDatabaseColumns() {
        return databaseColumns;
    }

    public static void makeDatabaseTablesObjects(){
        DatabaseColumn[] fighterColumns = new DatabaseColumn[6];
        fighterColumns[0] = new DatabaseColumn("uuid", true, false, null, false, true, 0, "func_uuid");
        fighterColumns[1] = new DatabaseColumn("player_name", false, false, null, false, true, 0, "func_player_name");
        fighterColumns[2] = new DatabaseColumn("kit_id", false, false, null, false, true, 0, "W001");
        fighterColumns[3] = new DatabaseColumn("server_cakes", false, false, null, true, false, 100, null);
        fighterColumns[4] = new DatabaseColumn("server_level", false, false, null, true, false, 1, null);
        fighterColumns[5] = new DatabaseColumn("server_exp", false, false, null, true, false, 0, null);
        fighterTable = new DatabaseTable("Fighter", fighterColumns);
        allTables.add(fighterTable);

        DatabaseColumn[] unlockedKitsColumns = new DatabaseColumn[2];
        unlockedKitsColumns[0] = new DatabaseColumn("uuid", true, true, "Fighter (uuid)", false, true, 0, "func_uuid");
        unlockedKitsColumns[1] = new DatabaseColumn("kit_id", true, false, null, false, true, 0, null);
        unlockedKitsTable = new DatabaseTable("UnlockedKit", unlockedKitsColumns);
        allTables.add(unlockedKitsTable);

        weaponTables = new DatabaseTable[WeaponType.values().length];
        for(int i = 0; i < weaponTables.length; i++){
            WeaponType weaponType = WeaponType.values()[i];
            StatTable statTable = weaponType.getStatTable();
            DatabaseColumn[] weaponColumns = new DatabaseColumn[statTable.getStatRows().length + 1];
            weaponColumns[0] = new DatabaseColumn("uuid", true, true, "Fighter (uuid)", false, true, 0, "func_uuid");
            for (int j = 1; j < weaponColumns.length; j++){
                StatRow statRow = statTable.getStatRows()[j - 1];
                weaponColumns[j] = new DatabaseColumn(statRow.getStatName(), false, false, null, true, false, statRow.getDefaultUpgradeLevel(), null);
            }
            weaponTables[i] = new DatabaseTable(weaponType.getWeaponID(), weaponColumns);
            allTables.add(weaponTables[i]);
        }
    }

    public static DatabaseTable getFighterTable() {
        return fighterTable;
    }

    public static DatabaseTable getUnlockedKitsTable() {
        return unlockedKitsTable;
    }

    public static DatabaseTable[] getWeaponTables() {
        return weaponTables;
    }

    public int getColumnIndexWithStatName(String statName){
        for(int i = 0; i < databaseColumns.length; i++){
            if(databaseColumns[i] == null || databaseColumns[i].getColumnName() == null){
                return -1;
            }
            if(statName.equals(databaseColumns[i].getColumnName())){
                return i;
            }
        }
        return -1;
    }

    public static List<DatabaseTable> getAllTables() {
        return allTables;
    }
}

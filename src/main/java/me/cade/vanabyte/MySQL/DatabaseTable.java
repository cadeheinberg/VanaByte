package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.Fighters.Enums.StatRow;
import me.cade.vanabyte.Fighters.Enums.StatTable;
import me.cade.vanabyte.Fighters.Enums.WeaponType;

public class DatabaseTable {

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
        fighterColumns[0] = new DatabaseColumn("uuid", true, false, null, false, true, 0);
        fighterColumns[1] = new DatabaseColumn("player_name", false, false, null, false, true, 0);
        fighterColumns[2] = new DatabaseColumn("kit_id", false, true, "Unlocked Kit (uuid)", false, true, 0);
        fighterColumns[3] = new DatabaseColumn("server_cakes", false, false, null, true, false, 100);
        fighterColumns[4] = new DatabaseColumn("server_level", false, false, null, true, false, 1);
        fighterColumns[5] = new DatabaseColumn("server_exp", false, false, null, true, false, 0);
        fighterTable = new DatabaseTable("Fighter", fighterColumns);

        DatabaseColumn[] unlockedKitsColumns = new DatabaseColumn[2];
        unlockedKitsColumns[0] = new DatabaseColumn("uuid", true, true, "Fighter (uuid)", false, true, 0);
        unlockedKitsColumns[1] = new DatabaseColumn("kit_id", true, false, null, false, true, 0);
        unlockedKitsTable = new DatabaseTable("UnlockedKit", unlockedKitsColumns);

        weaponTables = new DatabaseTable[WeaponType.values().length];
        for(int i = 0; i < weaponTables.length; i++){
            WeaponType weaponType = WeaponType.values()[i];
            StatTable statTable = weaponType.getStatTable();
            DatabaseColumn[] weaponColumns = new DatabaseColumn[statTable.getStatRows().length + 1];
            weaponColumns[0] = new DatabaseColumn("uuid", true, true, "Fighter (uuid)", false, true, 0);
            for (int j = 1; j < weaponColumns.length; j++){
                StatRow statRow = statTable.getStatRows()[j - 1];
                weaponColumns[j] = new DatabaseColumn(statRow.getStatName(), false, false, null, true, false, statRow.getDefaultUpgradeLevel());
            }
            weaponTables[i] = new DatabaseTable(weaponType.getWeaponID(), weaponColumns);
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
}

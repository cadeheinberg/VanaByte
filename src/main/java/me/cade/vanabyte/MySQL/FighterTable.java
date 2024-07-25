package me.cade.vanabyte.MySQL;

public class FighterTable {

    private final DatabaseTable databaseTable;
    private final FighterColumn[] fighterColumns;

    public FighterTable(DatabaseTable databaseTable, FighterColumn[] fighterColumns){
        this.databaseTable = databaseTable;
        this.fighterColumns = fighterColumns;
    }

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }

    public FighterColumn[] getFighterColumns() {
        return fighterColumns;
    }
}

package me.cade.vanabyte.MySQL;

import java.util.List;

public class FighterTable {

    private final DatabaseTable databaseTable;
    private final List<FighterColumn> fighterColumns;

    public FighterTable(DatabaseTable databaseTable, List<FighterColumn> fighterColumns){
        this.databaseTable = databaseTable;
        this.fighterColumns = fighterColumns;
    }

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }

    public List<FighterColumn> getFighterColumns() {
        return fighterColumns;
    }

    public void addFighterColumn(FighterColumn fighterColumn){
        fighterColumns.add(fighterColumn);
    }
}

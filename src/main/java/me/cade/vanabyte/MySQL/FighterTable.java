package me.cade.vanabyte.MySQL;

import java.util.ArrayList;
import java.util.List;

public class FighterTable {

    private final DatabaseTable databaseTable;
    private final ArrayList<FighterColumn> fighterColumns;

    public FighterTable(DatabaseTable databaseTable, ArrayList<FighterColumn> fighterColumns){
        this.databaseTable = databaseTable;
        this.fighterColumns = fighterColumns;
    }

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }

    public ArrayList<FighterColumn> getFighterColumns() {
        return fighterColumns;
    }

    public void addFighterColumn(FighterColumn fighterColumn){
        fighterColumns.add(fighterColumn);
    }

}

package me.cade.vanabyte.MySQL;

public class FighterColumn {

    private final DatabaseTable databaseTable;
    private final DatabaseColumn databaseColumn;
    private int valueInt;
    private String valueString;

    public FighterColumn(DatabaseTable databaseTable, DatabaseColumn databaseColumn, int valueInt, String valueString){
        this.databaseTable = databaseTable;
        this.databaseColumn = databaseColumn;
        this.valueInt = valueInt;
        this.valueString = valueString;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public DatabaseColumn getDatabaseColumn() {
        return databaseColumn;
    }

    public DatabaseTable getDatabaseTable() {
        return databaseTable;
    }
}

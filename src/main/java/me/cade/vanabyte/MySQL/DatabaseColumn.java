package me.cade.vanabyte.MySQL;

public class DatabaseColumn {

    private final String columnName;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;
    private final String foreignReferences;
    private final boolean isInt;
    private final boolean isVarChar;
    private final int defaultValueInt;
    private final String defaultValueString;

    public DatabaseColumn(String columnName, boolean isPrimaryKey, boolean isForeignKey, String foreignReferences, boolean isInt, boolean isVarChar, int defaultValueInt, String defaultValueString){
        if(isForeignKey && foreignReferences == null){
            throw new RuntimeException("DatabaseColumn: isForeignKey cant be true without giving a foreign reference");
        }
        if(isInt && isVarChar){
            throw new RuntimeException("DatabaseColumn: isInt and isVarChar cant both be true or false");
        }
        this.columnName = columnName;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.foreignReferences = foreignReferences;
        this.isInt = isInt;
        this.isVarChar = isVarChar;
        this.defaultValueInt = defaultValueInt;
        this.defaultValueString = defaultValueString;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }

    public String getForeignReferences() {
        return foreignReferences;
    }

    public boolean isInt() {
        return isInt;
    }

    public boolean isVarChar() {
        return isVarChar;
    }

    public int getDefaultValueInt() {
        return defaultValueInt;
    }

    public String getDefaultValueString() {
        return defaultValueString;
    }
}

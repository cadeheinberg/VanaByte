package me.cade.vanabyte.MySQL;

public class DatabaseColumn {

    private final String columnName;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;
    private final String foreignReferences;
    private final boolean isInt;
    private final boolean isVarChar;
    private final int defaultValue;

    public DatabaseColumn(String columnName, boolean isPrimaryKey, boolean isForeignKey, String foreignReferences, boolean isInt, boolean isVarChar, int defaultValue){
        this.columnName = columnName;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.foreignReferences = foreignReferences;
        this.isInt = isInt;
        this.isVarChar = isVarChar;
        this.defaultValue = defaultValue;
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

    public int getDefaultValue() {
        return defaultValue;
    }
}

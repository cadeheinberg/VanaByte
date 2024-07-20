package me.cade.vanabyte.MySQL;

public class DatabaseTable {

    private final String tableName;
    private final DatabaseColumn[] databaseColumns;

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
}

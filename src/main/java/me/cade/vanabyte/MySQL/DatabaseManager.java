package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.MySQL.HiddenPersonal.DatabaseAccess;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DatabaseManager {

  private Connection connection;
  private String url;
  private String username;
  private String password;
  private int port;

  private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);

  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void connect() throws ClassNotFoundException, SQLException {
    connection = DriverManager.getConnection(url, username, password);
  }

  public boolean isConnectionValid() throws SQLException {
    return connection != null && connection.isValid(5);
  }

  private boolean doubleCheckAndDoQuery(String SQL_QUERY) throws SQLException {
    Statement statement = connection.createStatement();
    System.out.println("type yes to confirm " + SQL_QUERY);
    Scanner scanner = new Scanner(System.in);
    String USER_INPUT = scanner.nextLine();
    while (true) {
      if (USER_INPUT.equals("yes")) {
        statement.execute(SQL_QUERY);
        statement.close();
        break;
      } else if (USER_INPUT.equals("vana")) {
        return false;
      } else {
        System.out.println("type vana to exit");
        continue;
      }
    }
    return true;
  }

  public DatabaseManager() {
    url = DatabaseAccess.getURL();
    port = DatabaseAccess.getPort();
    username = DatabaseAccess.getUsername();
    password = DatabaseAccess.getPassword();

    try {
      Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL: CONNECTING...");
      connect();
      Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: CONNECTED");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL: ERROR 1");
    } catch (SQLException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL: ERROR 2");
    }

    DatabaseBackup.backupDatabase();

    DatabaseTable.makeDatabaseTablesObjects();

    List<DatabaseTable> code_TableNames = new ArrayList<>();
    code_TableNames.add(DatabaseTable.getFighterTable());
    code_TableNames.add(DatabaseTable.getUnlockedKitsTable());
    code_TableNames.addAll(Arrays.asList(DatabaseTable.getWeaponTables()));

    VanaByte.sendConsoleMessageWarning("DatabaseManager Tables", "Comparing code to DB schema...");
    List<String> db_TableNames = new ArrayList<>();
    try {
      db_TableNames = getNamesOfEveryTableInDatabase();
    } catch (SQLException e) {
      VanaByte.sendConsoleMessageBad("DatabaseManager Tables", "getNamesOfEveryTableInDatabase error");
      throw new RuntimeException(e);
    }
    try {
      if(!synchTableNames(code_TableNames, db_TableNames)){
        VanaByte.sendConsoleMessageBad("DatabaseManager Tables", "doTableNameMatching error");
        return;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    VanaByte.sendConsoleMessageGood("DatabaseManager Tables", "Code and DB have compatible schema");

    VanaByte.sendConsoleMessageWarning("DatabaseManager Columns", "Comparing code to DB schema...");
    try {
      if(!syncColumnNames(DatabaseTable.getFighterTable())){
        // table not successfully created
        VanaByte.sendConsoleMessageBad("DatabaseManager Columns", "sync " + DatabaseTable.getFighterTable().getTableName() + " columns error");
        return;
      }
      if(!syncColumnNames(DatabaseTable.getUnlockedKitsTable())) {
        // table not successfully created
        VanaByte.sendConsoleMessageBad("DatabaseManager Columns", "sync " + DatabaseTable.getUnlockedKitsTable().getTableName() + " columns error");
        return;
      }
      for (DatabaseTable dt : DatabaseTable.getWeaponTables()){
        if(!syncColumnNames(dt)) {
          VanaByte.sendConsoleMessageBad("DatabaseManager Columns", "sync " + dt.getTableName() + " columns error");
          return;
        }
      }
    } catch (SQLException e){
      e.printStackTrace();
      System.out.println("Error creating table");
    }
    VanaByte.sendConsoleMessageGood("DatabaseManager Columns", "Code and DB have compatible schema");
  }

  private List<String> getNamesOfEveryTableInDatabase() throws SQLException {
    List<String> tableNamesList = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = '" + DatabaseAccess.getDatabase() + "';";
    ResultSet resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
      tableNamesList.add(resultSet.getString("table_name"));
    }
    resultSet.close();
    statement.close();
    return tableNamesList;
  }

  private boolean synchTableNames(List<DatabaseTable> code_TableNames, List<String> db_TableNames) throws SQLException {
    String SQL_QUERY = "";
    Scanner scanner = new Scanner(System.in);
    List<String> matches = new ArrayList<>();
    List<DatabaseTable> matches_Tables = new ArrayList<>();
    for(DatabaseTable code_TableName : code_TableNames){
      if(db_TableNames.contains(code_TableName.getTableName())){
        matches.add(code_TableName.getTableName());
        matches_Tables.add(code_TableName);
      }
    }
    code_TableNames.removeAll(matches_Tables);
    db_TableNames.removeAll(matches);
    for(DatabaseTable code_TableName : code_TableNames){
      System.out.println("Table name \"" + code_TableName.getTableName() + "\" exists in code but not in database");
      System.out.println("1. make a new table");
      int i = 2;
      for (String db_TableName : db_TableNames){
        System.out.println(i + ". rename \"" + db_TableName + "\" from database to \"" + code_TableName.getTableName() + "\"");
        i++;
      }
      while (true){
        String USER_INPUT = scanner.nextLine();
        System.out.println("You entered '" + USER_INPUT + "'");
        int USER_INTEGER = 0;
        if(USER_INPUT.equals("vana")){
          scanner.close();
          return false;
        }
        try {
          USER_INTEGER = Integer.parseInt(USER_INPUT);
        } catch (NumberFormatException e){
          System.out.println("type vana to exit");
          continue;
        }
        if (USER_INTEGER == 1){
          //create new table code_TableName
          if(!createNewTable(code_TableName)){
            scanner.close();
            return false;
          }
          break;
        }
        else if(USER_INTEGER > 1 && ((USER_INTEGER - 2) < db_TableNames.size())){
          //rename db_TableNames.get(USER_INTERGER - 2) to code_TableName
          SQL_QUERY = "RENAME TABLE " + db_TableNames.get(USER_INTEGER - 2) + " TO " + code_TableName + ";";
          if(!doubleCheckAndDoQuery(SQL_QUERY)){
            System.out.println("canceling table renaming");
            return false;
          }
          System.out.println("table successfully renamed");
          db_TableNames.remove(code_TableName.getTableName());
          break;
        }else{
          System.out.println("type vana to exit");
          continue;
        }
      }
    }
    for (String db_TableName : db_TableNames){
      System.out.println("Table name \"" + db_TableName + "\" exists in database but not in code");
      System.out.println("1. ignore");
      System.out.println("2. delete table");
      while (true){
        String USER_INPUT = scanner.nextLine();
        int USER_INTEGER = 0;
        if(USER_INPUT.equals("vana")){
          scanner.close();
          return false;
        }
        try {
          USER_INTEGER = Integer.parseInt(USER_INPUT);
        } catch (NumberFormatException e){
          System.out.println("type vana to exit");
          continue;
        }
        if (USER_INTEGER == 1){
          break;
        }
        else if(USER_INTEGER == 2){
          SQL_QUERY = "DROP TABLE IF EXISTS " +  db_TableName;
          doubleCheckAndDoQuery(SQL_QUERY);
          break;
        }else{
          System.out.println("type vana to exit");
          continue;
        }
      }
    }
    scanner.close();
    return true;
  }

  // makes a brand new table
  private boolean createNewTable(DatabaseTable databaseTable) throws SQLException {
    Statement statement = connection.createStatement();
    String createTableSQL = "CREATE TABLE IF NOT EXISTS " + databaseTable.getTableName() + " (";
    String primaryKey = "";
    List<String> foreignKeys = new ArrayList<>();
    for(DatabaseColumn databaseColumn : databaseTable.getDatabaseColumns()){
      String dataType;
      String dataDefault;
      if(databaseColumn.isInt()){
        dataType = "int";
        dataDefault = "DEFAULT " + databaseColumn.getDefaultValue();
      } else if (databaseColumn.isVarChar()) {
        dataType = "varchar(100)";
        dataDefault = "NOT NULL";
      } else{
        System.out.println("data type is not correct for this");
        return false;
      }
      if(databaseColumn.isPrimaryKey()){
        primaryKey = primaryKey + databaseColumn.getColumnName() + ", ";
      }
      if(databaseColumn.isForeignKey()){
        foreignKeys.add("FOREIGN KEY (" + databaseColumn.getColumnName() + ") REFERENCES " + databaseColumn.getForeignReferences());
      }
      createTableSQL = createTableSQL + databaseColumn.getColumnName() + " " + dataType + " " + dataDefault + ", ";
    }
    if(primaryKey.isEmpty()){
      System.out.println("primary key shouldnt be empty for a table");
      return false;
    }
    primaryKey = primaryKey.substring(0, primaryKey.length() - 2);
    primaryKey = "PRIMARY KEY (" + primaryKey + ")";
    createTableSQL = createTableSQL + primaryKey;
    if(!foreignKeys.isEmpty()){
      createTableSQL = createTableSQL + ", ";
      for(String fKey : foreignKeys){
        if(foreignKeys.getLast().equals(fKey)){
          createTableSQL = createTableSQL + fKey + ");";
        }else{
          createTableSQL = createTableSQL + fKey + ", ";
        }
      }
    }else{
      createTableSQL = createTableSQL + ");";
    }
    //createTableSQL = createTableSQL.substring(0, createTableSQL.length() - 2);
    System.out.println("SQL TO SEND:" + createTableSQL);
    statement.execute(createTableSQL);
    statement.close();
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: TABLE CREATED!");
    return true;
  }

  private boolean tableExists(String tableName) {
    String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, tableName);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt(1) > 0;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<String> getColumnNamesIfTableExists(String tableName) throws SQLException {
    List<String> columnNames = new ArrayList<>();
    if (tableExists(tableName)) {
      DatabaseMetaData metaData = connection.getMetaData();
      try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
        while (columns.next()) {
          String columnName = columns.getString("COLUMN_NAME");
          columnNames.add(columnName);
        }
      }
    } else {
      System.out.println("Table " + tableName + " does not exist.");
      return null;
    }
    return columnNames;
  }

  private boolean syncColumnNames(DatabaseTable databaseTable) throws SQLException {
    String SQL_QUERY = "";
    boolean tableCreationSuccessful = false;
    Scanner scanner = new Scanner(System.in);
    List<DatabaseColumn> code_ColNames = new ArrayList<>(Arrays.stream(databaseTable.getDatabaseColumns()).toList());
    List<String> db_ColNames = getColumnNamesIfTableExists(databaseTable.getTableName());
    Statement statement = connection.createStatement();
    if (db_ColNames == null) {
      VanaByte.sendConsoleMessageBad("DatabaseManager", "no columns found for " + databaseTable.getTableName() + "!");
      return false;
    }
    List<String> matches = new ArrayList<>();
    List<DatabaseColumn> matches_Column = new ArrayList<>();
    for (DatabaseColumn code_ColName : code_ColNames) {
      if (db_ColNames.contains(code_ColName.getColumnName())) {
        matches.add(code_ColName.getColumnName());
        matches_Column.add(code_ColName);
      }
    }
    code_ColNames.removeAll(matches_Column);
    db_ColNames.removeAll(matches);
    for (DatabaseColumn code_ColName : code_ColNames) {
      System.out.println("Column \"" + code_ColName.getColumnName() + "\" exists in code but not in database");
      System.out.println("1. make a new column");
      int i = 2;
      for (String db_ColName : db_ColNames) {
        System.out.println(i + ". rename \"" + db_ColName + "\" from database to \"" + code_ColName.getColumnName() + "\"");
        i++;
      }
      while (true) {
        String USER_INPUT = scanner.nextLine();
        int USER_INTEGER = 0;
        if (USER_INPUT.equals("vana")) {
          scanner.close();
          return false;
        }
        try {
          USER_INTEGER = Integer.parseInt(USER_INPUT);
        } catch (NumberFormatException e) {
          System.out.println("type vana to exit");
          continue;
        }
        if (USER_INTEGER == 1) {
          //todo create a new row code_ColName in this table
          SQL_QUERY = "ALTER TABLE " + databaseTable.getTableName() + " ADD COLUMN " + code_ColName + " INT DEFAULT " + code_ColName.getDefaultValue();
          if(!doubleCheckAndDoQuery(SQL_QUERY)){
            scanner.close();
            return false;
          }
          break;
        } else if (USER_INTEGER > 1 && ((USER_INTEGER - 2) < db_ColNames.size())) {
          SQL_QUERY = "RENAME TABLE " + db_ColNames.get(USER_INTEGER - 2) + " TO " + code_ColName + ";";
          if (!doubleCheckAndDoQuery(SQL_QUERY)) {
            System.out.println("canceling table renaming");
            scanner.close();
            return false;
          }
          System.out.println("table successfully renamed");
          db_ColNames.remove(code_ColName.getColumnName());
          break;
        } else {
          System.out.println("type vana to exit");
          continue;
        }
      }
    }
    for (String db_ColName : db_ColNames) {
      System.out.println("Column \"" + db_ColName + "\" exists in database but not in code");
      System.out.println("1. ignore");
      System.out.println("2. delete column");
      while (true) {
        String USER_INPUT = scanner.nextLine();
        int USER_INTEGER = 0;
        if (USER_INPUT.equals("vana")) {
          scanner.close();
          return false;
        }
        try {
          USER_INTEGER = Integer.parseInt(USER_INPUT);
        } catch (NumberFormatException e) {
          System.out.println("type vana to exit");
          continue;
        }
        if (USER_INTEGER == 1) {
          //ignore
          break;
        } else if (USER_INTEGER == 2) {
          SQL_QUERY = "ALTER TABLE " + databaseTable.getTableName() + " DROP COLUMN " + db_ColName;
          //statement.executeUpdate(sql);
          if(!doubleCheckAndDoQuery(SQL_QUERY)){
            return false;
          }
          break;
        } else {
          System.out.println("type vana to exit");
          continue;
        }
      }
    }
    scanner.close();
    return true;
  }
}

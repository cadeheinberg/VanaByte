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

    getNamesOfEveryTableInDatabase();

    try{
      //keep calling until its true
      while(createTableFromDatabaseTableObject(DatabaseTable.getFighterTable())){
        //pass
      }
      createTableFromDatabaseTableObject(DatabaseTable.getUnlockedKitsTable());
      for (DatabaseTable dt : DatabaseTable.getWeaponTables()){
        createTableFromDatabaseTableObject(dt);
      }
    } catch (SQLException e){
      e.printStackTrace();
      System.out.println("Error creating table");
    }
  }

  private void getNamesOfEveryTableInDatabase() {
//    SELECT table_name
//    FROM information_schema.tables
//    WHERE table_schema = 'your_database_name';
  }

  private boolean createTableFromDatabaseTableObject(DatabaseTable databaseTable) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    String tableName = databaseTable.getTableName();
    Statement statement = connection.createStatement();
    VanaByte.sendConsoleMessageWarning("DatabaseManager", "checking if " + tableName + " exists");
    String[] DATABASE_COLUMN_NAMES = getColumnNamesIfTableExists(tableName);
    if(DATABASE_COLUMN_NAMES == null){
      VanaByte.sendConsoleMessageGood("DatabaseManager", "table does not exist");
      //prompt user if they would like to create new table, or rename an existing one
      System.out.println("1. Create a new Table in the database");
      String linkedTo = null;
      System.out.println("OR Rename one of the below database columns");
      int i = 2;
      for (String tableName : ALL_TABLES_IN_DATABASE) {
        System.out.println(i + ": " + tableName);
        i++;
      }
      String USER_INPUT = scanner.nextLine();
      int USER_INTEGER;
      try {
        USER_INTEGER = Integer.getInteger(USER_INPUT);
      } catch (Exception e) {
        e.printStackTrace();
        scanner.close();
        return false;
      }
      if (USER_INTEGER == 1) {
        //make a new Table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        String primaryKey = "";
        List<String> foreignKeys = new ArrayList<>();
        for(DatabaseColumn databaseColumn : databaseTable.getDatabaseColumns()){
          String dataType;
          String dataDefault;
          if(databaseColumn.isInt()){
            dataType = "int";
            dataDefault = "" + databaseColumn.getDefaultValue();
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
          createTableSQL = databaseColumn.getColumnName() + " " + dataType + " " + dataDefault + ", ";
        }
        if(primaryKey.isEmpty()){
          System.out.println("primary key shouldnt be empty for a table");
          return false;
        }
        createTableSQL = createTableSQL + primaryKey;
        if(!foreignKeys.isEmpty()){
          for(String fKey : foreignKeys){
            createTableSQL = createTableSQL + fKey;
          }
        }
        createTableSQL = createTableSQL.substring(0, createTableSQL.length() - 2);
        statement.execute(createTableSQL);
        statement.close();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: TABLE CREATED!");
      } else if (USER_INTEGER > 1 && ((USER_INTEGER - 2) < ALL_TABLES_IN_DATABASE.size())) {
        System.out.println("Type yes to confirm renaming of \"" + ALL_TABLES_IN_DATABASE.get(USER_INTEGER - 2) + "\" to \"" + codeName.getColumnName());
        String YES_INPUT = scanner.nextLine();
        if (YES_INPUT.equals("yes")) {
          //rename the old table
//          RENAME TABLE old_table_name TO new_table_name;
          //recursive call
        }else {
          System.out.println("canceled by user");
          scanner.close();
          return;
        }
      } else {
        System.out.println("bad user input");
        scanner.close();
        return;
      }
      createTableFromDatabaseTableObject(databaseTable);
      scanner.close();
      return;
    }
    List<String> codeColNamesWithPartners = new ArrayList<>();
    List<DatabaseColumn> codeColNamesWithoutPartners = new ArrayList<>();
    VanaByte.sendConsoleMessageGood("DatabaseManager", "table exists, checking schema");
    List<DatabaseColumn> codeColNames = Arrays.stream(databaseTable.getDatabaseColumns()).toList(); //.getColName
    List<String> databaseColNames = new ArrayList<>(Arrays.stream(DATABASE_COLUMN_NAMES).toList());
    for(DatabaseColumn codeName : codeColNames){
      if(databaseColNames.contains(codeName.getColumnName())){
        codeColNamesWithPartners.add(codeName.getColumnName());
      }else{
        codeColNamesWithoutPartners.add(codeName);
      }
    }
    if(!codeColNamesWithoutPartners.isEmpty()) {
      databaseColNames.removeAll(codeColNamesWithPartners);
      for (DatabaseColumn codeName : codeColNamesWithoutPartners) {
        System.out.println("Column name given in code: " + codeName.getColumnName() + " was not found in Table: " + databaseTable.getTableName());
        System.out.println("1. Create a new column in the table");
        String linkedTo = null;
        System.out.println("OR Rename one of the below database columns");
        int i = 2;
        for (String remainingDatabaseCol : databaseColNames) {
          System.out.println(i + ": " + remainingDatabaseCol);
          i++;
        }
        String USER_INPUT = scanner.nextLine();
        int USER_INTEGER;
        try {
          USER_INTEGER = Integer.getInteger(USER_INPUT);
        } catch (Exception e) {
          e.printStackTrace();
          scanner.close();
          return;
        }
        if (USER_INTEGER == 1) {
          //make a new column using codeName
        } else if (USER_INTEGER > 1 && ((USER_INTEGER - 2) < databaseColNames.size())) {
          System.out.println("Type yes to confirm renaming of \"" + databaseColNames.get(USER_INTEGER - 2) + "\" to \"" + codeName.getColumnName());
          String YES_INPUT = scanner.nextLine();
          if (YES_INPUT.equals("yes")) {
            //rename the database column
//            ALTER TABLE table_name
//            CHANGE COLUMN old_column_name new_column_name datatype;
            //remove it from list we are working with.
            databaseColNames.remove(USER_INTEGER - 2);
          }else {
            System.out.println("canceled by user");
            scanner.close();
            return;
          }
        } else {
          System.out.println("bad user input");
          scanner.close();
          return;
        }
      }
      if (!databaseColNames.isEmpty()) {
        //there are database columns we arent using anymore
        for (String dataName : databaseColNames) {
          System.out.println("Database has column name: " + dataName + " leftover");
          System.out.println("Type yes to keep and no to remove");
          String YES_INPUT = scanner.nextLine();
          if (YES_INPUT.equals("yes")) {

          } else if (YES_INPUT.equals("no")) {

          } else {
            System.out.println("bad user input");
            scanner.close();
            return;
          }
        }
        createTableFromDatabaseTableObject(databaseTable);
        scanner.close();
        return;
      }
      VanaByte.sendConsoleMessageGood("DatabaseManager", "Table and schema is okay");
      scanner.close();
    }
  }

  public String[] getColumnNamesIfTableExists(String tableName) throws SQLException {
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

    return columnNames.toArray(new String[0]);
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

  //SET THE DEFAULT VALUES FOR EACH STAT HERE
  public void addScore(Player player) {
    String insertStatement = "INSERT INTO " + tableName + "(";
    for (int i = 0; i < column.length; i++) {
      if (i == column.length - 1) {
        insertStatement = insertStatement + column[i] + ")";
      } else {
        insertStatement = insertStatement + column[i] + ",";
      }
    }
    insertStatement = insertStatement + " VALUES (";
    for (int i = 0; i < column.length; i++) {
      if (i == column.length - 1) {
        insertStatement = insertStatement + "?)";
      } else {
        insertStatement = insertStatement + "?,";
      }
    }
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(insertStatement);
      statement.setString(1, player.getUniqueId().toString());
      statement.setString(2, player.getName());
      statement.setInt(3, 500);
      statement.setInt(4, 1);
      statement.setInt(5, 0);
      statement.setInt(6, 0);
      statement.setInt(7, 0);
      statement.setInt(8, 0);
      statement.setInt(9, 0);
      //KITS
      statement.setInt(10, 1);
      statement.setInt(11, 1);
      statement.setInt(12, 0);
      statement.setInt(13, 0);
      statement.setInt(14, 0);
      statement.setInt(15, 0);
      statement.setInt(16, 0);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public void deletePlayerFromTable(Player player) {
    PreparedStatement statement;
    try {
      statement = connection
              .prepareStatement("DELETE FROM " + tableName + " WHERE " + column[0] + " = ?");
      statement.setString(1, player.getUniqueId().toString());
      statement.execute();
      player.sendMessage("You have been cleared from kitpvp stats");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public int getStat(Player player, String stat) {
    int getter = -1;
    PreparedStatement statement;
    try {
      statement = connection
        .prepareStatement("SELECT " + stat + " FROM " + tableName + " WHERE " + column[0] + " = ?");
      statement.setString(1, player.getUniqueId().toString());
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        getter = result.getInt(stat);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return getter;
  }
  
  public void setStat(String uuid, String stat, int setter) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
      statement.setString(2, uuid);
      statement.setInt(1, setter);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateName(Player player, String stat, String setter) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
      statement.setString(2, player.getUniqueId().toString());
      statement.setString(1, setter);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void incOfflineStat(String name, String stat, int setter) {
    // GET THE AMOUNT OF STAT
    int getter = 0;
    {
      PreparedStatement statement;
      try {
        statement = connection.prepareStatement(
          "SELECT " + stat + " FROM " + tableName + " WHERE " + column[1] + " = ?");
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
          getter = result.getInt(stat);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    // INCREASE THE AMOUNT OF STAT
    {
      PreparedStatement statement;
      try {
        statement = connection.prepareStatement(
          "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[1] + " = ?");
        statement.setString(2, name);
        statement.setInt(1, setter + getter);
        statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean playerExists(Player player) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "SELECT " + column[2] + " FROM " + tableName + " WHERE " + column[0] + " = ?");
      statement.setString(1, player.getUniqueId().toString());
      ResultSet results = statement.executeQuery();
      if (results.next()) {
        return true;
      }
      return false;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  
}

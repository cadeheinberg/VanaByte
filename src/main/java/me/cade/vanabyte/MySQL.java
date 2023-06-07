package me.cade.vanabyte;

import me.cade.vanabyte.HiddenPersonal.DatabaseAccess;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class MySQL {

  private Connection connection;
  private String url;
  private String username;
  private String password;
  private int port;
  
  private String tableName;
  public String[] column;
  
  private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);
  
  public MySQL() {

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
    
    tableName = "playerstats";
    column = new String[17];
    column[0] = "UUID";
    column[1] = "PlayerName";
    column[2] = "KitID";
    column[3] = "KitIndex";
    column[4] = "PlayerLevel";
    column[5] = "Kills";
    column[6] = "KillStreak";
    column[7] = "Deaths";
    column[8] = "Cakes";
    column[9] = "Exp";
    column[10] = "Kit00";
    column[11] = "Kit01";
    column[12] = "Kit02";
    column[13] = "Kit03";
    column[14] = "Kit04";
    column[15] = "Kit05";
    column[16] = "Kit06";

    try{
      createTable();
    } catch (SQLException e){
      e.printStackTrace();
      System.out.println("Error creating table");
    }

    
    refreshConnection();

  }

  private void createTable() throws SQLException{
    Statement statement = connection.createStatement();

    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: CREATING TABLE");
    String createTableSQL = "CREATE TABLE IF NOT EXISTS playerstats (UUID varchar(36) primary key, PlayerName varchar(16), ";
    for (int i = 2; i < column.length; i++) {
      if (i == column.length - 1) {
        createTableSQL = createTableSQL + column[column.length -1] + " int)";
      } else {
        createTableSQL = createTableSQL + column[i] + " int, ";
      }
    }
    statement.execute(createTableSQL);
    statement.close();
    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: TABLE CREATED!");
  }
  
  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void connect() throws ClassNotFoundException, SQLException {
    connection = DriverManager.getConnection(url, username, password);
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
      statement.setInt(3, 0);
      statement.setInt(4, 0);
      statement.setInt(5, 1);
      statement.setInt(6, 0);
      statement.setInt(7, 0);
      statement.setInt(8, 0);
      statement.setInt(9, 500);
      statement.setInt(10, 0);
      //KITS
      statement.setInt(11, 1);
      statement.setInt(12, 1);
      statement.setInt(13, 1);
      statement.setInt(14, 1);
      statement.setInt(15, 0);
      statement.setInt(16, 0);
      statement.setInt(17, 0);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public int getStat(Player player, String stat) {
    int getter = 0;
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
  
  public void refreshConnection() {
	new BukkitRunnable(){
        @Override
        public void run() {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL: REFRESHING CONNECTION");
            try {
                connection.isValid(0);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: REFRESHED");
            } catch (SQLException e) {
                e.printStackTrace();
            }              
        }
    }.runTaskTimer(plugin, 20*60*60*7, 20*60*60*7);
  }
  
}

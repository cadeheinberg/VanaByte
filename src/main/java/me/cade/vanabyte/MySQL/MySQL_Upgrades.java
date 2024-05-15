package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.MySQL.HiddenPersonal.DatabaseAccess;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class MySQL_Upgrades {

    private Connection connection;
    private String url;
    private String username;
    private String password;
    private int port;

    private String tableName;
    public String[] column;

    private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);

    public MySQL_Upgrades() {

        url = DatabaseAccess.getURL();
        port = DatabaseAccess.getPort();
        username = DatabaseAccess.getUsername();
        password = DatabaseAccess.getPassword();

        try {
            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL_Upgrades: CONNECTING...");
            connect();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_Upgrades: CONNECTED");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL_Upgrades: ERROR 1");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL_Upgrades: ERROR 2");
        }

        tableName = "player_upgrades";
        column = new String[(Fighter.getNumberOfKits() * 6) + 1];
        column[0] = "UUID";

        // 6 for the number of upgrade stats listed
        for (int i = 0; i < Fighter.getNumberOfKits(); i++){
            column[(6*i)+1] = "w" + i + "_melee_inc";
            column[(6*i)+2] = "w" + i + "_proj_inc";
            column[(6*i)+3] = "w" + i + "_special_inc";
            column[(6*i)+4] = "w" + i + "_duration_inc";
            column[(6*i)+5] = "w" + i + "_reacharge_dec";
            column[(6*i)+6] = "w" + i + "_cooldownticks_dec";
        }

        try{
            createTable();
        } catch (SQLException e){
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL_Upgrades: Error creating table");
        }

        this.refreshConnection();

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

    private void createTable() throws SQLException{
        Statement statement = connection.createStatement();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_Upgrades: CREATING TABLE");
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (UUID varchar(36) primary key, ";
        for (int i = 1; i < column.length; i++) {
            if (i == column.length - 1) {
                createTableSQL = createTableSQL + column[column.length -1] + " int)";
            } else {
                createTableSQL = createTableSQL + column[i] + " int, ";
            }
        }
        statement.execute(createTableSQL);
        statement.close();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_Upgrades: TABLE CREATED!");
    }

    public void deletePlayerFromTable(Player player) {
        PreparedStatement statement;
        try {
            statement = connection
                    .prepareStatement("DELETE FROM " + tableName + " WHERE " + column[0] + " = ?");
            statement.setString(1, player.getUniqueId().toString());
            statement.execute();
            player.sendMessage("You have been cleared from upgrade stats");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            for (int i = 2; i <= column.length; i++){
                statement.setInt(i, 0);
            }
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
                Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL_Upgrades: REFRESHING CONNECTION");
                try {
                    connection.isValid(0);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_Upgrades: REFRESHED");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(plugin, 20*60*60*7, 20*60*60*7);
    }
}

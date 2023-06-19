package me.cade.vanabyte.MySQL;

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

        tableName = "playerupgrades";
        column = new String[43];
        column[0] = "UUID";

        column[1] = "Kit00_melee_inc";
        column[2] = "Kit00_proj_inc";
        column[3] = "Kit00_special_inc";
        column[4] = "Kit00_duration_inc";
        column[5] = "Kit00_reacharge_dec";
        column[6] = "Kit00_cooldownticks_dec";

        column[7] = "Kit01_melee_inc";
        column[8] = "Kit01_proj_inc";
        column[9] = "Kit01_special_inc";
        column[10] = "Kit01_duration_inc";
        column[11] = "Kit01_reacharge_dec";
        column[12] = "Kit01_cooldownticks_dec";

        column[13] = "Kit02_melee_inc";
        column[14] = "Kit02_proj_inc";
        column[15] = "Kit02_special_inc";
        column[16] = "Kit02_duration_inc";
        column[17] = "Kit02_reacharge_dec";
        column[18] = "Kit02_cooldownticks_dec";

        column[19] = "Kit03_melee_inc";
        column[20] = "Kit03_proj_inc";
        column[21] = "Kit03_special_inc";
        column[22] = "Kit03_duration_inc";
        column[23] = "Kit03_reacharge_dec";
        column[24] = "Kit03_cooldownticks_dec";

        column[25] = "Kit04_melee_inc";
        column[26] = "Kit04_proj_inc";
        column[27] = "Kit04_special_inc";
        column[28] = "Kit04_duration_inc";
        column[29] = "Kit04_reacharge_dec";
        column[30] = "Kit04_cooldownticks_dec";

        column[31] = "Kit05_melee_inc";
        column[32] = "Kit05_proj_inc";
        column[33] = "Kit05_special_inc";
        column[34] = "Kit05_duration_inc";
        column[35] = "Kit05_reacharge_dec";
        column[36] = "Kit05_cooldownticks_dec";

        column[37] = "Kit06_melee_inc";
        column[38] = "Kit06_proj_inc";
        column[39] = "Kit06_special_inc";
        column[40] = "Kit06_duration_inc";
        column[41] = "Kit06_reacharge_dec";
        column[42] = "Kit06_cooldownticks_dec";

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

    private void connect() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    private void createTable() throws SQLException{
        Statement statement = connection.createStatement();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_Upgrades: CREATING TABLE");
        String createTableSQL = "CREATE TABLE IF NOT EXISTS playerupgrades (UUID varchar(36) primary key, ";
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

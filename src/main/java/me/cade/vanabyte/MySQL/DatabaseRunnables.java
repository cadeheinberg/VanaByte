package me.cade.vanabyte.MySQL;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class DatabaseRunnables {

    private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);

    public void refreshConnections() {
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL_HUB: CHECKING CONNECTION");
                try {
                    if(!VanaByte.databaseManager.isConnectionValid()){
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "POLLING: ERROR 0");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "POLLING: ERROR 1");
                }
                try {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL_HUB: CONNECTING...");
                    VanaByte.databaseManager.connect();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL_HUB: CONNECTED");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "POLLING: ERROR 2");
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "POLLING: ERROR 3");
                }
            } //retest the connections every 30 minutes. 20 ticks per second
        }.runTaskTimer(plugin, 20*60*30, 20*60*30);
    }

    public void backupDatabaseEverDay() {

    }

}

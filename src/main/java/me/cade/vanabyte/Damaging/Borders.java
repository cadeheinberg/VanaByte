package me.cade.vanabyte.Damaging;

import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Borders {

  //test comment
  private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);
  private static int bordersTask = 0;

  public static void startCheckingBorders() {
    setBordersTask(new BukkitRunnable() {
      @Override
      public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
          if (player.getLocation().getY() < 15 && SafeZone.inHub(player.getWorld())) {
            if (player.getGameMode() == GameMode.CREATIVE) {
              return;
            }
            player.setHealth(0);
          } 
        }
      }
    }.runTaskTimer(plugin, 60L, 20L).getTaskId());
  }
  
  public static int getBordersTask() {
    return bordersTask;
  }

  public static void setBordersTask(int task) {
    bordersTask = task;
  }

  public static void stopCheckingBorders() {
    Bukkit.getScheduler().cancelTask(bordersTask);
  }

}

package me.cade.vanabyte.Money;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class CakeDropper {

  private static int cakeLimit = 1;
  private static int timeBetweenDropSeconds = 10;
  private static int timeCountdown = timeBetweenDropSeconds;

  private static final Location iceSpawn = new Location(VanaByte.hub, -1014.5, 65.5, -143.5);
  private static final Location grassSpawn = new Location(VanaByte.hub, -1057.5, 65.5, -101.5);
  private static final Location sandSpawn = new Location(VanaByte.hub, -1092.5, 65.5, -93.5);

  private static Plugin plugin = VanaByte.getPlugin(VanaByte.class);
  private static int cakeTimeTask = -1;

  private static final Random rand = new Random();

  protected static void startDropping() {
    doCakeTime();
  }

  protected static void endDropping() {
    Bukkit.getScheduler().cancelTask(cakeTimeTask);
    clearCakeAreas();
  }

  private static void doCakeTime() {
    if (cakeTimeTask != -1) {
      Bukkit.getScheduler().cancelTask(cakeTimeTask);
    }
    timeCountdown = timeBetweenDropSeconds;
    cakeTimeTask = new BukkitRunnable() {
      @Override
      public void run() {
        timeCountdown -= 1;
        SpawnCakeTags.updateTagNames(timeCountdown);
        if (timeCountdown <= 0) {
          spawnCakes();
          doCakeTime();
        }
      }
    }.runTaskTimer(plugin, 20L, 20L).getTaskId();
  }

  private static void spawnCakes() {
    sumUpCakesInArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(iceSpawn, 5, 5, 5), iceSpawn);
    sumUpCakesInArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(grassSpawn, 5, 5, 5),
      grassSpawn);
    sumUpCakesInArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(sandSpawn, 5, 5, 5), sandSpawn);
  }

  private static void sumUpCakesInArea(ArrayList<Entity> chunkEntities, Location cakeSpawn) {
    int count = 0;
    if (chunkEntities != null) {
      if (chunkEntities.size() > 0) {
        for (int i = 0; i < chunkEntities.size(); i++) {
          if (chunkEntities.get(i) instanceof LivingEntity) {
            continue;
          }
          if (chunkEntities.get(i).getName().equals(CakeManager.currencyNameSingular)) {
            count++;
          }
        }
      }
    }
    spawnAppropriateNumberOfCakes(count, cakeSpawn);
  }

  private static void spawnAppropriateNumberOfCakes(int cakesAlreadyThere, Location cakeSpawn) {
    if (cakesAlreadyThere >= cakeLimit) {
      //spawn no cakes if the amount of cakes
      //sitting at that spawn has reached the limit
      //the cakeLimit is set to 1 right now
      return;
    }
    
    //debug line
    //Bukkit.getServer().broadcast(ChatColor.YELLOW + "Dropping A Cake", "none");
    
    //create itemstack
    //A_CakeManager.cakeMaterial is set to Material.CAKE
    ItemStack cakeDrop = new ItemStack(CakeManager.cakeMaterial, 1);
    
    //prevent item stacking
    ArrayList<String> cakeLore = new ArrayList<String>();
    cakeLore.add("" + rand.nextInt(99));
    
    //set the proper name/lore for the dropped item
    //A_CakeManager.currencyNameSingular is set to "Cake"
    ItemMeta meta = cakeDrop.getItemMeta();
    meta.setDisplayName(CakeManager.currencyNameSingular);
    meta.setLore(cakeLore);
    cakeDrop.setItemMeta(meta);
    
    //drop the cake
    //I have a variable for the world, but I just get a new reference
    //to ensure that the reference isnt being lost. I thought
    //this was the problem at first but idk.
    Bukkit.getServer().getWorld("world").dropItem(cakeSpawn, cakeDrop);
  }

  private static void clearCakeAreas() {
    clearEachArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(iceSpawn, 5, 5, 5), iceSpawn);
    clearEachArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(grassSpawn, 5, 5, 5), grassSpawn);
    clearEachArea((ArrayList<Entity>) VanaByte.hub.getNearbyEntities(sandSpawn, 5, 5, 5), sandSpawn);
  }

  private static void clearEachArea(ArrayList<Entity> chunkEntities, Location cakeSpawn) {
    for (int i = 0; i < chunkEntities.size(); i++) {
      if (chunkEntities.get(i) instanceof LivingEntity) {
        continue;
      }
      chunkEntities.get(i).remove();
    }
  }

}

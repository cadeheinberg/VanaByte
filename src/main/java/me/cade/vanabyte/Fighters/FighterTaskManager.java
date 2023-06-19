package me.cade.vanabyte.Fighters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FighterTaskManager {

    private Player player = null;
    private Fighter fighter = null;

    protected int groundPoundTask,cooldownTask,rechargeTask, refreshMySQLUploadTaskID = -1;

    public FighterTaskManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    public void cancelAllTasks(){
        Bukkit.getScheduler().cancelTask(this.refreshMySQLUploadTaskID);
        this.refreshMySQLUploadTaskID = -1;
        Bukkit.getScheduler().cancelTask(this.cooldownTask);
        this.cooldownTask = -1;
        Bukkit.getScheduler().cancelTask(this.groundPoundTask);
        this.groundPoundTask = -1;
        Bukkit.getScheduler().cancelTask(this.rechargeTask);
        this.rechargeTask = -1;
    }

    public void cancelCooldownTask() {
        if (this.cooldownTask != -1) {
            Bukkit.getScheduler().cancelTask(this.cooldownTask);
            this.cooldownTask = -1;
        }
    }

    public void cancelRechargeTask() {
        if (this.rechargeTask != -1) {
            Bukkit.getScheduler().cancelTask(this.rechargeTask);
            this.rechargeTask = -1;
        }
    }

    public void refreshMySQLUpload() {
        UUID uuid_p = this.fighter.getUuid();
        this.refreshMySQLUploadTaskID = new BukkitRunnable(){
            @Override
            public void run() {
                if (!Bukkit.getOfflinePlayer(uuid_p).isOnline()) {
                    this.cancel();
                    return;
                }
                Fighter.get((Player) Bukkit.getOfflinePlayer(uuid_p)).fighterMYSQLManager.uploadFighter();
                ((Player) Bukkit.getOfflinePlayer(uuid_p)).sendMessage("Uploading your stats!");
            }
        }.runTaskTimer(this.fighter.getPlugin(), 20*60*3, 20*60*3).getTaskId();
    }

}

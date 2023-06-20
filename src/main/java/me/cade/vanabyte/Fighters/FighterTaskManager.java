package me.cade.vanabyte.Fighters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FighterTaskManager {

    private Player player = null;
    private Fighter fighter = null;

    protected int groundPoundTask,cooldownTask,rechargeTask, parachuteTask, refreshMySQLUploadTaskID = 0;

    protected void fighterJoined(){
        this.refreshMySQLUpload();
    }

    public FighterTaskManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }

    protected void fighterDied(){
        this.cancelGameplayTasks();
    }

    protected void fighterLeftServer(){
        this.cancelAllTasks();
    }

    protected void fighterChangedWorld(){
        this.cancelGameplayTasks();
    }

    protected void fighterRespawned(){

    }

    public void cancelAllTasks(){
        this.cancelGameplayTasks();
        if(this.refreshMySQLUploadTaskID != 0){
            Bukkit.getScheduler().cancelTask(this.refreshMySQLUploadTaskID);
            this.refreshMySQLUploadTaskID = 0;
        }
    }

    public void cancelGameplayTasks(){
        if(this.cooldownTask != 0){
            Bukkit.getScheduler().cancelTask(this.cooldownTask);
            this.cooldownTask = 0;
        }
        if(this.groundPoundTask != 0){
            Bukkit.getScheduler().cancelTask(this.groundPoundTask);
            this.groundPoundTask = 0;
        }
        if(this.rechargeTask != 0){
            Bukkit.getScheduler().cancelTask(this.rechargeTask);
            this.rechargeTask = 0;
        }
        if(this.parachuteTask != 0){
            Bukkit.getScheduler().cancelTask(this.parachuteTask);
            this.parachuteTask = 0;
        }
    }

    public void cancelCooldownTask() {
        if (this.cooldownTask != 0) {
            Bukkit.getScheduler().cancelTask(this.cooldownTask);
            this.cooldownTask = 0;
        }
    }

    public void cancelRechargeTask() {
        if (this.rechargeTask != 0) {
            Bukkit.getScheduler().cancelTask(this.rechargeTask);
            this.rechargeTask = 0;
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

    public void setGroundPoundTask(int setter){
        this.groundPoundTask = setter;
    }

    public int getGroundPoundTask() {
        return groundPoundTask;
    }

    public void setParachuteTask(int parachuteTask) {
        this.parachuteTask = parachuteTask;
    }

    public int getParachuteTask() {
        return parachuteTask;
    }

    public void cancelParachuteTask() {
        if (this.parachuteTask != 0) {
            Bukkit.getScheduler().cancelTask(this.parachuteTask);
            this.parachuteTask = 0;
        }
    }
}

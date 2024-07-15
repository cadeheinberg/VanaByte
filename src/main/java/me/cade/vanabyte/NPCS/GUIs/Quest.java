package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Quest {

    private int season = 0;
    private int guiSlot = 0;
    private String title = null;
    private String task = null;
    private WeaponType weaponType = null;
    private EntityType targetEntityType = null;
    private UpgradeType upgradeType = null;
    private UpgradeAlgebraType upgradeAlegebraType = null;
    private double upgradeAmount = 0.0;
    private double goal = 0;
    private double progress = 0;

    public Quest(int season, int guiSlot, String title, String task, WeaponType weaponType, EntityType targetEntityType, UpgradeType upgradeType, UpgradeAlgebraType upgradeAlegebraType, double upgradeAmount, double goal) {
        this.season = season;
        this.guiSlot = guiSlot;
        this.title = title;
        this.task = task;
        this.weaponType = weaponType;
        this.targetEntityType = targetEntityType;
        this.upgradeType = upgradeType;
        this.upgradeAlegebraType = upgradeAlegebraType;
        this.upgradeAmount = upgradeAmount;
        this.goal = goal;
        this.progress = 0;
    }

    public ItemStack getQuestItemStack(){
        if(!isGoalMet()){
            //task is not completed
            ItemStack redPane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            ItemMeta redMeta = redPane.getItemMeta();
            redMeta.setItemName(ChatColor.RED + this.title);
            if(this.upgradeAlegebraType == UpgradeAlgebraType.BOOLEAN){
                redMeta.setLore(Arrays.asList(ChatColor.WHITE + "Quest: " + ChatColor.GRAY + "" + task,
                        ChatColor.WHITE + "Reward: " + ChatColor.GRAY + "" + upgradeType.getName(),
                        ChatColor.WHITE + "Progress: " + ChatColor.GRAY + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
            }else{
                redMeta.setLore(Arrays.asList(ChatColor.WHITE + "Quest: " + ChatColor.GRAY + "" + task,
                        ChatColor.WHITE + "Reward: " + ChatColor.GRAY + "" + this.upgradeAlegebraType.getName() + convertDouble(this.upgradeAmount) + " " + upgradeType.getName(),
                        ChatColor.WHITE + "Progress: " + ChatColor.GRAY + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
            }
            redPane.setItemMeta(redMeta);
            return redPane;
        }else{
            ItemStack greenPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            ItemMeta greenMeta = greenPane.getItemMeta();
            greenMeta.setItemName(ChatColor.RED + this.title);
            if(this.upgradeAlegebraType == UpgradeAlgebraType.BOOLEAN){
                greenMeta.setLore(Arrays.asList(ChatColor.WHITE + "Quest: " + ChatColor.GRAY + "" + task,
                        ChatColor.WHITE + "Reward: " + ChatColor.GRAY + "" + upgradeType.getName(),
                        ChatColor.WHITE + "Progress: " + ChatColor.GRAY + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
            }else{
                greenMeta.setLore(Arrays.asList(ChatColor.WHITE + "Quest: " + ChatColor.GRAY + "" + task,
                        ChatColor.WHITE + "Reward: " + ChatColor.GRAY + "" + this.upgradeAlegebraType.getName() + convertDouble(this.upgradeAmount) + " " + upgradeType.getName(),
                        ChatColor.WHITE + "Progress: " + ChatColor.GRAY + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
            }
            greenPane.setItemMeta(greenMeta);
            greenPane.setItemMeta(greenMeta);
            return greenPane;
        }
    }

    public static Number convertDouble(double value) {
        if (value == (int) value) {
            return (int) value;
        } else {
            return value;
        }
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public EntityType getTargetEntityType(){
        return targetEntityType;
    }

    public UpgradeType getUpgradeType(){
        return upgradeType;
    }

    public UpgradeAlgebraType getUpgradeAlegebraType(){
        return upgradeAlegebraType;
    }

    public boolean isGoalMet(){
        return goal <= progress;
    }

    public int getGuiSlot(){
        return guiSlot;
    }

    public double getProgress(){
        return progress;
    }

    public double getGoal() {
        return goal;
    }

    public String getTitle(){
        return title;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}

package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Quest {

    private String task = null;
    private WeaponType weaponType = null;
    private UpgradeType upgradeType = null;
    private UpgradeAlgebraType upgradeAlegebraType = null;
    private double upgradeAmount = 0.0;
    private double goal = 0;
    private double progress = 0;

    public Quest(String task, WeaponType weaponType, UpgradeType upgradeType, UpgradeAlgebraType upgradeAlegebraType, double upgradeAmount, double goal) {
        this.task = task;
        this.weaponType = weaponType;
        this.upgradeType = upgradeType;
        this.upgradeAlegebraType = upgradeAlegebraType;
        this.upgradeAmount = upgradeAmount;
        this.goal = goal;
        this.progress = 0;
    }

    public ItemStack getQuestItemStack(){
        if(progress < goal){
            //task is not completed
            ItemStack redPane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
            ItemMeta redMeta = redPane.getItemMeta();
            redMeta.setItemName(ChatColor.RED + "Upgrade Locked");
            redMeta.setLore(Arrays.asList(ChatColor.WHITE + task,
                    ChatColor.WHITE + "Reward: " + ChatColor.RED + "" + this.upgradeAlegebraType.getName() + convertDouble(this.upgradeAmount) + " " + upgradeType.getName(),
                    ChatColor.WHITE + "Progress: " + ChatColor.RED + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
            redPane.setItemMeta(redMeta);
            return redPane;
        }else{
            ItemStack greenPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
            ItemMeta greenMeta = greenPane.getItemMeta();
            greenMeta.setItemName(ChatColor.GREEN + "Unlocked");
            greenMeta.setLore(Arrays.asList(ChatColor.WHITE + task,
                    ChatColor.WHITE + "Reward: " + ChatColor.GREEN + "" + this.upgradeAlegebraType.getName() + convertDouble(this.upgradeAmount) + " " + upgradeType.getName(),
                    ChatColor.WHITE + "Progress: " + ChatColor.GREEN + "" + convertDouble(this.progress) + "/" + convertDouble(this.goal)));
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
}

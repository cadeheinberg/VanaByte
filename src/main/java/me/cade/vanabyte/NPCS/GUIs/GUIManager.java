package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class GUIManager {

    private Player player = null;
    private MyGUI upgradeLecturnGUI = null;
    private static Location upgradeLecternLocation = new Location(VanaByte.hub, -1053, 194, -113, 0, 0);

    public GUIManager(Player player){
        this.player = player;
        upgradeLecturnGUI = new MyGUI(player);
    }

    public void openKitLecternGUI(Location location){
            if(upgradeLecternLocation.getX() == location.getX()){
                if(upgradeLecternLocation.getY() == location.getY()){
                    if(upgradeLecternLocation.getZ() == location.getZ()){
                        ItemStack toUpgrade = player.getInventory().getItemInMainHand();
                        WeaponType weaponType = Weapon.getWeaponType(toUpgrade);
                        if(weaponType == WeaponType.UNKNOWN_WEAPON){
                            player.sendMessage(ChatColor.RED + "Only special items can be upgrades");
                            return;
                        }
                        ArrayList<Quest> quests = Fighter.get(player).getQuestManager().getQuestsOfWeaponType(weaponType);
                        if(quests.size() < 8){
                            player.sendMessage(ChatColor.RED + "There are no quests for this special item");
                            return;
                        }
                        upgradeLecturnGUI.clear();
                        upgradeLecturnGUI.addItem(12, quests.get(0).getQuestItemStack());
                        upgradeLecturnGUI.addItem(13, quests.get(1).getQuestItemStack());
                        upgradeLecturnGUI.addItem(14, quests.get(2).getQuestItemStack());
                        upgradeLecturnGUI.addItem(21, quests.get(3).getQuestItemStack());
                        upgradeLecturnGUI.addItem(22, toUpgrade);
                        upgradeLecturnGUI.addItem(23, quests.get(4).getQuestItemStack());
                        upgradeLecturnGUI.addItem(30, quests.get(5).getQuestItemStack());
                        upgradeLecturnGUI.addItem(31, quests.get(6).getQuestItemStack());
                        upgradeLecturnGUI.addItem(32, quests.get(7).getQuestItemStack());
//                        kitLectern.addItem(15, () -> {
//                                    KitUpgradeHandler.handleKitUpgradeIncreaseMeleeDamage(player, kitID);}, Material.EMERALD, "Upgrade", "Cost: 100");
                        upgradeLecturnGUI.open();
                    }
                }
            }
    }

    public MyGUI getMatchingGUI(Inventory inventory){
        if(upgradeLecturnGUI != null){
            if(upgradeLecturnGUI.getInventory().equals(inventory))
                return upgradeLecturnGUI;
        }
        return null;
    }
}

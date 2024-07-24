package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.WeaponHolders.Weapon;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

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
                        WeaponType weaponType = Weapon.getWeaponTypeFromItemStack(toUpgrade);
                        if(weaponType == null){
                            player.sendMessage(ChatColor.RED + "Only special items can be upgrades");
                            return;
                        }
                        ArrayList<Quest> quests = Fighter.get(player).getQuestManager().getQuestsOfWeaponType(weaponType);
                        if(quests.size() < 1){
                            player.sendMessage(ChatColor.RED + "There are no quests for this special item right now");
                            return;
                        }
                        upgradeLecturnGUI.clear();
                        for(Quest quest : quests){
                            upgradeLecturnGUI.addItem(quest.getGuiSlot(), quest.getQuestItemStack());
                        }
                        //put weapon to upgrade right in middle
                        upgradeLecturnGUI.addItem(22, toUpgrade);
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

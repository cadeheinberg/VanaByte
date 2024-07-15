package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Fighters.WeaponHolders.Weapon;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class MyGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        //e.getWhoClicked().sendMessage("raw: " + e.getRawSlot() + "slot " + e.getSlot());
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE){
            return;
        }
        if(Fighter.get((Player) e.getWhoClicked()).getGUIManager().getMatchingGUI(e.getClickedInventory()) != null){
            Fighter.get((Player) e.getWhoClicked()).getGUIManager().getMatchingGUI(e.getClickedInventory()).onInventoryClick(e);
            return;
        }
        if(e.getInventory() != null && e.getInventory().getType() == InventoryType.CRAFTING){
            if(e.getView() != null && e.getView().getTopInventory() != null && e.getView().getTopInventory().getType() == InventoryType.CRAFTING){
                if(e.getView().getBottomInventory() != null && e.getView().getBottomInventory().getType() == InventoryType.PLAYER){
                    return;
                }
            }
        }
        if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                e.getAction() == InventoryAction.PICKUP_ALL ||
                e.getAction() == InventoryAction.PLACE_SOME ||
                e.getAction() == InventoryAction.PICKUP_HALF ||
                e.getAction() == InventoryAction.PICKUP_ONE ||
                e.getAction() == InventoryAction.PLACE_SOME ||
                e.getAction() == InventoryAction.PLACE_ALL ||
                e.getAction() == InventoryAction.PLACE_ONE){
            if(Weapon.getWeaponTypeFromItemStack(e.getCursor()) == null || Weapon.getWeaponTypeFromItemStack(e.getCursor()) == WeaponType.UNKNOWN_WEAPON){
                return;
            }
            if (e.getCurrentItem() == null || Weapon.getWeaponTypeFromItemStack(e.getCurrentItem()) == WeaponType.UNKNOWN_WEAPON) {
                return;
            }
            e.setCancelled(true);
        }
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        if(Fighter.get((Player) e.getWhoClicked()).getGUIManager().getMatchingGUI(e.getInventory()) == null){
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e){
        if(!(e.getPlayer() instanceof Player)){
            return;
        }
        if(Fighter.get((Player) e.getPlayer()).getGUIManager().getMatchingGUI(e.getInventory()) == null){
            return;
        }
        //e.setCancelled(true);
    }

}

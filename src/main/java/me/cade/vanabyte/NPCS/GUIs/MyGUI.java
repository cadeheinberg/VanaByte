package me.cade.vanabyte.NPCS.GUIs;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class MyGUI {

    private Player player = null;
    private Inventory inv = null;
    private final HashMap<Integer, Runnable> clickMap = new HashMap<>();

    public MyGUI(Player player){
        this.player = player;
        this.inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "" + ChatColor.BOLD + "Weapon Upgrades:");
    }

    protected void clear(){
        this.inv.clear();
    }

    protected void open(){
        this.player.openInventory(this.inv);
    }

    protected void addItem(int index, ItemStack item) {
        this.inv.setItem(index, item);
    }

    protected void addItem(int index, Runnable func, Material material, String name, String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        this.inv.setItem(index, item);
        this.clickMap.put(index, func);
    }

    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClickedInventory().equals(this.inv)){
            e.setCancelled(true);
            final ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType().isAir()) return;
            final Player p = (Player) e.getWhoClicked();
            if(clickMap.containsKey(e.getRawSlot()) && clickMap.get(e.getRawSlot()) != null){
                this.clickMap.get(e.getRawSlot()).run();
            }
        }
    }

    public Inventory getInventory(){
        return this.inv;
    }
}

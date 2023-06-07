package me.cade.vanabyte.Permissions;

import me.cade.vanabyte.BuildKits.FighterKit;
import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class BasicPermissions implements Listener {

	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
		if(event.getWhoClicked() instanceof Player && ((Player) event.getWhoClicked()).getGameMode() == GameMode.CREATIVE){
			//if player is in creative mode ignore
			return;
		}
		if(SafeZone.inHub(event.getWhoClicked().getWorld())){
			event.setCancelled(true);
			return;
		}
		if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.PICKUP_ALL || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PICKUP_HALF || event.getAction() == InventoryAction.PICKUP_ONE || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_ONE){
			if (event.getCursor() != null && event.getCursor().hasItemMeta()) {
				if (FighterKit.isFighterWeaponOrSpecialItem(event.getCursor())) {
					event.setCancelled(true);
				}
			}
			if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
				if (FighterKit.isFighterWeaponOrSpecialItem(event.getCurrentItem())) {
					event.setCancelled(true);
				}
			}
		}

	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		if (e.getPlayer().getWorld() == VanaByte.hub){
			Fighter.get(e.getPlayer()).setInHub(true);
		}else{
			Fighter.get(e.getPlayer()).setInHub(false);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		if (e.getPlayer().hasPermission("seven.builder")) {
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
				// allow to build
				return;
			}
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		if (e.getPlayer().hasPermission("seven.builder")) {
			if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
				// allow to bureak
				return;
			}
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onFade(BlockFadeEvent e) {

		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onGrow(BlockGrowEvent e) {

		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onBurn(BlockBurnEvent e) {

		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent e) {

		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onBook(PlayerTakeLecternBookEvent e) {
		if(!SafeZone.inHub(e.getLectern().getWorld())){
			return;
		}
		if (e.getPlayer().isOp()) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onBed(PlayerBedEnterEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onExplode(BlockExplodeEvent e) {
		if(!SafeZone.inHub(e.getBlock().getWorld())){
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {

		if(!SafeZone.inHub(e.getEntity().getWorld())){
			return;
		}
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onCombust(EntityCombustEvent e) {
		if(!SafeZone.inHub(e.getEntity().getWorld())){
			return;
		}

		if (!(e.getEntity() instanceof LivingEntity)) {
			e.setCancelled(true);
		}
	}

	public void onSwapHand(PlayerSwapHandItemsEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onExpChange(PlayerExpChangeEvent e) {

//		if(!SafeZone.inHub(e.getPlayer().getWorld())){
			//if player is a fighter kit then set amount to 0 otherwise ignore
//			return;
//		}

		e.setAmount(0);
	}

	@EventHandler
	public void onLevelChangeEvent(PlayerLevelChangeEvent e) {
//
//		if(!SafeZone.inHub(e.getPlayer().getWorld())){
//			return;
//		}
		e.getPlayer().setLevel(0);
	}

	@EventHandler
	public void onGrabArmorStand(PlayerArmorStandManipulateEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDrinkPotion(PlayerItemConsumeEvent e) {
		if(!SafeZone.inHub(e.getPlayer().getWorld())){
			return;
		}
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			// allow to build
			return;
		}
		e.setCancelled(true);
	}

}

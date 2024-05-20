package me.cade.vanabyte.Permissions;

import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

public class BasicPermissions implements Listener {

	public static void youCantDoThatHere(Player player, String message){
		player.sendMessage(ChatColor.RED + message);
		player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
	}

	@EventHandler
	public static void onPlayerLevelChange(PlayerLevelChangeEvent e){
		if(e.getNewLevel() - e.getOldLevel() > 0){
			Fighter.get(e.getPlayer()).incPlayerLevel(e.getNewLevel() - e.getOldLevel());
		}else{
			Fighter.get(e.getPlayer()).decPlayerLevel(Math.abs((e.getNewLevel() - e.getOldLevel())));
		}
	}

	@EventHandler
	public static void onPlayerLevelChange(PlayerExpChangeEvent e){
		Fighter.get(e.getPlayer()).getFighterScoreBoardManager().updateExp();
	}

	//Triggered when a hanging entity is created in the world, ie item frame
	@EventHandler
	public void onHangingPlace(HangingPlaceEvent e){
		if(SafeZone.safeZone(e.getEntity().getLocation())){
			e.setCancelled(true);
			BasicPermissions.youCantDoThatHere(e.getPlayer(), "You can't place that here!");
			BasicPermissions.youCantDoThatHere(e.getPlayer(), "PS: You didn't loose your block :)");
		}
	}

	//Triggered when a entity is created in the world by a player "placing" an item on a block.
	//Note that this event is currently only fired for four specific placements: armor stands, boats, minecarts, and end crystals.
	@EventHandler
	public void onEntityPlace(EntityPlaceEvent e){
		if(SafeZone.safeZone(e.getEntity().getLocation())){
			e.setCancelled(true);
			BasicPermissions.youCantDoThatHere(e.getPlayer(), "You can't place that here!");
			BasicPermissions.youCantDoThatHere(e.getPlayer(), "PS: You didn't loose your block :)");
		}
	}
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e){
		if(e.getEntity().getType() == EntityType.ALLAY){
			e.setCancelled(true);
		} else if(e.getEntity().getType() == EntityType.PHANTOM){
			e.setCancelled(true);
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

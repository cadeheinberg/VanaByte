package me.cade.vanabyte.Permissions;

import me.cade.vanabyte.Fighters.FighterKit;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
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
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;

import java.util.Random;

public class BasicPermissions implements Listener {

	public static void youCantDoThatHere(Player player, String message){
		player.sendMessage(ChatColor.RED + message);
		player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
	}

	@EventHandler
	public void onPlayerUsePortal(PlayerPortalEvent e){
		Bukkit.getConsoleSender().sendMessage("using portal: " + e.getFrom().getWorld() + " " + e.getTo().getWorld());
		e.setCanCreatePortal(false);
		e.setCancelled(true);
		if(e.getFrom().getWorld() == VanaByte.hub && e.getTo().getWorld() == VanaByte.anarchyWorld){
			Bukkit.getConsoleSender().sendMessage("using portal:  1");
			e.setTo(VanaByte.anarchyWorldSpawn);
			e.getPlayer().teleport(VanaByte.anarchyWorldSpawn);
		}else if(e.getFrom().getWorld() == VanaByte.anarchyWorld && e.getTo().getWorld() == VanaByte.hub){
			Bukkit.getConsoleSender().sendMessage("using portal:  2");
			e.setTo(VanaByte.hubSpawn);
			e.getPlayer().teleport(VanaByte.hubSpawn);
		}
	}

	@EventHandler
	public void onEntityUsePortal(EntityPortalEvent e){
		if(!(e.getEntity() instanceof Player)){
			e.setCancelled(true);
		}
		return;
	}

	@EventHandler
	public void onPlayerSwapHands(PlayerSwapHandItemsEvent e){
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerLevelChange(PlayerLevelChangeEvent e){
		Fighter.get(e.getPlayer()).refreshLevel();
	}

	@EventHandler
	public void onPlayerLevelChange(PlayerExpChangeEvent e){
		Fighter.get(e.getPlayer()).refreshXP();
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
		if(!(e.getEntity() instanceof LivingEntity)){
			return;
		}
		if(e.getLocation().getWorld() == VanaByte.anarchyWorld){
			if(e.getEntityType() == EntityType.CREEPER || e.getEntityType() == EntityType.COW){
				return;
			}
			Random rand = new Random();
			int num = rand.nextInt(10 - 1 + 1) + 1;
//			if(num <= 5){
//				e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.CREEPER);
//			}else{
//				e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.COW);
//			}
		}
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
		if(!(e.getEntity() instanceof Player)){
			return;
		}
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

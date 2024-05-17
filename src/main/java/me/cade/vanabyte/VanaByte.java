package me.cade.vanabyte;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.Damaging.*;
import me.cade.vanabyte.Money.CakeManager;
import me.cade.vanabyte.MySQL.MySQL_KitPVP;
import me.cade.vanabyte.MySQL.MySQL_Royale;
import me.cade.vanabyte.MySQL.MySQL_Upgrades;
import me.cade.vanabyte.MySQL.Polling;
import me.cade.vanabyte.NPCS.*;
import me.cade.vanabyte.Permissions.BasicPermissions;
import me.cade.vanabyte.Permissions.PickingUp;
import me.cade.vanabyte.Permissions.PlayerChat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;

public class VanaByte extends JavaPlugin {

	public static World hub;
	public static Location hubSpawn;
	public static World secondWorld;
	public static Location secondWorldSpawn;

	public static World thirdWorld;
	public static Location thirdWorldSpawn;
	public static MySQL_KitPVP mySQL_Hub;
	public static MySQL_Upgrades mySQL_upgrades;

	public static MySQL_Royale mySQL_royale;

	public static Polling poller;

	private static Plugin plugin = null;
	private static PlayerParticlesAPI ppAPI;
	
	private static NumberFormat myFormat = NumberFormat.getInstance();

	@Override
	public void onEnable() {
		plugin = VanaByte.getPlugin(VanaByte.class);
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            ppAPI = PlayerParticlesAPI.getInstance();
        }
		getConfig().options().copyDefaults(true);
		saveConfig();
		startMySQL();
		myFormat.setGroupingUsed(true);
		setLocations();
		SpawnRealEntities.removeAllNpcs();
		SpawnRealEntities.spawnNPCS();
		registerListeners();
		Borders.startCheckingBorders();
		CakeManager.startCakePackage();
		//getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		// do last
		addPlayersToFighters();
	}

	private static void startMySQL() {
		mySQL_Hub = new MySQL_KitPVP();
		mySQL_upgrades = new MySQL_Upgrades();
		mySQL_royale = new MySQL_Royale();
		poller = new Polling();
		poller.refreshConnections();
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new NPCListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new FallDamageListener(), this);
		pm.registerEvents(new KitListener(), this);
		pm.registerEvents(new BasicPermissions(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new PickingUp(), this);
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(Fighter.get(player) != null){
				Fighter.get(player).fighterLeftServer();
			}
		}
		mySQL_Hub.closeConnection();
		mySQL_upgrades.closeConnection();
		mySQL_royale.closeConnection();
		CakeManager.stopCakePackage();
		Borders.stopCheckingBorders();
	}

	public void setLocations() {
		hub = Bukkit.getServer().getWorld("world");
		hubSpawn = new Location(hub, -1052.5, 197.5, -131.5);
		Bukkit.getServer().createWorld(new WorldCreator("world2"));
		secondWorld = Bukkit.getServer().getWorld("world2");
		secondWorldSpawn = new Location(secondWorld, -13, 200, -11);
		Bukkit.getServer().createWorld(new WorldCreator("world3"));
		thirdWorld = Bukkit.getServer().getWorld("world3");
		thirdWorldSpawn = new Location(thirdWorld, -0, 200, -0);
	}

	private void addPlayersToFighters() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			new Fighter(player);
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			return false;
		}
		Player player = (Player) sender;
		if (label.equals("spawn")) {
			player.teleport(hubSpawn);
			return true;
		} else if (label.equals("changeworld")) {
			if (!player.isOp()){
				return false;
			}
			Player toSend = Bukkit.getPlayer(args[0].toString());
			int input = Integer.parseInt(args[1].toString());
			if(input == 0){
				toSend.teleport(hubSpawn);
			}else if (input == 1){
				toSend.teleport(secondWorldSpawn);
			} else if (input == 2) {
				toSend.teleport(thirdWorldSpawn);
			}
			return true;
		} else if (label.equals("hidescoreboard")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			Fighter.get(player).getFighterScoreBoardManager().hideScoreBoard();
		} else if (label.equals("unhidescoreboard")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			Fighter.get(player).getFighterScoreBoardManager().unhideScoreBoard();
		} else if (label.equals("fly")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
		      double input = Double.parseDouble(args[0].toString());
		      player.setFlySpeed((float) input);
		} else if (label.equals("heal")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			player.setHealth(20);
			player.setFoodLevel(20);
		} else if (label.equals("vanascuteness")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			ItemStack lhelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
			ItemMeta lhe = lhelmet.getItemMeta();
			ItemStack lchest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
			ItemMeta lch = lchest.getItemMeta();
			ItemStack lleggs = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
			ItemMeta lle = lleggs.getItemMeta();
			ItemStack lboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
			ItemMeta lbo = lboots.getItemMeta();

			lhe.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			lch.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			lle.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			lbo.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

			player.getEquipment().setHelmet(lhelmet);
			player.getEquipment().setChestplate(lchest);
			player.getEquipment().setLeggings(lleggs);
			player.getEquipment().setBoots(lboots);
		}
		return false;
	}

	public static Plugin getInstance() {
		return plugin;
	}

//	public static ProtocolManager getProtocolManager() {
//		return protocolManager;
//	}

	public static PlayerParticlesAPI getPpAPI() {
		return ppAPI;
	}
	
	public static NumberFormat getMyNumberFormat() {
		return myFormat;
	}

}

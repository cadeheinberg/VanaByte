package me.cade.vanabyte;

import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageManager;
import me.cade.vanabyte.Fighters.*;
import me.cade.vanabyte.Damaging.*;
import me.cade.vanabyte.Money.CakeManager;
import me.cade.vanabyte.MySQL.MySQL_KitPVP;
import me.cade.vanabyte.MySQL.MySQL_Royale;
import me.cade.vanabyte.MySQL.MySQL_Upgrades;
import me.cade.vanabyte.MySQL.Polling;
import me.cade.vanabyte.NPCS.GUIs.MyGUIListener;
import me.cade.vanabyte.NPCS.RealEntities.NPCListener;
import me.cade.vanabyte.NPCS.RealEntities.SpawnRealEntities;
import me.cade.vanabyte.Permissions.BasicPermissions;
import me.cade.vanabyte.Permissions.PickingUp;
import me.cade.vanabyte.Permissions.PlayerChat;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.NumberFormat;

public class VanaByte extends JavaPlugin {

	public static World hub;
	public static Location hubSpawn;
	public static World anarchyWorld;
	public static Location anarchyWorldSpawn;
	public static World thirdWorld;
	public static Location thirdWorldSpawn;

	public static MySQL_KitPVP mySQL_Hub;
	public static MySQL_Upgrades mySQL_upgrades;
	public static MySQL_Royale mySQL_royale;
	public static Polling poller;

	public static EntityDamageManager entityDamageManager;

	private static Plugin plugin = null;
	//private static PlayerParticlesAPI ppAPI;
	
	private static NumberFormat myFormat = NumberFormat.getInstance();

	@Override
	public void onEnable() {
		plugin = VanaByte.getPlugin(VanaByte.class);
//        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
//            ppAPI = PlayerParticlesAPI.getInstance();
//        }
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
		entityDamageManager = new EntityDamageManager();
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "SERVER STARTED SUCCESSFULLY");
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
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new PickingUp(), this);
		pm.registerEvents(new MyGUIListener(), this);
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
		anarchyWorld = Bukkit.getWorld("world_nether");
		anarchyWorldSpawn = new Location(anarchyWorld, -55.5, 73, -132);
		anarchyWorldSpawn.setYaw(90);
//		Bukkit.getServer().createWorld(new WorldCreator("world3"));
//		thirdWorld = Bukkit.getServer().getWorld("world3");
//		thirdWorldSpawn = new Location(thirdWorld, -0, 200, -0);
//		WorldCreator creator = WorldCreator.name("nether_world").environment(World.Environment.NETHER);
//		Bukkit.createWorld(creator);
//		player.teleport(Bukkit.getWorld("nether_world").getSpawnLocation());
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
				toSend.teleport(anarchyWorldSpawn);
			}
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

			lhe.addEnchant(Enchantment.PROTECTION, 4, true);
			lch.addEnchant(Enchantment.PROTECTION, 4, true);
			lle.addEnchant(Enchantment.PROTECTION, 4, true);
			lbo.addEnchant(Enchantment.PROTECTION, 4, true);

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

//	public static PlayerParticlesAPI getPpAPI() {
//		return ppAPI;
//	}
	
	public static NumberFormat getMyNumberFormat() {
		return myFormat;
	}

	public static EntityDamageManager getEntityDamageManger() {
		return entityDamageManager;
	}

	public static Plugin getVanaBytePlugin(){
		return plugin;
	}

}

package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.FighterWeapons.FighterAbilityManager;
import me.cade.vanabyte.NPCS.Holograms.HologramsManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class Fighter {

	private Plugin plugin = VanaByte.getPlugin(VanaByte.class);

	public static HashMap<UUID, Fighter> fighters = new HashMap<UUID, Fighter>();
	private Player player = null;
	private UUID uuid,lastToDamage,lastDamagedBy = null;
	private static final int numberOfKits = 7;
	private int fighterLevel, fighterXP, kills,killStreak,deaths,cakes = -1;
	protected HologramsManager fighterHologramManager = null;
	protected FighterTaskManager fighterTaskManager = null;
	protected FighterMYSQLManager fighterMYSQLManager = null;
	protected FighterKitManager fighterKitManager = null;
	protected FighterScoreBoardManager fighterScoreBoardManager = null;
	protected FighterAbilityManager fighterAbilityManager = null;

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		this.fighterHologramManager = new HologramsManager(this.player, this);
		this.fighterTaskManager = new FighterTaskManager(this.player, this);
		this.fighterKitManager = new FighterKitManager(this.player, this);
		this.fighterMYSQLManager = new FighterMYSQLManager(this.player, this);
		this.fighterAbilityManager = new FighterAbilityManager(this);
		this.fighterScoreBoardManager = new FighterScoreBoardManager(player);
		this.fighterJoined();
	}

	public void fighterJoined(){
		fighterKitManager.fighterJoined();
		fighterMYSQLManager.fighterJoined();
		fighterTaskManager.fighterJoined();
		fighterHologramManager.fighterJoined();
		//Need to do this after everything has been setup
		fighterKitManager.giveKit();
		fighterAbilityManager.fighterJoined();
	}

	public void fighterRespawn() {
		fighterAbilityManager.fighterRespawned();
		fighterKitManager.fighterRespawned();
		fighterTaskManager.fighterRespawned();
		fighterHologramManager.fighterRespawned();
		fighterMYSQLManager.fighterRespawned();
		this.setLastDamagedBy(null);
		this.setLastToDamage(null);
	}

	public void fighterLeftServer() {
		fighterMYSQLManager.fighterLeftServer();
		fighterTaskManager.fighterLeftServer();
		fighterKitManager.fighterLeftServer();
		fighterAbilityManager.fighterLeftServer();
		fighterHologramManager.fighterLeftServer();
	}

	public void fighterDeath() {
		this.incDeaths();
		fighterAbilityManager.fighterDied();
		fighterKitManager.fighterDied();
		fighterTaskManager.fighterDied();
		fighterHologramManager.fighterDied();
		fighterMYSQLManager.fighterDied();
	}

	public void fighterChangeWorld(){
		fighterAbilityManager.fighterChangedWorld();
		fighterKitManager.fighterChangedWorld();
		fighterTaskManager.fighterChangedWorld();
		fighterHologramManager.fighterChangedWorld();
		fighterMYSQLManager.fighterChangedWorld();
	}

	public void giveKit(){
		fighterKitManager.giveKit();
	}

	public void dropFighterKitSoul(){}

	public void setLastToDamage(Player lastToDamage) {
		if (this.lastToDamage == null) {
			this.lastToDamage = null;
			return;
		}
		this.lastToDamage = lastToDamage.getUniqueId();
	}
	public void setLastDamagedBy(Player lastDamagedBy) {
		if (lastDamagedBy == null) {
			this.lastDamagedBy = null;
			return;
		}
		this.lastDamagedBy = lastDamagedBy.getUniqueId();
	}
	protected void setFighterLevel(int fighterLevel) {
		this.fighterLevel = fighterLevel;
		fighterScoreBoardManager.updateLevel();
	}
	public void incPlayerLevel(int amount) {
		this.fighterLevel = this.fighterLevel + amount;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + fighterLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void decPlayerLevel(int amount) {
		this.fighterLevel = this.fighterLevel - amount;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled down to level " + fighterLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void setKills(int kills) {
		this.kills = kills;
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterHologramManager.fighterKilled();
	}
	public void incKills() {
		this.kills++;
		this.incKillStreak();
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterHologramManager.fighterKilled();
	}
	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
		fighterScoreBoardManager.updateKillstreak();
	}
	public void incKillStreak() {
		this.killStreak++;
		fighterScoreBoardManager.updateKillstreak();
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
		fighterScoreBoardManager.updateDeaths();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterHologramManager.fighterDied();
	}
	public void incDeaths() {
		this.deaths++;
		setKillStreak(0);
		fighterScoreBoardManager.updateDeaths();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterTaskManager.cancelGameplayTasks();
		fighterHologramManager.fighterDied();
	}

	public void setCakes(int cakes) {
		this.cakes = cakes;
		fighterScoreBoardManager.updateCookies();
	}
	public void incCakesByAmount(int inc) {
		this.cakes = this.cakes + inc;
		fighterScoreBoardManager.updateCookies();
	}
	public void decCakes(int inc) {
		this.cakes = this.cakes - inc;
		fighterScoreBoardManager.updateCookies();
	}
	public UUID getLastDamagedBy() {return lastDamagedBy;}
	public int getFighterLevel() {return fighterLevel;}
	public int getCakes() {return cakes;}
	public int getDeaths() {return deaths;}
	public int getKillStreak() {return killStreak;}
	public int getKills() {return kills;}
	public void fighterDismountParachute(){
		fighterKitManager.fighterDismountParachute();
	}
	public void addToFightersHashMap() {fighters.put(this.uuid, this);}
	public Player getPlayer() {return player;}
	public static Fighter get(Player player) {return fighters.get(player.getUniqueId());}
	public UUID getUuid() {return uuid;}
	public UUID getLastToDamage() {return lastToDamage;}
	public Plugin getPlugin() {return plugin;}
	public void setCooldownTask(int cooldownTask) {fighterTaskManager.cooldownTask = cooldownTask;}
	public void setRechargeTask(int rechargeTask) {fighterTaskManager.rechargeTask = rechargeTask;}
	public FighterScoreBoardManager getFighterScoreBoardManager() {return this.fighterScoreBoardManager;}
	public FighterKit getFKit() {return fighterKitManager.getFKit();}
	public static FighterKit getFighterFKit(Player player) {return Fighter.get(player).getFKit();}
	public static int getNumberOfKits() {return numberOfKits;}
	public int getKitID(){return fighterKitManager.getKitID();}
	public FighterKitManager getFighterKitManager() {return fighterKitManager;}
	public HologramsManager getFighterHologramManager() {return fighterHologramManager;}

	public FighterAbilityManager getWeaponAbilityManager(){
		return fighterAbilityManager;
	}

	public FighterTaskManager getFighterTaskManager() {
		return fighterTaskManager;
	}

	public int getFighterXP() {
		return fighterXP;
	}

	public void setFighterXP(int fighterXP) {
		this.fighterXP = fighterXP;
	}

	public void deleteMeFromDatabase(){
		this.fighterMYSQLManager.deleteMeFromDatabase();
	}

	public void incFighterXP(int amount){
		this.fighterXP = fighterXP + amount;
	}
}

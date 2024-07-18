package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.NPCS.GUIs.GUIManager;
import me.cade.vanabyte.NPCS.GUIs.QuestManager;
import me.cade.vanabyte.NPCS.PacketHolograms.HologramManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class Fighter {

	public static HashMap<UUID, Fighter> fighters = new HashMap<UUID, Fighter>();

	private Plugin plugin = VanaByte.getPlugin(VanaByte.class);
	private Player player = null;
	private UUID uuid = null;
	private static final int numberOfKits = 7;
	private int kitID, fighterLevel, fighterXP, kills,killStreak,deaths,cakes = -1;

	protected HologramManager fighterPacketHologramsManager = null;
	protected FighterTaskManager fighterTaskManager = null;
	protected FighterMYSQLManager fighterMYSQLManager = null;
	protected FighterKitManager fighterKitManager = null;
	protected FighterScoreBoardManager fighterScoreBoardManager = null;
	protected FighterAbilityManager fighterAbilityManager = null;
	protected GUIManager guiManager = null;
	protected QuestManager questManager = null;

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		this.fighterMYSQLManager = new FighterMYSQLManager(this.player, this);
		this.fighterPacketHologramsManager = new HologramManager(this.player, this);
		this.fighterTaskManager = new FighterTaskManager(this.player, this);
		this.fighterKitManager = new FighterKitManager(this.player, this);
		this.fighterAbilityManager = new FighterAbilityManager(this);
		this.fighterScoreBoardManager = new FighterScoreBoardManager(player);
		this.guiManager = new GUIManager(player);
		this.questManager = new QuestManager(this.player, this);
		this.fighterJoined();
	}

	public void fighterJoined(){
		fighterMYSQLManager.fighterJoined();
		fighterKitManager.fighterJoined();
		fighterTaskManager.fighterJoined();
		fighterPacketHologramsManager.fighterJoined();
		//Need to do this after everything has been setup
		fighterKitManager.fighterJoined();
		fighterAbilityManager.fighterJoined();
	}

	public void fighterRespawn() {
		fighterAbilityManager.fighterRespawned();
		fighterKitManager.fighterRespawned();
		fighterTaskManager.fighterRespawned();
		fighterPacketHologramsManager.fighterRespawned();
		fighterMYSQLManager.fighterRespawned();
	}

	public void fighterLeftServer() {
		fighterMYSQLManager.fighterLeftServer();
		fighterTaskManager.fighterLeftServer();
		fighterKitManager.fighterLeftServer();
		fighterAbilityManager.fighterLeftServer();
		fighterPacketHologramsManager.fighterLeftServer();
	}

	public void fighterDeath() {
		this.incDeaths();
		fighterAbilityManager.fighterDied();
		fighterKitManager.fighterDied();
		fighterTaskManager.fighterDied();
		fighterPacketHologramsManager.fighterDied();
		fighterMYSQLManager.fighterDied();
	}

	public void fighterChangeWorld(World from){
		fighterAbilityManager.fighterChangedWorld();
		fighterKitManager.fighterChangedWorld();
		fighterTaskManager.fighterChangedWorld();
		fighterPacketHologramsManager.fighterChangedWorld(from);
		fighterMYSQLManager.fighterChangedWorld();
	}

	public void fighterGotNewKit(){
		fighterAbilityManager.fighterGotNewKit();
	}

	public void fighterPurchasedKit() {
		fighterPacketHologramsManager.fighterPurchasedKit();
	}

	public void setKitID(int kitID){
		this.kitID = kitID;
	}

	protected void setFighterLevel(int fighterLevel) {
		this.fighterLevel = fighterLevel;
		fighterScoreBoardManager.updateLevel();
	}
	public void setFighterXP(int fighterXP) {
		this.fighterXP = fighterXP;
		fighterScoreBoardManager.updateExp();
	}

	public void refreshLevel(){
		this.fighterLevel = player.getLevel();
		fighterScoreBoardManager.updateLevel();
	}

	public void refreshXP(){
		this.fighterXP = (int) player.getExp() * 100;
		fighterScoreBoardManager.updateLevel();
	}

	public void incFighterLevel(int amount) {
		this.fighterLevel = this.fighterLevel + amount;
//		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
//		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + fighterLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void decPlayerLevel(int amount) {
		this.fighterLevel = this.fighterLevel - amount;
//		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
//		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled down to level " + fighterLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void setKills(int kills) {
		this.kills = kills;
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterPacketHologramsManager.fighterKilled();
	}
	public void incKills() {
		this.kills++;
		this.incKillStreak();
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterPacketHologramsManager.fighterKilled();
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
		fighterPacketHologramsManager.fighterDied();
	}
	public void incDeaths() {
		this.deaths++;
		setKillStreak(0);
		fighterScoreBoardManager.updateDeaths();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterTaskManager.cancelGameplayTasks();
		fighterPacketHologramsManager.fighterDied();
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

	public void addPotionIfStrengthIsNonNegative(PotionEffectType potionEffect, int durationTicks, double strength){
		if((int) strength >= 0){
			player.addPotionEffect(new PotionEffect(potionEffect, durationTicks, (int) strength));
		}
	}

	public int getKitID() {return kitID;};
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
	public Plugin getPlugin() {return plugin;}
	public void setCooldownTask(int cooldownTask) {fighterTaskManager.cooldownTask = cooldownTask;}
	public void setRechargeTask(int rechargeTask) {fighterTaskManager.rechargeTask = rechargeTask;}
	public FighterScoreBoardManager getFighterScoreBoardManager() {return this.fighterScoreBoardManager;}
	public static int getNumberOfKits() {return numberOfKits;}
	public FighterKitManager getFighterKitManager() {return fighterKitManager;}
	public HologramManager getFighterPacketHologramsManager() {return fighterPacketHologramsManager;}
	public FighterAbilityManager getWeaponAbilityManager(){
		return fighterAbilityManager;
	}
	public FighterTaskManager getFighterTaskManager() {
		return fighterTaskManager;
	}
	public int getFighterXP() {
		return fighterXP;
	}
	public void deleteMeFromDatabase(){
		this.fighterMYSQLManager.deleteMeFromDatabase();
	}
	public void incFighterXP(int amount){
		this.fighterXP = fighterXP + amount;
	}
	public GUIManager getGUIManager() {
		return guiManager;
	}
	public QuestManager getQuestManager() {
		return questManager;
	}
	public FighterMYSQLManager getFighterMYSQLManager(){
		return fighterMYSQLManager;
	}

	public double getDoubleFromWeaponType(WeaponType weaponType, int index){
		return fighterMYSQLManager.getFIGHTER_STATS()[weaponType.getWeaponID()][index];
	}

	public int getTickFromWeaponType(WeaponType weaponType, int index){
		return VanaByte.convertDoubleToTicks(fighterMYSQLManager.getFIGHTER_STATS()[weaponType.getWeaponID()][index]);
	}

}

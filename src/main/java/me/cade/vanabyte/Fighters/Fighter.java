package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.FighterKits.F5;
import me.cade.vanabyte.Fighters.FighterKits.FighterKit;
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
	private int playerLevel,kills,killStreak,deaths,cakes = -1;
	protected FighterHologramManager fighterHologramManager = null;
	protected FighterAbilityManager fighterAbilityManager = null;
	protected FighterTaskManager fighterTaskManager = null;
	protected FighterMYSQLManager fighterMYSQLManager = null;
	protected FighterKitManager fighterKitManager = null;
	protected FighterScoreBoardManager fighterScoreBoardManager = null;

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		this.fighterHologramManager = new FighterHologramManager(this.player, this);
		this.fighterAbilityManager = new FighterAbilityManager(this.player, this);
		this.fighterTaskManager = new FighterTaskManager(this.player, this);
		this.fighterKitManager = new FighterKitManager(this.player, this);
		this.fighterMYSQLManager = new FighterMYSQLManager(this.player, this);
		fighterKitManager.setNegatives();
		this.fighterScoreBoardManager = new FighterScoreBoardManager(player);
		fighterMYSQLManager.addPlayerToDatabases();
		fighterMYSQLManager.initiateMySQLDownloads();
		fighterKitManager.giveKit();
		this.player.setInvisible(false);
		this.resetSpecialAbility();
		fighterAbilityManager.resetPlayerParticles();
		fighterAbilityManager.applyNightVision();
		fighterKitManager.resetAllFighterItemCooldowns();
		this.spawnHolograms();
		fighterTaskManager.refreshMySQLUpload();
	}

	public void fighterRespawn() {
		this.setLastDamagedBy(null);
		this.setLastToDamage(null);
		fighterKitManager.giveKit();
		VanaByte.getPpAPI().resetActivePlayerParticles(player);
		fighterAbilityManager.applyNightVision();
		this.resetSpecialAbility();
		fighterKitManager.resetAllFighterItemCooldowns();
	}

	public void fighterLeftServer() {
		fighterMYSQLManager.uploadFighter();
		fighterKitManager.fighterDismountParachute();
		fighterTaskManager.cancelAllTasks();
	}
	public void giveKit(){
		fighterKitManager.giveKit();
		fighterAbilityManager.applyNightVision();
		fighterAbilityManager.resetSpecialAbility();
		fighterKitManager.resetAllFighterItemCooldowns();
		fighterAbilityManager.changeAbilityRechargedParticleEffect();
	}
	public void fighterDeath() {
		fighterKitManager.fighterDismountParachute();
		this.incDeaths();
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
	protected void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
		fighterScoreBoardManager.updateLevel();
	}
	public void incPlayerLevel(int amount) {
		this.playerLevel = this.playerLevel + amount;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + playerLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void decPlayerLevel(int amount) {
		this.playerLevel = this.playerLevel - amount;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled down to level " + playerLevel + "!");
		fighterScoreBoardManager.updateLevel();
	}
	public void setKills(int kills) {
		this.kills = kills;
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterHologramManager.refreshWelcomeHologram();
	}
	public void incKills() {
		this.kills++;
		this.incKillStreak();
		fighterScoreBoardManager.updateKills();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		fighterHologramManager.refreshWelcomeHologram();
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
	}
	public void incDeaths() {
		this.deaths++;
		setKillStreak(0);
		fighterScoreBoardManager.updateDeaths();
		fighterScoreBoardManager.updateRatio();
		fighterScoreBoardManager.updateKillstreak();
		this.doDeathChecks();
		fighterHologramManager.refreshWelcomeHologram();
	}
	private void doDeathChecks() {
		if (fighterKitManager.getFKit() instanceof F5) {
			if (fighterTaskManager.groundPoundTask != -1) {
				F5.stopListening(this);
			}
		}
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
	public int getPlayerLevel() {return playerLevel;}
	public int getCakes() {return cakes;}
	public int getDeaths() {return deaths;}
	public int getKillStreak() {return killStreak;}
	public int getKills() {return kills;}
	public void setAbilityRecharged(boolean setter){fighterAbilityManager.setAbilityRecharged(setter);}
	public void setAbilityActive(boolean setter){fighterAbilityManager.setAbilityActive(setter);}
	public int getDurationTicks(){return this.getFKit().getDurationTicks();}
	public int getRechargeTicks(){return this.getFKit().getRechargeTicks();}
	public void startAbilityDuration(){fighterAbilityManager.startAbilityDuration();}
	public void spawnHolograms(){
		fighterHologramManager.spawnHolograms();
	}
	public void fighterDismountParachute(){
		fighterKitManager.fighterDismountParachute();
	}
	public void resetSpecialAbility() {
		fighterAbilityManager.resetSpecialAbility();
	}
	public void addToFightersHashMap() {fighters.put(this.uuid, this);}
	public Player getPlayer() {return player;}
	public static Fighter get(Player player) {return fighters.get(player.getUniqueId());}
	public UUID getUuid() {return uuid;}
	public boolean isAbilityActive() {return fighterAbilityManager.isAbilityActive();}
	public UUID getLastToDamage() {return lastToDamage;}
	public int getGroundPoundTask() {return fighterTaskManager.groundPoundTask;}
	public void setGroundPoundTask(int groundPoundTask) {fighterTaskManager.groundPoundTask = groundPoundTask;}
	public Plugin getPlugin() {return plugin;}
	public void setCooldownTask(int cooldownTask) {fighterTaskManager.cooldownTask = cooldownTask;}
	public void setRechargeTask(int rechargeTask) {fighterTaskManager.rechargeTask = rechargeTask;}
	public FighterScoreBoardManager getFighterScoreBoardManager() {return this.fighterScoreBoardManager;}
	public FighterKit getFKit() {return fighterKitManager.getFKit();}
	public static FighterKit getFighterFKit(Player player) {return Fighter.get(player).getFKit();}
	public static int getNumberOfKits() {return numberOfKits;}
	public void setActionBarToFloat(float setter){fighterAbilityManager.setActionBarToFloat(setter);}
	public static FighterKit[] getFkitsNoPlayer(){
		return FighterKitManager.getFkitsNoPlayer();
	}
	public int getKitID(){return fighterKitManager.getKitID();}
	public FighterKitManager getFighterKitManager() {return fighterKitManager;}
	public FighterHologramManager getFighterHologramManager() {return fighterHologramManager;}
}

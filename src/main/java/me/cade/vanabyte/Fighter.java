package me.cade.vanabyte;

import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.ParticleStyle;
import me.cade.vanabyte.BuildKits.*;
import me.cade.vanabyte.NPCS.D_ProtocolStand;
import me.cade.vanabyte.ScoreBoard.ScoreBoardObject;
import me.cade.vanabyte.SpecialItems.JetPackItem;
import me.cade.vanabyte.SpecialItems.ParachuteItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

import static me.cade.vanabyte.AbilityEnchantment.makeEnchanted;
import static me.cade.vanabyte.AbilityEnchantment.removeEnchanted;

public class Fighter {

	// your error for the unlocked might be in the way you are uploading it as a
	// boolean array
	private Plugin plugin;
	public static HashMap<UUID, Fighter> fighters = new HashMap<UUID, Fighter>();
	private Player player;
	private UUID uuid;

	private boolean abilityActive;
	private boolean abilityRecharged;

	// changes alot during the game
	private UUID lastToDamage;
	private UUID lastDamagedBy;
	private int cooldownTask;
	private int rechargeTask;

	private int kitID;
	private int kitIndex;
	private int playerLevel;
	private int kills;
	private int killStreak;
	private int deaths;
	private int cakes;
	private int exp;
	private int[] unlockedKits = new int[7];
	private ScoreBoardObject scoreBoardObject;

	private static float walkSpeed = (float) 0.275;
	private static float walkSpeedBoosted = (float) 0.3;

	private int groundPoundTask;
	private int doubleJumpTask;

	private D_ProtocolStand[] personalStands = new D_ProtocolStand[7];

	private D_ProtocolStand chargedStand = null;

	private FighterKit fKit = null;

	private boolean inHub = true;

	private static final int numberOfKits = 7;
	private static FighterKit[] fKits = { new F0(), new F1(), new F2(), new F3(), new F4(), new F5(), new F6() };

	public Fighter(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.addToFightersHashMap();
		this.fighterJoin();
	}

	private void fighterJoin() {
		this.initializeFighterVars();
		this.downloadDatabase();
		//chargedStand = new D_ProtocolStand(ChatColor.GREEN + "" + ChatColor.BOLD + player.getDisplayName() + "'s Spawn", VanaByte.hubSpawn, player);
		this.grantUnlocked();
		this.scoreBoardObject = new ScoreBoardObject(player);
		this.giveKit();
		VanaByte.getPpAPI().resetActivePlayerParticles(player);
		this.resetSpecialAbility();
		this.adjustJoinModifiers();
		this.fKit.resetSpecialItemCooldowns();
	}

	public void fighterRespawn() {
		this.setLastDamagedBy(null);
		this.setLastToDamage(null);
		this.adjustJoinModifiers();
		this.resetSpecialAbility();
		this.fKit.resetSpecialItemCooldowns();
	}

	public void fighterLeftServer() {
		this.uploadFighter();
		this.fighterDismountParachute();
		this.resetSpecialAbility();
	}

	public void fighterDeath() {
		this.resetSpecialAbility();
		this.fighterDismountParachute();
		this.incDeaths();
	}

	public void fighterDismountParachute() {
		if (((ParachuteItem) this.fKit.getSpecialItem(ParachuteItem.mat)) == null) {
			return;
		}
		if(((ParachuteItem) this.fKit.getSpecialItem(ParachuteItem.mat)).getItemTask() != -1){
			((ParachuteItem) this.fKit.getSpecialItem(ParachuteItem.mat)).getOff();
		}
	}

	public void resetSpecialAbility() {
		setAbilityActive(false);
		setAbilityRecharged(true);
		player.setExp(1);
		player.setLevel(0);
	}

	public void adjustJoinModifiers() {
		player.setWalkSpeed(getWalkSpeed());
		if(this.fKit.getJetPackItem() != null) {
			JetPackItem.changeFlightStatus(player, true);
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, true, false));
			}
		}, 1);
	}

	public void setAbilityActive(boolean fighterAbility) {
		if (!fighterAbility) {
			// turning ability active off

			// only if the ability was already active
			if (this.abilityActive) {
				player.setCooldown(Material.BIRCH_FENCE, 0);
				cancelCooldownTask();
				fKit.deActivateSpecial();
			}
		}
		this.abilityActive = fighterAbility;
		changeAbilityActiveParticleEffect();

	}

	public void setAbilityRecharged(boolean fighterRecharged) {
		if (fighterRecharged) {
			// turning ability charged fully on

			// only if the ability was already not recharged
			if (!this.abilityRecharged) {
				player.setCooldown(Material.JUNGLE_FENCE, 0);
				cancelRechargeTask();
			}
		}
		this.abilityRecharged = fighterRecharged;
		this.changeAbilityRechargedParticleEffect();
	}

	private void changeAbilityActiveParticleEffect() {
		if (this.kitID == 6) {
			// greif goes invisible
			return;
		}
		if (this.abilityActive) {
			if (VanaByte.getPpAPI() != null) {
				VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
				VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("normal"),
						new OrdinaryColor(this.getFKit().getArmorColor().getRed(),
								this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
				makeEnchanted(this.player);
			}
		} else {
			if (VanaByte.getPpAPI() != null) {
				VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("normal"));
				removeEnchanted(this.player);
			}
		}
	}

	private void changeAbilityRechargedParticleEffect() {
		if (this.abilityRecharged) {
			if (VanaByte.getPpAPI() != null) {
				VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
				VanaByte.getPpAPI().addActivePlayerParticle(player, ParticleEffect.DUST, ParticleStyle.fromName("point"),
						new OrdinaryColor(this.getFKit().getArmorColor().getRed(),
								this.getFKit().getArmorColor().getGreen(), this.getFKit().getArmorColor().getBlue()));
			}
		} else {
			if (VanaByte.getPpAPI() != null) {
				VanaByte.getPpAPI().removeActivePlayerParticles(player, ParticleStyle.fromName("point"));
			}
		}
	}

	public void cancelCooldownTask() {
		if (this.cooldownTask != -1) {
			Bukkit.getScheduler().cancelTask(this.cooldownTask);
			this.cooldownTask = -1;
		}
	}

	public void cancelRechargeTask() {
		if (this.rechargeTask != -1) {
			Bukkit.getScheduler().cancelTask(this.rechargeTask);
			this.rechargeTask = -1;
		}
	}

	public void addToFightersHashMap() {
		fighters.put(this.uuid, this);
	}

	private void downloadDatabase() {
		if (VanaByte.mysql.playerExists(player)) {
			downloadFighter();
		} else {
			VanaByte.mysql.addScore(player);
			downloadDatabase();
			return;
		}
		updateName();
	}

	public void giveKit() {
		if (kitID == fKits[0].getKitID()) {
			fKit = new F0(player);
		} else if (kitID == fKits[1].getKitID()) {
			fKit = new F1(player);
		} else if (kitID == fKits[2].getKitID()) {
			fKit = new F2(player);
		} else if (kitID == fKits[3].getKitID()) {
			fKit = new F3(player);
		} else if (kitID == fKits[4].getKitID()) {
			fKit = new F4(player);
		} else if (kitID == fKits[5].getKitID()) {
			fKit = new F5(player);
		} else if (kitID == fKits[6].getKitID()) {
			fKit = new F6(player);
		}
		this.adjustJoinModifiers();
		this.resetSpecialAbility();
		this.fKit.resetSpecialItemCooldowns();
		this.changeAbilityRechargedParticleEffect();
	}

	public void giveKitWithID(int kitID) {
		this.setKitID(kitID);
		this.giveKit();
	}

	public Player getPlayer() {
		return player;
	}

	public static Fighter get(Player player) {
		return fighters.get(player.getUniqueId());
	}

	public int getKitID() {
		return kitID;
	}

	public void setKitID(int kitID) {
		this.kitID = kitID;
	}

	public int getKitIndex() {
		return kitIndex;
	}

	public void setKitIndex(int kitIndex) {
		this.kitIndex = kitIndex;
	}

	public UUID getUuid() {
		return uuid;
	}

	public boolean isAbilityActive() {
		return abilityActive;
	}

	public UUID getLastToDamage() {
		return lastToDamage;
	}

	public void setLastToDamage(Player lastToDamage) {
		if (this.lastToDamage == null) {
			this.lastToDamage = null;
			return;
		}
		this.lastToDamage = lastToDamage.getUniqueId();
	}

	public UUID getLastDamagedBy() {
		return lastDamagedBy;
	}

	public void setLastDamagedBy(Player lastDamagedBy) {
		if (lastDamagedBy == null) {
			this.lastDamagedBy = null;
			return;
		}
		this.lastDamagedBy = lastDamagedBy.getUniqueId();
	}

	public void downloadFighter() {
		this.setKitID(VanaByte.mysql.getStat(player, VanaByte.mysql.column[2]));
		this.setKitIndex(VanaByte.mysql.getStat(player, VanaByte.mysql.column[3]));
		this.setPlayerLevel(VanaByte.mysql.getStat(player, VanaByte.mysql.column[4]));
		this.setKills(VanaByte.mysql.getStat(player, VanaByte.mysql.column[5]));
		this.setKillStreak(VanaByte.mysql.getStat(player, VanaByte.mysql.column[6]));
		this.setDeaths(VanaByte.mysql.getStat(player, VanaByte.mysql.column[7]));
		this.setCakes(VanaByte.mysql.getStat(player, VanaByte.mysql.column[8]));
		this.setExp(VanaByte.mysql.getStat(player, VanaByte.mysql.column[9]));
		this.setUnlockedKit(0, VanaByte.mysql.getStat(player, VanaByte.mysql.column[10]));
		this.setUnlockedKit(1, VanaByte.mysql.getStat(player, VanaByte.mysql.column[11]));
		this.setUnlockedKit(2, VanaByte.mysql.getStat(player, VanaByte.mysql.column[12]));
		this.setUnlockedKit(3, VanaByte.mysql.getStat(player, VanaByte.mysql.column[13]));
		this.setUnlockedKit(4, VanaByte.mysql.getStat(player, VanaByte.mysql.column[14]));
		this.setUnlockedKit(5, VanaByte.mysql.getStat(player, VanaByte.mysql.column[15]));
		this.setUnlockedKit(6, VanaByte.mysql.getStat(player, VanaByte.mysql.column[16]));
	}

	public void uploadFighter() {
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[2], this.getKitID());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[3], this.getKitIndex());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[4], this.getPlayerLevel());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[5], this.getKills());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[6], this.getKillStreak());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[7], this.getDeaths());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[8], this.getCakes());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[9], this.getExp());
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[10], this.getUnlockedKit(0));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[11], this.getUnlockedKit(1));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[12], this.getUnlockedKit(2));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[13], this.getUnlockedKit(3));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[14], this.getUnlockedKit(4));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[15], this.getUnlockedKit(5));
		VanaByte.mysql.setStat(player.getUniqueId().toString(), VanaByte.mysql.column[16], this.getUnlockedKit(6));
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public void incPlayerLevel() {
		this.playerLevel++;
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Leveled up to level " + playerLevel + "!");
		scoreBoardObject.updateLevel();
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void incKills() {
		this.kills++;
		this.killStreak++;
		incExpByAmount(30);
		scoreBoardObject.updateKills();
		scoreBoardObject.updateRatio();
		scoreBoardObject.updateKillstreak();
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}

	public void incKillStreak() {
		this.killStreak++;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void incDeaths() {
		this.deaths++;
		setKillStreak(0);
		scoreBoardObject.updateDeaths();
		scoreBoardObject.updateRatio();
		scoreBoardObject.updateKillstreak();
	}

	public void doDeathChecks() {
		if (kitID == fKits[5].getKitID()) {
			if (groundPoundTask != -1) {
				F5.stopListening(this);
			}
		}
		return;
	}

	public int getCakes() {
		return cakes;
	}

	public void setCakes(int cakes) {
		this.cakes = cakes;
	}

	public void incCakesByAmount(int inc) {
		this.cakes = this.cakes + inc;
		scoreBoardObject.updateCookies();
	}

	public void decCakes(int inc) {
		this.cakes = this.cakes - inc;
		scoreBoardObject.updateCookies();
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void incExpByAmount(int exp) {
		this.exp = this.exp + exp;
		scoreBoardObject.updateExp();
	}

	public void setUnlockedKit(int kitID, int index) {
		this.unlockedKits[kitID] = index;
	}

	public int getUnlockedKit(int kitID) {
		return unlockedKits[kitID];
	}

	private void updateName() {
		VanaByte.mysql.updateName(player, VanaByte.mysql.column[1], player.getName());
	}

	public void initializeFighterVars() {
		this.plugin = VanaByte.getPlugin(VanaByte.class);
		this.abilityActive = false;
		this.abilityRecharged = true;
		this.player.setExp(0);
		this.player.setLevel(0);
		this.player.setInvisible(false);
		this.player.setWalkSpeed(getWalkSpeed());
		// Glowing.setGlowingOffForAll(this.player);
		this.lastToDamage = null;
		this.lastDamagedBy = null;
		this.kitID = 0;
		this.kitIndex = 0;
		this.playerLevel = 0;
		this.kills = 0;
		this.deaths = 0;
		this.cakes = 0;
		this.killStreak = 0;
		this.exp = 0;
		this.unlockedKits[0] = 0;
		this.unlockedKits[1] = 0;
		this.unlockedKits[2] = 0;
		this.unlockedKits[3] = 0;
		this.unlockedKits[4] = 0;
		this.unlockedKits[5] = 0;
		this.unlockedKits[6] = 0;
		this.setDoubleJumpTask(-1);
		this.setGroundPoundTask(-1);
		this.setCooldownTask(-1);
	}

	public void grantUnlocked() {
		this.unlockedKits[0] = 1;
		this.unlockedKits[1] = 1;
		this.unlockedKits[2] = 1;
		this.unlockedKits[3] = 1;
		this.unlockedKits[4] = 1;
		this.unlockedKits[5] = 1;
		this.unlockedKits[6] = 1;
	}

	public int getGroundPoundTask() {
		return groundPoundTask;
	}

	public void setGroundPoundTask(int groundPoundTask) {
		this.groundPoundTask = groundPoundTask;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	public int getCooldownTask() {
		return cooldownTask;
	}

	public void setCooldownTask(int cooldownTask) {
		this.cooldownTask = cooldownTask;
	}

	public void setRechargeTask(int rechargeTask) {
		this.rechargeTask = rechargeTask;
	}

	public int getRechargeTask() {
		return rechargeTask;
	}

	public boolean isAbilityRecharged() {
		return abilityRecharged;
	}

	public ScoreBoardObject getScoreBoardObjext() {
		return this.scoreBoardObject;
	}

	public void setScoreBoardObjext(ScoreBoardObject scoreBoardObject) {
		this.scoreBoardObject = scoreBoardObject;
	}

	public D_ProtocolStand[] getPersonalStands() {
		return personalStands;
	}

	public D_ProtocolStand getChargedStand() {
		return chargedStand;
	}

	public FighterKit getFKit() {
		return fKit;
	}
	
	public static FighterKit getFighterFKit(Player player) {
		return Fighter.get(player).getFKit();
	}

	public void setFKit(FighterKit fkit) {
		this.fKit = fkit;
	}

	public static FighterKit[] getFKits() {
		return fKits;
	}

	public static float getWalkSpeed() {
		return walkSpeed;
	}

	public static float getWalkSpeedBoosted() {
		return walkSpeedBoosted;
	}

	public static int getNumberOfKits() {
		return numberOfKits;
	}

	public int getDoubleJumpTask() {
		return doubleJumpTask;
	}

	public void setDoubleJumpTask(int doubleJumpTask) {
		this.doubleJumpTask = doubleJumpTask;
	}

	public boolean isInHub() {
		return inHub;
	}

	public void setInHub(boolean inHub) {
		this.inHub = inHub;
	}
}

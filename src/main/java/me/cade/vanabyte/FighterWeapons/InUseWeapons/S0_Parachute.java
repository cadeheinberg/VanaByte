package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class S0_Parachute extends WeaponHolder {
	final String weaponDrop = "Open Parachute";
	final String weaponRightClick = "Open Parachute";
	final ChatColor weaponNameColor = ChatColor.YELLOW;
	final String weaponName = weaponNameColor + "Parachute";
	private Material material = null;
	private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
	private double specialDamage, meleeDamage, projectileDamage = -1;
	private FighterKitManager fighterKitManager = null;
	private Fighter fighter = null;
	private Player player = null;
	private Weapon weapon = null;
	private Chicken chicken = null;
	public S0_Parachute(Fighter fighter) {
		super(fighter);
		this.fighter = fighter;
		this.player = this.fighter.getPlayer();
		this.fighterKitManager = this.fighter.getFighterKitManager();
		this.meleeDamage = 5 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
		this.projectileDamage = 10 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
		this.specialDamage = 12 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
		this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
		this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
		this.rightClickCooldownTicks = 5 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
		this.material = Material.PHANTOM_MEMBRANE;
		this.weapon = new Weapon(WeaponType.PARACHUTE, this.getMaterial(), this.weaponName, this.meleeDamage,
				this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
				this.getAbilityRechargeTicks());
	}
	public S0_Parachute(){
		super();
		this.meleeDamage = 6;
		this.projectileDamage = 0;
		this.specialDamage = 7;
		this.abilityDurationTicks = 200;
		this.abilityRechargeTicks = 50;
		this.rightClickCooldownTicks = 0;
		this.material = Material.PHANTOM_MEMBRANE;
		this.weapon = new Weapon(WeaponType.PARACHUTE, this.getMaterial(), this.weaponName, this.meleeDamage,
				this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
				this.getAbilityRechargeTicks());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean doRightClick() {
		if (player.isOnGround()) {
			this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if (this.fighter.getFighterTaskManager().getParachuteTask() != 0) {
			this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if(this.chicken != null){
			this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		if(!super.doRightClick()){
			return false;
		}
		this.doParachute(player);
		return true;
	}
	@Override
	public boolean doDrop() {
		return this.doRightClick();
	}
	@Override
	public void activateSpecial() {
		super.activateSpecial();
	}
	@Override
	public void deActivateSpecial() {
		super.deActivateSpecial();
	}

	@SuppressWarnings("deprecation")
	private void doParachute(Player player) {
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 8, 1);
		Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
		chicken.addPassenger(player);
		chicken.setMetadata("parachute", new FixedMetadataValue(VanaByte.getInstance(), true));
		this.chicken = chicken;
		doGliding(chicken, player);
	}

	private void doGliding(Chicken chicken, Player player) {
		this.fighter.getFighterTaskManager().setParachuteTask(new BukkitRunnable() {
			@Override
			public void run() {
//				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Parachute Task Running");
				if (chicken.isOnGround()) {
					chicken.eject();
					getOff();
					return;
				}
				Location loc = player.getEyeLocation();
				if (loc.getPitch() < 40) {
					loc.setPitch(40);
				} else if (loc.getPitch() >= 75) {
					loc.setPitch(75);
				}
				Vector vector = loc.getDirection();
				chicken.setVelocity(vector.multiply(0.6));
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId());
	}

	public void getOff() {
		this.fighter.getFighterTaskManager().cancelParachuteTask();
		if(chicken != null) {
			chicken.remove();
			this.chicken = null;
		}
	}
	@Override
	public String getWeaponDrop() {
		return weaponDrop;
	}

	@Override
	public String getWeaponRightClick() {
		return weaponRightClick;
	}

	@Override
	public String getWeaponName() {return weaponName;}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public int getRightClickCooldownTicks() {
		return rightClickCooldownTicks;
	}

	@Override
	public int getAbilityDurationTicks() {
		return abilityDurationTicks;
	}

	@Override
	public int getAbilityRechargeTicks() {
		return abilityRechargeTicks;
	}

	@Override
	public double getProjectileDamage() {
		return projectileDamage;
	}

	@Override
	public double getMeleeDamage() {
		return meleeDamage;
	}

	@Override
	public double getSpecialDamage() {
		return specialDamage;
	}

	@Override
	public ChatColor getWeaponNameColor(){return weaponNameColor;}

	@Override
	public Weapon getWeapon(){
		return weapon;
	}
}

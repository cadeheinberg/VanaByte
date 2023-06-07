package me.cade.vanabyte.BuildKits;

import me.cade.vanabyte.Fighter;
import me.cade.vanabyte.SpecialItems.*;
import me.cade.vanabyte.VanaByte;
import me.cade.vanabyte.Weapon;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class FighterKit {

	Player player;
	Fighter pFight;
	Weapon[] weapons = new Weapon[2];

	EnchantmentPair perkEnchantment1;

	SpecialItem[] specialItems = new SpecialItem[2];

	private SpecialItem combatTracker;
	private SpecialItem jetPackItem;

	public FighterKit() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Creating FighterKit without player");
		this.setUpPrivateKitVariables();
		this.loadPrimaryWeapon();
	}

	public FighterKit(Player player) {
		Bukkit.getConsoleSender()
				.sendMessage(ChatColor.GREEN + "Creating FighterKit with player + " + player.getDisplayName());
		this.player = player;
		this.pFight = Fighter.get(player);
		this.setUpPrivateKitVariables();
		this.checkIfHasPerkEnchantments();
		this.giveKit();
	}

	public static boolean isFighterWeaponOrSpecialItem(ItemStack item){
		if(F0.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		} else if (F1.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (F2.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (F3.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (F4.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (F5.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (F6.weaponName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (ParachuteItem.displayName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}else if (ThrowingTNTItem.displayName.equals(item.getItemMeta().getDisplayName())){
			return true;
		}
		return false;
	}

	private void checkIfHasPerkEnchantments() {
		if (true) {
			// perkEnchantment1 = new EnchantmentPair(Enchantment.FIRE_ASPECT, 1);
		}
	}

	public void setUpPrivateKitVariables() {
		// TODO Auto-generated method stub
	}

	public void giveKit() {
		this.player.getInventory().clear();
		this.loadPrimaryWeapon();
		this.loadSecondaryWeapon();
		for (Weapon weapon : weapons) {
			if (weapon == null) {
				break;
			}
			this.player.getInventory().addItem(weapon.getWeaponItem());
		}
		if (this instanceof F3) {
			this.getPlayer().getInventory().setItem(9, new ItemStack(Material.ARROW, 1));
		}
		this.addSpecialWeapons();
		this.giveArmor();
		player.closeInventory();
		player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
	}

	private void loadPrimaryWeapon() {
		weapons[0] = new Weapon(this.getMaterial(), this.getWeaponName(), this.getMeleeDamage(),
				this.getProjectileDamage(), this.getSpecialDamage(), this.getCooldownTicks(), this.getDurationTicks(),
				this.getRechargeTicks(), this.getPrimaryEnchantment(), perkEnchantment1);
	}

	private void addSpecialWeapons() {
		if (true) {
			this.specialItems[0] = (new ParachuteItem(this.player));
		}
		if (true) {
			this.specialItems[1] = (new ThrowingTNTItem(this.player));
		}
//		if (true) {
//			this.specialItems[2] = (new IceCageItem(this.player));
//		}
//		if (true) {
//			this.combatTracker = (new CombatTracker(this.player));
//			this.specialItems[3] = this.combatTracker;
//		}
//		if (true) {
//			this.jetPackItem = (new JetPackItem(this.player));
//			this.specialItems[4] = this.jetPackItem;
//		}
	}

	public void doSpecialItemRightClick(Material material) {
		if(this.getSpecialItem(material) == null) {
			return;
		}
		this.getSpecialItem(material).doRightClick();
	}
	
	public boolean doSpecialItemDrop(Material material) {
		SpecialItem sItem = this.getSpecialItem(material);
		if(sItem != null) {
			sItem.doDrop();
			return true;
		}
		return  false;
	}

	public boolean doRightClick(Material material) {
		if (this.getMaterial() == material) {
			if(this.getCooldownTicks() > 0) {
				if (player.getCooldown(this.getMaterial()) > 0) {
					return false;
				}
				player.setCooldown(this.getMaterial(), this.getCooldownTicks());
				return true;
			}
		}else {
			this.doSpecialItemRightClick(material);
		}
		return false;
	}

	//Returns True if there is no cooldown and ability for either
	// the Fighter Kit or special item can be activated
	//
	//Returns False if there is a cooldown or not a Fighter Kits item
	public boolean doDrop(Material material, String displayName, int kitID) {
		if ((this.getMaterial() == material) && (this.getWeaponName().equals(displayName)) && (this.getKitID() == kitID) || this.getSecondaryMaterial() == material) {
			//The dropped item matches the material of their Fighter Kit
			//The dropped item matches the weapon name of their Fighter Kit
			//The player who dropped the item has the correct kitID for this FighterKit
			if (this.player.getCooldown(Material.BIRCH_FENCE) > 0
					|| this.player.getCooldown(Material.JUNGLE_FENCE) > 0) {
				this.player.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
				this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
				return true;
			}
			this.activateSpecial();
			return true;
		}else {
			return this.doSpecialItemDrop(material);
		}
	}

	public Material getSecondaryMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

	void activateSpecial() {
		this.startAbilityDuration();
		this.player.sendMessage(ChatColor.AQUA + "Special Ability Activated");
	}

	public void deActivateSpecial() {
		// TODO Auto-generated method stub
	}

	public void loadSecondaryWeapon() {
		// pass
	}

	public ItemStack getWeaponItem() {
		return null;
	}

	public Color getArmorColor() {
		return null;
	}

	public Weapon[] getWeapons() {
		return weapons;
	}

	public int getKitID() {
		return -1;
	}

	public Material getMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getDurationTicks() {
		return -1;
	}

	public int getRechargeTicks() {
		return -1;
	}

	public String getKitName() {
		return null;
	}

	public String getKitDrop() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getKitRightClick() {
		// TODO Auto-generated method stub
		return null;
	}

	public ChatColor getKitChatColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getWeaponName() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getSpecialDamage() {
		// TODO Auto-generated method stub
		return -1;
	}

	public double getMeleeDamage() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "calling parent melee");
		// TODO Auto-generated method stub
		return -1;
	}

	public double getProjectileDamage() {
		// TODO Auto-generated method stub
		return -1;
	}

	public int getCooldownTicks() {
		return -1;
	}

	private void startAbilityDuration() {
		this.pFight.setAbilityActive(true);
		this.pFight.setAbilityRecharged(false);
		int durationTicks = this.getDurationTicks();
		player.setCooldown(Material.BIRCH_FENCE, durationTicks);
		int cooldownTask = new BukkitRunnable() {
			@Override
			public void run() {
				//Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cooldown Task Running");
				if (player != null) {
					if (!player.isOnline()) {
						return;
					}
					if (player.getCooldown(Material.BIRCH_FENCE) < 1) {
						cancel();
						Fighter.get(player).setAbilityActive(false);
						player.setExp(0);
						player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 8, 1);
						startAbilityRecharge();
						return;
					}
					player.setExp(((float) player.getCooldown(Material.BIRCH_FENCE)) / durationTicks);
				}
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
		Fighter.get(player).setCooldownTask(cooldownTask);
	}

	private void startAbilityRecharge() {
		int rechargeTicks = this.getRechargeTicks();
		player.setCooldown(Material.JUNGLE_FENCE, rechargeTicks);
		int rechargeTask = new BukkitRunnable() {
			@Override
			public void run() {
				//Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Recharge Task Running");
				if (player != null) {
					if (!player.isOnline()) {
						return;
					}
					if (player.getCooldown(Material.JUNGLE_FENCE) < 1) {
						cancel();
						Fighter.get(player).setAbilityRecharged(true);
						player.setExp(1);
						player.sendMessage(ChatColor.GREEN + "Special Ability Recharged");
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
						return;
					}
					player.setExp(
							((float) (rechargeTicks - player.getCooldown(Material.JUNGLE_FENCE))) / rechargeTicks);
				}
			}
		}.runTaskTimer(VanaByte.getInstance(), 0L, 1L).getTaskId();
		Fighter.get(player).setRechargeTask(rechargeTask);
	}

	private void giveArmor() {

		ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
		LeatherArmorMeta lhe = (LeatherArmorMeta) lhelmet.getItemMeta();
		lhe.setUnbreakable(true);

		ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta lch = (LeatherArmorMeta) lchest.getItemMeta();
		lch.setUnbreakable(true);

		ItemStack lleggs = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		LeatherArmorMeta lle = (LeatherArmorMeta) lleggs.getItemMeta();
		lle.setUnbreakable(true);

		ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta lbo = (LeatherArmorMeta) lboots.getItemMeta();
		lbo.setUnbreakable(true);

		lhe.setColor(this.getArmorColor());
		lch.setColor(this.getArmorColor());
		lle.setColor(this.getArmorColor());
		lbo.setColor(this.getArmorColor());

		lhe.addAttributeModifier(Attribute.GENERIC_ARMOR,
				new AttributeModifier("GENERIC_ARMOR", 5, AttributeModifier.Operation.ADD_NUMBER));
		lch.addAttributeModifier(Attribute.GENERIC_ARMOR,
				new AttributeModifier("GENERIC_ARMOR", 5, AttributeModifier.Operation.ADD_NUMBER));
		lle.addAttributeModifier(Attribute.GENERIC_ARMOR,
				new AttributeModifier("GENERIC_ARMOR", 5, AttributeModifier.Operation.ADD_NUMBER));
		lbo.addAttributeModifier(Attribute.GENERIC_ARMOR,
				new AttributeModifier("GENERIC_ARMOR", 5, AttributeModifier.Operation.ADD_NUMBER));

		lhe.addEnchant(Enchantment.BINDING_CURSE, 1, true);
		lch.addEnchant(Enchantment.BINDING_CURSE, 1, true);
		lle.addEnchant(Enchantment.BINDING_CURSE, 1, true);
		lbo.addEnchant(Enchantment.BINDING_CURSE, 1, true);

		ArrayList<String> itemLore = new ArrayList<String>();
		itemLore.add(3 + " armor protection");
		lhe.setLore(itemLore);
		lch.setLore(itemLore);
		lle.setLore(itemLore);
		lbo.setLore(itemLore);

//		lhe.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//		lch.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//		lle.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//		lbo.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//		lhe.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//		lch.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//		lle.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//		lbo.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		lhe.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		lch.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		lle.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		lbo.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		lhelmet.setItemMeta(lhe);
		lchest.setItemMeta(lch);
		lleggs.setItemMeta(lle);
		lboots.setItemMeta(lbo);

		player.getEquipment().setHelmet(lhelmet);
		player.getEquipment().setChestplate(lchest);
		player.getEquipment().setLeggings(lleggs);
		player.getEquipment().setBoots(lboots);

	}

	public class EnchantmentPair {
		private Enchantment enchantment;
		private int level;

		public EnchantmentPair(Enchantment enchantment, int level) {
			this.enchantment = enchantment;
			this.level = level;
		}

		public Enchantment getEnchantment() {
			return enchantment;
		}

		public void setEnchantment(Enchantment enchantment) {
			this.enchantment = enchantment;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		@SuppressWarnings("deprecation")
		public String getString() {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "calling to String");
			return enchantment.getName().toLowerCase() + "(" + level + ")";
		}
	}

	public EnchantmentPair getPrimaryEnchantment() {
		// TODO Auto-generated method stub
		return null;
	}

	public CombatTracker getCombatTracker() {
		return (CombatTracker) combatTracker;
	}
	
	public JetPackItem getJetPackItem() {
		return (JetPackItem) jetPackItem;
	}

	public SpecialItem[] getSpecialItems() {
		return specialItems;
	}

	public SpecialItem getSpecialItem(Material material) {
		for(SpecialItem sItem : Fighter.getFighterFKit(player).getSpecialItems()) {
			if(sItem.getMaterial() == material) {
				return sItem;
			}
		}
		return null;
	}

	public void resetSpecialItemCooldowns() {
		for(SpecialItem sItem : Fighter.getFighterFKit(player).getSpecialItems()) {
			sItem.resetCooldown();
		}
	}

	//override in class where pickup is used
	public void doPickUp(LivingEntity rightClicked) {
		//pass
	}

	//override in class where pickup is used
	public void doThrow(Player killer, LivingEntity victim) {
		// pass
		
	}
	//override in class where pickup is used
	public void doStealHealth(Player victim) {
		// pass
		
	}

}

package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.SpecialItems.*;
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

import java.util.ArrayList;

public abstract class FighterKit {

	Player player = null;
	Fighter fighter = null;
	Weapon[] weapons = new Weapon[2];
	EnchantmentPair perkEnchantment1;
	SpecialItem[] specialItems = new SpecialItem[2];
	ParachuteItem parachuteItem = null;
	FighterKitManager fighterKitManager = null;

	private Material cooldownMaterial = Material.BARRIER;

	public FighterKit() {
//		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Creating FighterKit without player");
		this.setUpPrivateKitVariables();
		this.loadPrimaryWeapon();
	}

	public FighterKit(Player player) {
//		Bukkit.getConsoleSender()
//				.sendMessage(ChatColor.GREEN + "Creating FighterKit with player + " + player.getDisplayName());
		this.player = player;
		this.fighter = Fighter.get(player);
		this.fighterKitManager = this.fighter.getFighterKitManager();
		this.setUpPrivateKitVariables();
		this.checkIfHasPerkEnchantments();
		this.giveKit();
	}

	public static boolean isFighterWeaponOrSpecialItem(ItemStack item){
		if(item == null){
			return false;
		}
		if(!item.hasItemMeta()){
			return false;
		}if(item.getItemMeta().getDisplayName() == null){
			return false;
		}
		String displayName = item.getItemMeta().getDisplayName();
		if(F0.weaponName.equals(displayName)){
			return true;
		} else if (F1.weaponName.equals(displayName)){
			return true;
		}else if (F2.weaponName.equals(displayName)){
			return true;
		}else if (F3.weaponName.equals(displayName) || F3.secondaryWeaponName.equals(displayName) || F3.arrowName.equals(displayName)){
			return true;
		}else if (F4.weaponName.equals(displayName)){
			return true;
		}else if (F5.weaponName.equals(displayName)){
			return true;
		}else if (F6.weaponName.equals(displayName)){
			return true;
		}else if (ParachuteItem.displayName.equals(displayName)){
			return true;
		}else if (ThrowingTNTItem.displayName.equals(displayName)){
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
		this.clearFighterKitItems();
		this.loadPrimaryWeapon();
		this.loadSecondaryWeapon();
		for (Weapon weapon : weapons) {
			if (weapon == null) {
				break;
			}
			this.player.getInventory().addItem(weapon.getWeaponItem());
		}
		if (this instanceof F3) {
			((F3) this).goblinArrow = new Weapon(Material.ARROW, F3.arrowName, "Arrow of infinity");
			this.getPlayer().getInventory().setItem(9, ((F3) this).goblinArrow.getWeaponItem());
		}
		this.addSpecialWeapons();
		this.giveArmor();
		player.closeInventory();
		player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
	}

	private void clearFighterKitItems(){
		for (ItemStack item : player.getInventory()){
			if (item == null || !item.hasItemMeta()){
				continue;
			}
			if(FighterKit.isFighterWeaponOrSpecialItem(item)){
				player.getInventory().remove(item);
			}
		}
		ItemStack helmet = this.player.getEquipment().getHelmet();
		ItemStack chest = this.player.getEquipment().getChestplate();
		ItemStack leggings = this.player.getEquipment().getLeggings();
		ItemStack boots = this.player.getEquipment().getBoots();

		if(helmet != null){
			this.player.getInventory().remove(this.player.getPlayer().getEquipment().getHelmet());
		}
		if(chest != null){
			this.player.getInventory().remove(this.player.getPlayer().getEquipment().getChestplate());
		}
		if(leggings != null){
			this.player.getInventory().remove(this.player.getPlayer().getEquipment().getLeggings());
		}
		if(boots != null){
			this.player.getInventory().remove(this.player.getPlayer().getEquipment().getBoots());
		}
	}

	private void loadPrimaryWeapon() {
		weapons[0] = new Weapon(this.getMaterial(), this.getWeaponName(), this.getMeleeDamage(),
				this.getProjectileDamage(), this.getSpecialDamage(), this.getCooldownTicks(), this.getDurationTicks(),
				this.getRechargeTicks(), this.getPrimaryEnchantment(), perkEnchantment1);
	}

	private void addSpecialWeapons() {
		if (true) {
			this.specialItems[0] = (new ParachuteItem(this.player));
			parachuteItem = (ParachuteItem) this.specialItems[0];
		}
		if (true) {
			this.specialItems[1] = (new ThrowingTNTItem(this.player));
		}
	}

	//Finds the matching special item (if there is one)
	// and does the respective RIGHT CLICK for it
	public void doSpecialItemRightClick(ItemStack item) {
		SpecialItem sItem = this.getSpecialItem(item);
		if(sItem == null) {
			return;
		}
		sItem.doRightClick();
	}

	//Finds the matching special item (if there is one)
	// and does the respective DROP for it
	public boolean doSpecialItemDrop(ItemStack item) {
		SpecialItem sItem = this.getSpecialItem(item);
		if(sItem == null) {
			return false;
		}
		sItem.doDrop();
		return true;
	}

	//Return False - when not primary weapon and when cooldown is on primary weapon
	//Return True  - when it is primary weapon and cooldown successfully set
	public boolean doRightClick(ItemStack item) {
		//Check if it is the primary weapon
		if (!(this.getWeapons()[0].getWeaponItem().isSimilar(item))) {
			this.doSpecialItemRightClick(item);
			return false;
		}
		if (player.getCooldown(this.getMaterial()) > 0) {
			this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
			return false;
		}
		player.setCooldown(this.getMaterial(), this.getCooldownTicks());
		return true;
	}

	//Returns True if there is no cooldown and ability for either
	// the Fighter Kit or special item can be activated
	//
	//Returns False if there is a cooldown or not a Fighter Kits item
	public boolean doDrop(ItemStack item) {
		if (this.getWeapons()[0].getWeaponItem().isSimilar(item) || (this.getWeapons()[1] != null && this.getWeapons()[1].getWeaponItem().isSimilar(item))) {
			//The dropped item matches the material of their Fighter Kit
			//The dropped item matches the weapon name of their Fighter Kit
			//The player who dropped the item has the correct kitID for this FighterKit
			if (this.player.getCooldown(this.cooldownMaterial) > 0
					|| this.player.getCooldown(this.cooldownMaterial) > 0) {
				this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
				return true;
			}
			this.activateSpecial();
			return true;
		}else {
			return this.doSpecialItemDrop(item);
		}
	}

	public Material getSecondaryMaterial() {
		// TODO Auto-generated method stub
		return null;
	}

	void activateSpecial() {
		this.fighter.startAbilityDuration();
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

	public SpecialItem[] getSpecialItems() {
		return specialItems;
	}

	public SpecialItem getSpecialItem(ItemStack item) {
		for(SpecialItem sItem : this.getSpecialItems()) {
			if(sItem.getWeapon().getWeaponItem().isSimilar(item)) {
				return sItem;
			}
		}
		return null;
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

	public ParachuteItem getParachuteItem(){
		return this.parachuteItem;
	}

}

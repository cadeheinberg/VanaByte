package me.cade.vanabyte.Fighters.Weapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Weapon {
  
  private ItemStack weaponItem;
  private Material weaponMaterial;
  private String weaponName;
  private static ChatColor noColor = ChatColor.GRAY;
  public Weapon(Material material, String weaponName, double meleeDamage, double projectileDamage, double specialDamage, int cooldownTicks, int durationTicks, int rechargeTicks) {
      ArrayList<String> itemLore = new ArrayList<String>();
      if(meleeDamage > 0) {
          itemLore.add(ChatColor.WHITE + "" + meleeDamage + ChatColor.YELLOW + " attack damage");
      }else {
          itemLore.add(noColor + "" + meleeDamage + noColor + " attack damage");
      }
      if(projectileDamage > 0) {
          itemLore.add(ChatColor.WHITE + "" + projectileDamage + ChatColor.YELLOW + " projectile damage");
      }else {
          itemLore.add(noColor + "" + projectileDamage + noColor + " projectile damage");
      }
      if(cooldownTicks > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((cooldownTicks/20.0) * 10)/10.0 + "s" + ChatColor.YELLOW + " RC cooldown");
      }else {
          itemLore.add(noColor + "" + Math.round((cooldownTicks/20.0) * 10)/10.0 + "s" + noColor + " RC cooldown");
      }
      if(specialDamage > 0) {
          itemLore.add(ChatColor.WHITE + "" + specialDamage + ChatColor.AQUA + " Q damage");
      }else {
          itemLore.add(noColor + "" + specialDamage + noColor + " Q damage");
      }
      if(durationTicks > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((durationTicks/20.0) * 10)/10.0 + "s" + ChatColor.AQUA + " Q duration");
      }else {
          itemLore.add(noColor + "" + Math.round((durationTicks/20.0) * 10)/10.0 + "s" + noColor + " Q duration");
      }if(rechargeTicks > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((rechargeTicks/20.0) * 10)/10.0 + "s" + ChatColor.AQUA + " Q recharge");
      }else {
          itemLore.add(noColor + "" + Math.round((rechargeTicks/20.0) * 10)/10.0 + "s" + noColor + " Q recharge");
      }
      weaponMaterial = material;
      this.weaponName = weaponName;
      weaponItem = new ItemStack(weaponMaterial, 1);
      ItemMeta meta = weaponItem.getItemMeta();
      meta.setDisplayName(weaponName);
      meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
      meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
      meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
      meta.setLore(itemLore);
      meta.setUnbreakable(true);
      weaponItem.setItemMeta(meta);
      this.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("GENERIC_ATTACK_DAMAGE",
              meleeDamage, AttributeModifier.Operation.ADD_NUMBER));
  }

  public Weapon(Material material, String name, String lore) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
        weaponItem.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    itemLore.add(lore3);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3, String lore4) {
    ArrayList<String> itemLore = new ArrayList<String>();
    itemLore.add(lore);
    itemLore.add(lore2);
    itemLore.add(lore3);
    itemLore.add(lore4);
    weaponMaterial = material;
    weaponName = name;
    weaponItem = new ItemStack(weaponMaterial, 1);
    ItemMeta meta = weaponItem.getItemMeta();
    meta.setDisplayName(weaponName);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    meta.setLore(itemLore);
    meta.setUnbreakable(true);
    weaponItem.setItemMeta(meta);
    if(!material.isBlock()) {
        weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
    }
  }
  
  public Weapon(Material material, String name, String lore, String lore2, String lore3, String lore4, String lore5) {
      ArrayList<String> itemLore = new ArrayList<String>();
      itemLore.add(lore);
      itemLore.add(lore2);
      itemLore.add(lore3);
      itemLore.add(lore4);
      itemLore.add(lore5);
      weaponMaterial = material;
      weaponName = name;
      weaponItem = new ItemStack(weaponMaterial, 1);
      ItemMeta meta = weaponItem.getItemMeta();
      meta.setDisplayName(weaponName);
      meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
      meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
      meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
      meta.setLore(itemLore);
      meta.setUnbreakable(true);
      weaponItem.setItemMeta(meta);
      if (!material.isBlock()) {
          weaponItem.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
      }
  }

  public void applyWeaponEnchantment(Enchantment enchantment, int power) {
    weaponItem.addEnchantment(enchantment, power);
  }
  
  public void applyWeaponUnsafeEnchantment(Enchantment enchantment, int power) {
    weaponItem.addUnsafeEnchantment(enchantment, power);
  }
  
  public void addNewAttribute(Attribute attribute, AttributeModifier modifier) {
    ItemMeta meta = weaponItem.getItemMeta();
    meta.addAttributeModifier(attribute, modifier);
    weaponItem.setItemMeta(meta);
  }
  
  public void giveWeapon(Player player) {
    player.getInventory().addItem(weaponItem);
  }
  
  public void removeWeapon(Player player) {
    player.getInventory().remove(weaponItem);
  }

  public String getWeaponName() {
    return weaponName;
  }

  public ItemStack getWeaponItem() {
    return weaponItem;
  }

    public void resetCooldown(Player player) {
        player.setCooldown(this.weaponMaterial, 0);
    }

}
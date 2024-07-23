package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Weapon {

  private final ItemStack weaponItem;
  private final Material weaponMaterial;
  private final String weaponName;
  private final WeaponType weaponType;
  private static final ChatColor noColor = ChatColor.GRAY;
  private static final NamespacedKey WEAPON_TYPE_KEY = new NamespacedKey(VanaByte.getInstance(), "WeaponType");

  public Weapon(WeaponType weaponType, Material material, String weaponName, double meleeDamage, double main_cooldown, double ability_duration, double ability_recharge, String... extraLore) {
      ArrayList<String> itemLore = new ArrayList<String>();
      if(meleeDamage > 0) {
          itemLore.add(ChatColor.WHITE + "" + meleeDamage + ChatColor.YELLOW + " melee damage");
      }else {
          itemLore.add(noColor + "no melee damage");
      }
      if(main_cooldown > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((main_cooldown/20.0) * 10)/10.0 + "s" + ChatColor.YELLOW + " main cooldown");
      }else {
          itemLore.add(noColor + "no main cooldown");
      }
      if(ability_duration > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((ability_duration/20.0) * 10)/10.0 + "s" + ChatColor.YELLOW + " special duration");
      }else {
          itemLore.add(noColor + "no ability cooldown");
      }
      if(ability_recharge > 0) {
          itemLore.add(ChatColor.WHITE + "" + Math.round((ability_recharge/20.0) * 10)/10.0 + "s" + ChatColor.YELLOW + " special recharge");
      }else {
          itemLore.add(noColor + "no main cooldown");
      }
      for(String extraLine : extraLore){
          itemLore.add(ChatColor.GRAY + "" + extraLine);
      }
      weaponMaterial = material;
      this.weaponType = weaponType;
      this.weaponName = weaponName;
      weaponItem = new ItemStack(weaponMaterial, 1);
      ItemMeta meta = weaponItem.getItemMeta();
      meta.setDisplayName(weaponName);
      meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      //meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
      meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
      meta.setLore(itemLore);
      meta.setMaxStackSize(1);
      meta.setUnbreakable(true);
      PersistentDataContainer container = meta.getPersistentDataContainer();
      container.set(WEAPON_TYPE_KEY, PersistentDataType.STRING, this.weaponType.name());
      weaponItem.setItemMeta(meta);
      this.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("GENERIC_ATTACK_DAMAGE",
              meleeDamage, AttributeModifier.Operation.ADD_NUMBER));
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

  public WeaponType getWeaponType(){
      return this.weaponType;
  }

  public static WeaponType getWeaponTypeFromItemStack(ItemStack itemStack) {
      if(itemStack == null){
          return null;
      }
      ItemMeta meta = itemStack.getItemMeta();
      if(meta == null){
          return null;
      }
      PersistentDataContainer container = meta.getPersistentDataContainer();
      if(container == null){
          return null;
      }
      if (!(container.has(WEAPON_TYPE_KEY, PersistentDataType.STRING))) {
          return null;
      }
      String weaponTypeName = container.get(WEAPON_TYPE_KEY, PersistentDataType.STRING);
      return WeaponType.valueOf(weaponTypeName);
  }

    public static WeaponType getWeaponTypeFromMainHand(Player player) {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();
        if(itemStack == null){
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null){
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container == null){
            return null;
        }
        if (!(container.has(WEAPON_TYPE_KEY, PersistentDataType.STRING))) {
            return null;
        }
        String weaponTypeName = container.get(WEAPON_TYPE_KEY, PersistentDataType.STRING);
        return WeaponType.valueOf(weaponTypeName);
    }

//    public Weapon(WeaponType weaponType, Material material, String weaponName, double meleeDamage, double projectileDamage, double specialDamage, int cooldownTicks, int durationTicks, int rechargeTicks, String... extraLore) {
//        ArrayList<String> itemLore = new ArrayList<String>();
//        if(meleeDamage > 0) {
//            itemLore.add(ChatColor.WHITE + "" + meleeDamage + ChatColor.YELLOW + " melee damage");
//        }else {
//            itemLore.add(noColor + "" + meleeDamage + noColor + " melee damage");
//        }
//        if(projectileDamage > 0) {
//            itemLore.add(ChatColor.WHITE + "" + projectileDamage + ChatColor.YELLOW + " projectile damage");
//        }else {
//            itemLore.add(noColor + "" + projectileDamage + noColor + " projectile damage");
//        }
//        if(cooldownTicks > 0) {
//            itemLore.add(ChatColor.WHITE + "" + Math.round((cooldownTicks/20.0) * 10)/10.0 + "s" + ChatColor.YELLOW + " RC cooldown");
//        }else {
//            itemLore.add(noColor + "" + Math.round((cooldownTicks/20.0) * 10)/10.0 + "s" + noColor + " RC cooldown");
//        }
//        if(specialDamage > 0) {
//            itemLore.add(ChatColor.WHITE + "" + specialDamage + ChatColor.AQUA + " Q damage");
//        }else {
//            itemLore.add(noColor + "" + specialDamage + noColor + " Q damage");
//        }
//        if(durationTicks > 0) {
//            itemLore.add(ChatColor.WHITE + "" + Math.round((durationTicks/20.0) * 10)/10.0 + "s" + ChatColor.AQUA + " Q duration");
//        }else {
//            itemLore.add(noColor + "" + Math.round((durationTicks/20.0) * 10)/10.0 + "s" + noColor + " Q duration");
//        }if(rechargeTicks > 0) {
//            itemLore.add(ChatColor.WHITE + "" + Math.round((rechargeTicks/20.0) * 10)/10.0 + "s" + ChatColor.AQUA + " Q recharge");
//        }else {
//            itemLore.add(noColor + "" + Math.round((rechargeTicks/20.0) * 10)/10.0 + "s" + noColor + " Q recharge");
//        }
//        for(String extraLine : extraLore){
//            itemLore.add(ChatColor.GRAY + "" + extraLine);
//        }
//        weaponMaterial = material;
//        this.weaponType = weaponType;
//        this.weaponName = weaponName;
//        weaponItem = new ItemStack(weaponMaterial, 1);
//        ItemMeta meta = weaponItem.getItemMeta();
//        meta.setDisplayName(weaponName);
//        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
//        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
//        meta.setLore(itemLore);
//        meta.setMaxStackSize(1);
//        meta.setUnbreakable(true);
//        PersistentDataContainer container = meta.getPersistentDataContainer();
//        container.set(WEAPON_TYPE_KEY, PersistentDataType.STRING, this.weaponType.name());
//        weaponItem.setItemMeta(meta);
//        this.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("GENERIC_ATTACK_DAMAGE",
//                meleeDamage, AttributeModifier.Operation.ADD_NUMBER));
//    }

}

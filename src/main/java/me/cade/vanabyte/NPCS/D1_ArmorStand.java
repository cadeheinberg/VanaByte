package me.cade.vanabyte.NPCS;

import me.cade.vanabyte.VanaByte;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class D1_ArmorStand {

  private String name;
  private Location location;
  private ArmorStand stand;
  
  public D1_ArmorStand(String name, Location location, float yaw, boolean visible, boolean marker, boolean gravity) {
    this.name = name;
    this.location = location;
    this.location.setYaw(yaw);
    stand = (ArmorStand) VanaByte.hub.spawnEntity(location, EntityType.ARMOR_STAND);
    stand.setArms(true);
    stand.setAI(false);
    stand.setVisible(visible);
    stand.setMarker(marker);
    stand.setGravity(gravity);
    stand.setCustomName(name);
    stand.setCustomNameVisible(true);
    stand.setRemoveWhenFarAway(false);
  }
  
  public String getName() {
    return this.name;
  }
  
  public ArmorStand getStand() {
    return stand;
  }
  
  public void equipColoredArmor(Color armorColor) {
    ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET, 1);
    LeatherArmorMeta lhe = (LeatherArmorMeta) lhelmet.getItemMeta();
    lhe.setColor(armorColor);
    lhelmet.setItemMeta(lhe);

    ItemStack lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
    LeatherArmorMeta lch = (LeatherArmorMeta) lchest.getItemMeta();
    lch.setColor(armorColor);
    lchest.setItemMeta(lch);

    ItemStack lleggs = new ItemStack(Material.LEATHER_LEGGINGS, 1);
    LeatherArmorMeta lle = (LeatherArmorMeta) lleggs.getItemMeta();
    lle.setColor(armorColor);
    lleggs.setItemMeta(lle);

    ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
    LeatherArmorMeta lbo = (LeatherArmorMeta) lboots.getItemMeta();
    lbo.setColor(armorColor);
    lboots.setItemMeta(lbo);

    stand.getEquipment().setHelmet(lhelmet);
    stand.getEquipment().setChestplate(lchest);
    stand.getEquipment().setLeggings(lleggs);
    stand.getEquipment().setBoots(lboots);
  }
  
  public void equipChainArmor() {
    ItemStack lhelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
    ItemStack lchest = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
    ItemStack lleggs = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
    ItemStack lboots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
    stand.getEquipment().setHelmet(lhelmet);
    stand.getEquipment().setChestplate(lchest);
    stand.getEquipment().setLeggings(lleggs);
    stand.getEquipment().setBoots(lboots);
  }
  
  public void equipGoldArmor() {
    ItemStack lhelmet = new ItemStack(Material.GOLDEN_HELMET, 1);
    ItemStack lchest = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
    ItemStack lleggs = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
    ItemStack lboots = new ItemStack(Material.GOLDEN_BOOTS, 1);
    stand.getEquipment().setHelmet(lhelmet);
    stand.getEquipment().setChestplate(lchest);
    stand.getEquipment().setLeggings(lleggs);
    stand.getEquipment().setBoots(lboots);
  }
  
  public void equipIronArmor() {
    ItemStack lhelmet = new ItemStack(Material.IRON_HELMET, 1);
    ItemStack lchest = new ItemStack(Material.IRON_CHESTPLATE, 1);
    ItemStack lleggs = new ItemStack(Material.IRON_LEGGINGS, 1);
    ItemStack lboots = new ItemStack(Material.IRON_BOOTS, 1);
    stand.getEquipment().setHelmet(lhelmet);
    stand.getEquipment().setChestplate(lchest);
    stand.getEquipment().setLeggings(lleggs);
    stand.getEquipment().setBoots(lboots);
  }
  
  public void equipDiamondArmor() {
    ItemStack lhelmet = new ItemStack(Material.DIAMOND_HELMET, 1);
    ItemStack lchest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
    ItemStack lleggs = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
    ItemStack lboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
    stand.getEquipment().setHelmet(lhelmet);
    stand.getEquipment().setChestplate(lchest);
    stand.getEquipment().setLeggings(lleggs);
    stand.getEquipment().setBoots(lboots);
  }
  
}

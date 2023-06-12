package me.cade.vanabyte;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class AbilityEnchantment {

  public static void makeEnchanted(Player player) {

    ItemStack helmet = player.getEquipment().getHelmet();
    ItemStack chest = player.getEquipment().getChestplate();
    ItemStack leggings = player.getEquipment().getLeggings();
    ItemStack boots = player.getEquipment().getBoots();

    if(helmet != null){
      helmet.addEnchantment(Enchantment.DURABILITY, 1);
    }
    if(chest != null){
      chest.addEnchantment(Enchantment.DURABILITY, 1);
    }
    if(leggings != null){
      leggings.addEnchantment(Enchantment.DURABILITY, 1);
    }
    if(boots != null){
      boots.addEnchantment(Enchantment.DURABILITY, 1);
    }

    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if(helmet != null){
        p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
      }
      if(chest != null){
        p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
      }
      if(leggings != null){
        p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
      }
      if(boots != null){
        p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
      }
    }
  }
  
  public static void removeEnchanted(Player player) {

    ItemStack helmet = player.getEquipment().getHelmet();
    ItemStack chest = player.getEquipment().getChestplate();
    ItemStack leggings = player.getEquipment().getLeggings();
    ItemStack boots = player.getEquipment().getBoots();

    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if(helmet != null){
        p.sendEquipmentChange(player, EquipmentSlot.HEAD, helmet);
      }
      if(chest != null){
        p.sendEquipmentChange(player, EquipmentSlot.CHEST, chest);
      }
      if(leggings != null){
        p.sendEquipmentChange(player, EquipmentSlot.LEGS, leggings);
      }
      if(boots != null){
        p.sendEquipmentChange(player, EquipmentSlot.FEET,  boots);
      }
    }
  }

  //    Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
//
//    ItemStack helmet = p.getEquipment().getHelmet();
//    helmet.removeEnchantment(Enchantment.DURABILITY);
//
//    ItemStack chest = p.getEquipment().getChestplate();
//    chest.removeEnchantment(Enchantment.DURABILITY);
//
//    ItemStack legs = p.getEquipment().getLeggings();
//    legs.removeEnchantment(Enchantment.DURABILITY);
//
//    ItemStack boots = p.getEquipment().getBoots();
//    boots.removeEnchantment(Enchantment.DURABILITY);
//
//    // Adding an ice block to the head
//    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
//      CraftItemStack.asNMSCopy(helmet)));
//    equipmentList.add(new Pair<>(EnumItemSlot.CHEST,
//      CraftItemStack.asNMSCopy(chest)));
//    equipmentList.add(new Pair<>(EnumItemSlot.LEGS,
//      CraftItemStack.asNMSCopy(legs)));
//    equipmentList.add(new Pair<>(EnumItemSlot.FEET,
//      CraftItemStack.asNMSCopy(boots)));
//
//    // Creating the packet
//    PacketPlayOutEntityEquipment packet =
//      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);
//
//    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
//      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
//      conn.sendPacket(packet);
//    }

}

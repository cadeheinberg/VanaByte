package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.Enums.ArmorType;
import me.cade.vanabyte.Fighters.Enums.KitType;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolders.*;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;

public class FighterKitManager {

    private KitType kitType;
    private Player player = null;
    private Fighter fighter = null;
    protected ArrayList<WeaponHolder> weaponHolders = new ArrayList<WeaponHolder>();
    public static Material cooldownMaterial = Material.BARRIER;
    private static final NamespacedKey ARMOR_TYPE_KEY = new NamespacedKey(VanaByte.getInstance(), "ArmorType");

    //upgrades
    private boolean explosionImmune = false;

    protected FighterKitManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
        kitType = null;
    }

    protected void fighterJoined(){
        //remove all potions
        this.applyNightVision();
        //player.sendMessage("FighterKitManager, kit found: " + fighter.getFighterMYSQLManager().getFighterTable().getFighterColumns().get(2).getValueString());
        this.setKitType(KitType.getKitTypeFromKitID(fighter.getFighterMYSQLManager().getFighterTable().getFighterColumns().get(2).getValueString()));
        this.giveKit();
    }

    protected void fighterDied(){
        this.fighterDismountParachute();
    }

    protected void fighterLeftServer(){
        this.fighterDismountParachute();
        fighter.getFighterMYSQLManager().getFighterTable().getFighterColumns().get(2).setValueString(this.kitType.getKitID());
    }

    protected void fighterChangedWorld(){
        this.fighterDismountParachute();
        this.resetAllFighterItemCooldowns();
        if(SafeZone.inHub(player.getWorld())){
            this.applyNightVision();
        }
    }

    protected void fighterRespawned(){
        this.giveKit();
        //remove all potions
        this.applyNightVision();
    }

    public void setKitType(KitType kitType){
        this.kitType = kitType;
    }

    public void giveKit() {
        if(kitType == null){
            player.sendMessage(ChatColor.RED + "Error retrieving your kit, contact a mod");
            return;
        }
        this.clearFighterKitItems();
        if(weaponHolders != null){
            weaponHolders.clear();
        }
//        VanaByte.sendConsoleError("fighter", fighter.toString());
//        WeaponHolder weaponHolder = new W0_AirbenderSword(fighter);
//        this.weaponHolders.add(weaponHolder);
//        this.player.getInventory().addItem(weaponHolder.getWeapon().getWeaponItem());
        for(WeaponType weaponType : kitType.getWeaponTypes()){
            if(weaponType.getWeaponClass() != null){
                try {
                    WeaponHolder weaponHolder = weaponType.getWeaponClass().getDeclaredConstructor(Fighter.class).newInstance(fighter);
                    this.player.getInventory().addItem(weaponHolder.getWeapon().getWeaponItem());
                    this.weaponHolders.add(weaponHolder);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        this.giveArmor();
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
        this.resetAllFighterItemCooldowns();
        this.applyNightVision();
        fighter.fighterGotNewKit();
    }

    public WeaponHolder getWeaponHolderWithType(WeaponType weaponType) {
        for (WeaponHolder weaponHolder : this.weaponHolders) {
            if (weaponHolder == null || weaponHolder.getWeapon() == null || weaponHolder.getWeapon().getWeaponItem() == null) {
                continue;
            }
            if (weaponHolder.getWeapon().getWeaponType() == weaponType) {
                return weaponHolder;
            }
        }
        return null;
    }

    public void resetAllFighterItemCooldowns() {
        for(WeaponHolder weaponHolder : this.weaponHolders) {
            if(weaponHolder == null){
                break;
            }if(weaponHolder.getWeapon() == null){
                break;
            }
            weaponHolder.getWeapon().resetCooldown(this.player);
        }
    }

    public void fighterDismountParachute() {
        WeaponHolder weaponHolder = this.getWeaponHolderWithType(WeaponType.PARACHUTE);
        if(weaponHolder == null){
            return;
        }
        ((Parachute) weaponHolder).getOffChicken();
    }

    public void setExplosionImmune(boolean explosionImmune) {
        this.explosionImmune = explosionImmune;
    }
    public boolean isExplosionImmune() {
        return explosionImmune;
    }
    public ArrayList<WeaponHolder> getWeaponHolders() {
        return weaponHolders;
    }
    public Fighter getFighter() {return fighter;}

    public static ArmorType getArmorTypeFromItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null){
            return null;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container == null){
            return null;
        }
        if (!(container.has(ARMOR_TYPE_KEY, PersistentDataType.STRING))) {
            return null;
        }
        String armorTypeName = container.get(ARMOR_TYPE_KEY, PersistentDataType.STRING);
        return ArmorType.valueOf(armorTypeName);
    }

    private void clearFighterKitItems(){
        for (ItemStack item : player.getInventory()){
            if (item == null){
                continue;
            }
            if(Weapon.getWeaponTypeFromItemStack(item) != null){
                player.getInventory().remove(item);
            }
        }
        ItemStack helmet = this.player.getEquipment().getHelmet();
        ItemStack chest = this.player.getEquipment().getChestplate();
        ItemStack leggings = this.player.getEquipment().getLeggings();
        ItemStack boots = this.player.getEquipment().getBoots();

        if(helmet != null){
            if(getArmorTypeFromItemStack(helmet) == null){
                this.moveItemStackToOpenSpotOrDrop(helmet);
            }else{
                this.player.getInventory().remove(helmet);
            }
        }
        if(chest != null){
            if(getArmorTypeFromItemStack(chest) == null){
                this.moveItemStackToOpenSpotOrDrop(chest);
            }else{
                this.player.getInventory().remove(chest);
            }
        }
        if(leggings != null){
            if(getArmorTypeFromItemStack(leggings) == null){
                this.moveItemStackToOpenSpotOrDrop(leggings);
            }else{
                this.player.getInventory().remove(leggings);
            }
        }
        if(boots != null){
            if(getArmorTypeFromItemStack(boots) == null){
                this.moveItemStackToOpenSpotOrDrop(boots);
            }else{
                this.player.getInventory().remove(boots);
            }
        }
    }

    private void moveItemStackToOpenSpotOrDrop(ItemStack item){
        if(player.getInventory().firstEmpty() == -1){
            player.playSound(player.getLocation(), Sound.ITEM_BUNDLE_DROP_CONTENTS, 8, 1);
            player.getWorld().dropItem(player.getLocation(), item);
        }else{
            player.getInventory().setItem(player.getInventory().firstEmpty(), item);
        }
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

        lhe.setColor(kitType.getArmorColor());
        lch.setColor(kitType.getArmorColor());
        lle.setColor(kitType.getArmorColor());
        lbo.setColor(kitType.getArmorColor());

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
        itemLore.add(5 + " armor protection");
        lhe.setLore(itemLore);
        lch.setLore(itemLore);
        lle.setLore(itemLore);
        lbo.setLore(itemLore);

        lhe.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lch.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lle.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lbo.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        PersistentDataContainer lheContainer = lhe.getPersistentDataContainer();
        lheContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.name());
        PersistentDataContainer lchContainer = lch.getPersistentDataContainer();
        lchContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.name());
        PersistentDataContainer lleContainer = lle.getPersistentDataContainer();
        lleContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.name());
        PersistentDataContainer lboContainer = lbo.getPersistentDataContainer();
        lboContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.name());

        lhelmet.setItemMeta(lhe);
        lchest.setItemMeta(lch);
        lleggs.setItemMeta(lle);
        lboots.setItemMeta(lbo);

        player.getEquipment().setHelmet(lhelmet);
        player.getEquipment().setChestplate(lchest);
        player.getEquipment().setLeggings(lleggs);
        player.getEquipment().setBoots(lboots);

    }

    public void applyNightVision() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(fighter.getPlugin(), new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false));
            }
        }, 1);
    }


}

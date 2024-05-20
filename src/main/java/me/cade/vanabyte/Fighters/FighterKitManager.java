package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.*;
import me.cade.vanabyte.Fighters.FighterKits.*;
import me.cade.vanabyte.Permissions.SafeZone;
import me.cade.vanabyte.VanaByte;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
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

    private Player player = null;
    private Fighter fighter = null;
    private static FighterKit[] fKits = { new F0(), new F1(), new F2(), new F3(), new F4(), new F5(), new F6() };

    private static WeaponHolder[] wHolders = { new W0_AirbenderSword(), new W1_BeserkerAxe(), new W2_ShottyShotgun(), new W3_GoblinBow(), new W3_GoblinSword(), new W3_GoblinArrow(), new W4_IgorsTrident(), new W5_SumoStick(), new W6_GriefSword(), new S0_Parachute(), new S1_ThrowingTNT() };
    private int[] unlockedKits = new int[7];
    private int[] kitUpgrades = new int[42];
    private FighterKit fKit = null;
    private int kitID,kitIndex = -1;

    public static Material cooldownMaterial = Material.BARRIER;
    private static final NamespacedKey ARMOR_TYPE_KEY = new NamespacedKey(VanaByte.getInstance(), "ArmorType");

    protected FighterKitManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }
    protected void fighterJoined(){
        this.setNegatives();
        //remove all potions
        this.applyNightVision();
    }

    protected void fighterDied(){
        this.fighterDismountParachute();
    }

    protected void fighterLeftServer(){
        this.fighterDismountParachute();
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

    public void applyNightVision() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(fighter.getPlugin(), new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 0, true, false));
            }
        }, 1);
    }

    protected void setNegatives(){
        for (int i = 0; i < unlockedKits.length; i++){
            unlockedKits[i] = -1;
        }
        for (int i = 0; i < kitUpgrades.length; i++){
            kitUpgrades[i] = -1;
        }
    }
    public void giveKit() {
        this.clearFighterKitItems();
        if(fKit != null){
            fKit.getWeaponHolders().clear();
        }
        if (kitID == fKits[0].getKitID()) {
            fKit = new F0(fighter);
        } else if (kitID == fKits[1].getKitID()) {
            fKit = new F1(fighter);
        } else if (kitID == fKits[2].getKitID()) {
            fKit = new F2(fighter);
        } else if (kitID == fKits[3].getKitID()) {
            fKit = new F3(fighter);
        } else if (kitID == fKits[4].getKitID()) {
            fKit = new F4(fighter);
        } else if (kitID == fKits[5].getKitID()) {
            fKit = new F5(fighter);
        } else if (kitID == fKits[6].getKitID()) {
            fKit = new F6(fighter);
        }
        for (WeaponHolder weaponHolder : fKit.getWeaponHolders()) {
            if (weaponHolder == null) {
                continue;
            }if (weaponHolder.getWeapon() == null) {
                continue;
            }
            this.player.getInventory().addItem(weaponHolder.getWeapon().getWeaponItem());
        }
        this.giveArmor();
        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
        this.resetAllFighterItemCooldowns();
        this.applyNightVision();
        fighter.fighterAbilityManager.fighterGotNewKit();
    }

    private void clearFighterKitItems(){
        for (ItemStack item : player.getInventory()){
            if (item == null){
                continue;
            }
            if(this.hasNameOfWeapon(item)){
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

    public static boolean hasNameOfWeapon(ItemStack item){
        if(item == null || !item.hasItemMeta() || item.getItemMeta().getDisplayName() == null){
            return false;
        }
        String displayName = item.getItemMeta().getDisplayName();
        for (WeaponHolder weaponHolder : wHolders){
            if(weaponHolder == null || weaponHolder.getWeaponName() == null){
                continue;
            }
            if(weaponHolder.getWeaponName().equals(displayName)){
                return true;
            }
        }
        return false;
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

        lhe.setColor(fKit.getArmorColor());
        lch.setColor(fKit.getArmorColor());
        lle.setColor(fKit.getArmorColor());
        lbo.setColor(fKit.getArmorColor());

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
        lheContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.getName());
        PersistentDataContainer lchContainer = lhe.getPersistentDataContainer();
        lchContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.getName());
        PersistentDataContainer lleContainer = lhe.getPersistentDataContainer();
        lleContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.getName());
        PersistentDataContainer lboContainer = lhe.getPersistentDataContainer();
        lboContainer.set(ARMOR_TYPE_KEY, PersistentDataType.STRING, ArmorType.SPECIAL_ARMOR.getName());

        lhelmet.setItemMeta(lhe);
        lchest.setItemMeta(lch);
        lleggs.setItemMeta(lle);
        lboots.setItemMeta(lbo);

        player.getEquipment().setHelmet(lhelmet);
        player.getEquipment().setChestplate(lchest);
        player.getEquipment().setLeggings(lleggs);
        player.getEquipment().setBoots(lboots);

    }

    public void giveKitWithID(int kitID) {
        this.setKitID(kitID);
        this.giveKit();
    }

    public int[] getUnlockedKits(){
        return unlockedKits;
    }

    public int[] getKitUpgrades(){
        return kitUpgrades;
    }

    public int getKitID(){
        return kitID;
    }

    public void setKitID(int setter){
        this.kitID = setter;
    }

    public void resetAllFighterItemCooldowns() {
        for(WeaponHolder weaponHolder : fKit.getWeaponHolders()) {
            if(weaponHolder == null){
                break;
            }if(weaponHolder.getWeapon() == null){
                break;
            }
            weaponHolder.getWeapon().resetCooldown(this.player);
        }
    }

    public static FighterKit[] getFkitsNoPlayer(){
        return fKits;
    }

    public void setKitUpgradesRaw(int index, int value) {
        this.kitUpgrades[index] = value;
    }
    public void setKitUpgradesUsingIDAndOffset(int kitID, int offset, int value) {
        this.kitUpgrades[(6 * kitID) + offset] = value;
    }

    public int getKitUpgradesRaw(int kitID) {
        return kitUpgrades[kitID];
    }

    public int getKitUpgradesUsingIDAndOffset(int kitID, int offset) {
        return this.kitUpgrades[(6 * kitID) + offset];
    }

    public int getUnlockedKit(int kitID) {
        return unlockedKits[kitID];
    }

    public void setUnlockedKit(int kitID, int index) {
        this.unlockedKits[kitID] = index;
    }

    public double getKitUpgradesConvertedDamage(int kitID, int offset){
        if(this.getKitUpgradesRaw((kitID * 6 )+ offset) <= 0){
            return 0;
        }
        return this.getKitUpgradesRaw((kitID * 6 )+ offset)/10.0;
    }

    public int getKitUpgradesConvertedTicks(int kitID, int offset){
        if(this.getKitUpgradesRaw((kitID * 6 )+ offset) <= 0){
            return 0;
        }
        return this.getKitUpgradesRaw((kitID * 6 )+ offset);
    }

    public void fighterDismountParachute() {
        if(fKit.getSpecificWeaponHolderIfItExists(S0_Parachute.class) != null){
            ((S0_Parachute) fKit.getSpecificWeaponHolderIfItExists(S0_Parachute.class)).getOff();
        }
    }

    public FighterKit getFKit(){
        return fKit;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public static ArmorType getArmorType(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null){
            return ArmorType.UNKOWN_ARMOR;
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container == null){
            return ArmorType.UNKOWN_ARMOR;
        }
        if (!(container.has(ARMOR_TYPE_KEY, PersistentDataType.STRING))) {
            return ArmorType.UNKOWN_ARMOR;
        }
        String armorTypeName = container.get(ARMOR_TYPE_KEY, PersistentDataType.STRING);
        return ArmorType.valueOf(armorTypeName);
    }

}

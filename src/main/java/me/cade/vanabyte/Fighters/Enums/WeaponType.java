package me.cade.vanabyte.Fighters.Enums;

import me.cade.vanabyte.Fighters.WeaponHolders.WeaponHolder;
import me.cade.vanabyte.Fighters.WeaponHolders.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public enum WeaponType {
    AIRBENDER_SWORD("Airbender Sword",
            W0_AirbenderSword.class,
            ChatColor.WHITE,
            "Gust of Wind",
            "Use Shield",
            Material.IRON_SWORD,
            null,
            null,
            200,
            50,
            0,
            15,
            6,
            0),
    BESERKER_AXE("Beserker Axe",
            W1_BeserkerAxe.class,
            ChatColor.LIGHT_PURPLE,
            "Haste, Speed, Jump",
            "Beserker Leap",
            Material.IRON_AXE,
            null,
            null,
            200,
            50,
            80,
            7,
            6,
            0),
    SHOTTY_SHOTGUN("Shotty Shotgun",
            W2_ShottyShotgun.class,
            ChatColor.YELLOW,
            "Flaming Bullets",
            "Shoot Shotgun",
            Material.IRON_SHOVEL,
            null,
            null,
            200,
            50,
            20,
            15,
            6,
            15),
    GOBLIN_BOW("Goblin Bow",
            W3_GoblinArrow.class,
            ChatColor.GREEN,
            "Flaming Arrow Barrage",
            "Shoot Bow",
            Material.BOW,
            new Enchantment[]{Enchantment.INFINITY},
            new Integer[]{1},
            200,
            50,
            0,
            7,
            6,
            0),
    GOBLIN_SWORD("Goblin Sword",
            W3_GoblinBow.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.WOODEN_SWORD,
            new Enchantment[]{Enchantment.FIRE_ASPECT},
            new Integer[]{1},
            0,
            0,
            0,
            0,
            6,
            0),
    GOBLIN_ARROW("Goblin Arrow",
            W3_GoblinSword.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.ARROW,
            null,
            null,
            0,
            0,
            0,
            0,
            100,
            0),
    IGORS_TRIDENT("Igors Trident",
            W4_IgorsTrident.class,
            ChatColor.RED,
            "Explosive Tridents",
            "Throw Trident",
            Material.TRIDENT,
            null,
            null,
            200,
            50,
            5,
            10,
            6,
            10),
    SUMO_STICK("Sumo Stick",
            W5_SumoStick.class,
            ChatColor.LIGHT_PURPLE,
            "Ground Pound",
            "PickUp/Toss Enemy",
            Material.STICK,
            new Enchantment[]{Enchantment.KNOCKBACK},
            new Integer[]{2},
            200,
            50,
            50,
            15,
            6,
            0),
    GRIEF_SWORD("Grief Sword",
            W6_GriefSword.class,
            ChatColor.AQUA,
            "Invis & Health Steal",
            "None",
            Material.NETHERITE_SWORD,
            null,
            null,
            200,
            50,
            0,
            8,
            8,
            0),
    PARACHUTE("Parachute",
            S0_Parachute.class,
            ChatColor.YELLOW,
            "Open Parachute",
            "Open Parachute",
            Material.PHANTOM_MEMBRANE,
            null,
            null,
            200,
            50,
            5,
            12,
            5,
            10),
    THROWING_TNT("Throwing TNT",
            S1_ThrowingTNT.class,
            ChatColor.YELLOW,
            "Throw TNT",
            "Throw TNT",
            Material.COAL,
            null,
            null,
            200,
            50,
            50,
            10,
            3,
            0),
    UNKNOWN_WEAPON(null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1);

    private final String name;
    private final ChatColor textColor;
    private final String weaponDrop;
    private final String weaponRightClick;
    private final Material material;
    private final int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks;
    private final double specialDamage, meleeDamage, projectileDamage;
    private final Class<? extends WeaponHolder> weaponClass;
    private final Enchantment[] enchantments;
    private final Integer[] enchantmentPowers;

    private WeaponType(String name, Class<? extends WeaponHolder> weaponClass, ChatColor textColor, String weaponDrop, String weaponRightClick, Material material, Enchantment[] enchantments, Integer[] enchantmentPowers, int abilityDurationTicks, int abilityRechargeTicks, int rightClickCooldownTicks, double specialDamage, double meleeDamage, double projectileDamage){
        this.name = name;
        this.weaponClass = weaponClass;
        this.textColor = textColor;
        this.weaponDrop = weaponDrop;
        this.weaponRightClick = weaponRightClick;
        this.material = material;
        this.enchantments = enchantments;
        this.abilityDurationTicks = abilityDurationTicks;
        this.abilityRechargeTicks = abilityRechargeTicks;
        this.rightClickCooldownTicks = rightClickCooldownTicks;
        this.specialDamage = specialDamage;
        this.meleeDamage = meleeDamage;
        this.projectileDamage = projectileDamage;
        this.enchantmentPowers = enchantmentPowers;
    }

    public String getWeaponNameUncolored(){
        return this.name;
    }

    public String getWeaponNameColored(){
        return this.textColor + this.name;
    }

    public int getAbilityDurationTicks() {
        return abilityDurationTicks;
    }

    public String getWeaponDrop() {
        return weaponDrop;
    }

    public ChatColor getTextColor() {
        return textColor;
    }

    public String getWeaponRightClick() {
        return weaponRightClick;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAbilityRechargeTicks() {
        return abilityRechargeTicks;
    }

    public int getRightClickCooldownTicks() {
        return rightClickCooldownTicks;
    }

    public double getSpecialDamage() {
        return specialDamage;
    }

    public double getMeleeDamage() {
        return meleeDamage;
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    public Class<? extends WeaponHolder> getWeaponClass(){
        return weaponClass;
    }

    public Enchantment[] getEnchantments(){
        return enchantments;
    }

    public Integer[] getEnchantmentPowers(){
        return enchantmentPowers;
    }

}

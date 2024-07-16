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
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    BESERKER_AXE("Beserker Axe",
            W1_BeserkerAxe.class,
            ChatColor.LIGHT_PURPLE,
            "Haste, Speed, Jump",
            "Beserker Leap",
            Material.IRON_AXE,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 1.6, 0,
                    0, 0, 1.8, 0,
                    0, 0,
                    false, false, false, false)),
    SHOTTY_SHOTGUN("Shotty Shotgun",
            W2_ShottyShotgun.class,
            ChatColor.YELLOW,
            "Flaming Bullets",
            "Shoot Shotgun",
            Material.IRON_SHOVEL,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, -0.42, 0,
                    0, 0, -0.60, 0,
                    0, 0,
                    false, false, false, false)),
    GOBLIN_BOW("Goblin Bow",
            W3_GoblinArrow.class,
            ChatColor.GREEN,
            "Flaming Arrow Barrage",
            "Shoot Bow",
            Material.BOW,
            new Enchantment[]{Enchantment.INFINITY},
            new Integer[]{1},
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    GOBLIN_SWORD("Goblin Sword",
            W3_GoblinBow.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.WOODEN_SWORD,
            new Enchantment[]{Enchantment.FIRE_ASPECT},
            new Integer[]{1},
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    GOBLIN_ARROW("Goblin Arrow",
            W3_GoblinSword.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.ARROW,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    IGORS_TRIDENT("Igors Trident",
            W4_IgorsTrident.class,
            ChatColor.RED,
            "Explosive Tridents",
            "Throw Trident",
            Material.TRIDENT,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    SUMO_STICK("Sumo Stick",
            W5_SumoStick.class,
            ChatColor.LIGHT_PURPLE,
            "Ground Pound",
            "PickUp/Toss Enemy",
            Material.STICK,
            new Enchantment[]{Enchantment.KNOCKBACK},
            new Integer[]{2},
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    GRIEF_SWORD("Grief Sword",
            W6_GriefSword.class,
            ChatColor.AQUA,
            "Invis & Health Steal",
            "None",
            Material.NETHERITE_SWORD,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    PARACHUTE("Parachute",
            S0_Parachute.class,
            ChatColor.YELLOW,
            "Open Parachute",
            "Open Parachute",
            Material.PHANTOM_MEMBRANE,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0.6, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    THROWING_TNT("Throwing TNT",
            S1_ThrowingTNT.class,
            ChatColor.YELLOW,
            "Throw TNT",
            "Throw TNT",
            Material.COAL,
            null,
            null,
            new StatBundle(0, 0 ,0, 0,
                    0, 0, 0, 0,
                    0,0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,
                    false, false, false, false)),
    UNKNOWN_WEAPON(null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null);

    private final String name;
    private final ChatColor textColor;
    private final String weaponDrop;
    private final String weaponRightClick;
    private final Material material;
    private final Class<? extends WeaponHolder> weaponClass;
    private final Enchantment[] enchantments;
    private final Integer[] enchantmentPowers;
    private final StatBundle statBundle;

    private WeaponType(String name, Class<? extends WeaponHolder> weaponClass, ChatColor textColor, String weaponDrop, String weaponRightClick, Material material, Enchantment[] enchantments, Integer[] enchantmentPowers, StatBundle statBundle){
        this.name = name;
        this.weaponClass = weaponClass;
        this.textColor = textColor;
        this.weaponDrop = weaponDrop;
        this.weaponRightClick = weaponRightClick;
        this.material = material;
        this.enchantments = enchantments;
        this.enchantmentPowers = enchantmentPowers;
        this.statBundle = statBundle;
    }

    public String getWeaponNameUncolored(){
        return this.name;
    }

    public String getWeaponNameColored(){
        return this.textColor + this.name;
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

    public Class<? extends WeaponHolder> getWeaponClass(){
        return weaponClass;
    }

    public Enchantment[] getEnchantments(){
        return enchantments;
    }

    public Integer[] getEnchantmentPowers(){
        return enchantmentPowers;
    }

    public StatBundle getStatBundle() {
        return statBundle;
    }
}

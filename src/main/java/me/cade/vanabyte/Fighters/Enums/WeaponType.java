package me.cade.vanabyte.Fighters.Enums;

import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolders.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

//defaults for new player go here, store players version of this in MySQL table
public enum WeaponType {
    AIRBENDER_SWORD("Airbender Sword",
            "W000",
            AirbenderSword.class,
            ChatColor.WHITE,
            "Gust of Wind",
            "Use Shield",
            Material.IRON_SWORD,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.5, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("sword_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("gust_enemy_power", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("gust_enemy_damage", 0, new Double[]{6.0, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("gust_self_power", 0, new Double[]{1.6, 8.0, -1.0, -1.0, -1.0}),
            })),
    BESERKER_AXE("Beserker Axe",
            "W001",
            BeserkerAxe.class,
            ChatColor.LIGHT_PURPLE,
            "Haste, Speed, Jump",
            "Beserker Leap",
            Material.IRON_AXE,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("axe_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("leap_cooldown", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_leap_cooldown", 0, new Double[]{1.7, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("leap_power", 0, new Double[]{1.8, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_leap_power", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_speed_level", 0, new Double[]{1.0, 2.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_jump_level", 0, new Double[]{1.0, 2.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_haste_level", 0, new Double[]{1.0, 2.0, -1.0, -1.0, -1.0}),
            })),
    SHOTTY_SHOTGUN("Shotty Shotgun",
            "W002",
            ShottyShotgun.class,
            ChatColor.YELLOW,
            "Flaming Bullets",
            "Shoot Shotgun",
            Material.IRON_SHOVEL,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("shovel_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("shoot_cooldown", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_shoot_cooldown", 0, new Double[]{1.7, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("recoil_power", 0, new Double[]{1.2, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_recoil_power", 0, new Double[]{1.4, 7.0, -1.0, -1.0, -1.0}),
                    new StatRow("bullet_damage", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_bullet_damage", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
                    new StatRow("num_bullets", 0, new Double[]{5.0, 2.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_num_bullets", 0, new Double[]{20.0, 2.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_set_blocks_on_fire", 0, new Double[]{1.0, -1.0, -1.0, -1.0, -1.0}),
            })),
    GOBLIN_BOW("Goblin Bow",
            "W003",
            GoblinArrow.class,
            ChatColor.GREEN,
            "Flaming Arrow Barrage",
            "Shoot Bow",
            Material.BOW,
            new Enchantment[]{Enchantment.INFINITY},
            new Integer[]{1},
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("bow_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("arrow_damage", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_arrow_damage", 0, new Double[]{1.7, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_num_arrows_barrage", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_arrow_poison", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
            })),
    GOBLIN_SWORD("Goblin Sword",
            "W004",
            GoblinBow.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.WOODEN_SWORD,
            new Enchantment[]{Enchantment.FIRE_ASPECT},
            new Integer[]{1},
            new StatTable(new StatRow[]{
                    new StatRow("sword_melee_damage", 0, new Double[]{7.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("flame_aspect", 0, new Double[]{1.0, 2.0, -1.0, -1.0, -1.0}),
                    new StatRow("knockback", 0, new Double[]{1.0, 2.0, -1.0, -1.0, -1.0}),
            })),
    GOBLIN_ARROW("Goblin Arrow",
            "W005",
            GoblinSword.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.ARROW,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("melee_damage", 0, new Double[]{100.0, 14.0, -1.0, -1.0, -1.0}),
            })),
    IGORS_TRIDENT("Igors Trident",
            "W006",
            IgorsTrident.class,
            ChatColor.RED,
            "Explosive Tridents",
            "Throw Trident",
            Material.TRIDENT,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("trident_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("trident_projectile_damage", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_trident_projectile_damage", 0, new Double[]{1.7, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("trident_explosion_damage", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("trident_explosion_power", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
            })),
    SUMO_STICK("Sumo Stick",
            "W007",
            SumoStick.class,
            ChatColor.BLUE,
            "Ground Pound",
            "PickUp/Toss Enemy",
            Material.STICK,
            new Enchantment[]{Enchantment.KNOCKBACK},
            new Integer[]{2},
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("stick_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("stick_knockback_power", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
                    new StatRow("pickup_cooldown", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_pickup_cooldown", 0, new Double[]{1.7, 1.8, -1.0, -1.0, -1.0}),
                    new StatRow("throw_power", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_throw_power", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
                    new StatRow("sumo_slam_explosion_damage", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("sumo_slam_explosion_power", 0, new Double[]{9.0, 8.0, -1.0, -1.0, -1.0}),
                    new StatRow("sumo_jump_power", 0, new Double[]{8.0, 7.0, -1.0, -1.0, -1.0}),
            })),
    GRIEF_SWORD("Grief Sword",
            "W008",
            GriefSword.class,
            ChatColor.AQUA,
            "Invis & Health Steal",
            "None",
            Material.NETHERITE_SWORD,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("a_dur", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("a_rech", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("sword_melee", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_melee_health_steal", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
                    new StatRow("ability_on_explosive_immune", 0, new Double[]{1.0, -1.0, -1.0, -1.0, -1.0}),
            })),
    PARACHUTE("Parachute",
            "W009",
            Parachute.class,
            ChatColor.YELLOW,
            "Open Parachute",
            "Open Parachute",
            Material.PHANTOM_MEMBRANE,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("item_melee_damage", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("parachute_cooldown", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("chicken_speed", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("chicken_carpet_bomb_tnt", 0, new Double[]{1.6, 1.7, -1.0, -1.0, -1.0}),
            })),
    THROWING_TNT("Throwing TNT",
            "W010",
            ThrowingTNT.class,
            ChatColor.YELLOW,
            "Throw TNT",
            "Throw TNT",
            Material.COAL,
            null,
            null,
            new StatTable(new StatRow[]{
                    new StatRow("melee_damage", 0, new Double[]{15.0, 14.0, -1.0, -1.0, -1.0}),
                    new StatRow("throw_cooldown", 0, new Double[]{20.0, 18.0, -1.0, -1.0, -1.0}),
                    new StatRow("explode_damage", 0, new Double[]{7.0, 7.3, -1.0, -1.0, -1.0}),
                    new StatRow("explode_power", 0, new Double[]{1.0, -1.0, -1.0, -1.0, -1.0}),
                    new StatRow("set_blocks_on_fire", -1, new Double[]{1.0, -1.0, -1.0, -1.0, -1.0}),
            }));

    private final String name;
    private final String weaponID;
    private final ChatColor textColor;
    private final String weaponDrop;
    private final String weaponRightClick;
    private final Material material;
    private final Class<? extends WeaponHolder> weaponClass;
    private final Enchantment[] enchantments;
    private final Integer[] enchantmentPowers;
    private final StatTable statBundle;

    private WeaponType(String name, String weaponID, Class<? extends WeaponHolder> weaponClass, ChatColor textColor, String weaponDrop, String weaponRightClick, Material material, Enchantment[] enchantments, Integer[] enchantmentPowers, StatTable statBundle){
        this.name = name;
        this.weaponID = weaponID;
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

    public StatTable getStatTable() {
        return statBundle;
    }

    public String getWeaponID() {
        return weaponID;
    }
}

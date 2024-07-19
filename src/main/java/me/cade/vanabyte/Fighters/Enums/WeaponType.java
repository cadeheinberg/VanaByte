package me.cade.vanabyte.Fighters.Enums;

import me.cade.vanabyte.Fighters.WeaponHolders.WeaponHolder;
import me.cade.vanabyte.Fighters.WeaponHolders.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

//defaults for new player go here, store players version of this in MySQL table
public enum WeaponType {
    AIRBENDER_SWORD("Airbender Sword",
            W0_AirbenderSword.class,
            ChatColor.WHITE,
            "Gust of Wind",
            "Use Shield",
            Material.IRON_SWORD,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "sword_melee", //[2]
                            "gust_enemy_power", //[3]
                            "gust_enemy_damage", //[4]
                            "gust_self_power" //[5]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.5, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {6.0, 1.8, -1.0, -1.0, -1.0}, //[4]
                            {1.6, 8.0, -1.0, -1.0, -1.0}  //[5]
                    }
            )),
    BESERKER_AXE("Beserker Axe",
            W1_BeserkerAxe.class,
            ChatColor.LIGHT_PURPLE,
            "Haste, Speed, Jump",
            "Beserker Leap",
            Material.IRON_AXE,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "axe_melee", //[2]
                            "leap_cooldown", //[3]
                            "ability_leap_cooldown", //[4]
                            "leap_power", //[5]
                            "ability_leap_power", //[6]
                            "ability_speed_level", //[7]
                            "ability_jump_level", //[8]
                            "ability_haste_level" //[9]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {1.7, 1.8, -1.0, -1.0, -1.0}, //[4]
                            {1.8, 8.0, -1.0, -1.0, -1.0}, //[5]
                            {8.0, 7.0, -1.0, -1.0, -1.0}, //[6]
                            {1.0, 2.0, -1.0, -1.0, -1.0}, //[7]
                            {1.0, 2.0, -1.0, -1.0, -1.0}, //[8]
                            {1.0, 2.0, -1.0, -1.0, -1.0}  //[9]
                    }
            )),
    SHOTTY_SHOTGUN("Shotty Shotgun",
            W2_ShottyShotgun.class,
            ChatColor.YELLOW,
            "Flaming Bullets",
            "Shoot Shotgun",
            Material.IRON_SHOVEL,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "shovel_melee", //[2]
                            "shoot_cooldown", //[3]
                            "ability_on_shoot_cooldown", //[4]
                            "recoil_power", //[5]
                            "ability_on_recoil_power", //[6]
                            "bullet_damage", //[7]
                            "ability_on_bullet_damage", //[8]
                            "num_bullets", //[9]
                            "ability_on_num_bullets", //[10]
                            "ability_on_set_blocks_on_fire" //[11]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {1.7, 1.8, -1.0, -1.0, -1.0}, //[4]
                            {1.2, 8.0, -1.0, -1.0, -1.0}, //[5]
                            {1.4, 7.0, -1.0, -1.0, -1.0}, //[6]
                            {9.0, 8.0, -1.0, -1.0, -1.0}, //[7]
                            {8.0, 7.0, -1.0, -1.0, -1.0}, //[8]
                            {5.0, 2.0, -1.0, -1.0, -1.0}, //[9]
                            {20.0, 2.0, -1.0, -1.0, -1.0}, //[10]
                            {1.0, -1.0, -1.0, -1.0, -1.0}  //[11]
                    }
            )),
    GOBLIN_BOW("Goblin Bow",
            W3_GoblinArrow.class,
            ChatColor.GREEN,
            "Flaming Arrow Barrage",
            "Shoot Bow",
            Material.BOW,
            new Enchantment[]{Enchantment.INFINITY},
            new Integer[]{1},
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "bow_melee", //[2]
                            "arrow_damage", //[3]
                            "ability_on_arrow_damage", //[4]
                            "ability_on_num_arrows_barrage", //[5]
                            "ability_on_arrow_poison" //[6]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {1.7, 1.8, -1.0, -1.0, -1.0}, //[4]
                            {9.0, 8.0, -1.0, -1.0, -1.0}, //[5]
                            {8.0, 7.0, -1.0, -1.0, -1.0}  //[6]
                    }
            )),
    GOBLIN_SWORD("Goblin Sword",
            W3_GoblinBow.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.WOODEN_SWORD,
            new Enchantment[]{Enchantment.FIRE_ASPECT},
            new Integer[]{1},
            new StatBundle(
                    new String[]{
                            "sword_melee_damage", //[0]
                            "flame_aspect", //[1]
                            "knockback" //[2]
                    },
                    new Integer[]{0, 0, 0},
                    new Double[][]{
                            {7.0, 8.0, -1.0, -1.0, -1.0}, //[0]
                            {1.0, 2.0, -1.0, -1.0, -1.0}, //[1]
                            {1.0, 2.0, -1.0, -1.0, -1.0}  //[2]
                    }
            )),
    GOBLIN_ARROW("Goblin Arrow",
            W3_GoblinSword.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.ARROW,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "melee_damage", //[0]
                    },
                    new Integer[]{0},
                    new Double[][]{
                            {100.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                    }
            )),
    IGORS_TRIDENT("Igors Trident",
            W4_IgorsTrident.class,
            ChatColor.RED,
            "Explosive Tridents",
            "Throw Trident",
            Material.TRIDENT,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "trident_melee", //[2]
                            "trident_projectile_damage", //[3]
                            "ability_on_trident_projectile_damage", //[4]
                            "trident_explosion_damage", //[5]
                            "trident_explosion_power", //[6]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {1.7, 1.8, -1.0, -1.0, -1.0}, //[4]
                            {9.0, 8.0, -1.0, -1.0, -1.0}, //[5]
                            {8.0, 7.0, -1.0, -1.0, -1.0} //[6]
                    }
            )),
    SUMO_STICK("Sumo Stick",
            W5_SumoStick.class,
            ChatColor.BLUE,
            "Ground Pound",
            "PickUp/Toss Enemy",
            Material.STICK,
            new Enchantment[]{Enchantment.KNOCKBACK},
            new Integer[]{2},
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "stick_melee", //[2]
                            "stick_knockback_power", //[3]
                            "pickup_cooldown", //[4]
                            "ability_on_pickup_cooldown", //[5]
                            "throw_power", //[6]
                            "ability_on_throw_power", //[7]
                            "sumo_slam_explosion_damage", //[8]
                            "sumo_slam_explosion_power", //[9]
                            "sumo_jump_power" //[10]
                    },
                    new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {8.0, 7.0, -1.0, -1.0, -1.0}, //[3]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[4]
                            {1.7, 1.8, -1.0, -1.0, -1.0}, //[5]
                            {9.0, 8.0, -1.0, -1.0, -1.0}, //[6]
                            {8.0, 7.0, -1.0, -1.0, -1.0}, //[7]
                            {9.0, 8.0, -1.0, -1.0, -1.0},  //[8]
                            {9.0, 8.0, -1.0, -1.0, -1.0}, //[9]
                            {8.0, 7.0, -1.0, -1.0, -1.0}, //[10]
                    }
            )),
    GRIEF_SWORD("Grief Sword",
            W6_GriefSword.class,
            ChatColor.AQUA,
            "Invis & Health Steal",
            "None",
            Material.NETHERITE_SWORD,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "a_dur", //[0]
                            "a_rech", //[1]
                            "sword_melee", //[2]
                            "ability_on_melee_health_steal", //[3]
                            "ability_on_explosive_immune" //[4]
                    },
                    new Integer[]{0, 0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}, //[3]
                            {1.0, -1.0, -1.0, -1.0, -1.0}  //[4]
                    }
            )),
    PARACHUTE("Parachute",
            S0_Parachute.class,
            ChatColor.YELLOW,
            "Open Parachute",
            "Open Parachute",
            Material.PHANTOM_MEMBRANE,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "item_melee_damage", //[0]
                            "parachute_cooldown", //[1]
                            "chicken_speed", //[2]
                            "chicken_carpet_bomb_tnt" //[3]
                    },
                    new Integer[]{0, 0, 0, 0},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.6, 1.7, -1.0, -1.0, -1.0}  //[3]
                    }
            )),
    THROWING_TNT("Throwing TNT",
            S1_ThrowingTNT.class,
            ChatColor.YELLOW,
            "Throw TNT",
            "Throw TNT",
            Material.COAL,
            null,
            null,
            new StatBundle(
                    new String[]{
                            "tnt_melee_damage", //[0]
                            "tnt_throw_cooldown", //[1]
                            "tnt_explode_damage", //[2]
                            "tnt_explode_power", //[3]
                            "set_blocks_on_fire" //[4]
                    },
                    new Integer[]{0, 0, 0, 0, -1},
                    new Double[][]{
                            {15.0, 14.0, -1.0, -1.0, -1.0}, //[0]
                            {20.0, 18.0, -1.0, -1.0, -1.0}, //[1]
                            {7.0, 7.3, -1.0, -1.0, -1.0}, //[2]
                            {1.0, -1.0, -1.0, -1.0, -1.0}, //[3]
                            {1.0, -1.0, -1.0, -1.0, -1.0}  //[4]
                    }
            )),
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

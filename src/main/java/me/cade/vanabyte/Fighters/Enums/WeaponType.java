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
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] sword melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] gust of wind power
                    {6.0, 1.8, -1.0, -1.0, -1.0}, //[4][0] gust of wind damage
                    {1.6, 8.0, -1.0, -1.0, -1.0}, //[5][0] gust of wind self power
            },
            //Integer[X] = Y means that upgrade Double[X][Y] has been unlocked. First level of each unlocked here.
            new Integer[]{
                    1,
                    1,
                    1,
                    1,
                    1,
                    1}),
    BESERKER_AXE("Beserker Axe",
            W1_BeserkerAxe.class,
            ChatColor.LIGHT_PURPLE,
            "Haste, Speed, Jump",
            "Beserker Leap",
            Material.IRON_AXE,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] axe melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] leap power
                    {1.7, 1.8, -1.0, -1.0, -1.0}, //[4][0] ability on, leap power
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[5][0] leap cooldown
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[6][0] ability on, leap cooldown
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[7][0] ability on, speed potion level
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[8][0] ability on, jump potion level
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[9][0] ability on, haste potion levels
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {true, false, false, false, false}, //[4][0]
                    {true, false, false, false, false}, //[5][0]
                    {true, false, false, false, false}, //[6][0]
                    {false, false, false, false, false}, //[7][0]
                    {false, false, false, false, false}, //[8][0]
                    {false, false, false, false, false},} //[9][0]
    ),
    SHOTTY_SHOTGUN("Shotty Shotgun",
            W2_ShottyShotgun.class,
            ChatColor.YELLOW,
            "Flaming Bullets",
            "Shoot Shotgun",
            Material.IRON_SHOVEL,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] shovel melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] recoil power
                    {1.7, 1.8, -1.0, -1.0, -1.0}, //[4][0] ability on, recoil power
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[5][0] shoot cooldown
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[6][0] ability on, shoot cooldown
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[7][0] bullet damage
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[8][0] ability on, bullet damage
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[9][0] number of bullets
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[10][0] ability on, number of bullets
                    {1.0, -1.0, -1.0, -1.0, -1.0}, //[11][0] set blocks on fire that it hits
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {true, false, false, false, false}, //[4][0]
                    {true, false, false, false, false}, //[5][0]
                    {true, false, false, false, false}, //[6][0]
                    {true, false, false, false, false}, //[7][0]
                    {true, false, false, false, false}, //[8][0]
                    {true, false, false, false, false}, //[9][0]
                    {true, false, false, false, false}, //[10][0]
                    {false, false, false, false, false},} //[11][0]
    ),
    GOBLIN_BOW("Goblin Bow",
            W3_GoblinArrow.class,
            ChatColor.GREEN,
            "Flaming Arrow Barrage",
            "Shoot Bow",
            Material.BOW,
            new Enchantment[]{Enchantment.INFINITY},
            new Integer[]{1},
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] bow melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] arrow damage
                    {1.7, 1.8, -1.0, -1.0, -1.0}, //[4][0] ability on, arrow damage
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[5][0] ability on, number of arrows in barrage
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[6][0] ability on, arrow hit poison potion
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {true, false, false, false, false}, //[4][0]
                    {true, false, false, false, false}, //[5][0]
                    {false, false, false, false, false},} //[6][0]
    ),
    GOBLIN_SWORD("Goblin Sword",
            W3_GoblinBow.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.WOODEN_SWORD,
            new Enchantment[]{Enchantment.FIRE_ASPECT},
            new Integer[]{1},
            new Double[][]{
                    {7.0, 8.0, -1.0, -1.0, -1.0}, //[0][0] sword melee damage
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[1][0] flame aspect
                    {1.0, 2.0, -1.0, -1.0, -1.0}, //[2][0] knockback
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {false, false, false, false, false}, //[1][0]
                    {false, false, false, false, false},} //[9][0]
    ),
    GOBLIN_ARROW("Goblin Arrow",
            W3_GoblinSword.class,
            ChatColor.GREEN,
            "None",
            "None",
            Material.ARROW,
            null,
            null,
            null,
            null
    ),
    IGORS_TRIDENT("Igors Trident",
            W4_IgorsTrident.class,
            ChatColor.RED,
            "Explosive Tridents",
            "Throw Trident",
            Material.TRIDENT,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] trident melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] trident projectile damage
                    {1.7, 1.8, -1.0, -1.0, -1.0}, //[4][0] ability on, trident projectile damage
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[5][0] trident explosion damage
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[6][0] ability on, trident explosion damage
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[7][0] trident explosion power
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[8][0] ability on, trident explosion power
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {true, false, false, false, false}, //[4][0]
                    {true, false, false, false, false}, //[5][0]
                    {true, false, false, false, false}, //[6][0]
                    {true, false, false, false, false}, //[7][0]
                    {false, false, false, false, false},} //[8][0]
    ),
    SUMO_STICK("Sumo Stick",
            W5_SumoStick.class,
            ChatColor.LIGHT_PURPLE,
            "Ground Pound",
            "PickUp/Toss Enemy",
            Material.STICK,
            new Enchantment[]{Enchantment.KNOCKBACK},
            new Integer[]{2},
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] stick melee
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[3][0] stick knockback power
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[4][0] throw power
                    {1.7, 1.8, -1.0, -1.0, -1.0}, //[5][0] ability on, throw power
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[6][0] summo slam explosion damage
                    {8.0, 7.0, -1.0, -1.0, -1.0}, //[7][0] summo slam explosion power
                    {9.0, 8.0, -1.0, -1.0, -1.0}, //[8][0] summo jump height
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {true, false, false, false, false}, //[4][0]
                    {true, false, false, false, false}, //[5][0]
                    {true, false, false, false, false}, //[6][0]
                    {true, false, false, false, false}, //[7][0]
                    {true, false, false, false, false}, //[8][0]
                    {false, false, false, false, false},} //[9][0]
    ),
    GRIEF_SWORD("Grief Sword",
            W6_GriefSword.class,
            ChatColor.AQUA,
            "Invis & Health Steal",
            "None",
            Material.NETHERITE_SWORD,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] ability duration
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] ability recharge
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] sword melee
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] sword melee health steal amount
                    {1.0, -1.0, -1.0, -1.0, -1.0}, //[4][0] blast shield, explosive immune
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {true, false, false, false, false}, //[3][0]
                    {false, false, false, false, false},} //[9][0]
    ),
    PARACHUTE("Parachute",
            S0_Parachute.class,
            ChatColor.YELLOW,
            "Open Parachute",
            "Open Parachute",
            Material.PHANTOM_MEMBRANE,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] item melee damage
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] parachute cooldown
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] chicken speed
                    {1.6, 1.7, -1.0, -1.0, -1.0}, //[3][0] chicken carpet bomb TNT
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {false, false, false, false, false},} //[4][0]
    ),
    THROWING_TNT("Throwing TNT",
            S1_ThrowingTNT.class,
            ChatColor.YELLOW,
            "Throw TNT",
            "Throw TNT",
            Material.COAL,
            null,
            null,
            new Double[][]{
                    {15.0, 14.0, -1.0, -1.0, -1.0}, //[0][0] tnt melee damage
                    {20.0, 18.0, -1.0, -1.0, -1.0}, //[1][0] TNT explode damage
                    {7.0, 7.3, -1.0, -1.0, -1.0}, //[2][0] TNT explode power
                    {1.0, -1.0, -1.0, -1.0, -1.0}, //[3][0] turn blocks on fire that it hits
            },
            new Boolean[][]{ //defaults for new player go here, store players version of this in MySQL table
                    {true, false, false, false, false}, //[0][0] [X][Y] true or false if this upgrade Y of stat X is unlocked
                    {true, false, false, false, false}, //[1][0]
                    {true, false, false, false, false}, //[2][0]
                    {false, false, false, false, false},} //[3][0]
    ),
    UNKNOWN_WEAPON(null,
            null,
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
    private final Double[][] stats;
    private final Integer[] unlocked;

    private WeaponType(String name, Class<? extends WeaponHolder> weaponClass, ChatColor textColor, String weaponDrop, String weaponRightClick, Material material, Enchantment[] enchantments, Integer[] enchantmentPowers, Double[][] stats, Integer[] unlocked){
        this.name = name;
        this.weaponClass = weaponClass;
        this.textColor = textColor;
        this.weaponDrop = weaponDrop;
        this.weaponRightClick = weaponRightClick;
        this.material = material;
        this.enchantments = enchantments;
        this.enchantmentPowers = enchantmentPowers;
        this.stats = stats;
        this.unlocked = unlocked;
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

    public Integer[] getUnlocked() {
        return unlocked;
    }

    public Double[][] getStats() {
        return stats;
    }

    //    public Boolean getUnlocked(int i, int j) {
//        return unlocked[i][j];
//    }
//
//    public Double getStat(int i, int j) {
//        return stats[i][j];
//    }
//
//    public Double getStatOrNull(int i, int j) {
//        if(getUnlocked(i, j)){
//            return stats[i][j];
//        }
//        return null;
//    }
}

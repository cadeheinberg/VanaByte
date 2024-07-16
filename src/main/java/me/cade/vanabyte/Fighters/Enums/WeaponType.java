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
    //----baseMeleeDamage| sword damage
    //baseProjectileDamage|
    //baseExplosonDamage|
    //----baseDamage1| gust of wind enemy damage
    //specialMeleeDamage|
    //specialProjectileDamage|
    //specialExplosionDamage|
    //----specialDamage1| gust of wind enemy damage
    //baseMainCoolDown|
    //baseExplosionPower|
    //----basePower1| gust of wind enemy power
    //----basePower2| gust of wind self power
    //specialMainCooldown|
    //specialExplosionPower|
    //specialPower1|
    //specialPower2|
    //----abilityDuration| strength/regen potions
    //----abilityRecharge| strength/regen potions
    //upgrade1| power to gust wind self up when looking down?
    //upgrade2|
    //upgrade3|
    //upgrade4|
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
    //----baseMeleeDamage| axe damage
    //baseProjectileDamage|
    //baseExplosonDamage|
    //baseDamage1|
    //specialMeleeDamage|
    //specialProjectileDamage|
    //specialExplosionDamage|
    //specialDamage1|
    //----baseMainCoolDown| axe leap
    //baseExplosionPower|
    //----basePower1| axe leap power
    //basePower2|
    //----specialMainCooldown| axe leap
    //specialExplosionPower|
    //----specialPower1| axe leap power
    //specialPower2|
    //----abilityDuration| potions
    //----abilityRecharge| potions
    //upgrade1|
    //upgrade2|
    //upgrade3|
    //upgrade4|
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
    //----baseMeleeDamage| shovel damage
    //----baseProjectileDamage| snowball damage
    //baseExplosonDamage|
    //baseDamage1|
    //specialMeleeDamage|
    //----specialProjectileDamage| snowball damage
    //specialExplosionDamage|
    //specialDamage1|
    //----baseMainCoolDown| shotgun cooldown
    //baseExplosionPower|
    //----basePower1| shotgun recoil
    //basePower2|
    //----specialMainCooldown| shotgun cooldown
    //specialExplosionPower|
    //----specialPower1| shotgun recoil
    //specialPower2|
    //----abilityDuration| potions
    //----abilityRecharge| potions
    //upgrade1|
    //upgrade2|
    //upgrade3|
    //upgrade4|
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
    //----baseMeleeDamage| bow melee damage
    //----baseProjectileDamage| arrow damage
    //baseExplosonDamage|
    //baseDamage1|
    //specialMeleeDamage|
    //----specialProjectileDamage| arrow damage
    //specialExplosionDamage|
    //specialDamage1|
    //----baseMainCoolDown| bow cooldown, prevent hacking/spamming
    //baseExplosionPower|
    //basePower1|
    //basePower2|
    //----specialMainCooldown| bow cooldown, prevent hacking/spamming
    //specialExplosionPower|
    //----specialPower1| number of arrows in barrage
    //specialPower2|
    //----abilityDuration| potions
    //----abilityRecharge| potions
    //upgrade1|
    //upgrade2|
    //upgrade3|
    //upgrade4|
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
    //----baseMeleeDamage| trident melee damage
    //----baseProjectileDamage| trident projectile damage
    //baseExplosonDamage|
    //baseDamage1|
    //specialMeleeDamage|
    //----specialProjectileDamage| trident projectile damage
    //specialExplosionDamage| trident explosion damage
    //specialDamage1|
    //----baseMainCoolDown| trident cooldown, prevent hacking/spamming
    //baseExplosionPower|
    //basePower1|
    //basePower2|
    //----specialMainCooldown| trident cooldown, prevent hacking/spamming
    //----specialExplosionPower| trident explosion power
    //specialPower1|
    //specialPower2|
    //----abilityDuration| potions
    //----abilityRecharge| potions
    //upgrade1|
    //upgrade2|
    //upgrade3|
    //upgrade4|
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

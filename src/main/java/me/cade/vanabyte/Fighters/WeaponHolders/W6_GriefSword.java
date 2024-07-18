package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class W6_GriefSword extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.PARACHUTE;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final int abilityOnMeleeHealthSteel = fighter.getTickFromWeaponType(weaponType, 3);

    private final int abilityOnBlastShield = fighter.getTickFromWeaponType(weaponType, 4);

    public W6_GriefSword(Fighter fighter) {
        super(WEAPON_TYPE);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                -1,
                abilityDuration,
                abilityRecharge);
        super.player = fighter.getPlayer();
        super.weaponAbility = new WeaponAbility(fighter, this);
        super.fighter = fighter;
        this.player = this.fighter.getPlayer();
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        if (weaponAbility.isAbilityActive()) {
            double combined = player.getHealth() + abilityOnMeleeHealthSteel;
            if (combined > 20) {
                player.setHealth(20);
            } else {
                player.setHealth(combined);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
        }
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        // instant reload shotgun
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, abilityDuration, 0));
        this.makePlayerInvisible();
        fighter.getFighterKitManager().setExplosionImmune(abilityOnBlastShield >= 0);
    }

    @Override
    public void deActivateSpecial() {
        this.makeVisible();
        fighter.getFighterKitManager().setExplosionImmune(false);
    }

    public void makePlayerInvisible() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, new ItemStack(Material.AIR));
            p.sendEquipmentChange(player, EquipmentSlot.FEET, new ItemStack(Material.AIR));
        }
    }

    public void makeVisible() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendEquipmentChange(player, EquipmentSlot.HEAD, player.getEquipment().getHelmet());
            p.sendEquipmentChange(player, EquipmentSlot.CHEST, player.getEquipment().getChestplate());
            p.sendEquipmentChange(player, EquipmentSlot.LEGS, player.getEquipment().getLeggings());
            p.sendEquipmentChange(player, EquipmentSlot.FEET,  player.getEquipment().getBoots());
        }
    }
}

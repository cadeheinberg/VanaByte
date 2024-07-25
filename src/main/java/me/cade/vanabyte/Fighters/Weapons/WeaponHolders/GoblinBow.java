package me.cade.vanabyte.Fighters.Weapons.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import me.cade.vanabyte.Fighters.Weapons.WeaponHolder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class GoblinBow extends WeaponHolder {

    private final static WeaponType WEAPON_TYPE = WeaponType.GOBLIN_BOW;
    private final int BOW_SPAM_COOLDOWN_TICKS = 3;

    private final int abilityDuration = fighter.getTickFromWeaponType(weaponType, 0);
    private final int abilityRecharge = fighter.getTickFromWeaponType(weaponType, 1);

    private final double meleeDamage = fighter.getDoubleFromWeaponType(weaponType, 2);

    private final double baseArrowDamage = fighter.getDoubleFromWeaponType(weaponType, 3);
    private final double abilityOnArrowDamage = fighter.getDoubleFromWeaponType(weaponType, 4);

    private final double abilityOnNumArrowsBarrage = fighter.getDoubleFromWeaponType(weaponType, 5);

    private final double abilityOnArrowPoison = fighter.getDoubleFromWeaponType(weaponType, 6);

    public GoblinBow(Fighter fighter) {
        super(WEAPON_TYPE, fighter);
        super.weapon = new Weapon(
                WEAPON_TYPE,
                WEAPON_TYPE.getMaterial(),
                WEAPON_TYPE.getWeaponNameColored(),
                meleeDamage,
                BOW_SPAM_COOLDOWN_TICKS,
                abilityDuration,
                abilityRecharge);
        super.weapon.applyWeaponUnsafeEnchantment(Enchantment.INFINITY, 1);
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown(abilityDuration, abilityRecharge)){
            return;
        }
        player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 8, 1);
    }

    @Override
    public void doProjectileHitBlock(ProjectileHitEvent e) {
        e.getEntity().remove();
    }

    @Override
    public void doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        if (victim instanceof Player) {
            Fighter.get((Player) victim).fighterDismountParachute();
        }
        if (damagingEntity.getFireTicks() > 0) {
            e.setDamage(baseArrowDamage);
            victim.setFireTicks(50);
        } else {
            e.setDamage(abilityOnArrowDamage);
        }
        e.getDamager().remove();
        super.trackWeaponDamage(victim, e.getFinalDamage());
    }

    @Override
    public void doBowShootEvent(EntityShootBowEvent e){
        if(!checkAndSetMainCooldown(BOW_SPAM_COOLDOWN_TICKS, -1)){
            e.setCancelled(true);
            return;
        }
        Arrow arrow = (Arrow) e.getProjectile();
        double force = e.getForce();
        EntityMetadata.addWeaponTypeToEntity(arrow, WEAPON_TYPE, this.player.getUniqueId());
        if (force > 0.75 && weaponAbility.isAbilityActive()) {
            doArrowBarrage(arrow, force);
        }
    }

    public void doArrowBarrage(Arrow arrow, double force) {
        arrow.setFireTicks(1000);
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 8, 1);
        ArrayList<Arrow> arrows = new ArrayList<Arrow>();
        int numBonusArrows = (int) abilityOnNumArrowsBarrage;
        for (int i = 0; i < numBonusArrows; i++){
            arrows.add(player.launchProjectile(Arrow.class));
            Random random = new Random();
            arrows.get(i).setVelocity(arrows.get(i).getVelocity().add(new Vector(random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25))));
            EntityMetadata.addWeaponTypeToEntity(arrows.get(i), WEAPON_TYPE, player.getUniqueId());
            arrows.get(i).setShooter(player);
            arrows.get(i).setFireTicks(1000);
        }
    }
}

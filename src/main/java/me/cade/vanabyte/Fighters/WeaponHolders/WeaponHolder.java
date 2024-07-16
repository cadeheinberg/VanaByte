package me.cade.vanabyte.Fighters.WeaponHolders;

import com.google.errorprone.annotations.ForOverride;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.PVP.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.VanaByte;
import org.bukkit.*;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public abstract class WeaponHolder {

    protected final WeaponType weaponType;
    protected Fighter fighter = null;
    protected Player player = null;
    protected Weapon weapon = null;
    protected WeaponAbility weaponAbility = null;
    protected StatBundle statBundle;

    public WeaponHolder(Fighter fighter, WeaponType weaponType){
        this.weaponType = weaponType;
        this.player = fighter.getPlayer();
        this.weaponAbility = new WeaponAbility(fighter, this);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.statBundle = weaponType.getStatBundle();
        this.weapon = new Weapon(this.weaponType, this.weaponType.getMaterial(), this.weaponType.getWeaponNameColored(),
                this.statBundle.getBaseMeleeDamage(),
                this.statBundle.getBaseProjectileDamage(),
                this.statBundle.getBaseExplosionDamage(),
                this.statBundle.getBaseMainCoolDown(),
                this.statBundle.getAbilityDuration(),
                this.statBundle.getAbilityRecharge());
        if(weaponType.getEnchantments() != null && weaponType.getEnchantmentPowers() != null &&
                weaponType.getEnchantments().length == weaponType.getEnchantmentPowers().length){
            for(int i = 0; i < weaponType.getEnchantments().length; i++){
                this.weapon.applyWeaponUnsafeEnchantment(weaponType.getEnchantments()[i], weaponType.getEnchantmentPowers()[i]);
            }
        }
        //MODIFY THE STAT BUNDLE TO DO UPGRADES
        //        for(Quest quest : fighter.getQuestManager().getQuestsOfWeaponType(weaponType)){
//            if(quest.getUpgradeType() == UpgradeType.EXPLOSION_IMMUNE_WHEN_SPECIAL_ACTIVATED){
//                this.explosionImmuneUpgrade = quest.isGoalMet();
//            }
//        }
    }

    @ForOverride
    public void doRightClick(PlayerInteractEvent e) {}
    @ForOverride
    public void doLeftClick(PlayerInteractEvent e) {}
    @ForOverride
    public void doDrop(PlayerDropItemEvent e) {}
    @ForOverride
    public void deActivateSpecial() {}
    @ForOverride
    public void doRightClickEntity(PlayerInteractEntityEvent e) {}
    @ForOverride
    public void doProjectileHitBlock(ProjectileHitEvent e) {}
    @ForOverride
    public void doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {}
    @ForOverride
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {}

    @ForOverride
    public boolean doBowShootEvent(EntityShootBowEvent e){
        checkAndSetMainCooldown();
        EntityMetadata.addWeaponTypeToEntity(e.getProjectile(), this.weapon.getWeaponType(), this.player.getUniqueId());
        return true;
    }

    @ForOverride
    public boolean doProjectileLaunch(ProjectileLaunchEvent e) {
        checkAndSetMainCooldown();
        EntityMetadata.addWeaponTypeToEntity(e.getEntity(), this.weapon.getWeaponType(), this.player.getUniqueId());
        return true;
    }
    @ForOverride
    public void doDismount(EntityDismountEvent e) {}
    @ForOverride
    public void doExplosion(ExplosionPrimeEvent e, Player killer) {}

    public void trackWeaponDamage(LivingEntity victim){
        VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(new EntityDamageByEntityEvent(this.player, victim, EntityDamageEvent.DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.PLAYER_ATTACK).build(), 1), this.weaponType));
    }

    public boolean checkAndSetMainCooldown(){
        if (player.getCooldown(this.weaponType.getMaterial()) > 0) {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
            return false;
        }
        if(weaponAbility.isAbilityActive()){
            player.setCooldown(this.weaponType.getMaterial(), statBundle.getSpecialMainCoolDown());
        }else{
            player.setCooldown(this.weaponType.getMaterial(), statBundle.getBaseMainCoolDown());
        }
        return true;
    }

    public boolean checkAndSetSpecialCooldown(){
        if (this.player.getCooldown(FighterKitManager.cooldownMaterial) > 0 || Fighter.get(player).getWeaponAbilityManager().isSomeWeaponAbilityActive() != null) {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
            return false;
        }
        weaponAbility.startAbilityDuration();
        return true;
    }

    public boolean checkRightClickEntity(PlayerInteractEntityEvent e){
        if(e.getRightClicked().getType() == EntityType.ITEM_FRAME || e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME || e.getRightClicked().getType() == EntityType.ITEM_DISPLAY){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
            ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
            return false;
        }
        return true;
    }

    public void createAnExplosion(Player shooter, Location location, double power, double damage) {
        //sends an EntityDamageByEntityEvent aswelll that we cancel
        //we can cancel if Cause=Explosion and damager=Player
        location.getWorld().createExplosion(location, 4F, false, true, shooter);
        //just create your own explosion to break blocks cause spigots is bad
        location.getWorld().spawnParticle(Particle.EXPLOSION, location.getX(), location.getY() + 2,
                location.getZ(), 2);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
        for (Entity ent : location.getWorld().getNearbyEntities(location, 4, 4, 4)) {
            if (!(ent instanceof LivingEntity)) {
                continue;
            }
            if(ent instanceof Player) {
                Player victimPlayer = (Player) ent;
                Fighter fighter = Fighter.get(victimPlayer);
                if (fighter == null){
                    return;
                }
                FighterKitManager fKit = fighter.getFighterKitManager();
                if(fKit == null){
                    return;
                }
                if(fKit.isExplosionImmune()){
                    return;
                }
                if(((Player) ent).getGameMode() == GameMode.CREATIVE) {
                    return;
                }
            }
            Location upShoot = ent.getLocation();
            if (ent.isOnGround()) {
                upShoot.setY(upShoot.getY() + 1);
            }
            Vector currentDirection = upShoot.toVector().subtract(location.toVector());
            currentDirection = currentDirection.multiply(new Vector(power, power, power));
            ent.setVelocity(currentDirection);
            if (((LivingEntity) ent) != shooter) {
                this.trackWeaponDamage((LivingEntity) ent);
                ((LivingEntity) ent).damage(damage);
            }
        }
    }
}

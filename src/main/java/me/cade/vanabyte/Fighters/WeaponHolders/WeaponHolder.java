package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import me.cade.vanabyte.Fighters.PVP.CreateExplosion;
import me.cade.vanabyte.Fighters.PVP.DamageTracker.CustomDamageWrapper;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.VanaByte;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WeaponHolder {

    private final WeaponType weaponType;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    private WeaponAbility weaponAbility = null;

    public WeaponHolder(Fighter fighter, WeaponType weaponType){
        this.weaponType = weaponType;
        this.player = fighter.getPlayer();
        this.weaponAbility = new WeaponAbility(fighter, this);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();

        //        for(Quest quest : fighter.getQuestManager().getQuestsOfWeaponType(weaponType)){
//            if(quest.getUpgradeType() == UpgradeType.EXPLOSION_IMMUNE_WHEN_SPECIAL_ACTIVATED){
//                this.explosionImmuneUpgrade = quest.isGoalMet();
//            }
//        }
        this.meleeDamage = this.weaponType.getMeleeDamage();
        this.projectileDamage = this.weaponType.getProjectileDamage();
        this.specialDamage = this.weaponType.getSpecialDamage();
        this.abilityDurationTicks = this.weaponType.getAbilityDurationTicks();
        this.abilityRechargeTicks = this.weaponType.getAbilityRechargeTicks();
        this.rightClickCooldownTicks = this.weaponType.getRightClickCooldownTicks();

        this.weapon = new Weapon(this.weaponType, this.weaponType.getMaterial(), this.weaponType.getWeaponNameColored(),
                this.meleeDamage,
                this.projectileDamage,
                this.specialDamage,
                this.rightClickCooldownTicks,
                this.abilityDurationTicks,
                this.abilityRechargeTicks);
        if(weaponType.getEnchantments() != null && weaponType.getEnchantmentPowers() != null &&
                weaponType.getEnchantments().length != weaponType.getEnchantmentPowers().length){
            for(int i = 0; i < weaponType.getEnchantments().length; i++){
                this.weapon.applyWeaponEnchantment(weaponType.getEnchantments()[i], weaponType.getEnchantmentPowers()[i]);
            }
        }
    }

    //Return False - when not primary weapon and when cooldown is on primary weapon
    //Return True  - when it is primary weapon and cooldown successfully set
    public boolean doRightClick(PlayerInteractEvent e) {
        if (player.getCooldown(this.weaponType.getMaterial()) > 0) {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
            return false;
        }
        player.setCooldown(this.weaponType.getMaterial(), this.getRightClickCooldownTicks());
        return true;
    }

    public boolean doLeftClick(PlayerInteractEvent e){
        return true;
    }

    public boolean doRightClickEntity(PlayerInteractEntityEvent e) {
        if(e.getRightClicked().getType() == EntityType.ITEM_FRAME || e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME || e.getRightClicked().getType() == EntityType.ITEM_DISPLAY){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Special items can't leave your inventory!");
            ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
        }
        if (player.getCooldown(this.weaponType.getMaterial()) > 0) {
            this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
            return false;
        }
        player.setCooldown(this.weaponType.getMaterial(), this.getRightClickCooldownTicks());
        return true;
    }

    //Returns True if there is no cooldown and ability for either
    // the Fighter Kit or special item can be activated
    //
    //Returns False if there is a cooldown or not a Fighter Kits item
    public boolean doDrop(PlayerDropItemEvent e) {
            if (this.player.getCooldown(FighterKitManager.cooldownMaterial) > 0 || Fighter.get(player).getWeaponAbilityManager().isSomeWeaponAbilityActive() != null) {
                this.player.playSound(this.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
                return false;
            }
            return true;
    }

    public boolean doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        VanaByte.getEntityDamageManger().register(new CustomDamageWrapper(e, this.weaponType));
        return true;
    }

    public boolean activateSpecial() {
        this.getWeaponAbility().startAbilityDuration();
        return true;
    }

    public boolean deActivateSpecial() {
        //pass
        return true;
    }

    public boolean doProjectileHitBlock(ProjectileHitEvent e) {
        return true;
    }

    public boolean doProjectileHitEntity(EntityDamageByEntityEvent e) {
        return true;
    }

    public boolean doBowShootEvent(EntityShootBowEvent e){
        if (this.player.getCooldown(this.weaponType.getMaterial()) > 0) {
            return false;
        }
        if(this.getRightClickCooldownTicks() > 0){
            player.setCooldown(this.weaponType.getMaterial(), this.rightClickCooldownTicks);
        }
        return true;
    }

    public boolean doProjectileLaunch(ProjectileLaunchEvent e) {
        if (player.getCooldown(weaponType.getMaterial()) > 0) {
            return false;
        }
        if(this.getRightClickCooldownTicks() > 0){
            player.setCooldown(weaponType.getMaterial(), this.rightClickCooldownTicks);
        }
        EntityMetadata.addWeaponTypeToEntity(e.getEntity(), this.weapon.getWeaponType(), this.player.getUniqueId());
        return true;
    }

    public boolean doDismount(EntityDismountEvent e) {
        return true;
    }

    public boolean doExplosion(ExplosionPrimeEvent e, Player killer) {
        CreateExplosion.doAnExplosion(killer, e.getEntity().getLocation(), 1.6, this.projectileDamage, false, weaponType);
        return true;
    }

    public int getRightClickCooldownTicks() {
        return rightClickCooldownTicks;
    }


    public int getAbilityDurationTicks() {
        return abilityDurationTicks;
    }

    public int getAbilityRechargeTicks() {
        return abilityRechargeTicks;
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    public double getMeleeDamage() {
        return meleeDamage;
    }

    public double getSpecialDamage() {
        return specialDamage;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public WeaponAbility getWeaponAbility() {
        return weaponAbility;
    }

    public Player getPlayer(){
        return player;
    }

    public WeaponType getWeaponType(){
        return weaponType;
    }

    public Fighter getFighter(){
        return fighter;
    }
}

package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class W2_ShottyShotgun extends WeaponHolder {

    private final Player player;

    public W2_ShottyShotgun(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
        this.player = fighter.getPlayer();
    }

    @Override
    public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
        super.trackWeaponDamage(victim);
    }

    @Override
    public void doRightClick(PlayerInteractEvent e) {
        if(!super.checkAndSetMainCooldown()){
            return;
        }
        this.shootSnowballs();
        this.doShotgunRecoil();
    }

    @Override
    public void doDrop(PlayerDropItemEvent e) {
        if(!super.checkAndSetSpecialCooldown()){
            return;
        }
        // instant reload shotgun
        player.setCooldown(weaponType.getMaterial(), 0);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
    }

    @Override
    public void doProjectileHitBlock(ProjectileHitEvent e){
        //snowball hit ground
        e.getEntity().remove();
    }

    @Override
    public void doProjectileHitEntity(EntityDamageByEntityEvent e, Player shooter, LivingEntity victim, Entity damagingEntity) {
        super.trackWeaponDamage(victim);
        if (damagingEntity.getFireTicks() > 0) {
            victim.setFireTicks(50);
            e.setDamage(statBundle.getSpecialProjectileDamage());
        }else{
            e.setDamage(statBundle.getBaseProjectileDamage());
        }
        e.getDamager().remove();
    }

    private void doShotgunRecoil() {
        Vector currentDirection = player.getLocation().getDirection().normalize();
        if(weaponAbility.isAbilityActive()){
            currentDirection = currentDirection.multiply(new Vector(statBundle.getBasePower1(), statBundle.getBasePower1(), statBundle.getBasePower1()));
        }else{
            currentDirection = currentDirection.multiply(new Vector(statBundle.getSpecialPower1(), statBundle.getSpecialPower1(), statBundle.getSpecialPower1()));
        }
        player.setVelocity(currentDirection);
    }

    public void shootSnowballs() {
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);
        ArrayList<Snowball> snowBalls = new ArrayList<Snowball>();
        for (int i = 0; i < 6; i++){
            snowBalls.add(player.launchProjectile(Snowball.class));
            Random random = new Random();
            snowBalls.get(i).setVelocity(snowBalls.get(i).getVelocity().add(new Vector(random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25), random.nextDouble(-0.25, 0.25))));
            EntityMetadata.addWeaponTypeToEntity(snowBalls.get(i), weaponType, player.getUniqueId());
            snowBalls.get(i).setShooter(player);
            if (weaponAbility.isAbilityActive()) {
                snowBalls.get(i).setFireTicks(1000);
            }
        }
    }
}

package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class S1_ThrowingTNT extends WeaponHolder {

	private final int baseCooldown = fighter.getTickFromWeaponType(weaponType, 1);
	private final int abilityOnCooldown = -1;
	private final double explosionDamage = fighter.getDoubleFromWeaponType(weaponType, 2);
	private final double explosionPower = fighter.getDoubleFromWeaponType(weaponType, 3);

	public S1_ThrowingTNT(Fighter fighter, WeaponType weaponType) {
		super(fighter, weaponType);
	}

	@Override
	public void doMeleeAttack(EntityDamageByEntityEvent e, Player killer, LivingEntity victim) {
		super.trackWeaponDamage(victim);
	}

	@Override
	public void doRightClick(PlayerInteractEvent e) {
		if(!super.checkAndSetMainCooldown(baseCooldown, abilityOnCooldown)){
			return;
		}
		throwTNT();
	}

	@Override
	public void doDrop(PlayerDropItemEvent e) {
		if(!super.checkAndSetMainCooldown(baseCooldown, abilityOnCooldown)){
			return;
		}
		throwTNT();
	}

	@Override
	public void doExplosion(ExplosionPrimeEvent e, Player killer){
		super.createAnExplosion(killer, e.getEntity().getLocation(), explosionDamage, explosionPower);
	}

	private void throwTNT(){
		Entity tnt = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.TNT);
		TNTPrimed fuse = (TNTPrimed) tnt;
		tnt.setCustomNameVisible(false);
		tnt.setCustomName(player.getName());
		EntityMetadata.addWeaponTypeToEntity(tnt, weaponType, player.getUniqueId());
		fuse.setFuseTicks(15);
		Vector currentDirection4 = player.getLocation().getDirection().normalize();
		currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
		tnt.setVelocity(currentDirection4);
	}


}

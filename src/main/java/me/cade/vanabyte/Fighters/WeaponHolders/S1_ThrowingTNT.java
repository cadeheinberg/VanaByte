package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.PVP.CreateExplosion;
import me.cade.vanabyte.Fighters.PVP.EntityMetadata;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class S1_ThrowingTNT extends WeaponHolder {

	public S1_ThrowingTNT(Fighter fighter, WeaponType weaponType) {
		super(fighter, weaponType);
	}

	@Override
	public boolean doRightClick(PlayerInteractEvent e) {
		if(!super.doRightClick(e)) {
			return false;
		}
		Entity tnt = super.getPlayer().getWorld().spawnEntity(super.getPlayer().getEyeLocation(), EntityType.TNT);
		TNTPrimed fuse = (TNTPrimed) tnt;
		tnt.setCustomName(super.getPlayer().getName());
		tnt.setCustomNameVisible(false);
		EntityMetadata.addWeaponTypeToEntity(tnt, super.getWeaponType(), super.getPlayer().getUniqueId());
		fuse.setFuseTicks(15);
		Vector currentDirection4 = super.getPlayer().getLocation().getDirection().normalize();
		currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
		tnt.setVelocity(currentDirection4);
		return true;
	}
	@Override
	public boolean doDrop(PlayerDropItemEvent e) {
		return this.doRightClick(new PlayerInteractEvent(null, null, null, null, null));
	}

	@Override
	public boolean doExplosion(ExplosionPrimeEvent e, Player killer){
		if(super.doExplosion(e, killer)){
			return true;
		}
		return false;
	}


}

package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Damaging.EntityMetadata;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class S1_ThrowingTNT extends WeaponHolder {

	public S1_ThrowingTNT(Fighter fighter, WeaponType weaponType) {
		super(fighter, weaponType);
	}

	@Override
	public boolean doRightClick() {
		if(!super.doRightClick()) {
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
	public boolean doDrop() {
		if(!super.doDrop()){
			return false;
		}
		return this.doRightClick();
	}


}

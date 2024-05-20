package me.cade.vanabyte.Damaging.DamageTracker;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CustomDamageWrapper {
    private final EntityDamageByEntityEvent event;
    private final WeaponType weaponType;

    public CustomDamageWrapper(EntityDamageByEntityEvent event, WeaponType customValue) {
        this.event = event;
        this.weaponType = customValue;
    }

    public EntityDamageByEntityEvent getEvent() {
        return event;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
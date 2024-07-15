package me.cade.vanabyte.Fighters.PVP.DamageTracker;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

public class CustomDamageWrapper {
    private final EntityDamageByEntityEvent event;
    private final WeaponType weaponType;

    // used for tracking projectiles, tnt, where a second entity is involved in attack besides player
    private final UUID weaponEntity;

    public CustomDamageWrapper(EntityDamageByEntityEvent event, WeaponType customValue) {
        this.event = event;
        this.weaponType = customValue;
        this.weaponEntity = null;
    }

    public CustomDamageWrapper(EntityDamageByEntityEvent event, WeaponType customValue, UUID weaponEntity) {
        this.event = event;
        this.weaponType = customValue;
        this.weaponEntity = weaponEntity;
    }

    public EntityDamageByEntityEvent getEvent() {
        return event;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public UUID getWeaponEntity(){
        return weaponEntity;
    }

}
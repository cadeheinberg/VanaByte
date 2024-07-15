package me.cade.vanabyte.Fighters.PVP.DamageTracker;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import java.util.UUID;

public class EntityDamageEntry {
    private final double damage;
    private final DamageCause cause;
    private final UUID attackerUUID; // null if no attacker
    private final UUID victimUUID;
    private final EntityType attackerType;
    private final EntityType victimType;
    private final long timestamp;
    private final WeaponType weaponType;

    public EntityDamageEntry(CustomDamageWrapper event) {
        this.damage = event.getEvent().getFinalDamage();
        this.cause = event.getEvent().getCause();
        this.attackerUUID = event.getEvent().getDamager().getUniqueId();
        this.victimUUID = event.getEvent().getEntity().getUniqueId();
        this.timestamp = System.currentTimeMillis();
        this.weaponType = event.getWeaponType();
        this.attackerType = event.getEvent().getDamager().getType();
        this.victimType = event.getEvent().getEntity().getType();
    }

    public double getDamage() {
        return damage;
    }

    public DamageCause getCause() {
        return cause;
    }

    public UUID getAttackerUUID() {
        return attackerUUID;
    }

    public UUID getVictimUUID() {
        return victimUUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public WeaponType getWeaponType(){
        return weaponType;
    }

    public EntityType getAttackerType() {
        return attackerType;
    }

    public EntityType getVictimType() {
        return victimType;
    }
}

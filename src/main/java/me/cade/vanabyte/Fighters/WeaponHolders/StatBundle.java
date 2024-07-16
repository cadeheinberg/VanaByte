package me.cade.vanabyte.Fighters.WeaponHolders;

public class StatBundle {

    protected double baseMeleeDamage;
    protected double baseProjectileDamage;
    protected double baseExplosionDamage;
    protected double baseDamage1;

    protected double specialMeleeDamage;
    protected double specialProjectileDamage;
    protected double specialExplosionDamage;
    protected double specialDamage1;

    protected int baseMainCoolDown;
    protected double baseExplosionPower;
    protected double basePower1;
    protected double basePower2;

    protected int specialMainCoolDown;
    protected double specialExplosionPower;
    protected double specialPower1;
    protected double specialPower2;

    protected int abilityDuration;
    protected int abilityRecharge;

    protected boolean upgrade1;
    protected boolean upgrade2;
    protected boolean upgrade3;
    protected boolean upgrade4;

    public StatBundle(double baseMeleeDamage, double baseProjectileDamage, double baseExplosionDamage, double baseDamage1,
                         double specialMeleeDamage, double specialProjectileDamage, double specialExplosionDamage, double specialDamage1,
                         int baseMainCoolDown, double baseExplosionPower, double basePower1, double basePower2,
                         int specialMainCoolDown, double specialExplosionPower, double specialPower1, double specialPower2,
                         int abilityDuration, int abilityRecharge,
                         boolean upgrade1, boolean upgrade2, boolean upgrade3, boolean upgrade4) {
        this.baseMeleeDamage = baseMeleeDamage;
        this.baseProjectileDamage = baseProjectileDamage;
        this.baseExplosionDamage = baseExplosionDamage;
        this.baseDamage1 = baseDamage1;

        this.specialMeleeDamage = specialMeleeDamage;
        this.specialProjectileDamage = specialProjectileDamage;
        this.specialExplosionDamage = specialExplosionDamage;
        this.specialDamage1 = specialDamage1;

        this.baseMainCoolDown = baseMainCoolDown;
        this.baseExplosionPower = baseExplosionPower;
        this.basePower1 = basePower1;
        this.basePower2 = basePower2;

        this.specialMainCoolDown = specialMainCoolDown;
        this.specialExplosionPower = specialExplosionPower;
        this.specialPower1 = specialPower1;
        this.specialPower2 = specialPower2;

        this.abilityDuration = abilityDuration;
        this.abilityRecharge = abilityRecharge;

        this.upgrade1 = upgrade1;
        this.upgrade2 = upgrade2;
        this.upgrade3 = upgrade3;
        this.upgrade4 = upgrade4;
    }
}

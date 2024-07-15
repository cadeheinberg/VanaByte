package me.cade.vanabyte.Fighters.WeaponHolders;

import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;

public class W3_GoblinArrow extends WeaponHolder {

    public W3_GoblinArrow(Fighter fighter, WeaponType weaponType) {
        super(fighter, weaponType);
    }

    @Override
    public boolean doRightClick() {
        return false;
    }
    @Override
    public boolean doDrop() {
        return false;
    }
    @Override
    public void activateSpecial() {
        //pass
    }
    @Override
    public void deActivateSpecial() {
        //pass
    }

}

package me.cade.vanabyte.Fighters;

import me.cade.vanabyte.Fighters.FighterKits.*;
import me.cade.vanabyte.SpecialItems.SpecialItem;
import me.cade.vanabyte.Fighters.Weapons.Weapon;
import org.bukkit.entity.Player;

public class FighterKitManager {

    private Player player = null;
    private Fighter fighter = null;
    private static FighterKit[] fKits = { new F0(), new F1(), new F2(), new F3(), new F4(), new F5(), new F6() };
    private int[] unlockedKits = new int[7];
    private int[] kitUpgrades = new int[42];
    private FighterKit fKit = null;
    private int kitID,kitIndex = -1;

    protected FighterKitManager(Player player, Fighter fighter){
        this.player = player;
        this.fighter = fighter;
    }
    protected void setNegatives(){
        for (int i = 0; i < unlockedKits.length; i++){
            unlockedKits[i] = -1;
        }
        for (int i = 0; i < kitUpgrades.length; i++){
            kitUpgrades[i] = -1;
        }
    }
    public void giveKit() {
        if (kitID == fKits[0].getKitID()) {
            fKit = new F0(player);
        } else if (kitID == fKits[1].getKitID()) {
            fKit = new F1(player);
        } else if (kitID == fKits[2].getKitID()) {
            fKit = new F2(player);
        } else if (kitID == fKits[3].getKitID()) {
            fKit = new F3(player);
        } else if (kitID == fKits[4].getKitID()) {
            fKit = new F4(player);
        } else if (kitID == fKits[5].getKitID()) {
            fKit = new F5(player);
        } else if (kitID == fKits[6].getKitID()) {
            fKit = new F6(player);
        }
    }

    public void giveKitWithID(int kitID) {
        this.setKitID(kitID);
        this.giveKit();
    }

    public int[] getUnlockedKits(){
        return unlockedKits;
    }

    public int[] getKitUpgrades(){
        return kitUpgrades;
    }

    public int getKitID(){
        return kitID;
    }

    public void setKitID(int setter){
        this.kitID = setter;
    }

    public void resetAllFighterItemCooldowns() {
        for(Weapon weapon : fighter.getFKit().getWeapons()) {
            if(weapon == null){
                continue;
            }
            weapon.resetCooldown(this.player);
        }
        for(SpecialItem sItem : fighter.getFKit().getSpecialItems()) {
            if(sItem == null){
                continue;
            }
            sItem.resetCooldown();
        }
    }

    public static FighterKit[] getFkitsNoPlayer(){
        return fKits;
    }

    public void setKitUpgradesRaw(int index, int value) {
        this.kitUpgrades[index] = value;
    }
    public void setKitUpgradesUsingIDAndOffset(int kitID, int offset, int value) {
        this.kitUpgrades[(6 * kitID) + offset] = value;
    }

    public int getKitUpgradesRaw(int kitID) {
        return kitUpgrades[kitID];
    }

    public int getKitUpgradesUsingIDAndOffset(int kitID, int offset) {
        return this.kitUpgrades[(6 * kitID) + offset];
    }

    public int getUnlockedKit(int kitID) {
        return unlockedKits[kitID];
    }

    public void setUnlockedKit(int kitID, int index) {
        this.unlockedKits[kitID] = index;
    }

    public double getKitUpgradesConvertedDamage(int kitID, int offset){
        if(this.getKitUpgradesRaw((kitID * 6 )+ offset) <= 0){
            return 0;
        }
        return this.getKitUpgradesRaw((kitID * 6 )+ offset)/10.0;
    }

    public int getKitUpgradesConvertedTicks(int kitID, int offset){
        if(this.getKitUpgradesRaw((kitID * 6 )+ offset) <= 0){
            return 0;
        }
        return this.getKitUpgradesRaw((kitID * 6 )+ offset);
    }

    public void fighterDismountParachute() {
        if ((fKit.getParachuteItem() == null)) {
            return;
        }
        if(fKit.getParachuteItem().getItemTask() != -1){
            fKit.getParachuteItem().getOff();
        }
    }

    public FighterKit getFKit(){
        return fKit;
    }

}

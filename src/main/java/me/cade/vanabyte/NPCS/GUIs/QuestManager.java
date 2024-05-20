package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuestManager {

    private static final int numberOfQuestsPerItem = 9;
    private Player player;
    private Fighter fighter;
    private ArrayList<Quest> quests = new ArrayList<Quest>();

    public QuestManager(Player player, Fighter fighter) {
        this.player = player;
        this.fighter = fighter;
        this.addQuests();
    }

    public void addQuests(){
        for (WeaponType weaponType : WeaponType.values()){
            for(int i = 0; i < numberOfQuestsPerItem; i++){
                quests.add(new Quest("Kill 100 spiders in Anarchy with Airbender Sword",
                        weaponType,
                        UpgradeType.MELEE_DAMAGE,
                        UpgradeAlgebraType.ADDITION,
                        1, 100));
            }
        }
    }

    public ArrayList<Quest> getQuestsOfWeaponType(WeaponType weaponType) {
        return quests.stream().filter((quest) -> quest.getWeaponType() == weaponType).collect(Collectors.toCollection(ArrayList::new));
    }

}

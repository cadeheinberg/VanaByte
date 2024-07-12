package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.entity.EntityType;
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

    //square gui slots around weapon
    //12, 13, 14, 21, 23, 30, 31, 32
    public void addQuests(){
//        for (WeaponType weaponType : WeaponType.values()){
//            for(int i = 0; i < numberOfQuestsPerItem; i++){
//                quests.add(new Quest(1,
//                        "Kill 100 spiders in Anarchy with Airbender Sword",
//                        weaponType,
//                        UpgradeType.MELEE_DAMAGE,
//                        UpgradeAlgebraType.ADDITION,
//                        1, 100));
//            }
//        }
        quests.add(new Quest(1,
                12,
                "Blast Suit",
                "Kill 10 creepers before they explode with grief sword",
                WeaponType.GRIEF_SWORD,
                EntityType.CREEPER,
                UpgradeType.EXPLOSION_IMMUNE_WHEN_SPECIAL_ACTIVATED,
                UpgradeAlgebraType.BOOLEAN,
                1,
                10));
    }

    public ArrayList<Quest> getQuestsOfWeaponType(WeaponType weaponType) {
        return quests.stream().filter((quest) -> quest.getWeaponType() == weaponType).collect(Collectors.toCollection(ArrayList::new));
    }

}

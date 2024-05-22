package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageEntry;
import me.cade.vanabyte.FighterWeapons.InUseWeapons.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;

public class QuestListener implements Listener {

    public static void entityKilledByPlayer(EntityDamageEntry e) {
        Player pkiller = Bukkit.getServer().getPlayer(e.getAttackerUUID());
        if(pkiller == null){
            return;
        }
        Fighter fKiller = Fighter.get(pkiller);
        if(fKiller == null){
            return;
        }
        QuestManager qManager = fKiller.getQuestManager();
        if(qManager == null){
            return;
        }
        if(e.getWeaponType() == null || e.getWeaponType() == WeaponType.UNKNOWN_WEAPON){
            return;
        }
        ArrayList<Quest> quests = qManager.getQuestsOfWeaponType(e.getWeaponType());
        if(quests == null || quests.isEmpty()){
            return;
        }
        for(Quest quest : quests){
            if(quest.getTargetEntityType() == null){
                continue;
            }
            if(e.getVictimType() == null){
                continue;
            }
            if(quest.getTargetEntityType() == e.getVictimType()){
                pkiller.sendMessage("Quest Progress: you killed " + e.getVictimType() + " with " + e.getWeaponType().getName());
                quest.setProgress(quest.getProgress()+1);
            }
        }
    }
}

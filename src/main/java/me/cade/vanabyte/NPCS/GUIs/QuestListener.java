package me.cade.vanabyte.NPCS.GUIs;

import me.cade.vanabyte.Damaging.DamageTracker.EntityDamageEntry;
import me.cade.vanabyte.Fighters.Enums.WeaponType;
import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
                quest.setProgress(quest.getProgress()+1);
                pkiller.sendMessage("Killed " + quest.getProgress() + "/" + quest.getGoal() + ": " + e.getVictimType() + " with " + e.getWeaponType().name());
                if(quest.isGoalMet()){
                    pkiller.sendMessage("Quest " + ChatColor.YELLOW + "" + quest.getTitle() + ChatColor.WHITE + " complete, go to hub upgrade table to receive reward!");
                    pkiller.playSound(pkiller.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 8, 1);
                }
            }
        }
    }
}

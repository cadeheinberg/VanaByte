package me.cade.vanabyte.Money;

import me.cade.vanabyte.Fighters.Fighter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CakePickUp {

  public static void pickUpCake(Player player) {
    Fighter fighter = Fighter.get(player);
    fighter.incCakesByAmount(CakeManager.cakeMoneyWorth);
    //Increase experience here
    // change player cooldown recharge multiplier
    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 8, 1);
    player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 140, 1));
  }
  
}

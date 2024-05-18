package me.cade.vanabyte.FighterWeapons.InUseWeapons;

import me.cade.vanabyte.Fighters.Fighter;
import me.cade.vanabyte.Fighters.FighterKitManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class W0_AirbenderSword extends WeaponHolder {
    final String weaponDrop = "Gust of Wind";
    final String weaponRightClick = "Use Shield";
    final ChatColor weaponNameColor = ChatColor.WHITE;
    final String weaponName = weaponNameColor + "Airbender Sword";
    private Material material = null;
    private int abilityDurationTicks, abilityRechargeTicks, rightClickCooldownTicks = -1;
    private double specialDamage, meleeDamage, projectileDamage = -1;
    private FighterKitManager fighterKitManager = null;
    private Fighter fighter = null;
    private Player player = null;
    private Weapon weapon = null;
    public W0_AirbenderSword(Fighter fighter) {
        super(fighter);
        this.fighter = fighter;
        this.player = this.fighter.getPlayer();
        this.fighterKitManager = this.fighter.getFighterKitManager();
        this.meleeDamage = 6 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 0);;
        this.projectileDamage = 0 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 1);;
        this.specialDamage = 7 + this.fighterKitManager.getKitUpgradesConvertedDamage(0, 2);
        this.abilityDurationTicks = 200 + this.fighterKitManager.getKitUpgradesConvertedTicks(0, 3);
        this.abilityRechargeTicks = 50 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 4);
        this.rightClickCooldownTicks = 0 - this.fighterKitManager.getKitUpgradesConvertedTicks(0, 5);
        this.material = Material.IRON_SWORD;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    public W0_AirbenderSword(){
        super();
        this.meleeDamage = 6;
        this.projectileDamage = 0;
        this.specialDamage = 7;
        this.abilityDurationTicks = 200;
        this.abilityRechargeTicks = 50;
        this.rightClickCooldownTicks = 0;
        this.material = Material.IRON_SWORD;
        this.weapon = new Weapon(this.getMaterial(), this.weaponName, this.meleeDamage,
                this.getProjectileDamage(), this.getSpecialDamage(), this.getRightClickCooldownTicks(), this.getAbilityDurationTicks(),
                this.getAbilityRechargeTicks());
    }
    @Override
    public boolean doRightClick() {
        return false;
    }
    @Override
    public boolean doDrop() {
        if (!super.doDrop()){
            return false;
        }
        this.activateSpecial();
        return true;
    }
    @Override
    public void activateSpecial() {
        super.activateSpecial();
        this.gustOfWindSpell();
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, abilityDurationTicks, 1));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, abilityDurationTicks, 1));
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 8, 1);
    }
    @Override
    public void deActivateSpecial() {
        super.deActivateSpecial();
    }
    public void gustOfWindSpell() {
        Location playerLocation = this.player.getLocation();
        if (playerLocation.getPitch() > 49) {
            launchPlayer(this.player, -1.5);
            return;
        }
        Location origin = this.player.getEyeLocation();
        Vector direction = this.player.getLocation().getDirection();
        double dX = direction.getX();
        double dY = direction.getY();
        double dZ = direction.getZ();
        playerLocation.setPitch((float) -30.0);
        int range = 13;
        double power = 2.8;
        ArrayList<Integer> hitList = new ArrayList<Integer>();
        for (int j = 2; j < range; j++) {
            origin = origin.add(dX * j, dY * j, dZ * j);
            this.player.spawnParticle(Particle.DUST, origin, 100, 0.5, 0.75, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
            ArrayList<Entity> entityList = (ArrayList<Entity>) this.player.getWorld().getNearbyEntities(origin, 2.5, 2.5,
                    2.5);
            for (Entity entity : entityList) {
                //Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "1 Living Entity Found");
                if (!(entity instanceof LivingEntity)) {
                    continue;
                } else if (hitList.contains(((LivingEntity) entity).getEntityId())) {
                    continue;
                } else if (this.player.getName().equals(((LivingEntity) entity).getName())) {
                    continue;
                }
                if(entity instanceof Player) {
                    if(((Player) entity).getGameMode() == GameMode.CREATIVE) {
                        return;
                    }
                }
                ((LivingEntity) entity).damage(this.specialDamage, this.player);
                Vector currentDirection = playerLocation.getDirection().normalize();
                currentDirection = currentDirection.multiply(new Vector(power, power, power));
                entity.setVelocity(currentDirection);
                hitList.add(((LivingEntity) entity).getEntityId());
            }
            origin = origin.subtract(dX * j, dY * j, dZ * j);
        }
    }

    private static void launchPlayer(Player player, Double power) {
        player.spawnParticle(Particle.DUST, player.getLocation(), 100, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.fromRGB(255, 255, 255), 1.0F));
        Vector currentDirection = player.getLocation().getDirection().normalize();
        currentDirection = currentDirection.multiply(new Vector(power, power, power));
        player.setVelocity(currentDirection);
    }

    @Override
    public String getWeaponDrop() {
        return weaponDrop;
    }

    @Override
    public String getWeaponRightClick() {
        return weaponRightClick;
    }

    @Override
    public String getWeaponName() {return weaponName;}

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public int getRightClickCooldownTicks() {
        return rightClickCooldownTicks;
    }

    @Override
    public int getAbilityDurationTicks() {
        return abilityDurationTicks;
    }

    @Override
    public int getAbilityRechargeTicks() {
        return abilityRechargeTicks;
    }

    @Override
    public double getProjectileDamage() {
        return projectileDamage;
    }

    @Override
    public double getMeleeDamage() {
        return meleeDamage;
    }

    @Override
    public double getSpecialDamage() {
        return specialDamage;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public ChatColor getWeaponNameColor(){return weaponNameColor;}
}

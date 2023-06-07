package me.cade.vanabyte.NPCS;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class D1_LivingEntity {
	
	  private String name;
	  private Location location;
	  private EntityType type;
	  private LivingEntity entity;
	  
	  public D1_LivingEntity(EntityType type, String name, Location location, String hover1, String hover2, String hover3) {
	    this.name = name;
	    this.location = location;;
	    this.type = type;
	    this.entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
	    this.entity.setSilent(true);
	    this.entity.setAI(false);
	    this.entity.setCustomName(name);
	    this.entity.setCustomNameVisible(true);
	    this.entity.setRemoveWhenFarAway(false);
	    if(hover1 != null) {
	    	
	    }
	    if(hover2 != null) {
	    	
	    }
	    if(hover3 != null) {
	    	
	    }
	  }
	  
	  public String getName() {
	    return this.name;
	  }
	  
	  public EntityType getType() {
	    return this.type;
	  }
	  
	  public LivingEntity getEntity() {
	    return entity;
	  }

	public Location getLocation() {
		return location;
	}

}

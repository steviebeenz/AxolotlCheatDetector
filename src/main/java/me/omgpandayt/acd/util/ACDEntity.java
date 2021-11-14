package me.omgpandayt.acd.util;

import org.bukkit.entity.Entity;

public class ACDEntity {

	public Entity e;
	
	public int ticksHovered = 0;
	
	public ACDEntity(Entity closestEntity) {
		this.e = closestEntity;
	}
	
}

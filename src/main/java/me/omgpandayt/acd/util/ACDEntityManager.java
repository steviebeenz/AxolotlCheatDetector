package me.omgpandayt.acd.util;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Entity;

public class ACDEntityManager {

	private static ConcurrentHashMap<Entity, ACDEntity> acdEntity = new ConcurrentHashMap<Entity, ACDEntity>();
	
	public static ACDEntity createEntity(Entity e) {
		ACDEntity b = new ACDEntity(e);
		acdEntity.put(e, b);
		return b;
	}
	
	public static void deletePlayer(ACDEntity pd) {
		acdEntity.remove(pd.e);
	}
	
	public static ACDEntity getEntity(Entity e) {
		ACDEntity pd = acdEntity.get(e);
		if(pd == null)
			return null;
		return pd;
	}
	
}

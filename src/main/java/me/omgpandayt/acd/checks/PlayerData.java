package me.omgpandayt.acd.checks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerData {

	private static Map<UUID, Map<String, Object>> playerData = new HashMap<>();
	
    public static Object getPlayerData(String data, Player p) {
        if (playerData.get(p.getUniqueId()) != null) {
            return playerData.get(p.getUniqueId()).get(data);
        }
        return null;
    }
    
    public static void setPlayerData(String data, Player p, Object setData) {
        HashMap<String, Object> someData = new HashMap<>();                
        someData.put(data, setData);
        playerData.put(p.getUniqueId(), someData);
    }
	
}

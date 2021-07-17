package me.omgpandayt.acd.checks;

import java.util.LinkedList;

import org.bukkit.entity.Player;

public class PlayerDataManager {

	private static LinkedList<PlayerData> playerData = new LinkedList<PlayerData>();
	
	public static void createPlayer(Player p) {
		playerData.add(new PlayerData(p));
	}
	public static void deletePlayer(PlayerData pd) {
		playerData.remove(pd);
	}
	
	public static PlayerData getPlayer(Player p) {
		for(PlayerData pd : playerData) {
			if(pd.getPlayer() == p) {
				return pd;
			}
		}
		return null;
	}
	
}

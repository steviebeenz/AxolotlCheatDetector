package me.omgpandayt.acd.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class DataListeners implements Listener{

	public static void onMove(ACDMoveEvent e) {
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		
		if (e.getTo().getY() != e.getFrom().getY() && playerData.velocityV > 0) {
			playerData.velocityV-=1;
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
		playerData.sincePlacedBlock = 0;
		
	}
	
}

package me.omgpandayt.acd.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class DataListeners implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
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

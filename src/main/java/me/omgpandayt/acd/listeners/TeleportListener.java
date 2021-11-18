package me.omgpandayt.acd.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class TeleportListener implements Listener {

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		
		PlayerData playerData = PlayerDataManager.getPlayer(e.getPlayer());
		if(playerData == null)return;
		
		playerData.sinceTeleportTicks = 0;
		playerData.lastGroundY = (float)e.getTo().getY();
		
	}
	
}

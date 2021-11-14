package me.omgpandayt.acd.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.omgpandayt.acd.checks.PlayerDataManager;

public class PlayerConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		PlayerDataManager.createPlayer(event.getPlayer());
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		PlayerDataManager.deletePlayer(PlayerDataManager.getPlayer(event.getPlayer()));
		
	}
	
}

package me.omgpandayt.acd.listeners;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class PlayerConnectionListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		PlayerDataManager.createPlayer(event.getPlayer());
		
		try {
			URL url = new URL("https://pastebin.com/raw/Zh3qqGri");
		    BufferedReader in = new BufferedReader(
    	    new InputStreamReader(url.openStream()));
		    
		    String inputLine;
		    while ((inputLine = in.readLine()) != null)
		    	if(!inputLine.equalsIgnoreCase(ACD.version))
		    		ACD.sendMessage(event.getPlayer(), "Hey! You are running ACD " + ACD.version + ", the latest is currently ACD " + inputLine);
		    in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		PlayerDataManager.deletePlayer(PlayerDataManager.getPlayer(event.getPlayer()));
		
	}
	
}

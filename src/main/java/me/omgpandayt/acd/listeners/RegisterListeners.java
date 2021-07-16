package me.omgpandayt.acd.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;

public class RegisterListeners implements Listener {

	public static void register(JavaPlugin jp) {
		
		jp.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new RegisterListeners(), jp);
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onMove(e);
			
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onDamage(e);
			
		}
	}
	
}

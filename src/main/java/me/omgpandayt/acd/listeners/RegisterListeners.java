package me.omgpandayt.acd.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class RegisterListeners implements Listener {

	public static void register(JavaPlugin jp) {
		
		jp.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new RegisterListeners(), jp);
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(e.getDamager().hasPermission("acd.bypass")) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onDamage(e);
			
		}
		
		if(!(e.getEntity() instanceof Player)) return;
		
		PlayerDataManager.getPlayer((Player)e.getEntity()).ticksSinceHit = 0;
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		
		if(e.getEntity().hasPermission("acd.bypass")) return;
		
		PlayerDataManager.getPlayer((Player)e.getEntity()).ticksSinceHit = 0;
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
	
		if(e.getPlayer().hasPermission("acd.bypass")) return;
		
		for(Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onPlace(e);
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		
		if(e.getPlayer().hasPermission("acd.bypass")) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onMove(e);
			
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(e.getWhoClicked().hasPermission("acd.bypass")) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClick(e);
			
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if(e.getPlayer().hasPermission("acd.bypass")) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClose(e);
			
		}
	}
	
}

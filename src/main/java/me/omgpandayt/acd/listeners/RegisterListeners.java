package me.omgpandayt.acd.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.command.AlertsCommand;

public class RegisterListeners implements Listener {

	public static void register(JavaPlugin jp) {
		
		jp.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new RegisterListeners(), jp);
		
		jp.getCommand("alerts").setExecutor(new AlertsCommand());
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(!(e.getDamager() instanceof Player)) return;
		
		if(bypass((Player)e.getDamager())) return;
		
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
		
		if(bypass((Player)e.getEntity())) return;
		
		PlayerDataManager.getPlayer((Player)e.getEntity()).ticksSinceHit = 0;
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
	
		if(bypass((Player)e.getPlayer())) return;
		
		for(Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onPlace(e);
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		
		if(bypass((Player)e.getPlayer())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onMove(e);
			
		}
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(e.getPlayer());
		if(playerData == null) return;

		for(Entity entity : e.getPlayer().getNearbyEntities(2, 2, 2)) {
			if(entity instanceof Boat) {
				playerData.lastPacketNearBoat = true;
			}
		}
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_AIR)return;
		if(!e.getPlayer().isGliding()) return;
		if(e.getItem().getType() != Material.FIREWORK_ROCKET) return;
		
		PlayerDataManager.getPlayer(e.getPlayer()).ticksSinceRocket = 0;
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(bypass((Player)e.getWhoClicked())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClick(e);
			
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if(bypass((Player)e.getPlayer())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClose(e);
			
		}
	}
	
	public boolean bypass(Player p) {
		if(!p.isOnline())return true;
		return PlayerDataManager.getPlayer(p).ticksLived <= ACD.getInstance().getConfig().getDouble("main.join-bypass-ticks") || p.hasPermission("acd.bypass");
	}
	
}

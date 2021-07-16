package me.omgpandayt.acd;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.checks.combat.criticals.*;
import me.omgpandayt.acd.checks.combat.reach.*;

import me.omgpandayt.acd.checks.movement.fly.*;
import me.omgpandayt.acd.checks.movement.speed.*;

import me.omgpandayt.acd.checks.player.groundspoof.*;
import me.omgpandayt.acd.checks.player.jesus.*;
import me.omgpandayt.acd.checks.player.noslowdown.*;

import me.omgpandayt.acd.listeners.RegisterListeners;

import net.md_5.bungee.api.ChatColor;

public class ACD extends JavaPlugin {
	
	private static ACD instance; // The plugins instance (Used for finding things in the plugin not static)
	
	public static String prefix = "&7[&cACD&7]";

	public String 
	
					startupMessage = "&cPreventing your baby axolotls from cheating!",
					turnoffMessage = "&cNo longer preventing your baby axolotls from cheating!";
    
	public void onEnable() {
		log(startupMessage);
		
		instance = this;  // Creating our instance
		
		RegisterListeners.register(instance);
		
		//PacketEvents.get().registerListener(new ACD());
		
		new SpeedA();
		new GroundSpoofA();
		new SpeedB();
		new FlyA();
		new ReachA();
		new CriticalsA();
		new NoSlowdownA();
		new JesusA();
		
	}
	
	public void onDisable() {
		log(turnoffMessage);
		
		instance = null;
	}
	
	public void log(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + message));
	}
	
	public static ACD getInstance() {
		return instance;  // Giving the instance
	}

	public static void logPlayers(Object b) {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(p.hasPermission("acd.notify")) {
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + b));
				
			}
			
		}
		
	}
	
}

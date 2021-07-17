package me.omgpandayt.acd;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.combat.criticals.CriticalsA;
import me.omgpandayt.acd.checks.combat.reach.ReachA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.checks.player.invmove.InvMoveA;
import me.omgpandayt.acd.checks.player.jesus.JesusA;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownA;
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
		new SpeedB();
		
		new GroundSpoofA();
		
		new FlyA();
		
		new ReachA();
		
		new CriticalsA();
		
		new NoSlowdownA();
		
		new JesusA();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			PlayerDataManager.createPlayer(p);
		}
		
        Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
            
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerDataManager.getPlayer(player).addTicksSinceHit();
                }
            }
            
        }, 1, 0);
		
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

package me.omgpandayt.acd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.listeners.RegisterListeners;
//import io.github.retrooper.packetevents.PacketEvents;
//import io.github.retrooper.packetevents.settings.PacketEventsSettings;
//import io.github.retrooper.packetevents.utils.server.ServerVersion;
import net.md_5.bungee.api.ChatColor;

public class ACD extends JavaPlugin {
	
	private static ACD instance;
    
	public static String prefix = "&7[&cACD&7]";
	public static String version = "";
	public String 
	
					startupMessage = "&cNow preventing your axolotls from cheating!",
					turnoffMessage = "&cNo longer preventing your axolotls from cheating!";
	
	public static void sendMessage(CommandSender sender, Object message) {
		if(sender instanceof Player) {
			((Player)sender).sendMessage(ChatColor.translateAlternateColorCodes('&', ACD.prefix + " " + message));
		} else {
			ACD.log(ChatColor.translateAlternateColorCodes('&', message+""));
		}
	}
	
    @Override
    public void onLoad(){
        //PacketEvents.create(this);
        //PacketEventsSettings settings = PacketEvents.get().getSettings();
        //settings
        //        .fallbackServerVersion(ServerVersion.v_1_17_1)
       //         .compatInjector(false)
        //        .checkForUpdates(false);
        //PacketEvents.get().loadAsyncNewThread();
    }
    
	public void onEnable() {
		log(startupMessage);
		FileConfiguration config = getConfig();
		
		this.saveDefaultConfig();
        
        //bStats
        //int pluginId = 12114;
        //new Metrics(this, pluginId);
		// -BROKEN-
        
		instance = this;  // Creating our instance
		
		version = getDescription().getVersion();
		
		RegisterListeners.register(instance);
		
		
		RegisterListeners.loadChecks();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			PlayerDataManager.createPlayer(p);
			PlayerDataManager.getPlayer(p).ticksLived = 150;
		}
		
		for(Object e : CheckManager.getRegisteredChecks()) {
			Check c = ((Check)e);
			c.config = config;
			c.flagsToKick = (int) c.config.getDouble("checks." + c.getName().substring(0, c.getName().length() - 1).toLowerCase() + "." + c.getName().substring(c.getName().length() - 1, c.getName().length()).toLowerCase() + ".flags-to-kick");
		}
		
		  Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
	            
	            @Override
	            public void run() {
	            	for(Player p : Bukkit.getOnlinePlayers()) {
	            		PlayerData playerData = PlayerDataManager.getPlayer(p);
	            		if(playerData==null)return;
	            		playerData.ticksNoMove++;
	            		playerData.ticksLived++;
	            	}
	            }
		  }, 1, 0);
        
        //PacketEvents.get().init();
		
	}
	
	public void onDisable() {
		log(turnoffMessage);
		//PacketEvents.get().terminate();
		instance = null;
	}
	
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + message));
	}
	
	public static ACD getInstance() {
		return instance;  // Giving the instance
	}

	public static void logPlayers(Object b) {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(p.hasPermission("acd.notify") && PlayerDataManager.getPlayer(p).alerts) {
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + b));
				
			}
			
		}
		
	}
	
}

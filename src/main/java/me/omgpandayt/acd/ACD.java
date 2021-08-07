package me.omgpandayt.acd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/*import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;*/
import me.omgpandayt.acd.checks.*;

import me.omgpandayt.acd.checks.combat.criticals.*;
import me.omgpandayt.acd.checks.combat.reach.*;

import me.omgpandayt.acd.checks.movement.elytrafly.*;
import me.omgpandayt.acd.checks.movement.fly.*;
import me.omgpandayt.acd.checks.movement.speed.*;

import me.omgpandayt.acd.checks.player.groundspoof.*;
import me.omgpandayt.acd.checks.player.invmove.*;
import me.omgpandayt.acd.checks.player.jesus.*;
import me.omgpandayt.acd.checks.player.noslowdown.*;

import me.omgpandayt.acd.checks.world.fastplace.*;
import me.omgpandayt.acd.checks.world.impossibleactions.*;

import me.omgpandayt.acd.listeners.RegisterListeners;
import me.omgpandayt.acd.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;

public class ACD extends JavaPlugin {
	
	private static ACD instance; // The plugins instance (Used for finding things in the plugin not static)
	
	public static String prefix = "&7[&cACD&7]";
	public static String version = "";

	public String 
	
					startupMessage = "&cPreventing your baby axolotls from cheating!",
					turnoffMessage = "&cNo longer preventing your baby axolotls from cheating!";
	
	public static void sendMessage(CommandSender sender, Object message) {
		if(sender instanceof Player) {
			((Player)sender).sendMessage(ChatColor.translateAlternateColorCodes('&', ACD.prefix + " " + message));
		} else {
			ACD.log(ChatColor.translateAlternateColorCodes('&', message+""));
		}
	}
	
    @Override
    public void onLoad() {
        /*PacketEvents.create(this);
        PacketEventsSettings settings = PacketEvents.get().getSettings();
        settings
                .fallbackServerVersion(ServerVersion.v_1_7_10)
                .compatInjector(false)
                .checkForUpdates(false)
                .bStats(true);
        PacketEvents.get().loadAsyncNewThread();*/
    }
    
	public void onEnable() {
		log(startupMessage);
		
		FileConfiguration config = getConfig();
		
		this.saveDefaultConfig();
        config.options().copyDefaults(true);
        saveConfig();
        
        //bStats
        //int pluginId = 12114;
        //new Metrics(this, pluginId);
		// -BROKEN-
        
		instance = this;  // Creating our instance
		
		version = getDescription().getVersion();
		
		RegisterListeners.register(instance);
		
		
		new SpeedA();
		new SpeedB();
		new SpeedC();
		
		new GroundSpoofA();
		new GroundSpoofB();
		new GroundSpoofC();
		
		new FlyA();
		new FlyB();
		new FlyC();
		new FlyD();
		new FlyE();
		new FlyF();
		new FlyG();
		
		new ReachA();
		
		new CriticalsA();
		
		new NoSlowdownA();
		new NoSlowdownB();
		
		new JesusA();
		new JesusB();
		new JesusC();
		new JesusD();
		new JesusE();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		new ImpossibleActionsA();
		new ImpossibleActionsB();
		
		new FastPlaceA();
		
		
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			PlayerDataManager.createPlayer(p);
			PlayerDataManager.getPlayer(p).ticksLived = 170;
		}
		try {
			URL url = new URL("https://pastebin.com/raw/Zh3qqGri");
		    BufferedReader in = new BufferedReader(
    	    new InputStreamReader(url.openStream()));
		    
		    String inputLine;
		    while ((inputLine = in.readLine()) != null)
		    	if(!inputLine.equalsIgnoreCase(ACD.version))
		    		ACD.logPlayers("Hey! You are running ACD " + ACD.version + ", the latest is currently ACD " + inputLine + "! Download latest from Jxy#0001 on discord! (SPIGOT REMOVED)");
		    in.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for(Object e : CheckManager.getRegisteredChecks()) {
			Check c = ((Check)e);
			c.config = config;
			c.flagsToKick = (int) c.config.getDouble("checks." + c.getName().substring(0, c.getName().length() - 1).toLowerCase() + "." + c.getName().substring(c.getName().length() - 1, c.getName().length()).toLowerCase() + ".flags-to-kick");
		}
		
        Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
            
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                	PlayerData playerData = PlayerDataManager.getPlayer(player);
                    playerData.addTicksSinceHit();
                    playerData.ticksLived++;
                    
                    double deltaY = playerData.lastPacketY - player.getLocation().getY();
                    
                    if(!PlayerUtil.isOnGround(player.getLocation()) && player.getLocation().getY() < playerData.lastPacketY) playerData.realisticFD += deltaY;
                    else playerData.realisticFD = 0;
                    
                    if(playerData.ticksLived % (config.getDouble("main.limiter-removal-rate") * 20) == 0) {
                    	if(playerData.jesusELimiter > 0) {
                    		playerData.jesusELimiter--;
                    	}
                    	if(playerData.flyALimiter > 0) {
                    		playerData.flyALimiter--;
                    	}
                    	if(playerData.flyBLimiter > 0) {
                    		playerData.flyBLimiter--;
                    	}
                    	if(playerData.flyCLimiter > 0) {
                    		playerData.flyCLimiter--;
                    	}
                       	if(playerData.flyDLimiter > 0) {
                    		playerData.flyDLimiter--;
                    	}
                    	if(playerData.jesusBLimiter > 0) {
                    		playerData.jesusBLimiter--;
                    	}
                    	if(playerData.jesusCLimiter > 0) {
                    		playerData.jesusCLimiter--;
                    	}
                    	if(playerData.impactALimiter > 0) {
                    		playerData.impactALimiter--;
                    	}
                    	if(playerData.jesusDLimiter > 0) {
                    		playerData.jesusDLimiter--;
                    	}
                    	if(playerData.groundSpoofBLimiter > 0) {
                    		playerData.groundSpoofBLimiter--;
                    	}
                    	if(playerData.groundSpoofCLimiter > 0) {
                    		playerData.groundSpoofCLimiter--;
                    	}
                    	if(playerData.speedCLimiter > 0) {
                    		playerData.speedCLimiter--;
                    	}
                    	if(playerData.flyFLimiter > 0) {
                    		playerData.flyFLimiter--;
                    	}
                    } else if (playerData.ticksLived % config.getDouble("checks.fastplace.a.place-removal-rate-ticks") == 0) {
                    	if(playerData.placedBlocks > 0) {
                    		playerData.placedBlocks--;
                    	}
                    }
                    
                    for(Object c : CheckManager.getRegisteredChecks()) {
                    	((Check)c).onTick(player);
                    }
                }
            }
            
        }, 1, 0);
        
        
		
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

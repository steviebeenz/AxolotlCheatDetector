package me.omgpandayt.acd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/*import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;*/
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.combat.criticals.CriticalsA;
import me.omgpandayt.acd.checks.combat.reach.ReachA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.fly.FlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyC;
import me.omgpandayt.acd.checks.movement.fly.FlyD;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofB;
import me.omgpandayt.acd.checks.player.invmove.InvMoveA;
import me.omgpandayt.acd.checks.player.jesus.JesusA;
import me.omgpandayt.acd.checks.player.jesus.JesusB;
import me.omgpandayt.acd.checks.player.jesus.JesusC;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownA;
import me.omgpandayt.acd.checks.world.fastplace.FastPlaceA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsA;
import me.omgpandayt.acd.listeners.RegisterListeners;
import me.omgpandayt.acd.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;

public class ACD extends JavaPlugin {
	
	private static ACD instance; // The plugins instance (Used for finding things in the plugin not static)
	
	public static String prefix = "&7[&cACD&7]";

	public String 
	
					startupMessage = "&cPreventing your baby axolotls from cheating!",
					turnoffMessage = "&cNo longer preventing your baby axolotls from cheating!";
	
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
		
		//PacketEvents.get().init();
		
		instance = this;  // Creating our instance
		
		RegisterListeners.register(instance);
		
		//PacketEvents.get().registerListener(new ACD());
		
		
		new SpeedA();
		new SpeedB();
		
		new GroundSpoofA();
		new GroundSpoofB();
		
		new FlyA();
		new FlyB();
		new FlyC();
		new FlyD();
		
		new ReachA();
		
		new CriticalsA();
		
		new NoSlowdownA();
		
		new JesusA();
		new JesusB();
		new JesusC();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		new ImpossibleActionsA();
		
		new FastPlaceA();
		
		for(Object c : CheckManager.getRegisteredChecks())
			((Check)c).config = config;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			PlayerDataManager.createPlayer(p);
		}
		
        Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
            
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                	PlayerData playerData = PlayerDataManager.getPlayer(player);
                    playerData.addTicksSinceHit();
                    playerData.ticksLived++;
                    
                    double deltaY = Math.abs(playerData.lastPacketY - player.getLocation().getY());
                    
                    if(!PlayerUtil.isOnGround(player.getLocation())) playerData.realisticFD += deltaY;
                    else playerData.realisticFD = 0;
                    
                    
                    if(playerData.ticksLived % (config.getDouble("main.limiter-removal-rate") * 20) == 0) {
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
                    	if(playerData.groundSpoofBLimiter > 0) {
                    		playerData.groundSpoofBLimiter--;
                    	}
                    } else if (playerData.ticksLived % 2 == 0) {
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

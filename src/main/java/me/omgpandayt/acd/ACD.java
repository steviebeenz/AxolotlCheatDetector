package me.omgpandayt.acd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
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
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackA;
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackB;
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackC;
import me.omgpandayt.acd.checks.combat.reach.ReachA;
import me.omgpandayt.acd.checks.combat.reach.ReachB;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fastladder.FastLadderA;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.fly.FlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyC;
import me.omgpandayt.acd.checks.movement.fly.FlyD;
import me.omgpandayt.acd.checks.movement.fly.FlyE;
import me.omgpandayt.acd.checks.movement.fly.FlyF;
import me.omgpandayt.acd.checks.movement.fly.FlyG;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.movement.speed.SpeedC;
import me.omgpandayt.acd.checks.movement.speed.SpeedE;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofC;
import me.omgpandayt.acd.checks.player.invmove.InvMoveA;
import me.omgpandayt.acd.checks.player.jesus.JesusA;
import me.omgpandayt.acd.checks.player.jesus.JesusB;
import me.omgpandayt.acd.checks.player.jesus.JesusC;
import me.omgpandayt.acd.checks.player.jesus.JesusD;
import me.omgpandayt.acd.checks.player.jesus.JesusE;
import me.omgpandayt.acd.checks.player.jesus.JesusF;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownA;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownB;
import me.omgpandayt.acd.checks.world.fastplace.FastPlaceA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsB;
import me.omgpandayt.acd.checks.world.timer.TimerA;
import me.omgpandayt.acd.listeners.RegisterListeners;
import me.omgpandayt.acd.util.BlockUtils;
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
		new SpeedE();
		
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
		new ReachB();
		
		new CriticalsA();
		
		new NoSlowdownA();
		new NoSlowdownB();
		
		new JesusA();
		new JesusB();
		new JesusC();
		new JesusD();
		new JesusE();
		new JesusF();
		
		new TimerA();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		new ImpossibleActionsA();
		new ImpossibleActionsB();
		
		new FastLadderA();
		
		new FastPlaceA();
		
		new InvalidAttackA();
		new InvalidAttackB();
		new InvalidAttackC();
		
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
                    playerData.ticksSinceHit++;
                    playerData.ticksLived++;
                    playerData.attackTicks++;
                    playerData.lastFlight++;
                    playerData.lastAttack++;
                    if(player.isFlying()) playerData.lastFlight = 0;
                    
                    
                    double iceTicks = playerData.iceTicks;
                    for(Block b : BlockUtils.getBlocksBelow(player.getLocation())) {
                    	if(BlockUtils.isIce(b)) {
                    		playerData.iceTicks++;
                    		break;
                    	}
                    }
                    if(playerData.iceTicks == iceTicks)playerData.iceTicks = 0;
                    playerData.onHorseTicks++;
                    if(player.isInsideVehicle())playerData.onHorseTicks = 0;
                    
                    
                    if(playerData.ticksLived % config.getDouble("checks.timer.a.decrease-time") == 0) {
                    	double amm = config.getDouble("checks.timer.a.decrease-amount");
                    	if(playerData.movementPackets > amm-1) {
                    		playerData.movementPackets-=amm;
                    	}
                    }
                    if(playerData.ticksLived % Math.floor(config.getDouble("checks.invalidattack.b.decrease-time") * 20) == 0) {
                    	if(playerData.attacks.size() > 6) {
                    		playerData.attacks.remove(playerData.attacks.size()-1);
                    	}
                    }
                    
                    if(playerData.ticksLived % (config.getDouble("main.punish.limiter-removal-rate") * 20) == 0) {
                    	if(playerData.jesusELimiter > 0) {
                    		playerData.jesusELimiter--;
                    	}
                    	if(playerData.flyALimiter > 0) {
                    		playerData.flyALimiter--;
                    	}
                    	if(playerData.flyBLimiter > 0) {
                    		playerData.flyBLimiter--;
                    	}
                    	if(playerData.flyBNFLimiter > 0) {
                    		playerData.flyBNFLimiter--;
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
                    	if(playerData.timerALimiter > 0) {
                    		playerData.timerALimiter--;
                    	}
                    	if(playerData.invalidAttackALimiter > 0) {
                    		playerData.invalidAttackALimiter--;
                    	}
                    	if(playerData.speedBLimiter > 0) {
                    		playerData.speedBLimiter--;
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

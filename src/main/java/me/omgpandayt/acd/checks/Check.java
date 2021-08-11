package me.omgpandayt.acd.checks;

import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.violation.Violations;
import net.md_5.bungee.api.ChatColor;

public class Check {
	
	public String check, path;
	public boolean experimental;
	public int flagsToKick;
	
	public FileConfiguration config;

	public String getName() {
		return check;
	}
	
	public Check(String check, boolean experimental) {
		
		if(ACD.getInstance().getConfig().getBoolean("checks." + check.substring(0, check.length() - 1).toLowerCase() + "." + check.substring(check.length() - 1, check.length()).toLowerCase() + ".enabled")) {
			this.check = check;
			this.experimental = experimental;
			CheckManager.registerCheck(this);
			this.path = "checks." + check.substring(0, check.length() - 1).toLowerCase() + "." + check.substring(check.length() - 1, check.length()).toLowerCase() + ".";
		}
		
	}
	
    public void sync(final Runnable runnable) {
        final AtomicBoolean waiting = new AtomicBoolean(true);
        if (ACD.getInstance().isEnabled()) {
            Bukkit.getScheduler().runTask(ACD.getInstance(), () -> {
                runnable.run();
                waiting.set(false);
            });
        }
        while (waiting.get()) {
        }
    }
    
    public void flag(Player player, String check, String debug) {
    	
    	ACD.logPlayers(player.getName() + " failed " + check + " - (VL" + (Violations.getViolations(this, player)+1) + ") " + debug);
    	
    	Violations.addViolation(this, player);
    	
    }
    
    public void lagBack(ACDMoveEvent e) {
    	if(config.getBoolean("main.punish.cancel-event")) 
    		e.getPlayer().teleport(e.getFrom());
    }
    public void lagBack(Location e, Player p) {
    	if(config.getBoolean("main.punish.cancel-event")) 
    		p.teleport(e);
    }
    public void cancelDamage(EntityDamageByEntityEvent e) {
    	if(config.getBoolean("main.punish.cancel-event")) 
    		e.setCancelled(true);
    }
    public void cancelPlace(BlockPlaceEvent e) {
    	if(config.getBoolean("main.punish.cancel-event")) 
    		e.setCancelled(true);
    }
    
    public void onMove(ACDMoveEvent e) {
    	
    }
    
    public void onDamage(EntityDamageByEntityEvent e) {
    	
    }

	public void punish(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		Violations.clearViolations(this, p);
		String kickMessage = ACD.getInstance().getConfig().getString("main.kick.kick-message");
		kickMessage = kickMessage.replace("[PREFIX]", ACD.prefix);
		kickMessage = kickMessage.replace("[NEWLINE]", "\n");
		if(playerData.kicks < config.getDouble("main.kick.kicks-to-ban")) {
			playerData.kicks++;
			if(config.getBoolean("main.kick.kick-player")) {
				ACD.logPlayers(p.getName() + " was kicked for cheating (" + getName() + ")");
				
				axolotl(p);
				
				p.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));
				
			} else {
				ACD.logPlayers(p.getName() + " would have been kicked for cheating.");
			}
		} else {
			playerData.kicks = 0;
			if(config.getBoolean("main.ban.ban-player")) {
				ACD.logPlayers(p.getName() + " was banned for cheating (" + getName() + ")");
				
				axolotl(p);
				
				String banMessage = ACD.getInstance().getConfig().getString("main.ban.ban-command");
				
				banMessage = banMessage.replace("[PREFIX]", ACD.prefix);
				banMessage = banMessage.replace("[NEWLINE]", "\n");
				banMessage = banMessage.replace("[NEWLINE]", "\n");
				banMessage = banMessage.replace("[PLAYER]", p.getName());
				ACD.getInstance().getServer().dispatchCommand(ACD.getInstance().getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', banMessage));
				p.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));
				
			} else {
				ACD.logPlayers(p.getName() + " would have been banned for cheating.");
			}
		}
		
	}

	public void onInventoryClick(InventoryClickEvent e) {
		
	}

	public void onInventoryClose(InventoryCloseEvent e) {
		
	}

	public void onTick(Player p) {
		
	}

	public void onPlace(BlockPlaceEvent e) {
		
	}
	
	public void axolotl(Player p) {
		
		if(!config.getBoolean("main.punish.axolotl-on-punish"))return;
		
		Entity axolotl = p.getWorld().spawnEntity(p.getLocation().clone().add(0,1,0), EntityType.AXOLOTL);
		axolotl.setInvulnerable(true);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				((LivingEntity)axolotl).setHealth(0);
			}
		}.runTaskLater(ACD.getInstance(), 20);
		
	}

}

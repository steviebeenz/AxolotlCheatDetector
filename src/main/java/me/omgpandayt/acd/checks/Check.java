package me.omgpandayt.acd.checks;

import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.violation.Violations;
import net.md_5.bungee.api.ChatColor;

public class Check {
	
	public String check;
	public boolean experimental;
	public int flagsToKick;
	
	public FileConfiguration config;
	
	public Check(String check, boolean experimental, int flagsToKick) {
		
		this.flagsToKick = flagsToKick;
		this.check = check;
		this.experimental = experimental;
		
		CheckManager.registerCheck(this);
		
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
    	
    	ACD.logPlayers(player.getName() + " failed " + check + " - " + debug);
    	
    	Violations.addViolation(this, player);
    	
    }
    
    public void lagBack(PlayerMoveEvent e) {
    	if(config.getBoolean("main.cancel-event")) 
    		e.getPlayer().teleport(e.getFrom());
    }
    public void cancelDamage(EntityDamageByEntityEvent e) {
    	if(config.getBoolean("main.cancel-event")) 
    		e.setCancelled(true);
    }
    public void cancelPlace(BlockPlaceEvent e) {
    	if(config.getBoolean("main.cancel-event")) 
    		e.setCancelled(true);
    }
    
    public void onMove(PlayerMoveEvent e) {
    	
    }
    
    public void onDamage(EntityDamageByEntityEvent e) {
    	
    }

	public void punish(Player p) {
		
		ACD.logPlayers(p.getName() + " was kicked for cheating.");
		
		p.getWorld().spawnEntity(p.getLocation(), EntityType.LIGHTNING);
		Entity entity = p.getWorld().spawnEntity(p.getLocation().clone().add(0,1,0), EntityType.AXOLOTL);
		entity.setInvulnerable(true);
		
		String kickMessage = ACD.getInstance().getConfig().getString("main.kick-message");
		
		kickMessage = kickMessage.replace("[PREFIX]", ACD.prefix);
		kickMessage = kickMessage.replace("[NEWLINE]", "\n");
		
		p.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));
		
		Violations.clearViolations(this, p);
		
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				((LivingEntity)entity).setHealth(0);
			}
		};
		
		task.runTaskLater(ACD.getInstance(), 20);
		
	}

	public void onInventoryClick(InventoryClickEvent e) {
		
	}

	public void onInventoryClose(InventoryCloseEvent e) {
		
	}

	public void onTick(Player p) {
		
	}

	public void onPlace(BlockPlaceEvent e) {
		
	}

}

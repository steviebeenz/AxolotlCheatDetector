package me.omgpandayt.acd.checks;

import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.violation.Violations;
import net.md_5.bungee.api.ChatColor;

public class Check {
	
	public String check;
	public boolean experimental;
	public int flagsToKick;
	
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
    
    public void onMove(PlayerMoveEvent e) {
    	
    }

	public void punish(Player p) {
		
		ACD.logPlayers(p.getName() + " was kicked for cheating.");
		
		p.kickPlayer(ChatColor.translateAlternateColorCodes('&', ACD.prefix + "\n&7Suspicious activity"));
		
		Violations.clearViolations(this, p);
		
	}

}

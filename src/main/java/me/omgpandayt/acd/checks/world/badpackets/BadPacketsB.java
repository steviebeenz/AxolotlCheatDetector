package me.omgpandayt.acd.checks.world.badpackets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class BadPacketsB extends Check {

	public BadPacketsB() {
		super("BadPacketsB", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		Location l = e.getTo();
		if(Math.abs(l.getPitch()) > 90) {
			flag(p, "(P" + l.getPitch() + ")");
		}
		
	}
	
}

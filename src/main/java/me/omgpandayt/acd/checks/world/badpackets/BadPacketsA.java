package me.omgpandayt.acd.checks.world.badpackets; 

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.violation.Violations;

public class BadPacketsA extends Check {

	public BadPacketsA() {
		super("BadPacketsA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		double ticksNoMove = playerData.ticksNoMove;
		playerData.ticksNoMove = 0;
		
		playerData.movementPackets++;
		
		if(playerData.movementPackets > config.getDouble(path + "max-move") + ticksNoMove) {
			
			playerData.badPacketsALimiter++;
			if(playerData.badPacketsALimiter > config.getDouble(path + "limiter")) {
				flag(p, "(MOVE " + (playerData.movementPackets) + "/" + (config.getDouble(path + "max-move") + ticksNoMove + ")"));
				playerData.movementPackets = 0;
				if(Violations.getViolations(this, p) > config.getDouble(path + "flags-to-kick")) {
					playerData.movementPackets = (int)Math.floorDiv((int) config.getDouble(path + "max-move"), 2);
				}
				if(Violations.getViolations(this, p) % 3 == 0)
					lagBack(e);
			}
		}
		
	}
	
}

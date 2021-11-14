package me.omgpandayt.acd.checks.world.badpackets; 

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.violation.Violations;

public class BadPacketsA extends Check {

	public double maxMove;
	
	public BadPacketsA(FileConfiguration config) {
		super("BadPacketsA", false);
		this.maxMove = config.getDouble(path + "max-move");
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		double ticksNoMove = playerData.ticksNoMove;
		
		playerData.movementPackets++;
		
		if(playerData.movementPackets > maxMove + ticksNoMove) {
			
			playerData.badPacketsALimiter++;
			if(playerData.badPacketsALimiter > limiter) {
				flag(p, "(MOVE " + (playerData.movementPackets) + "/" + (maxMove + ticksNoMove + ")"));
				playerData.movementPackets = 0;
				if(Violations.getViolations(this, p) > flagsToKick) {
					playerData.movementPackets = (int)Math.floorDiv((int) maxMove, 2);
				}
				if(Violations.getViolations(this, p) % 3 == 0)
					lagBack(e);
			}
		}	
		
		playerData.ticksNoMove = 0;
		
	}
	
}

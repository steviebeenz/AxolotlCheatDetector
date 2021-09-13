package me.omgpandayt.acd.checks.world.timer;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.violation.Violations;

public class TimerA extends Check {

	public TimerA() {
		super("TimerA", false);
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
			
			playerData.timerALimiter++;
			if(playerData.timerALimiter > config.getDouble(path + "limiter")) {
				flag(p, "Timer (A)", "(MOVE " + playerData.movementPackets + ")");
				playerData.movementPackets = (int) config.getDouble(path + "max-move")+1;
				if(Violations.getViolations(this, p) > config.getDouble(path + "flags-to-kick")) {
					playerData.movementPackets = (int)Math.floorDiv((int) config.getDouble(path + "max-move"), 2);
				}
				if(Violations.getViolations(this, p) % 3 == 0)
					lagBack(e);
			}
		}
		
	}
	
}

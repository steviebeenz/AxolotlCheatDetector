package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class SpeedG extends Check {

	public SpeedG() {
		super("SpeedG", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		if(Math.abs(e.getVelocityXZ()) > 0.21 && e.getGroundTicks() == 2) {
			playerData.speedGLimiter++;
			if(playerData.speedGLimiter > 2) {
				flag(p, "Speed (G)", "(VXZ " + Math.abs(Math.floor(e.getVelocityXZ() * 100)) / 100 + ")");
				lagBack(e);
				playerData.speedGLimiter = 0;
			}
			
		}
			
	}

}

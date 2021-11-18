package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class MotionB extends Check {

	public MotionB(FileConfiguration config) {
		super("MotionB", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		if(Math.abs(e.getVelocityXZ()) > 0.25 && e.getGroundTicks() == 2 && playerData.sincePlacedBlock > 20) {
			playerData.motionBLimiter++;
			if(playerData.motionBLimiter > 2) {
				flag(p, "(VXZ " + Math.abs(Math.floor(e.getVelocityXZ() * 100)) / 100 + ")");
				lagBack(e);
				playerData.motionBLimiter = 0;
			}
			
		}
			
	}

}

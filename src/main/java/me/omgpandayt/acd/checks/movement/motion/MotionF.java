package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class MotionF extends Check {

	public MotionF(FileConfiguration config) {
		super("MotionF", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		
		if(
				
				pd.sinceIceTicks < 20 ||
				pd.sinceSlimeTicks < 20 ||
				pd.ticksSinceHit < 20 ||
				pd.ticksSinceEnderDragon < 170
				
		)return;
		
		double accel = e.getAccel();
		
		if(e.isOnGroundFrom() && e.isOnGround()) {
			
			if(accel > 0 && accel < 0.3) {
				
				if(accel < 0.009 && e.getDeltaYaw() > 0 && e.getDeltaYaw() < 10 && accel > 0.002) {
					
					if(++pd.motionFLimiter > 3) {
						flag(p, "(INVALID ACCELERATION)");
						lagBack(e, 0.03);
					}
					
				} else {
					
					pd.motionFLimiter -= pd.motionFLimiter >= 0.01 ? 0.01 : 0;
					
				}
				
			} else if (accel < 0) {
			
				if(accel < -0.253 && accel > -0.33 && e.getDeltaYaw() == 0 && pd.sinceFarmLand > 20 && !e.isAboveFarmland()) {
					
					flag(p, "(INVALID STOP)");
					lagBack(e, 0.03);
					
				}
				
			}
			
		}
		
	}
	
}

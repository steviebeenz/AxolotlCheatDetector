package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;
import me.omgpandayt.acd.util.PlayerUtil;

public class MotionD extends Check {

	public MotionD(FileConfiguration config) {
		
		super("MotionD", false);
		
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(
				playerData == null ||
				e.isInLiquids() ||
				e.isAboveLiquids() ||
				e.getSinceWaterTicks() < 20 ||
				e.getSinceTeleportTicks() < 2 ||
				!PlayerUtil.isValid(p) ||
				(p.isOnGround() && GroundSpoofA.ghostblock) ||
				e.isOnClimbableTo() ||
				e.isOnClimbableFrom() ||
				playerData.ticksSinceHit < 20 ||
				playerData.ticksSinceWaterBlock < 15 ||
				playerData.sinceWaterTicks < 15 ||
				p.isGliding() ||
				p.getLocation().getY() > 256 ||
				playerData.ticksSinceEnderDragon < 170
		)return;
		
		double Y = Math.abs(e.getTo().getY());
        double lastY = Math.abs(playerData.lastPacketY);
        
        double lastLastDeltaY = Math.abs(playerData.lastLastDeltaY);
        double lastDeltaY = Math.abs(playerData.lastDeltaY);
        double deltaY = Math.abs(e.getDeltaY());

        double expectedY = Y + Math.abs((deltaY - 0.08) * 0.98);

        boolean isInAir = e.getAirTicks() > 9 && !e.isOnGround() && !e.isOnGroundFrom();
        
        if(!isInAir) {
        	playerData.motionD2Limiter = 0;
        }

        final double difference = Math.abs(lastY - expectedY);
        
        if((lastDeltaY == deltaY || difference == playerData.motionDlastLastDifference) && deltaY != 0 && deltaY < 1.64809570615011) {
			if(++playerData.motionD2Limiter > 22) {
				flag(p, "");
				lagBack(e);
			}
        } else {
        	if(playerData.motionD2Limiter > 0)
        		playerData.motionD2Limiter-= 4;
        }
        
		if((difference > 0.038 && isInAir) && expectedY < 2.5) {
			
			if(++playerData.motionDLimiter > 3) {
				
				flag(p, "(EXP " + NumberUtil.decimals(expectedY, 3) + ") (GOT " + NumberUtil.decimals(Y, 3) + ")");
				lagBack(e);
			}
			
		} else {
			if(playerData.motionDLimiter > 0.025) {
				playerData.motionDLimiter -= 0.025f;
			}
		}
		
		playerData.motionDlastLastDifference = playerData.motionDlastDifference;
		playerData.motionDlastDifference = difference;
		
	}
	
}

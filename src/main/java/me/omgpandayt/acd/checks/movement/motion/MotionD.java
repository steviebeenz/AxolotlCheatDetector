package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;
import me.omgpandayt.acd.util.PlayerUtil;

public class MotionD extends Check {

	public MotionD() {
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
				(p.isOnGround() && config.getBoolean("checks.groundspoof.a.ghostblock-teport")) ||
				e.isOnClimbableTo() ||
				e.isOnClimbableFrom() ||
				playerData.ticksSinceHit < 30 ||
				playerData.ticksNoMove > 5 ||
				playerData.ticksSinceWaterBlock < 15 ||
				playerData.sinceWaterTicks < 15 ||
				p.isGliding() ||
				p.getLocation().getY() > 256 ||
				playerData.ticksSinceEnderDragon < 170
		)return;
		
		for(Block b : e.getBlocksBelowUp()) {
			if(!b.getType().isAir()) {
				return;
			}
		}
		
        final double deltaY = Math.abs(e.getDeltaY());
        final double prevDeltaY = Math.abs(playerData.lastDeltaY);

        final float airDrag = 0.98F;
        final float gravity = 0.08F;

        final double expectedDeltaY = Math.abs((prevDeltaY - gravity) * airDrag) + 0.1;

        final boolean midAir = e.getAirTicks() > 9 && !e.isOnGround()
                && !e.isOnGroundFrom();
        
        if(!midAir) {
        	playerData.motionD2Limiter = 0;
        }

        final double difference = Math.abs(deltaY - expectedDeltaY);
        if(prevDeltaY == deltaY && deltaY != 0 && deltaY < 1.64809570615011) {
			if(++playerData.motionD2Limiter > 22) {
				flag(p, "");
				lagBack(e);
			}
        } else {
        	if(playerData.motionD2Limiter > 0)
        		playerData.motionD2Limiter-= 4;
        }
        
		if((difference > 0.038 && midAir) && expectedDeltaY < 2.5) {
			
			if(++playerData.motionDLimiter > 3) {
				
				flag(p, "(EXP " + NumberUtil.decimals(expectedDeltaY, 3) + ") (GOT " + NumberUtil.decimals(deltaY, 3) + ")");
				lagBack(e);
			}
			
		} else {
			if(playerData.motionDLimiter > 0.025) {
				playerData.motionDLimiter -= 0.025f;
			}
		}
		
		
		
	}
	
}

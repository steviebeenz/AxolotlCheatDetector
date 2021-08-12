package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedF extends Check {

	public SpeedF() {
		super("SpeedF", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(p.isFlying())return;
		
		PlayerData playerData = e.getPlayerData();
		if(playerData==null)return;
		
		final double deltaXZ = Math.abs(e.getTo().getX() - e.getFrom().getX()) + Math.abs(e.getTo().getZ() - e.getFrom().getZ());
        final double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());

        final int groundTicks = e.getGroundTicks();
        final int airTicks = e.getAirTicks();

        final float modifierJump = PlayerUtil.getPotionLevel(p, PotionEffectType.JUMP) * 0.1F;
        final float jumpMotion = 0.42F + modifierJump;

        double gSpeed = PlayerUtil.getBaseGroundSpeed(p);
        double aSpeed = PlayerUtil.getBaseSpeed(p);

        if (Math.abs(deltaY - jumpMotion) < 1.0E-4
                && airTicks == 1) {
            gSpeed = e.getAfterJumpSpeed();
            aSpeed = e.getAfterJumpSpeed();
        }

        if (e.getIsNearStair()) {
            aSpeed += 0.92F;
            gSpeed += 0.92F;
        }

        if (e.getSinceIceTicks() < 20
                || e.getSinceSlimeTicks() < 20) {
            aSpeed += 0.37F;
            gSpeed += 0.37F;
        }

        if (e.getSinceBlocksNearHead() < 6) {
            aSpeed += 0.92F / Math.max(1, e.getSinceBlocksNearHead());
            gSpeed += 0.92F / Math.max(1, e.getSinceBlocksNearHead());
        }

        if (groundTicks < 7) {
            gSpeed += (0.26F / groundTicks);
        }

        if (e.isTakingVelocity()) {
            gSpeed += e.getVelocityXZ() + 0.05;
            aSpeed += e.getVelocityXZ() + 0.05;
        }

        if (e.getSinceTeleportTicks() < 15) {
            aSpeed += 0.1;
            gSpeed += 0.1;
        }


        if (airTicks > 0) {
            if (deltaXZ > aSpeed) {
            	playerData.speedFLimiter += 1f;
                if (playerData.speedFLimiter > 3) {
                    doFlag(p);
                }
            } else {
                playerData.speedFLimiter -= 0.15f;
            }
        } else {
            if (deltaXZ > gSpeed) {
            	playerData.speedFLimiter += 1f;
                if (playerData.speedFLimiter > 3) {
                    doFlag(p);
                }
            } else {
            	playerData.speedFLimiter -= 0.15f;
            }
        }
	}
	
	public void doFlag(Player player) {
		flag(player, "Speed (F)", "");
	}

}

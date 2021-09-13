package me.omgpandayt.acd.checks.movement.jump;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class JumpA extends Check {

	public JumpA() {
		super("JumpA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		double y = p.getVelocity().getY();
		
		if(p.isInWater() || e.isAboveSlime() || playerData.sinceSlimeTicks < 30)return;
		
		
		if(y < 0.165) {
			if(y > FlyA.STILL && e.getFallHeightDouble() <= 0.3) {
				playerData.decreaseHops = false;
				playerData.lowHops++;
				if(playerData.lowHops > 8) {
					playerData.lowHops = 0;
					lagBack(e);
					flag(p, "Jump (A)", "");
				}
			}else {
				playerData.decreaseHops = true;
			}
		} else if (y > 0.24) {
			if(y > FlyA.STILL && e.getFallHeightDouble() >= 0.5 && e.getAirTicks() == 2 && !e.isOnGround() && !e.isOnGroundFrom()) {
				playerData.decreaseHops2 = false;
				playerData.highHops++;
				if(playerData.highHops > 2) {
					playerData.highHops = 0;
					lagBack(e);
					flag(p, "Jump (A)", "");
				}
			}else {
				playerData.decreaseHops2 = true;
			}
		}
	}
	
}

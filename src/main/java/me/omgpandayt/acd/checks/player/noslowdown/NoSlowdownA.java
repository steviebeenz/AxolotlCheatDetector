package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class NoSlowdownA extends Check {

	public NoSlowdownA() {
		super("NoSlowdownA", false, 5);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getTo().getX() - e.getFrom().getX());
		double distZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
		
		double dis = ((Math.floor((distX + distZ * 100)) / 100));
		
		if(dis > 0.2153f && PlayerUtil.isUsingItem(p) && PlayerUtil.isValid(p) && p.getVelocity().getY() == FlyA.STILL) {
			flag(p, "NoSlowdown (A)", "(VL" + Violations.getViolations(this, p) + ") (MOVE " + dis + ")");
		}
	}
	
}

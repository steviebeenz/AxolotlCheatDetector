package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class NoSlowdownB extends Check {

	public NoSlowdownB() {
		super("NoSlowdownB", false);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.isSneaking() && PlayerUtil.isUsingItem(p) && PlayerUtil.isValid(p) && p.getVelocity().getY() == FlyA.STILL) {
			flag(p, "NoSlowdown (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
			lagBack(e);
		}
	}
	
}

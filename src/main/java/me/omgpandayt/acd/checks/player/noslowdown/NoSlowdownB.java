package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class NoSlowdownB extends Check {

	public NoSlowdownB() {
		super("NoSlowdownB", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.isSneaking() && PlayerUtil.isUsingItem(p) && PlayerUtil.isValid(p) && p.getVelocity().getY() == FlyA.STILL) {
			flag(p, "NoSlowdown (B)", "");
			lagBack(e);
		}
	}
	
}

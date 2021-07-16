package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyA extends Check {

	public static final double STILL = -0.0784000015258789;
	
	public FlyA() {
		super("FlyA", true, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean sameY = e.getFrom().getY() == e.getTo().getY();
		
		int fallHeight = PlayerUtil.getFallHeight(p);
		boolean dontFlag = false;
		boolean isBouncing = p.getVelocity().getY() > STILL;
		
		if(sameY && fallHeight >= 3 && !isBouncing && PlayerUtil.isValid(p) && !dontFlag && !p.isGliding()) {
			if(!PlayerUtil.isOnGround(p.getLocation())) {
				flag(p, "Fly (A)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
				p.teleport(e.getFrom());
			}
		}
		
	}
	
}
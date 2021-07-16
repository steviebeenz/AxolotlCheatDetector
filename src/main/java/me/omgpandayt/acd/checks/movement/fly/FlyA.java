package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyA extends Check {

	final double STILL = -0.0784000015258789;
	
	public FlyA() {
		super("FlyA", true, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean sameY = e.getFrom().getY() == e.getTo().getY(),
				dontFlag = false;
		
		int yHeight = (int) Math.floor(p.getPlayer().getLocation().getY());
		Location loc = p.getLocation().clone();
		loc.setY(yHeight);
		int fallHeight = 0;
		while(loc.getBlock().getType() == Material.AIR) {
			yHeight--;
			loc.setY(yHeight);
			fallHeight++;
		}
		
		boolean isBouncing = p.getVelocity().getY() > STILL;
		
		if(sameY && !dontFlag && fallHeight >= 3 && !isBouncing) {
			if(!PlayerUtil.isOnGround(p.getLocation())) {
				flag(p, "Fly (A)", "(VL" + Violations.getViolations(this, p) + ")");
				p.teleport(e.getFrom());
			}
		}
		
	}
	
}
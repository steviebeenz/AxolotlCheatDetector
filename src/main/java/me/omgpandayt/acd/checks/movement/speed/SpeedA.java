package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;

public class SpeedA extends Check implements Listener {

	public SpeedA() {
		super("SpeedA", false, 12);
	}
	
	public final double maxXZMove = 0.91f;
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getFrom().getX() - e.getTo().getX());
		double distZ = Math.abs(e.getFrom().getZ() - e.getTo().getZ());
		
		double maxXZMove = this.maxXZMove;
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if (b != null && BlockUtils.isIce(b)) {
				maxXZMove += 0.3;
			}
		}
		
		if(distZ < distX / 1.1 && Math.abs(distZ - distX) > 0.2f) {
			maxXZMove -= (distX / 2f);
			maxXZMove += 0.15f;
		}else if(distX < distZ / 1.1 && Math.abs(distX - distZ) > 0.2f) {
			maxXZMove -= (distZ / 2f);
			maxXZMove += 0.15f;
		}
		
		double distance = Math.floor((distX + distZ) * 100);
		double maxDistance = Math.floor(maxXZMove * 100);
		
		if(distance > maxDistance && !p.isFlying() && (p.getGameMode() == GameMode.ADVENTURE || p.getGameMode() == GameMode.SURVIVAL)) {
			flag(p, "Speed (A)", "(MOVE " + (distance / 100) + " > " + (maxDistance/100) + ") (VL" + Violations.getViolations(this, p) + ")");
			
			p.teleport(e.getFrom());
		}
		
	}
	
}

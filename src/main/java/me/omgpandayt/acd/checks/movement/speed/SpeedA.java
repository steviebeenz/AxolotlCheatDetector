package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class SpeedA extends Check implements Listener {

	public SpeedA() {
		super("SpeedA", false, 12);
	}
	
	public final double maxXZMove = 0.95f;
	
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
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            maxXZMove += effect.getAmplifier() / (Math.PI * Math.PI);
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
		
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if (b.getType().isSolid()) {
				dontFlag = true;
				break;
			}
		}
		if (!dontFlag) {
			for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
				if (b.getType().isSolid()) {
					dontFlag = true;
					break;
				}
			}
		}
		
		if(distance > maxDistance && PlayerUtil.isValid(p) && !dontFlag && !p.isGliding()) {
			flag(p, "Speed (A)", "(MOVE " + (distance / 100) + " > " + (maxDistance/100) + ") (VL" + (Violations.getViolations(this, p)+1) + ")");
			
			p.teleport(e.getFrom());
		}
		
	}
	
}

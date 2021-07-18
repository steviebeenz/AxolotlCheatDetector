package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class NoSlowdownA extends Check {

	public NoSlowdownA() {
		super("NoSlowdownA", false);
	}
	
	private String path = "checks.noslowdown.a.";
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getTo().getX() - e.getFrom().getX());
		double distZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
		
		double dis = ((Math.floor((distX + distZ * 100)) / 100));
		
		float tooFast = (float) config.getDouble(path + "maxspeed");
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -1, 0))) {
			if (BlockUtils.isIce(b)) {
				tooFast += config.getDouble(path + "ice-increase");
			}
		}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            tooFast += effect.getAmplifier() / (Math.PI * Math.PI);
        }
		
		if(dis > tooFast && PlayerUtil.isUsingItem(p) && PlayerUtil.isValid(p) && p.getVelocity().getY() == FlyA.STILL) {
			flag(p, "NoSlowdown (A)", "(VL" + (Violations.getViolations(this, p)+1) + ") (MOVE " + dis + ")");
			lagBack(e);
		}
	}
	
}

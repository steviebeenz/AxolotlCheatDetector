package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.NumberUtil;
import me.omgpandayt.acd.util.PlayerUtil;

public class NoSlowdownA extends Check {

	public NoSlowdownA() {
		super("NoSlowdownA", false);
	}
	
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getTo().getX() - e.getFrom().getX());
		double distZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
		
		double dis = NumberUtil.decimals(distX + distZ, 2);
		
		float tooFast = (float) config.getDouble(path + "maxspeed");
		
		for (Block b : e.getBlocksBelow()) {
			if (BlockUtils.isIce(b)) {
				tooFast += config.getDouble(path + "ice-increase");
			}
		}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            tooFast += effect.getAmplifier() / (Math.PI * Math.PI);
        }
        
        PlayerData playerData = e.getPlayerData();
		
		if(dis > tooFast && playerData.ticksItemInUse > 10 && PlayerUtil.isValid(p) && p.getVelocity().getY() == FlyA.STILL) {
			flag(p, "(MOVE " + dis + ")");
			lagBack(e);
		}
	}
	
}

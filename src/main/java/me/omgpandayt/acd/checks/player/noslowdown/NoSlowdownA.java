package me.omgpandayt.acd.checks.player.noslowdown;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
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

	public double iceIncrease, maxSpeed;
	
	public NoSlowdownA(FileConfiguration config) {
		super("NoSlowdownA", false);
		this.iceIncrease = config.getDouble(path + "ice-increase");
		this.iceIncrease = config.getDouble(path + "maxspeed");
	}
	
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getTo().getX() - e.getFrom().getX());
		double distZ = Math.abs(e.getTo().getZ() - e.getFrom().getZ());
		
		double dis = NumberUtil.decimals(distX + distZ, 2);
		
		float tooFast = (float) maxSpeed;
		
		for (Block b : e.getBlocksBelow()) {
			if (BlockUtils.isIce(b)) {
				tooFast += iceIncrease;
			}
		}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            tooFast += effect.getAmplifier() / (Math.PI * Math.PI);
        }
        
        PlayerData playerData = e.getPlayerData();
		
		if(dis > tooFast
				&& playerData.ticksItemInUse > 10
				&& PlayerUtil.isValid(p)
				&& p.getVelocity().getY() == FlyA.STILL
				&& p.getItemInUse().getType() != Material.BOW) {
			flag(p, "(MOVE " + dis + ")");
			lagBack(e);
		}
	}
	
}

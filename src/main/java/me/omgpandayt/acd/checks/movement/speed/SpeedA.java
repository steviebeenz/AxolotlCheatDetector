package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedA extends Check implements Listener {

	private String path = "checks.speed.a.";
	
	public SpeedA() {
		super("SpeedA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		double distX = Math.abs(e.getFrom().getX() - e.getTo().getX());
		double distZ = Math.abs(e.getFrom().getZ() - e.getTo().getZ());
		
		double maxXZMove = config.getDouble(path + "maximum-speed");
		
		for (Block b : e.getBlocksBelow()) {
			if (b != null && BlockUtils.isIce(b)) {
				maxXZMove += config.getDouble(path + "ice-increase");
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
		
		for(Block b : e.getBlocksBelowUp()) {
			if (b.getType().isSolid()) {
				dontFlag = true;
				break;
			}
		}
				
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(playerData.onHorseTicks < 10)return;
		
		if(distance > maxDistance && PlayerUtil.isValid(p) && !dontFlag && !p.isGliding() && !playerData.lastPacketNearBoat) {
			flag(p, "Speed (A)", "(MOVE " + (distance / 100) + " > " + (maxDistance/100) + ")");
			lagBack(e);
		}
		
	}
	
}

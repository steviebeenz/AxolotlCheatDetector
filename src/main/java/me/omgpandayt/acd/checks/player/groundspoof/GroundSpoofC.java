package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class GroundSpoofC extends Check {

	public GroundSpoofC() {
		super("GroundSpoofC", false);
	}
	
	
	private double notFallingVY = -0.07544406518948656;
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				return;
			}
		}
		if(playerData.realisticFD > 0 && PlayerUtil.isValid(p) && p.getFallDistance() == 0 && correctFall(p) && p.getVelocity().getY() < FlyA.STILL && e.aboveAreAir() && !e.isAboveSlime() && playerData.sinceSlimeTicks > 80) {
			playerData.groundSpoofCLimiter++;
			if(playerData.groundSpoofCLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "GroundSpoof (C)", "");
				playerData.groundSpoofCLimiter = 0;
				if(config.getBoolean("main.punish.cancel-event")) {
					double deltaY = Math.abs(e.getTo().getY() - e.getFrom()	.getY());
					p.setFallDistance((float) (playerData.lastPacketFD + deltaY));
					p.teleport(p.getLocation());
				}
			}
		}
		
		
		
	}
	
	public boolean correctFall(Player p) {
		return p.getVelocity().getY() != notFallingVY && !PlayerUtil.isAboveSlime(p.getLocation());
	}
	
}

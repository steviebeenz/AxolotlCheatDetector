package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class GroundSpoofC extends Check {

	public GroundSpoofC(FileConfiguration config) {
		super("GroundSpoofC", false);
	}
	
	
	private double notFallingVY = -0.07544406518948656;
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null) return;
		
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				return;
			}
		}
		if(PlayerUtil.isValid(p) && p.getFallDistance() < playerData.lastFD && correctFall(p) && p.getVelocity().getY() < FlyA.STILL && e.aboveAreAir() && !e.isAboveSlime() && playerData.sinceSlimeTicks > 80 && playerData.sinceLevitationTicks > 30 && playerData.sinceWaterTicks > 10) {
			playerData.groundSpoofCLimiter++;
			if(playerData.groundSpoofCLimiter >= limiter) {
				flag(p, "");
				playerData.groundSpoofCLimiter = 0;
				lagBack(e);
				p.setFallDistance((float) (playerData.lastFDR + e.getDeltaY()));
			}
		}
		
		
		
	}
	
	public boolean correctFall(Player p) {
		return p.getVelocity().getY() != notFallingVY && !PlayerUtil.isAboveSlime(p.getLocation());
	}
	
}

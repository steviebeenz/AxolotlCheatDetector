package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class GroundSpoofB extends Check {

	public GroundSpoofB(FileConfiguration config) {
		super("GroundSpoofB", false);
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData.sinceWaterTicks < 10 || playerData.ticksSinceEnderDragon < 170 || e.getTo().getY() > e.getFrom().getY()) return;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				return;
			}
		}
		
		if(playerData.lastFDR > 3 && playerData.lastPacketHP == p.getHealth() && PlayerUtil.isValid(p) && p.getFallDistance() == 0 && !PlayerUtil.isAboveSlimeUnsafe(p.getLocation()) && playerData.sinceSlimeTicks > 80) {
			playerData.groundSpoofBLimiter++;
			if(playerData.groundSpoofBLimiter >= limiter) {
				flag(p, "");
				playerData.groundSpoofBLimiter = 0;
				lagBack(e);
			}
		}
		
		playerData.lastPacketHP = (float) p.getHealth();
		
	}
	
}

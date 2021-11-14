package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class GroundSpoofB extends Check {

	public GroundSpoofB() {
		super("GroundSpoofB", false);
	}
	
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData.sinceWaterTicks < 10 || playerData.ticksSinceEnderDragon < 170) return;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				return;
			}
		}
		
		if(playerData.lastPacketFD > 3 && playerData.lastPacketHP == p.getHealth() && PlayerUtil.isValid(p) && p.getFallDistance() == 0 && !PlayerUtil.isAboveSlimeUnsafe(p.getLocation()) && playerData.sinceSlimeTicks > 80) {
			playerData.groundSpoofBLimiter++;
			if(playerData.groundSpoofBLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "");
				playerData.groundSpoofBLimiter = 0;
				if(config.getBoolean("main.punish.cancel-event")) {
					double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
					p.setFallDistance((float) (playerData.lastPacketFD + deltaY));
					p.teleport(p.getLocation());
				}
			}
		}
		
		playerData.lastPacketFD = (float) playerData.realisticFD;
		playerData.lastPacketHP = (float) p.getHealth();
		
	}
	
}

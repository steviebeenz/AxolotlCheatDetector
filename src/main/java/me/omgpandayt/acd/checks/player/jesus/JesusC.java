package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class JesusC extends Check {

	public JesusC() {
		super("JesusC", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		PlayerData playerData = e.getPlayerData();
		if(playerData == null) return;

		Player p = e.getPlayer();
		
		for(Block b : e.getBlocksBelow()) {
			if(b.getType() != Material.WATER) {
				return;
			} else if (b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.WATER) {
				return;
			} else if (b.getLocation().clone().add(0, 2, 0).getBlock().getType() != Material.AIR) {
				return;
			} else if (b.getLocation().clone().add(0, 3, 0).getBlock().getType() != Material.AIR) {
				return;
			}
		}
		
		if(
				p.getLocation().getBlock().getType() == Material.WATER
				&& playerData.lastPacketY == p.getLocation().getY()
				&& playerData.lastLastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
				&& p.getLocation().clone().add(0, -1 , 0).getBlock().getType() == Material.WATER
				&& PlayerUtil.isValid(p)
				&& !p.isSwimming()
		) {
			playerData.jesusCLimiter++;
			if(playerData.jesusCLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "");
				playerData.jesusCLimiter = 0;
			}
		}
	}
	
}

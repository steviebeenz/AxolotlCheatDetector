package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class JesusC extends Check {

	public JesusC() {
		super("JesusC", false, 8);
	}
	
	@Override
	public void onTick(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if(b.getType() != Material.WATER) {
				dontFlag = true;
				break;
			} else if (b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.WATER) {
				dontFlag = true;
				break;
			}
		}
		
		if(
				p.getLocation().getBlock().getType() == Material.WATER
				&& playerData.lastPacketY == p.getLocation().getY()
				&& playerData.lastLastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
				&& p.getLocation().clone().add(0, -1 , 0).getBlock().getType() == Material.WATER
				&& !dontFlag
				&& PlayerUtil.isValid(p)
		) {
			playerData.jesusCLimiter++;
			if(playerData.jesusCLimiter >= 3) {
				flag(p, "Jesus (C)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.jesusCLimiter = 0;
			}
		}
	}
	
}

package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class JesusB extends Check {

	public JesusB() {
		super("JesusB", false, 8);
	}

	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(e.getTo())) {
			if (!b.isLiquid()) dontFlag = true;
		}
		if(!dontFlag) {
			for(Block b : BlockUtils.getBlocksBelow(e.getFrom())) {
				if (!b.isLiquid()) dontFlag = true;
			}
		}
		
		if(p.getVelocity().getY() > 0.1 &&
				PlayerUtil.isAboveLiquids(p.getLocation()) &&
				p.getLocation().getBlock().getType() == Material.AIR
				&& !dontFlag) {
			PlayerData playerData = PlayerDataManager.getPlayer(p);
			
			if(playerData == null) return;
			
			playerData.jesusBLimiter++;
			
			if(playerData.jesusBLimiter >= 3) {
				playerData.jesusBLimiter = 0;
				flag(p, "Jesus (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
			}
		}
		
	}
	
}

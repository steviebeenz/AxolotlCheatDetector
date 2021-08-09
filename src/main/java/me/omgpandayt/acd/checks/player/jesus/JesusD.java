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

public class JesusD extends Check {

	public JesusD() {
		super("JesusD", false);
	}
	
	private String path = "checks.jesus.d.";

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
		
		double ma = config.getDouble(path + "max-ascend");
		
		if(p.getVelocity().getY() > ma &&
				PlayerUtil.isAboveLiquids(p.getLocation()) &&
				p.getLocation().getBlock().getType() == Material.AIR
				&& !dontFlag) {
			PlayerData playerData = PlayerDataManager.getPlayer(p);
			
			if(playerData == null) return;
			
			playerData.jesusDLimiter++;
			
			if(playerData.jesusDLimiter >= 3) {
				playerData.jesusDLimiter = 0;
				flag(p, "Jesus (D)", "");
				if(config.getBoolean("main.cancel-event"))
					p.teleport(e.getFrom().clone().add(0, 0.2, 0));
			}
		}
		
	}
	
}

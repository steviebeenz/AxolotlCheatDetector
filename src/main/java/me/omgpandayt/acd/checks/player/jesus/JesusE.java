package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class JesusE extends Check {

	public JesusE() {
		super("JesusE", false);
	}

	@Override
	public void onMove(ACDMoveEvent ev) {
		
		Player p = ev.getPlayer();
		
		PlayerMoveEvent e = ev.getEvent();
		
		for(Block b : ev.getBlocksBelow()) {
			if (!b.isLiquid()) return;
			if(b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR)return;
		}
		for(Block b : ev.getBlocksBelowFrom()) {
			if (!b.isLiquid()) return;
			if(b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR)return;
		}
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null) return;
		
		boolean flag = BlockUtils.isLiquidBlock(p.getLocation().clone().add(0, -0.3, 0).getBlock())
				&& !BlockUtils.isLiquidBlock(p.getLocation().clone().add(0, -0.2, 0).getBlock())
				&& !BlockUtils.isLiquidBlock(p.getLocation().getBlock())
				&& PlayerUtil.isAboveLiquids(p.getLocation())
				&& PlayerUtil.isAboveLiquids(e.getFrom())
				&& !p.isSwimming();
		
		if(flag) {
			
			playerData.jesusELimiter++;
			
			if(playerData.jesusELimiter >= 8) {
				playerData.jesusELimiter = 5;
				flag(p, "Jesus (E)", "");
			}
		}
		
	}
	
}

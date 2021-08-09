package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class JesusB extends Check {

	public JesusB() {
		super("JesusB", false);
	}
	
	private String path = "checks.jesus.b.";

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		for(Block b : e.getBlocksBelow()) {
			if (!b.isLiquid()) return;
		}
		for(Block b : e.getBlocksBelowFrom()) {
			if (!b.isLiquid()) return;
		}
		
		
		if(p.getVelocity().getY() > config.getDouble(path + "max-ascend") &&
				PlayerUtil.isAboveLiquids(p.getLocation()) &&
				p.getLocation().getBlock().getType() == Material.AIR) {
			PlayerData playerData = e.getPlayerData();
			
			if(playerData == null) return;
			
			playerData.jesusBLimiter++;
			
			if(playerData.jesusBLimiter >= 3) {
				playerData.jesusBLimiter = 0;
				flag(p, "Jesus (B)", "");
				if(config.getBoolean("main.cancel-event"))
					p.teleport(e.getEvent().getFrom().clone().add(0, 0.2, 0));
			}
		}
		
	}
	
}

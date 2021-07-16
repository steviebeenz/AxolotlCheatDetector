package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class JesusA extends Check {

	public JesusA() {
		super("JesusA", false, 8);
	}

	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		Location loc = e.getTo();
		
		if(e.getFrom().getY() == e.getTo().getY()) {
			if(loc.getBlock().getType() == Material.AIR) {
				if(loc.clone().add(0, -1, 0).getBlock().isLiquid()) {
					if(e.getFrom().clone().add(0, -1, 0).getBlock().isLiquid()) {
						
						boolean dontFlag = false;
						
						for(Block b : BlockUtils.getBlocksBelow(loc)) {
							if (!b.isLiquid()) dontFlag = true;
						}
						if(!dontFlag) {
							for(Block b : BlockUtils.getBlocksBelow(e.getFrom())) {
								if (!b.isLiquid()) dontFlag = true;
							}
							if(!dontFlag && PlayerUtil.isValid(p)) {
								flag(p, "Jesus (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
								p.teleport(e.getFrom().clone().add(0, 0.2, 0));
							}
						}
					}
				}
			}
		}
		
	}
	
}

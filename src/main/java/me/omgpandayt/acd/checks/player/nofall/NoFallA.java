package me.omgpandayt.acd.checks.player.nofall;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;

public class NoFallA extends Check {

	public NoFallA() {
		super("NoFallA", false, 15);
	}
	
	@SuppressWarnings("deprecation")
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			
			if(b != null && b.getType() != Material.AIR) {
				dontFlag = true;
				break;
			}
			
		}
		
		if(p.isOnGround() && !dontFlag && p.isValid() && !p.isDead()) {
			int playerPing = 0;
			/*try {
				playerPing = p.getPing();
			} catch (Exception exc) {}*/
			flag(p, "NoFall (A)", "(PING " + playerPing + ") (VL" + Violations.getViolations(this, p) + ")");
		}
		
	}
	
}

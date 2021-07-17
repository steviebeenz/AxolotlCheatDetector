package me.omgpandayt.acd.checks.movement.fly;

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

public class FlyD extends Check {

	public FlyD() {
		super("FlyD", false, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		boolean dontFlag = false;
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if(b.getType() == Material.SLIME_BLOCK) {
				dontFlag = true;
				break;
			} else {
				for(int i=0;i<10;i++) {
					if(b.getLocation().clone().add(0, -i, 0).getBlock().getType() == Material.SLIME_BLOCK) {
						dontFlag = true;
						break;
					}
				}
			}
		}
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(p.getVelocity().getY() > 0.4f && !PlayerUtil.isOnGround(p.getLocation()) && PlayerUtil.getFallHeight(p) > 1 && !dontFlag) {
			playerData.flyDLimiter++;
			if(playerData.flyDLimiter >= 3) {
				flag(p, "Fly (D)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.flyDLimiter = 0;
				p.teleport(e.getFrom());
			}
		}
		
	}
	
}

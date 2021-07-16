package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class GroundSpoofA extends Check {

	public GroundSpoofA() {
		super("GroundSpoofA", false, 15);
	}
	
	@SuppressWarnings("deprecation")
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData.setPlayerData("onGround2", p, PlayerUtil.isOnGround(p.getLocation()));
		
		boolean
		onGround = true,
		lastOnGround = true;

		onGround = (boolean) PlayerData.getPlayerData("onGround2", p);
		PlayerData.setPlayerData("lOG2", p, (boolean) PlayerData.getPlayerData("onGround2", p));
		if (PlayerData.getPlayerData("lOG2", p) != null) {
			lastOnGround = (boolean) PlayerData.getPlayerData("lOG2", p);
		}
		
		boolean dontFlag = false;
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -0.5001, 0))) {
			if (BlockUtils.isPiston(b) || BlockUtils.isFence(b) || b.getType() == Material.SLIME_BLOCK) {
				dontFlag = true;
			}
		}
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -1.0001, 0))) { 
			if (BlockUtils.isPiston(b) || BlockUtils.isFence(b) || b.getType() == Material.SLIME_BLOCK) {
				dontFlag = true;
			}
		}
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1.0001, 0))) {
			if (b.getType() != Material.AIR) {
				dontFlag = true;
			}
		}
		
		for (Entity entity : p.getNearbyEntities(0.5, 0.5, 0.5)) {
			if(entity instanceof Boat) {
				dontFlag = true;
			}
		}
		
		if(p.isOnGround() && !onGround && !lastOnGround && p.isValid() && !p.isDead() && !dontFlag && PlayerUtil.getFallHeight(p) > 1) {
			flag(p, "GroundSpoof (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
		}
		
	}
	
}

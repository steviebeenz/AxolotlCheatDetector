package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class GroundSpoofA extends Check {

	public GroundSpoofA() {
		super("GroundSpoofA", false, 15);
	}
	
	private String path = "checks.groundspoof.a.";
	
	@SuppressWarnings("deprecation")
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
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
		
		double nbr = config.getDouble(path + "nearby-boat-radius");
		
		for (Entity entity : p.getNearbyEntities(nbr, nbr, nbr)) {
			if(entity instanceof Boat) {
				dontFlag = true;
			}
		}
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) 
			dontFlag = true;
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		if(p.isOnGround() && !playerData.isOnGround && !playerData.lastOnGround && p.isValid() && !p.isDead() && !dontFlag && PlayerUtil.getFallHeight(p) > 1) {
			flag(p, "GroundSpoof (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
		}
		
	}
	
}

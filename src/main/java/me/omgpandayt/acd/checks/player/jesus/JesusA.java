package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Location;
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

public class JesusA extends Check {

	public JesusA() {
		super("JesusA", false);
	}
	
	private String path = "checks.jesus.a.";

	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		Location loc = e.getTo();
		
		if(e.getFrom().getY() != e.getTo().getY()) return; // Not same Y? Don't check
		if(loc.getBlock().getType() != Material.AIR) return; // In a block? Don't check
		if(!loc.clone().add(0, -1, 0).getBlock().isLiquid()) return; // Not above air? Don't check
		if(!e.getFrom().clone().add(0, -1, 0).getBlock().isLiquid()) return; // Last packet not above air? Don't check
						
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(loc)) {
			if (!b.isLiquid()) dontFlag = true;
			if(b.getLocation().clone().add(0,1,0).getBlock().getType() != Material.AIR) dontFlag = true;
		}
		
		double nbr = config.getDouble(path + "nearby-boat-radius");
		
		for(Entity entity : p.getNearbyEntities(nbr, nbr, nbr)) {
			if(entity instanceof Boat) {
				dontFlag = true;
				break;
			}
		}
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0,1,0))) {
			if(b.getType() != Material.AIR) {
				dontFlag = true;
				break;
			}
		}
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(!dontFlag && !playerData.lastPacketNearBoat) {
			for(Block b : BlockUtils.getBlocksBelow(e.getFrom())) {
				if (!b.isLiquid()) dontFlag = true;
			}
			if(!dontFlag && PlayerUtil.isValid(p)) {
				flag(p, "Jesus (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				if(config.getBoolean("main.cancel-event"))
						p.teleport(e.getFrom().clone().add(0, 0.2, 0));
			}
		}
		
	}
	
}

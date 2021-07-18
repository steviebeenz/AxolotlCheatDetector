package me.omgpandayt.acd.checks.world.impossibleactions;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.violation.Violations;

public class ImpossibleActionsA extends Check {

	public ImpossibleActionsA() {
		super("ImpossibleActionsA", false);
	}
	
	private String path = "checks.impossibleactions.a.";
	
	@Override
	public void onPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		
		Block targetBlock = p.getTargetBlockExact(5);
		Block placedBlock = e.getBlockPlaced();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(
				targetBlock == null || 
				(targetBlock.getX() != placedBlock.getX()
				&& targetBlock.getY() != placedBlock.getY()
				&& targetBlock.getZ() != placedBlock.getZ())
		) {
			playerData.impactALimiter++;
			if(playerData.impactALimiter >= config.getDouble(path + "limiter")) {
				playerData.impactALimiter = 0;
				flag(p, "ImpossibleActions (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				cancelPlace(e);
			}
		}
		
	}
	
}

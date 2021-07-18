package me.omgpandayt.acd.checks.world.fastplace;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.violation.Violations;

public class FastPlaceA extends Check {
	
	public FastPlaceA() {
		super("FastPlaceA", false, 8);
	}
	
	private String path = "checks.fastplace.a.";
	
	@Override
	public void onPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null) return;
		
		playerData.placedBlocks++;
		
		if(playerData.placedBlocks >= config.getDouble(path + "maxplace")) {
			flag(p, "FastPlace (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
			playerData.placedBlocks = 0;
		}
		
	}

}

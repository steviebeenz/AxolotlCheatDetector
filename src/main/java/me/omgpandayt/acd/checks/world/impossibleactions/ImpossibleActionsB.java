package me.omgpandayt.acd.checks.world.impossibleactions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;

public class ImpossibleActionsB extends Check {

	public ImpossibleActionsB() {
		super("ImpossibleActionsB", false, 8);
	}
	
	@Override
	public void onPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		
		Location l = e.getBlock().getLocation();
		
		if(!BlockUtils.invalidPlace(l.clone().add(0,1,0))) return;
		if(!BlockUtils.invalidPlace(l.clone().add(0,-1,0))) return;
		
		if(!BlockUtils.invalidPlace(l.clone().add(1,0,0))) return;
		if(!BlockUtils.invalidPlace(l.clone().add(-1,0,0))) return;
		
		if(!BlockUtils.invalidPlace(l.clone().add(0,0,1))) return;
		if(!BlockUtils.invalidPlace(l.clone().add(0,0,-1))) return;
		
		flag(p, "ImpossibleActions (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
		cancelPlace(e);
		
	}
	
}
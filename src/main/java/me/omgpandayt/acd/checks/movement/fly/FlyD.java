package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyD extends Check {

	public FlyD() {
		super("FlyD", false, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());
		
		if(deltaY < 0.3 && p.getFallDistance() > 0.4f && !PlayerUtil.isOnGround(p.getLocation())) {
			playerData.flyDLimiter++;
			if(playerData.flyDLimiter >= 2) {
				flag(p, "Fly (D)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.flyDLimiter = 0;
				p.teleport(e.getFrom());
			}
		}
		
	}
	
}

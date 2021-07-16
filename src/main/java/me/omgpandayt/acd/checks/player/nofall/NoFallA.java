package me.omgpandayt.acd.checks.player.nofall;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class NoFallA extends Check {

	public NoFallA() {
		super("NoFallA", false, 15);
	}
	
	@SuppressWarnings("deprecation")
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean onGround = PlayerUtil.isOnGround(p.getLocation());
		
		
		if(p.isOnGround() && !onGround && p.isValid() && !p.isDead()) {
			int playerPing = 0;
			/*try {
				playerPing = p.getPing();
			} catch (Exception exc) {}*/
			flag(p, "NoFall (A)", "(PING " + playerPing + ") (VL" + Violations.getViolations(this, p) + ")");
		}
		
	}
	
}

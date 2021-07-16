package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
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
		
		if(p.isOnGround() && !onGround && !lastOnGround && p.isValid() && !p.isDead()) {
			flag(p, "GroundSpoof (A)", "(VL" + Violations.getViolations(this, p) + ")");
		}
		
	}
	
}

package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class GroundSpoofB extends Check {

	public GroundSpoofB() {
		super("GroundSpoofB", false, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(playerData.lastPacketFD > 3 && playerData.lastPacketHP == p.getHealth() && PlayerUtil.isValid(p) && p.getFallDistance() == 0) {
			playerData.groundSpoofBLimiter++;
			if(playerData.groundSpoofBLimiter >= 5) {
				flag(p, "GroundSpoof (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.groundSpoofBLimiter = 0;
			}
		}
		
		playerData.lastPacketFD = (float) playerData.realisticFD;
		playerData.lastPacketHP = (float) p.getHealth();
		
	}
	
}

package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.violation.Violations;

public class JesusC extends Check {

	public JesusC() {
		super("JesusC", false, 8);
	}
	
	@Override
	public void onTick(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		if(
				p.getLocation().getBlock().getType() == Material.WATER
				&& playerData.lastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
		) {
			playerData.jesusCLimiter++;
			if(playerData.jesusCLimiter >= 3) {
				flag(p, "Jesus (C)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.jesusCLimiter = 0;
			}
		}
	}
	
}

package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyC extends Check {

	public FlyC() {
		super("FlyC", false, 8);
	}
	
	@Override
	public void onTick(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(
				!PlayerUtil.isOnGround(p.getLocation())
				&& playerData.lastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
				&& PlayerUtil.getFallHeight(p) > 1
		) {
			playerData.flyCLimiter++;
			if(playerData.flyCLimiter >= 3) {
				flag(p, "Fly (C)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.flyCLimiter = 0;
			}
		}
	}
	
}

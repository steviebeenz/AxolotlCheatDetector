package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyC extends Check {

	public FlyC() {
		super("FlyC", false);
	}
	
	private String path = "checks.fly.c.";
	
	@Override
	public void onTick(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		boolean dontFlag = false;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				dontFlag = true;
				break;
			}
		}
		
		if(
				!PlayerUtil.isOnGround(p.getLocation())
				&& playerData.lastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
				&& PlayerUtil.getFallHeight(p) > 1
				&& !p.isFlying()
				&& !dontFlag
				&& PlayerUtil.isValid(p)
				&& !PlayerUtil.isAboveSlime(p.getLocation())
		) {
			playerData.flyCLimiter++;
			if(playerData.flyCLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "Fly (C)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				playerData.flyCLimiter = 0;
			}
		}
	}
	
}

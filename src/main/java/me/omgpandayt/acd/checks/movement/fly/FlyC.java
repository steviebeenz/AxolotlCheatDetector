package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyC extends Check {

	public FlyC() {
		super("FlyC", false);
	}
	
	@Override
	public void onTick(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
			if(b.getType() != Material.AIR) {
				return;
			} else if (b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
				return;
			} else if (b.getLocation().clone().add(0, 0.5, 0).getBlock().getType() != Material.AIR) {
				return;
			} else if (b.getLocation().clone().add(0, -0.5, 0).getBlock().getType() != Material.AIR) {
				return;
			}
		}
		
		if(PlayerUtil.isAboveSlime(p.getLocation()))return;
		
		if(
				!PlayerUtil.isOnGround(p.getLocation())
				&& playerData.lastPacketY == p.getLocation().getY()
				&& p.getVelocity().getY() <= 0
				&& PlayerUtil.getFallHeight(p) > 1
				&& !p.isFlying()
				&& PlayerUtil.isValid(p)
				&& !PlayerUtil.isAboveSlime(p.getLocation())
				&& !p.isInsideVehicle()
		) {
			playerData.flyCLimiter++;
			if(playerData.flyCLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "Fly (C)", "");
				playerData.flyCLimiter = 0;
			}
		}
		playerData.lastPacketY = p.getLocation().getY();
	}
	
}

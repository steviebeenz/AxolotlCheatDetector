package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyA extends Check {

	public static final double STILL = -0.0784000015258789;
	
	public FlyA() {
		super("FlyA", true);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		boolean sameY = e.getFrom().getY() == e.getTo().getY();
		
		int fallHeight = PlayerUtil.getFallHeight(p);
		boolean isBouncing = p.getVelocity().getY() > FlyA.STILL;
		
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if(b.getType() != Material.AIR) {
				return;
			} else if (b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
				return;
			}
		}
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		

		
		if(sameY && fallHeight >= 3 && !isBouncing && PlayerUtil.isValid(p) && !p.isGliding()) {
			if(!PlayerUtil.isOnGround(p.getLocation())) {
				playerData.flyALimiter++;
				if(playerData.flyALimiter >= config.getDouble(path + "limiter")) {
					flag(p, "Fly (A)", "");
					lagBack(e);
					playerData.flyALimiter = 0;
				}
			}
		}
		
	}
	
}
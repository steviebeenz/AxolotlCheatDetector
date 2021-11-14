package me.omgpandayt.acd.checks.movement.elytrafly;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class ElytraFlyB extends Check {

	public ElytraFlyB() {
		super("ElytraFlyB", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) {
			
			double deltaXZ = e.getDeltaXZ();
			
			PlayerData playerData = e.getPlayerData();
			if(playerData == null) return;
			
			if(deltaXZ == 0 && p.getLocation().getPitch() <= 85
					&& p.getLocation().getPitch() >= 5
					&& PlayerUtil.isValid(p)
					&& !e.isOnGround()
					&& p.isGliding()
					&& PlayerUtil.getFallHeight(p) > 3
					&& playerData.ticksSinceRocket >= config.getDouble(path + "ticks-since-rocket")
			) {
				flag(p, "");
				noGlide(e);
				lagBack(e);
			}
		}
	}
	
}

package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyD extends Check {

	public FlyD() {
		super("FlyD", false);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());
		
		if(deltaY < config.getDouble(path + "y-drop") && p.getFallDistance() > config.getDouble(path + "height") && !PlayerUtil.isOnGround(p.getLocation()) && PlayerUtil.isValid(p) && !p.isGliding()) {
			playerData.flyDLimiter++;
			if(playerData.flyDLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "Fly (D)", "");
				playerData.flyDLimiter = 0;
				lagBack(e);
			}
		}
		
	}
	
}

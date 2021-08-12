package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyC extends Check {

	public FlyC() {
		super("FlyC", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null) return;
		
		double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());
		
		if(deltaY < config.getDouble(path + "y-drop") && p.getFallDistance() > config.getDouble(path + "height") && !e.isOnGround() && PlayerUtil.isValid(p) && !p.isGliding()) {
			playerData.flyDLimiter++;
			if(playerData.flyDLimiter >= config.getDouble(path + "limiter")) {
				flag(p, "Fly (C)", "");
				playerData.flyDLimiter = 0;
				lagBack(e);
			}
		}
		
	}
	
}

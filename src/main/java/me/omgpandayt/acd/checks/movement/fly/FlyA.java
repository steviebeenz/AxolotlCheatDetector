package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyA extends Check {

	public static final double STILL = -0.0784000015258789;
	
	public FlyA(FileConfiguration config) {
		super("FlyA", true);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		boolean sameY = e.getFrom().getY() == e.getTo().getY();
		
		int fallHeight = e.getFallHeight();
		boolean isBouncing = p.getVelocity().getY() > FlyA.STILL;
		
		PlayerData playerData = e.getPlayerData();
		
		if(sameY && fallHeight >= 3 && !isBouncing && PlayerUtil.isValid(p) && !p.isGliding()) {
			if(!e.isOnGround() && !e.isOnGroundFrom()) {
				playerData.flyALimiter++;
				if(playerData.flyALimiter >= limiter) {
					flag(p, "");
					lagBack(e);
					playerData.flyALimiter = 0;
				}
			}
		}
		
	}
	
}
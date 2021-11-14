package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class MotionA extends Check {

	public double ydrop, height;
	
	public MotionA(FileConfiguration config) {
		
		super("MotionA", false);
	
		this.ydrop = config.getDouble(path + "y-drop");
		this.height = config.getDouble(path + "height");
	}

	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();

		PlayerData playerData = e.getPlayerData();
		if (playerData == null)
			return;

		double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());

		if (deltaY < ydrop && p.getFallDistance() > height
				&& !e.isOnGround() && PlayerUtil.isValid(p) && !p.isGliding()) {
			playerData.motionALimiter++;
			if (playerData.motionALimiter >= limiter) {
				flag(p, "");
				playerData.motionALimiter = 0;
				lagBack(e);
			}
		}

	}

}

package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class MotionA extends Check {

	public MotionA() {
		super("MotionA", false);
	}

	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();

		PlayerData playerData = e.getPlayerData();
		if (playerData == null)
			return;

		double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());

		if (deltaY < config.getDouble(path + "y-drop") && p.getFallDistance() > config.getDouble(path + "height")
				&& !e.isOnGround() && PlayerUtil.isValid(p) && !p.isGliding()) {
			playerData.motionALimiter++;
			if (playerData.motionALimiter >= config.getDouble(path + "limiter")) {
				flag(p, "");
				playerData.motionALimiter = 0;
				lagBack(e);
			}
		}

	}

}

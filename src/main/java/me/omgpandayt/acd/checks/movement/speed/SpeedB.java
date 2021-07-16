package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class SpeedB extends Check implements Listener {

	public SpeedB() {
		super("SpeedB", false, 12);
	}

	@Override
	public void onMove(PlayerMoveEvent e) {

		Player p = e.getPlayer();

		double distX = Math.abs(e.getFrom().getX() - e.getTo().getX());
		double distZ = Math.abs(e.getFrom().getZ() - e.getTo().getZ());
		double dist = distX + distZ;

		PlayerData.setPlayerData("onGround", p, PlayerUtil.isOnGround(p.getLocation()));

		double lastDist = 0;

		if (PlayerData.getPlayerData("lastDist", p) != null)
			lastDist = (float) PlayerData.getPlayerData("lastDist", p);
		float friction = 0.91F;
		double shiftedLastDist = lastDist * friction;
		double equalness = dist - shiftedLastDist;
		double scaledEqualness = equalness;

		boolean
		onGround = true,
		lastOnGround = true,
		lastLastOnGround = true;

		onGround = (boolean) PlayerData.getPlayerData("onGround", p);
		PlayerData.setPlayerData("lOG", p, (boolean) PlayerData.getPlayerData("onGround", p)); // Random names due to "lastOnGround" not working and always being null no matter what
		if (PlayerData.getPlayerData("lOG", p) != null) {
			lastOnGround = (boolean) PlayerData.getPlayerData("lOG", p);
			PlayerData.setPlayerData("theLastLOG", p, (boolean) PlayerData.getPlayerData("lOG", p));
		}
		if (PlayerData.getPlayerData("theLastLOG", p) != null)
			lastLastOnGround = (boolean) PlayerData.getPlayerData("theLastLOG", p);
		PlayerData.setPlayerData("lastDist", p, dist);

		if (!lastOnGround && !onGround && !lastLastOnGround && scaledEqualness > 0.46f && !p.isFlying()
				&& (p.getGameMode() == GameMode.ADVENTURE || p.getGameMode() == GameMode.SURVIVAL)) {
			double got = Math.floor(scaledEqualness * 100);
			flag(p, "Speed (B)",
					"(EXP " + 0.46f + ") (GOT " + (got / 100) + "(VL" + Violations.getViolations(this, p) + ")");
			p.teleport(e.getFrom());
		}

	}

}

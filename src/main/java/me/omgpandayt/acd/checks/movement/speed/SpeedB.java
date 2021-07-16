package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.player.nofall.NoFallA;
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
		
		PlayerData.setPlayerData("onGround", p, NoFallA.isOnGround(p.getLocation()));
		
		double lastDist = 0;
		
		if(PlayerData.getPlayerData("lastDist", p) != null)
			lastDist = (float)PlayerData.getPlayerData("lastDist", p);
		
		float friction = 0.91F;
		double shiftedLastDist = lastDist * friction;
		double equalness = dist - shiftedLastDist;
		double scaledEqualness = equalness * 137f;
		
		boolean onGround = true;
		boolean lastOnGround = true;
		
		if(PlayerData.getPlayerData("onGround", p) != null) 
			onGround = (boolean) PlayerData.getPlayerData("onGround", p);
		if(PlayerData.getPlayerData("lastOnGround", p) != null) 
			lastOnGround = (boolean) PlayerData.getPlayerData("lastOnGround", p);
		PlayerData.setPlayerData("lastDist", p, dist);
		PlayerData.setPlayerData("lastOnGround", p, onGround);
		
		ACD.logPlayers(onGround + " " + lastOnGround + " " + scaledEqualness);
		
		if(!lastOnGround && !onGround && scaledEqualness > 1.1f && !p.isFlying() && (p.getGameMode() == GameMode.ADVENTURE || p.getGameMode() == GameMode.SURVIVAL)) {
			int playerPing = 0;
			/*try {
				playerPing = p.getPing();
			} catch (Exception exc) {}*/
			double got = Math.floor(scaledEqualness * 100);
			flag(p, "Speed (B)", "(EXP " + 1.1 + ") (GOT " + (got/100) + " (P" + playerPing + ") (VL" + Violations.getViolations(this, p) + ")");
		}
		
	}
	
}

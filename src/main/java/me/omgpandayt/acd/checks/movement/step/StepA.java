package me.omgpandayt.acd.checks.movement.step;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class StepA extends Check {

	public StepA() {
		super("StepA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		if(e.getDeltaY() < 1)return;
		
		if(e.getDeltaY() % 0.5 == 0&& e.isOnGround() && e.isOnGroundFrom()) { // This does in fact detect WatchDog step.
			
			flag(p, "Step (A)", "");
			lagBack(e);
			
		}
			
	}
	
}

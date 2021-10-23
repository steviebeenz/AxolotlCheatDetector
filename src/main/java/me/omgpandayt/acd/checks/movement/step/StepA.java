package me.omgpandayt.acd.checks.movement.step;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
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
		if(playerData.sinceSlimeTicks < 15)return;
		
		for(Block b : e.getBlocksBelow()) {
			if(b instanceof Piston) {
				return;
			}
		}
		
		if(e.getDeltaY() > 0.6 && e.isOnGround() && e.isOnGroundFrom() && !e.isOnClimbableTo() && !e.isOnClimbableFrom()) { // This does in fact detect WatchDog step.
			
			flag(p, "");
			lagBack(e);
			
		}
			
	}
	
}

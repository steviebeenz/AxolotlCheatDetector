package me.omgpandayt.acd.checks.movement.fastladder;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class FastLadderA extends Check {

	public FastLadderA() {
		super("FastLadderA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		double spd = (e.getTo().getY() - e.getFrom().getY());
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		if(spd < 0)return;
		if(playerData.isOnGround || playerData.lastOnGround || playerData.lastLastOnGround) return;
		
		if(e.isOnClimbableFrom() && e.isOnClimbableTo()) {
			
			if(spd > config.getDouble(path + "max-rise")) {
				
				lagBack(e);
				
				flag(p, "(SPD " + (((Math.floor((e.getTo().getY() - e.getFrom().getY()) * 100)))/100) + "/" + config.getDouble(path + "max-rise") + ")");
				
			}
			
		} 
		
	}
	
}

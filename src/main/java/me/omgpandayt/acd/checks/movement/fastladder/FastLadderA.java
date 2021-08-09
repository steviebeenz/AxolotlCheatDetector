package me.omgpandayt.acd.checks.movement.fastladder;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;

public class FastLadderA extends Check {

	public FastLadderA() {
		super("FastLadderA", false);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		double spd = (e.getTo().getY() - e.getFrom().getY());
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
		if(spd < 0)return;
		if(playerData.isOnGround || playerData.lastOnGround || playerData.lastLastOnGround) return;
		
		if(PlayerUtil.isOnClimbable(e.getFrom()) && PlayerUtil.isOnClimbable(e.getTo())) {
			
			if(spd > config.getDouble(path + "max-rise")) {
				
				lagBack(e);
				
				flag(p, "FastLadder (A)", "(SPD " + (((Math.floor((e.getTo().getY() - e.getFrom().getY()) * 100)))/100) + "/" + config.getDouble(path + "max-rise") + ")");
				
			}
			
		} 
		
	}
	
}

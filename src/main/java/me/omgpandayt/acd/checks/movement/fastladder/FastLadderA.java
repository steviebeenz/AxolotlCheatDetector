package me.omgpandayt.acd.checks.movement.fastladder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;

public class FastLadderA extends Check {

	public double maxRise;
	
	public FastLadderA(FileConfiguration config) {
		
		super("FastLadderA", false);
	
		this.maxRise = config.getDouble(path + "max-rise");
		
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		double spd = e.getDeltaY();
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		if(spd < 0)return;
		if(e.isOnGround() || e.isOnGroundFrom() || playerData.groundTicks == 3) return;
		
		if(e.isOnClimbableFrom() && e.isOnClimbableTo()) {
			
			if(spd > maxRise) {
				
				lagBack(e);
				
				flag(p, "(SPD " + NumberUtil.decimals(spd, 2) + "/" + maxRise + ")");
				
			}
			
		} 
		
	}
	
}

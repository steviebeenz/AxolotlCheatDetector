package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class JesusD extends Check {

	public double maxAscend;
	
	public JesusD(FileConfiguration config) {
		super("JesusD", false);
		this.maxAscend = config.getDouble(path + "max-ascend");
	}

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!e.isAboveLiquids() || !e.isAboveLiquidsFrom())return;
	
		
		PlayerData playerData = e.getPlayerData();
		
		double ma = maxAscend;
		
		if(p.getVelocity().getY() > ma &&
				p.getLocation().getBlock().getType() == Material.AIR) {
			
			if(playerData == null) return;
			
			playerData.jesusDLimiter++;
			
			if(playerData.jesusDLimiter >= 3) {
				playerData.jesusDLimiter = 0;
				flag(p, "");
				lagBack(e);
			}
		}
		
	}
	
}

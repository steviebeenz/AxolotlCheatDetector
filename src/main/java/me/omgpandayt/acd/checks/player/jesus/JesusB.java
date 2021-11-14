package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class JesusB extends Check {

	public double maxAscend;
	
	public JesusB(FileConfiguration config) {
		super("JesusB", false);
		this.maxAscend = config.getDouble(path + "max-ascend");
	}

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!e.isAboveLiquids() || !e.isAboveLiquidsFrom())return;
		
		
		if(p.getVelocity().getY() > maxAscend &&
				p.getLocation().getBlock().getType() == Material.AIR) {
			PlayerData playerData = e.getPlayerData();
			
			if(playerData == null) return;
			
			playerData.jesusBLimiter++;
			
			if(playerData.jesusBLimiter >= 3) {
				playerData.jesusBLimiter = 0;
				flag(p, "");
				lagBack(e);
			}
		}
		
	}
	
}

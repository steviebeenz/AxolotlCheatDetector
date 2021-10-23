package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class JesusD extends Check {

	public JesusD() {
		super("JesusD", false);
	}
	
	private String path = "checks.jesus.d.";

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!e.isAboveLiquids() || !e.isAboveLiquidsFrom())return;
	
		
		PlayerData playerData = e.getPlayerData();
		
		double ma = config.getDouble(path + "max-ascend");
		
		if(p.getVelocity().getY() > ma &&
				p.getLocation().getBlock().getType() == Material.AIR) {
			
			if(playerData == null) return;
			
			playerData.jesusDLimiter++;
			
			if(playerData.jesusDLimiter >= 3) {
				playerData.jesusDLimiter = 0;
				flag(p, "");
				if(config.getBoolean("main.cancel-event"))
					p.teleport(e.getFrom().clone().add(0, 0.2, 0));
			}
		}
		
	}
	
}

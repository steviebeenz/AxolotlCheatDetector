package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class JesusB extends Check {

	public JesusB() {
		super("JesusB", false);
	}
	
	private String path = "checks.jesus.b.";

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(!e.isAboveLiquids() || !e.isAboveLiquidsFrom())return;
		
		
		if(p.getVelocity().getY() > config.getDouble(path + "max-ascend") &&
				p.getLocation().getBlock().getType() == Material.AIR) {
			PlayerData playerData = e.getPlayerData();
			
			if(playerData == null) return;
			
			playerData.jesusBLimiter++;
			
			if(playerData.jesusBLimiter >= 3) {
				playerData.jesusBLimiter = 0;
				flag(p, "");
				if(config.getBoolean("main.cancel-event"))
					p.teleport(e.getFrom().clone().add(0, 0.2, 0));
			}
		}
		
	}
	
}

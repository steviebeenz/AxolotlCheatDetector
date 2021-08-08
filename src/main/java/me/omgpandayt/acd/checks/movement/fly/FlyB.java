package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyB extends Check {

	public FlyB() {
		super("FlyB", true);
	}
	
	private String path = "checks.fly.b.";
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		double y = p.getLocation().getY();
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		double lastY = playerData.lastPacketY,
			   lastLastY = playerData.lastLastPacketY,
			   fallHeight = PlayerUtil.getFallHeightDouble(p),
			   lastFallHeight = playerData.lastFallHeight,
			   lastLastFallHeight = playerData.lastLastFallHeight;

		playerData.lastLastPacketY = playerData.lastPacketY;
		playerData.lastPacketY = y;
		playerData.lastLastFallHeight = playerData.lastFallHeight;
		playerData.lastFallHeight = fallHeight;
		
		
		double fhm = config.getDouble(path + "fall-height");
		
		if(fallHeight < fhm
				|| lastFallHeight < fhm
				|| lastLastFallHeight < fhm
		)return;
	
		
		if(playerData.lastLastPacketY != -1 && playerData.lastPacketY != -1 && lastFallHeight != -1 && lastLastFallHeight != -1) {
			
			if(playerData.isOnGround ||
					playerData.lastOnGround
			)return;
			
			if(PlayerUtil.isAboveSlime(p.getLocation()))
				return;
			
			for(Entity entity : p.getNearbyEntities(2, 2, 2)) {
				if(entity instanceof Boat) {
					return;
				}
			}
			
			if(y > lastY && lastY > lastLastY && p.getVelocity().getY() < config.getDouble(path + "velocity") && PlayerUtil.aboveAreAir(p)) {
				playerData.flyBLimiter += 1;
				
				if(playerData.flyBLimiter >= config.getDouble(path + "limiter")) {
					flag(p, "Fly (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
					playerData.flyBLimiter = 0;
					
					Location loc = e.getFrom().clone();
					for(int i=0;i<3;i++)
						loc.setY((Math.floor(y) + 1) - PlayerUtil.getFallHeight(p));
					
					if(config.getBoolean("main.cancel-event"))
						p.teleport(loc);
				}
			}
		}
	}
	
}

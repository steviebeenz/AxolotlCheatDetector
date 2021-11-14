package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyB extends Check {

	public FlyB() {
		super("FlyB", true);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(p.hasPotionEffect(PotionEffectType.JUMP) || e.isAboveSlime() || playerData.ticksSinceEnderDragon < 170 || p.hasPotionEffect(PotionEffectType.LEVITATION) || e.getPlayerData().ticksSinceClimbable < 15 || e.getPlayerData().sinceWaterTicks < 10)return;
		
		double y = e.getTo().getY();
		
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
				|| !PlayerUtil.isValid(p)
				|| playerData.sinceSlimeTicks < 80
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
			
			if(y > lastY && lastY - config.getDouble(path + "y-increase") > lastLastY && p.getVelocity().getY() < config.getDouble(path + "velocity") && PlayerUtil.aboveAreAir(p)) {
				doFlag(p, playerData, e);
			} else {
				if(y > lastY && lastY > lastLastY && lastY - config.getDouble(path + "y-increase") < lastLastY  && p.getVelocity().getY() < config.getDouble(path + "velocity") && PlayerUtil.aboveAreAir(p)) {
					
					playerData.flyBNFLimiter++;
					if(playerData.flyBNFLimiter > 4) {
						doFlag(p, playerData, e);
						playerData.flyBNFLimiter = (int) Math.floorDiv(playerData.flyBNFLimiter, 2);
					}
					
				}
			}
		}
	}
	
	public void doFlag(Player p, PlayerData playerData, ACDMoveEvent e) {
		playerData.flyBLimiter += 1;
		
		if(playerData.flyBLimiter >= config.getDouble(path + "limiter")) {
			flag(p, "");
			playerData.flyBLimiter = -1;
			
			Location loc = e.getFrom().clone();
			loc.setY((Math.floor(e.getTo().getY()) + 1) - PlayerUtil.getFallHeight(p));
			if(Violations.getViolations(this, p) % 3 == 0)
				lagBack(loc, e.getPlayer());
		}
	}
	
}

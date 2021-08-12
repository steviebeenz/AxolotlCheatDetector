package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class FlyG extends Check {

	public FlyG() {
		super("FlyG", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
	    if (!p.isFlying() && !p.isInsideVehicle() && 
	      p.getLocation().getBlock().getType() != Material.WATER && !e.isOnGround() && playerData.velocityV == 0 && e.getTo().getY() > e.getFrom().getY()
	    	&& !e.isAboveSlime() && playerData.sinceSlimeTicks > 80) {
	      double distance = e.getTo().getY() - playerData.lastGroundY;
	      double limit = config.getDouble(path + "limit");
	      if (p.hasPotionEffect(PotionEffectType.JUMP))
	        for (PotionEffect effect : p.getActivePotionEffects()) {
	          if (effect.getType().equals(PotionEffectType.JUMP)) {
	            int level = effect.getAmplifier() + 1;
	            limit += Math.pow(level + 4.2D, 2.0D) / 16.0D;
	            break;
	          } 
	        }  
	      if (distance > limit) {
	    	  playerData.flyGLimiter++;
	    	  if(playerData.flyGLimiter > config.getDouble(path + "limiter")) {
	    	  	flag(p, "Fly (G)", "(DIST " + ((Math.floor(distance * 100)) / 100) + ") (LIM " + ((Math.floor(limit * 100)) / 100) + ")");
	    	  	playerData.flyGLimiter = 0;
	    	  }
	      }
	    }
	}
	
}

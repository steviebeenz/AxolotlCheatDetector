package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class MotionC extends Check implements Listener {

	public double descendY;
	
	public MotionC(FileConfiguration config) {
		super("MotionC", false);
		this.descendY = config.getDouble(path + "descend-y");
	}
	
    public void onMove(ACDMoveEvent e) {
        Player p = e.getPlayer( );
        
        PlayerData playerData = e.getPlayerData();
        if(playerData == null)return;
        
        double descendY = this.descendY;
        
        double fromY = e.getFrom().getY();
        double toY = e.getTo().getY();
        
        if(p.isFlying() ||
        		p.isGliding() ||
        		e.isOnGround() ||
        		e.isOnGroundFrom() ||
        		e.isAboveLiquidsFrom() ||
        		e.isAboveLiquids() || 
        		e.isOnClimbableTo() ||
        		e.isOnClimbableFrom() || 
        		e.isOnHoneyTo() || 
        		e.isOnHoneyFrom() || 
        		p.getLocation().getBlock().getType() == Material.WATER || 
        		e.getTo().getBlock().getType() != Material.AIR ||
        		e.getFrom().getBlock().getType() != Material.AIR || 
        		playerData.lastFDR > 80 ||
        		p.hasPotionEffect(PotionEffectType.LEVITATION) || 
        		p.hasPotionEffect(PotionEffectType.SLOW_FALLING)
        )return;
        
        
        if( toY < fromY){
            double deltaY = toY - fromY;
            
            for(Entity en : p.getNearbyEntities(3, 3, 3)) {
            	if(en instanceof Boat) {
            		return;
            	}
            }
            
            if(deltaY <= descendY){
                double yDropDif = 0;
                if(playerData.lastDeltaY > deltaY)
                    yDropDif = playerData.lastDeltaY - deltaY;
                else if(playerData.lastDeltaY < deltaY )
                    yDropDif = deltaY - playerData.lastDeltaY;
                
                if(playerData.lastDeltaY != 0 && yDropDif < 0.02D)
                	doFlag(p, e.getFrom(), playerData);

                
            }

        }
    }

	private void doFlag(Player p, Location from, PlayerData playerData) {
        if(playerData == null)return;
		
		playerData.motionCLimiter++;
		if(playerData.motionCLimiter > limiter) {
			flag(p, "");
			lagBack(from, p);
			playerData.motionCLimiter = 0;
		}
	}
    
	
}

package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class FlyC extends Check {

	public double velo, dist;
	
	public FlyC(FileConfiguration config) {
		
		super("FlyC", false);
		
		this.velo = config.getDouble(path + "velocity");
		this.dist = config.getDouble(path + "dist");
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null || p.hasPotionEffect(PotionEffectType.LEVITATION) || playerData.airTicks < 4) return;
		
        if(!e.isOnGround()
                && !e.isOnGroundFrom()
                && p.isOnGround()){
            float dist = (float)e.getTo().distance( e.getFrom() ) ;

            Location f = e.getFrom();
            Location t = e.getTo();
            
            if(
            		dist > this.dist && 
            		(f.getY() == t.getY() ||
            		f.getY() + 0.2f > t.getY() )
            		&& p.getVelocity().getY() < velo
            		&& e.getFallHeightFloat() > 0.2f
            ) {
                
            	flag(p, "");
            	
            }

        }
        
	}
	
}

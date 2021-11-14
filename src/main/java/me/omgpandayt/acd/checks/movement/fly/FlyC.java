package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyC extends Check {

	public FlyC() {
		super("FlyC", false);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null || p.hasPotionEffect(PotionEffectType.LEVITATION) || playerData.airTicks < 4) return;
		
		if(PlayerUtil.isOnGround3(p.getLocation()))
			playerData.isOnGround = true;
		else
			playerData.isOnGround = false;
		
        if(!playerData.isOnGround
                && !playerData.lastOnGround
                && p.isOnGround()){
            double dist = e.getTo().distance( e.getFrom() ) ;

            Location f = e.getFrom();
            Location t = e.getTo();
            
            if(
            		dist > config.getDouble(path + "dist") && 
            		(f.getY() == t.getY() ||
            		f.getY() + 0.2f > t.getY() )
            		&& p.getVelocity().getY() < config.getDouble(path + "velocity")
            		&& e.getFallHeightDouble() > 0.2
            ) {
                
            	flag(p, "");
            	
            }

        }
		
        playerData.lastOnGround = playerData.isOnGround;
        
	}
	
}

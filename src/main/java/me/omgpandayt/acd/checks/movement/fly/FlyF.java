package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class FlyF extends Check implements Listener {

	public FlyF() {
		super("FlyF", false);
	}
	
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
        Location to = e.getTo();
        Location from  = e.getFrom();
        
        double deltaY = to.getY() - from.getY();
        double yVel = deltaY - p.getVelocity().getY() ;
        
        if(to.getY() < 0)return;
        
        if(p.isFlying()
        		|| (PlayerUtil.isOnGround2(to)
        		&& e.isOnGround()
        		|| PlayerUtil.isOnGroundCustom(to, 4, 3))
        )
        	return;
        
        
        double maxVel = config.getDouble(path + "velocity-max");
        
        if(yVel >= maxVel && Math.abs(p.getVelocity().getY()) > maxVel) {
            
        	flag(p, "Fly (F)", "(VELY " + ((Math.floor(yVel * 100)) / 100) + ") (VEL " + ((Math.floor(Math.abs(p.getVelocity().getY()) * 100)) / 100) + ")");
        	
        }
	}
	
}

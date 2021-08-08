package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyG extends Check implements Listener {

	public FlyG() {
		super("FlyG", false);
	}
	
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
        Location to = e.getTo();
        Location from  = e.getFrom();
        
        double deltaY = to.getY() - from.getY();
        double yVel = deltaY - p.getVelocity().getY() ;
        
        if(to.getY() < 0)return;
        
        if(p.isFlying()
        		|| (PlayerUtil.isOnGround2(to)
        		&& PlayerUtil.isOnGround(to)
        		|| PlayerUtil.isOnGroundCustom(to, 4, 3))
        )
        	return;
        
        double maxVel = config.getDouble(path + "velocity-max");
        
        if(yVel >= maxVel && Math.abs(p.getVelocity().getY()) > maxVel) {
            
        	flag(p, "Fly (G)", "(VL" + (Violations.getViolations(this, p)+1) + ") (VELY " + ((Math.floor(yVel * 100)) / 100) + ") (VEL " + ((Math.floor(Math.abs(p.getVelocity().getY()) * 100)) / 100) + ")");
        	
        }
	}
	
}

package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyE extends Check {

	public FlyE() {
		super("FlyE", false);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(PlayerUtil.isOnGround(p.getLocation()))
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
            ) {
                
            	flag(p, "Fly (E)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
            	
            }

        }
		
        playerData.lastOnGround = playerData.isOnGround;
        
	}
	
}

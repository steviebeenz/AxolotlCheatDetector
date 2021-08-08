package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyF extends Check implements Listener {

	public FlyF() {
		super("FlyF", false);
	}
	
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer( );
        
        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if(playerData == null)return;
        
        double descendY = config.getDouble(path + "descend-y");
        
        double fromY = e.getFrom( ).getY();
        double toY = e.getTo().getY();
        
        if(p.isFlying())return;
        if(!PlayerUtil.isOnGround(e.getFrom())) return;
        if(!PlayerUtil.isOnGround(e.getTo())) return;
        if(PlayerUtil.isAboveLiquids(e.getFrom())) return;
        if(PlayerUtil.isAboveLiquids(e.getTo())) return;
        if(PlayerUtil.isOnClimbable(e.getTo())) return;
        if(PlayerUtil.isOnClimbable(e.getFrom())) return;
        
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
                	doFlag(p, e.getFrom());

                
            }
            
            playerData.lastDeltaY = deltaY;

        }
    }

	private void doFlag(Player p, Location from) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
        if(playerData == null)return;
		
		playerData.flyFLimiter++;
		if(playerData.flyFLimiter > config.getDouble(path + "limiter")) {
			flag(p, "Fly (F)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
			lagBack(from, p);
			playerData.flyFLimiter = 0;
		}
	}
    
	
}

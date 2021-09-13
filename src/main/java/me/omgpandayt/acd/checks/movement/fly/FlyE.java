package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class FlyE extends Check implements Listener {

	public FlyE() {
		super("FlyE", false);
	}
	
    public void onMove(ACDMoveEvent e) {
        Player p = e.getPlayer( );
        
        PlayerData playerData = e.getPlayerData();
        if(playerData == null)return;
        
        double descendY = config.getDouble(path + "descend-y");
        
        double fromY = e.getFrom().getY();
        double toY = e.getTo().getY();
        
        if(p.isFlying())return;
        if(p.isGliding())return;
        if(e.isOnGround()) return;
        if(e.isOnGroundFrom()) return;
        if(e.isAboveLiquidsFrom()) return;
        if(e.isAboveLiquids()) return;
        if(e.isOnClimbableTo()) return;
        if(e.isOnClimbableFrom()) return;
        if(e.isOnHoneyTo())return;
        if(e.isOnHoneyFrom())return;
        if(p.getLocation().getBlock().getType() == Material.WATER)return;
        if(e.getTo().getBlock().getType() != Material.AIR || e.getFrom().getBlock().getType() != Material.AIR)return;
        if(playerData.realisticFD > 80)return;
        
        
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
            
            playerData.lastDeltaY = deltaY;

        }
    }

	private void doFlag(Player p, Location from, PlayerData playerData) {
        if(playerData == null)return;
		
		playerData.flyELimiter++;
		if(playerData.flyELimiter > config.getDouble(path + "limiter")) {
			flag(p, "Fly (E)", "");
			lagBack(from, p);
			playerData.flyELimiter = 0;
		}
	}
    
	
}

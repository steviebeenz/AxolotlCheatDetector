package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedF extends Check{

	public SpeedF() {
		super("SpeedF", false);
	}
	
	@Override
    public void onMove(ACDMoveEvent e){
        Player p = e.getPlayer();
        
        PlayerData playerData = e.getPlayerData();
        if(playerData == null)return;
        
        if(PlayerUtil.isValid(p)){
            
        	if(p.hasPotionEffect(PotionEffectType.SPEED))return;
        	
        	double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY()) + 0.00000001422;
            
            double y = p.getVelocity().getY();
            
            double dist = e.getTo().distance(e.getFrom());
            
            boolean onGround = e.isOnGround(), falling = p.getVelocity().getY() < FlyA.STILL && deltaY < 0 && !onGround;
            
            double det = PlayerData.det;
            double det2 = PlayerData.det2;

            double d = 0;
            if(e.getFrom().getY()+det > e.getTo().getY() && falling){
                d = e.getFrom().getY() - e.getTo().getY();
            }

            if(d > det2 && falling)return;
            if(e.isAboveLiquids() || e.isAboveLiquidsFrom())return;
            

            if(Math.abs( y ) >= 0.07 && deltaY >= 0.399 && deltaY <= 0.4){
                fail(p, e, 1);
            }

            else if(Math.abs( y ) >= 0.4 && deltaY >= 0.41 && deltaY <= 0.42 && onGround == playerData.speedFGround && !e.isAboveSlime() && playerData.sinceSlimeTicks > 20){
            	fail(p, e, 2);
            }

            else if(Math.abs( y ) >= 0.41 && 0.42 >= deltaY && 0.5 <= deltaY && onGround != playerData.speedFGround){
            	fail(p, e, 3);
            }

            else if( 0.21 >= deltaY  && deltaY <= 0.4 && Math.abs( y ) >= 0.078 && !onGround && !playerData.speedFGround && dist >= 0.406){
            	fail(p, e, 4);
            }

            else if(!onGround && !playerData.speedFGround && deltaY >= 0.129 && Math.abs( y ) >= 0.15 &&  deltaY <= 0.37 && dist >= 0.49 ){
            	fail(p, e, 5);
            }

            else if(deltaY > 0.377 && deltaY <= 0.4 && Math.abs( y ) > 0.7 && !onGround && !playerData.speedFGround && dist > 0.446){
            	fail(p, e, 6);
            }
            
            playerData.speedFGround = onGround;
        }
    }
	
	public void fail(Player p, ACDMoveEvent e, int v) {
		lagBack(e);
		flag(p, "Speed (F)", "(V" + v + ")");
	}
	
}

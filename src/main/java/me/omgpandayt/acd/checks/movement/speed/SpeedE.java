// This code is NOT made by Jxy(OMGPANDAYT)
// I know Who made it. It's Mr.t0206.( He deleted his anti cheat and spigot mc account for his private reason. )
// someone do not forgot this check is NOT made by Jxy. ( mr.t0206 almost allowed to use their code. but jxy call t0206 and t0206's ac for bad. so I cannot understand why he uses it.)
// Latest SpeedE is skid. but older than it is not skid.
package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedE extends Check{

	public SpeedE() {
		super("SpeedE", false);
	}

    /**
    *
    * @author Jxy
    * @author t0206
    *
    * Thank you t0206 for this great check, I have tweaked some things to fix falses <3
    *
    */
	
	@Override
    public void onMove(ACDMoveEvent e){
        Player p = e.getPlayer();
        
        PlayerData playerData = e.getPlayerData();
        if(playerData == null)return;
        

        if(PlayerUtil.isValid(p) && playerData.sinceIceTicks > 10 || playerData.sinceSlimeTicks > 10 && !p.isGliding() && playerData.ticksSinceHit > 40 && playerData.ticksSinceEnderDragon > 170){
            
        	if(p.hasPotionEffect(PotionEffectType.SPEED) || playerData.ticksSinceHit < 5)return;
        	
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

            else if(Math.abs( y ) >= 0.4 && deltaY >= 0.41 && deltaY <= 0.42 && onGround == playerData.speedEGround && !e.isAboveSlime() && playerData.sinceSlimeTicks > 20){
            	fail(p, e, 2);
            }

            else if(Math.abs( y ) >= 0.41 && 0.42 >= deltaY && 0.5 <= deltaY && onGround != playerData.speedEGround){
            	fail(p, e, 3);
            }

            else if( 0.21 >= deltaY  && deltaY <= 0.4 && Math.abs( y ) >= 0.078 && !onGround && !playerData.speedEGround && dist >= 0.406){
            	fail(p, e, 4);
            }

            else if(!onGround && !playerData.speedEGround && deltaY >= 0.129 && Math.abs( y ) >= 0.15 &&  deltaY <= 0.37 && dist >= 0.49 ){
            	fail(p, e, 5);
            }

            else if(deltaY > 0.377 && deltaY <= 0.4 && Math.abs( y ) > 0.7 && !onGround && !playerData.speedEGround && dist > 0.446){
            	fail(p, e, 6);
            }
            
            playerData.speedEGround = onGround;
        }
    }
	
	public void fail(Player p, ACDMoveEvent e, int v) {
		lagBack(e);
		flag(p, "(V" + v + ")");
	}
	
}

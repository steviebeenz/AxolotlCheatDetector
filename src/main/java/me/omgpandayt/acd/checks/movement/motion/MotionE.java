package me.omgpandayt.acd.checks.movement.motion;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;

public class MotionE extends Check {

	public MotionE(FileConfiguration config) {
		super("MotionE", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		// Jump prediction
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		
		double motionY = NumberUtil.decimals(Math.round(e.getDeltaY() * 100), 2) / 100;
		
		if(motionY >= 0.39 && motionY <= 0.42 // 0.39 - 0.42 SINCE BELOW 0.39 FLAGS SPEED E
				&& p.isSprinting() // MOTION CHANGES WHEN JUMP AND SPRINT
				&& !p.hasPotionEffect(PotionEffectType.SPEED) // SPEED FALSE
				&& pd.ticksSinceHit > 15
				&& pd.sinceBlocksNearHead > 15) {
			double rot = p.getLocation().getYaw() * 0.017453292F;
			double x = Math.sin(rot) * 0.2D;
			double z = Math.cos(rot) * 0.2D;
			
			double expectedX = e.getFrom().getX() - x;
			double expectedZ = e.getFrom().getZ() + z;
			
			double differenceX = Math.abs(expectedX - e.getTo().getX());
			double differenceZ = Math.abs(expectedZ - e.getTo().getZ());
			
			double speed = 0.41218321548922177;
			
			if(differenceX > differenceZ) {
				speed -= differenceZ / 2 - 0.05;
			} else if(differenceX < differenceZ) {
				speed -= differenceX / 2 - 0.05;
			} else {
				speed /= 2;
			}
			
			if(differenceX > speed || differenceZ > speed) {
				flag(p, "(X " + NumberUtil.decimals(differenceX, 2) + ") (Z " + NumberUtil.decimals(differenceZ, 2) + ")");
				lagBack(e);
			}
			
			
		}
		
	}
	
}

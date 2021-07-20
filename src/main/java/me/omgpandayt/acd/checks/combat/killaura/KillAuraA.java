package me.omgpandayt.acd.checks.combat.killaura;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;

public class KillAuraA extends Check{

	public KillAuraA() {
		super("KillAuraA", false);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		double x = e.getFrom().getX() - e.getTo().getX();
		double z = e.getFrom().getZ() - e.getTo().getZ();
		
		Location loc = p.getLocation();
		
		double y = loc.getYaw();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
		double speed = config.getDouble(path + "max-speed");
				
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if (b != null && BlockUtils.isIce(b)) {
				speed += config.getDouble(path + "ice-increase");
			} else if (b != null && BlockUtils.isIce(b.getLocation().clone().add(0, -0.825, 0).getBlock())) {
				speed += config.getDouble(path + "ice-increase");
			}
		}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
        	speed += effect.getAmplifier() / (Math.PI * Math.PI);
        }
        
        if(x < 0) {
			if(y > 0 && y < 90) {
				if(x < -speed) {
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			} else if (y > 90 && y > 130) {
				if (x < -speed && z < -(speed / 3)) {
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			} else if (y > 130 && y < 180) {
				if (x < -(speed / 3) && z < -speed) {
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			}
        }
		if(z < 0) {
			if(y < 0 && y > -90) {
				if(z < (-speed)-0.1832f) {       // This is the most buggiest one lol
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			} else if (y < -90 && y > -130) {
				if (z < -speed && x < -(speed / 3)) {
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			} else if (y < -130 && y > -180) {
				if (z < -(speed / 3) && x < -speed) {
					flag(p, "KillAura (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				}
			}
		}
		
	}
	
}

package me.omgpandayt.acd.checks.movement.jump;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class JumpA extends Check {

	public JumpA(FileConfiguration config) {
		super("JumpA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		double y = p.getVelocity().getY();
		
		if(p.isInWater() || e.isAboveSlime() || playerData.sinceSlimeTicks < 30 || p.hasPotionEffect(PotionEffectType.LEVITATION) || !PlayerUtil.isValid(p) || playerData.ticksSinceHit < 20)return;
		
		for(Block b : e.getBlocksBelowUp()) {
			if(b.getType().isSolid() || b.getType() == Material.SWEET_BERRY_BUSH) {
				return;
			}
		}
		
		double l = 8;
		double h = 2;
		
		if(e.getDeltaXZ() < 0.3) {
			l+=2;
			h+=2;
		}
		
		if(y < 0.165) {
			if(y > FlyA.STILL && e.getFallHeightFloat() <= 0.3) {
				playerData.decreaseHops = false;
				playerData.lowHops++;
				if(playerData.lowHops > l) {
					playerData.lowHops = 0;
					lagBack(e);
					flag(p, "");
				}
			}else {
				playerData.decreaseHops = true;
			}
		} else if (y > 0.24) {
			if(y > FlyA.STILL && e.getFallHeightFloat() >= 0.5 && e.getAirTicks() == 2 && !e.isOnGround() && !e.isOnGroundFrom()) {
				playerData.decreaseHops2 = false;
				playerData.highHops++;
				if(playerData.highHops > h) {
					playerData.highHops = 0;
					lagBack(e);
					flag(p, "");
				}
			}else {
				playerData.decreaseHops2 = true;
			}
		}
	}
	
}

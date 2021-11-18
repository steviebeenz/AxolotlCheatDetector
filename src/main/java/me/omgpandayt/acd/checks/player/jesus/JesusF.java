package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class JesusF extends Check {

	public JesusF(FileConfiguration config) {
		super("JesusF", false);
	}

	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(p.isSwimming())return;
		
		boolean aboveWater = false;
		
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			
			if(b.getType() == Material.WATER) {
				aboveWater = true;
			} else if(b.getLocation().clone().add(0, -1, 0).getBlock().getType() == Material.WATER) {
				aboveWater = true;
			} else if(b.getLocation().clone().add(0, -2, 0).getBlock().getType() == Material.WATER) {
				aboveWater = true;
			} else if(b.getLocation().clone().add(0, -3, 0).getBlock().getType() == Material.WATER) {
				aboveWater = true;
			} else if(b.getLocation().clone().add(0, -4, 0).getBlock().getType() == Material.WATER) {
				aboveWater = true;
			}
			
		}
		if(!aboveWater)return;
		
		if(p.getLocation().getBlock().getType() == Material.WATER)return;
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null || p.hasPotionEffect(PotionEffectType.LEVITATION)) return;
		
		double f = f(e.getTo());
		
		if(f >= 0.6 && f < 4 && !e.isOnGround() && !e.isOnGroundFrom()) {
		
			playerData.jesusFtb++;
			if(playerData.jesusFtb > 30)
			if(!playerData.lastPacketNearBoat) {
				if(PlayerUtil.isValid(p)) {
					flag(p, "");
					lagBack(e.getFrom().add(0, 0.2, 0), p);
				}
			}
			
		} else {
			
			playerData.jesusFtb = 0;
			
		}
		
	}
	
	private double f(Location l) {
		for(double i = 0; i <= 4; i += 0.1) {
			if(i > 4) {
				return 999;
			} else {
				
				Block b = l.clone().add(0, -i, 0).getBlock();
				if(b.getType() != Material.AIR && b.getType() != Material.CAVE_AIR) {
					return i;
				}
				
			}
		}
		return 0;
	}
	
}

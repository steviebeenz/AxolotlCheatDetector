package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedB extends Check implements Listener {
	
	public SpeedB() {
		super("SpeedB", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {	
		
		Player p = e.getPlayer();

		double distX = e.getFrom().getX() - e.getTo().getX();
		double distZ = e.getFrom().getZ() - e.getTo().getZ();

		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null || playerData.ticksSinceHit < 30 || playerData.onHorseTicks < 10) return;
		
		double y = p.getLocation().getYaw();
		
		if((playerData.isOnGround && !playerData.lastOnGround) || !PlayerUtil.isValid(p))return;
		
		double tooFastX = config.getDouble(path + "too-fast.x");
		double tooFastZ = config.getDouble(path + "too-fast.z");
		
		if(!PlayerUtil.isOnGround(p.getLocation())) {
			tooFastX += 0.1f;
			tooFastZ += 0.1f;
		}
		
		double ssi = 0;
		
		ItemStack boots = p.getInventory().getBoots();
		
		if(boots != null)
			if(boots.containsEnchantment(Enchantment.SOUL_SPEED)) {
				ssi = boots.getEnchantmentLevel(Enchantment.SOUL_SPEED) / (Math.PI * Math.PI);
			}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            tooFastX += effect.getAmplifier() / (Math.PI * Math.PI);
            tooFastZ += effect.getAmplifier() / (Math.PI * Math.PI);
        }
        
        double ii = config.getDouble(path + "ice-increase");
        double iim = config.getDouble(path + "ice-increase-multi");
        double it = (playerData.iceTicks * iim);
		
        for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
        	if(BlockUtils.isIce(b)) {
        		tooFastX += ii + it;
				tooFastZ += ii + it;
        	}
        }
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -0.825, 0))) {
			if (BlockUtils.isIce(b) || BlockUtils.isIce(b.getLocation().clone().add(0, 0.325, 0).getBlock())) {
				tooFastX += ii + it;
				tooFastZ += ii + it;
			} else if (b.getType() == Material.SLIME_BLOCK || b.getLocation().clone().add(0, 0.325, 0).getBlock().getType() == Material.SLIME_BLOCK) {
				tooFastX += config.getDouble(path + "slime-increase");
				tooFastZ += config.getDouble(path + "slime-increase");
			} else if (soil(b, p) || soil(b.getLocation().clone().add(0, 0.325, 0).getBlock(), p)) {
				tooFastX += ssi;
				tooFastZ += ssi;
			}
		}
		
		if (soil(p.getLocation().getBlock(), p)) {
			tooFastX += ssi;
			tooFastZ += ssi;
		}
		
		if(y > -140 && y < -47)	{
			
			double min = config.getDouble(path + "disable.min");
			double max = config.getDouble(path + "disable.min");
			
			if(distX + tooFastX >= min && distX + tooFastX <= max)return;
			
			if(distX > tooFastX) {
				doFlag(p, tooFastX, distX, e.getFrom());
			}
		} else if (y > -47 && y < 46) {
			double min = config.getDouble(path + "disable.min");
			double max = config.getDouble(path + "disable.min");
			
			if(distZ + tooFastZ >= min && distZ + tooFastZ <= max)return;
			
			if(distZ > tooFastZ) {
				doFlag(p, tooFastZ, distZ, e.getFrom());
			}
		} else if (y > 46 && y < 46 + 93) {
			
			double min = config.getDouble(path + "disable.min");
			double max = config.getDouble(path + "disable.min");
			
			tooFastX = -tooFastX;
			
			if(distX + tooFastX <= -min && distX + tooFastX >= -max)return;
			
			if(distX < tooFastX) {
				doFlag(p, -tooFastX, -distX, e.getFrom());
			}
		} else {
			double min = config.getDouble(path + "disable.min");
			double max = config.getDouble(path + "disable.min");
			
			tooFastZ = -tooFastZ;
			
			if(distZ + tooFastX <= -min && distZ + tooFastZ >= -max)return;
			
			if(distZ < tooFastZ) {
				doFlag(p, -tooFastZ, -distZ, e.getFrom());
			}
		}
	}
	
	public void doFlag(Player p, double tooFast, double speed, Location lastLoc) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		playerData.speedBLimiter++;
		if(playerData.speedBLimiter > config.getDouble(path + "limiter")) {
			double l = Math.pow(10, (new String(tooFast+"").length() - 2));
			flag(p, "(MAX " + tooFast + ") (GOT " + ((Math.floor(speed * l)) / l) + ")");
			playerData.speedBLimiter = 0;
			lagBack(lastLoc, p);
		}
	}
	
	public boolean soil(Block b, Player p) {
		if(p.getInventory().getBoots() == null)return false;
		return BlockUtils.isSoil(b) && p.getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED);
	}

}

package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class SpeedC extends Check implements Listener {
	
	public SpeedC() {
		super("SpeedC", false);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {	
		
		Player p = e.getPlayer();

		double distX = e.getFrom().getX() - e.getTo().getX();
		double distZ = e.getFrom().getZ() - e.getTo().getZ();

		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null) return;
		
		double y = p.getLocation().getYaw();
		
		if(playerData.isOnGround && !playerData.lastOnGround)return;
		
		double tooFastX = config.getDouble(path + "too-fast.x");
		double tooFastZ = config.getDouble(path + "too-fast.z");
		
		if(!PlayerUtil.isOnGround(p.getLocation())) {
			tooFastX += 0.1f;
			tooFastZ += 0.1f;
		}
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -0.825, 0))) {
			if (BlockUtils.isIce(b)) {
				tooFastX += config.getDouble(path + "ice-increase");
				tooFastZ += config.getDouble(path + "ice-increase");
			} else if (b.getType() == Material.SLIME_BLOCK) {
				tooFastX += config.getDouble(path + "slime-increase");
				tooFastZ += config.getDouble(path + "slime-increase");
			}
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
		playerData.speedCLimiter++;
		if(playerData.speedCLimiter > config.getDouble(path + "limiter")) {
			double l = Math.pow(10, (new String(tooFast+"").length() - 2));
			flag(p, "Speed (C)", "(MAX " + tooFast + ") (GOT " + ((Math.floor(speed * l)) / l) + ") (VL" + (Violations.getViolations(this, p)+1) + ")");
			playerData.speedCLimiter = 0;
			lagBack(lastLoc, p);
		}
	}

}
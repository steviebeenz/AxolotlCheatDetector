package me.omgpandayt.acd.checks.movement.fly;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class FlyB extends Check {

	public FlyB() {
		super("FlyB", true, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		double y = p.getLocation().getY();
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		playerData.lastLastPacketY = playerData.lastPacketY;
		playerData.lastPacketY = y;
		
		double lastY = playerData.lastPacketY,
			   lastLastY = playerData.lastLastPacketY;
		
		if(playerData.lastLastPacketY != -1 && playerData.lastPacketY != -1) {
			
			
			boolean dontFlag = false;
			
			for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
				if(b.getType() == Material.SLIME_BLOCK) {
					dontFlag = true;
					break;
				} else {
					for(int i=0;i<10;i++) {
						if(b.getLocation().clone().add(0, -i, 0).getBlock().getType() == Material.SLIME_BLOCK) {
							dontFlag = true;
							break;
						}
					}
				}
			}
			
			for(Entity entity : p.getNearbyEntities(2, 2, 2)) {
				if(entity instanceof Boat) {
					dontFlag = true;
				}
			}
			
			if(y == lastY && lastY > lastLastY && p.getVelocity().getY() < -0.1 && !dontFlag && PlayerUtil.getFallHeight(p) > 1 && !PlayerUtil.isOnGround(p.getLocation())) {
				playerData.flyBLimiter += 1;
				
				if(playerData.flyBLimiter >= 3) {
					flag(p, "Fly (B)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
					playerData.flyBLimiter = 0;
					
					Location loc = e.getFrom().clone();
					loc.setY(playerData.lastLastPacketY);
					
					p.teleport(loc);
				}
			}
		}
		
	}
	
}

package me.omgpandayt.acd.checks.player.jesus;

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

public class JesusA extends Check {

	public JesusA() {
		super("JesusA", false);
	}
	
	private String path = "checks.jesus.a.";

	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		
		if(e.getFrom().getY() != e.getTo().getY()) return;
		if(!PlayerUtil.isAboveLiquids(p.getLocation()) || !PlayerUtil.isAboveLiquids(e.getFrom()) || p.isSwimming())return;
		
		double nbr = config.getDouble(path + "nearby-boat-radius");
		
		for(Entity entity : p.getNearbyEntities(nbr, nbr, nbr)) {
			if(entity instanceof Boat) {
				return;
			}
		}
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
			if(b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR) 
				return;
			
		}
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(!playerData.lastPacketNearBoat) {
			if(PlayerUtil.isValid(p)) {
				flag(p, "Jesus (A)", "");
				lagBack(e.getFrom().add(0, 0.2, 0), p);
			}
		}
		
	}
	
}

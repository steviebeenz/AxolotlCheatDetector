package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class JesusA extends Check {

	public JesusA() {
		super("JesusA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getFrom().getY() != e.getTo().getY()) return;
		if(!e.isAboveLiquids() || !e.isAboveLiquidsFrom() || p.isSwimming())return;
		
		for(Block b : e.getBlocksBelowUp()) {
			if(!b.getType().isAir()) {
				return;
			}
		}
		
		double nbr = config.getDouble(path + "nearby-boat-radius");
		
		for(Entity entity : p.getNearbyEntities(nbr, nbr, nbr)) {
			if(entity instanceof Boat) {
				return;
			}
		}
		if(p.getLocation().getBlock().getType() == Material.WATER)return;
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(!playerData.lastPacketNearBoat) {
			if(PlayerUtil.isValid(p)) {
				flag(p, "");
				lagBack(e.getFrom().add(0, 0.2, 0), p);
			}
		}
		
	}
	
}

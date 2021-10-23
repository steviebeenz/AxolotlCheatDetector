package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;

public class GroundSpoofA extends Check {

	public GroundSpoofA() {
		super("GroundSpoofA", false);
	}
	
	
	@SuppressWarnings("deprecation")
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -0.5001, 0))) {
			if (BlockUtils.isPiston(b) || BlockUtils.isFence(b) || b.getType() == Material.SLIME_BLOCK) {
				return;
			}
		}
		for (Block b : e.getBlocksBelow()) { 
			if (BlockUtils.isPiston(b) || BlockUtils.isFence(b) || b.getType() == Material.SLIME_BLOCK) {
				return;
			}
		}
		for (Block b : e.getBlocksBelowUp()) {
			if (b.getType() != Material.AIR) {
				return;
			}
		}
		
		double nbr = config.getDouble(path + "nearby-boat-radius");
		
		for (Entity entity : p.getNearbyEntities(nbr, nbr, nbr)) {
			if(entity instanceof Boat) {
				return;
			}
		}
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) 
			return;
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(e.isOnGround()) {
			playerData.lastGroundX = p.getLocation().getX();
			playerData.lastGroundY = p.getLocation().getY();
			playerData.lastGroundZ = p.getLocation().getZ();
		}
		
		if(p.isOnGround() && e.getAirTicks() > 10 && p.isValid() && !p.isDead() && PlayerUtil.getFallHeight(p) > 1) {
			if(!config.getBoolean(path + "ghostblock-teleport")) {
				flag(p, "");
				double deltaY = e.getDeltaY();
				p.damage((playerData.lastPacketFD + deltaY) - 3);
			} else {
				p.sendMessage(ChatColor.RED + "You were walking on a ghost block! You have been pushed back!");
				if(playerData.lastGroundX != 1e+305 && playerData.lastGroundY != 1e+305 && playerData.lastGroundZ != 1e+305)
					p.teleport(new Location(p.getWorld(), playerData.lastGroundX, playerData.lastGroundY, playerData.lastGroundZ, p.getLocation().getYaw(), p.getLocation().getPitch()));
			}
		}
		
	}
	
}

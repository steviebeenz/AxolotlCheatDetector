package me.omgpandayt.acd.checks.movement.elytrafly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class ElytraFlyA extends Check {

	public ElytraFlyA() {
		super("ElytraFlyA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) {
			double fallY = e.getTo().getY() - e.getFrom().getY();
			
			
			for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 3, 0))) {
				if (b.getType() != Material.AIR)
					return;
			}
			
			PlayerData playerData = e.getPlayerData();
			if(playerData == null) return;
			
			double tsr = config.getDouble(path + "ticks-since-rocket");
			
			if(fallY == 0 && PlayerUtil.isValid(p) && !e.isOnGround() && p.isGliding() && e.getFallHeight() > 3 && playerData.ticksSinceRocket >= tsr) {
				flag(p, "ElytraFly (A)", "(FALL " + ((Math.floor(fallY * 100)) / 100) + ")");
				ItemStack chestplate = p.getInventory().getChestplate();
				p.getInventory().setChestplate(new ItemStack(Material.AIR));
				p.getInventory().setChestplate(chestplate);
				lagBack(e);
			}
		}
	}
	
}

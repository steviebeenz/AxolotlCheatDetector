package me.omgpandayt.acd.checks.movement.elytrafly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class ElytraFlyA extends Check {

	public ElytraFlyA() {
		super("ElytraFlyA", false);
	}
	
	private String path = "checks.elytrafly.a.";
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) {
			double fallY = e.getTo().getY() - e.getFrom().getY();
			
			boolean dontFlag = false;
			
			for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 3, 0))) {
				if (b.getType() != Material.AIR)
					dontFlag = true;
			}
			
			PlayerData playerData = PlayerDataManager.getPlayer(p);
			if(playerData == null) return;
			
			if(fallY == 0 && PlayerUtil.isValid(p) && !PlayerUtil.isOnGround(p.getLocation()) && p.isGliding() && PlayerUtil.getFallHeight(p) > 3 && !dontFlag && playerData.ticksSinceRocket >= config.getDouble(path + "ticks-since-rocket")) {
				flag(p, "ElytraFly (A)", "(VL" + Violations.getViolations(this, p) + ") (FALL " + ((Math.floor(fallY * 100)) / 100) + ")");
				ItemStack chestplate = p.getInventory().getChestplate();
				p.getInventory().setChestplate(new ItemStack(Material.AIR));
				p.getInventory().setChestplate(chestplate);
				lagBack(e);
			}
		}
	}
	
}

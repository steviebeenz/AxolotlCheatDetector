package me.omgpandayt.acd.checks.movement.elytrafly;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class ElytraFlyB extends Check {

	public ElytraFlyB() {
		super("ElytraFlyB", false);
	}
	
	private String path = "checks.elytrafly.b.";
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA) {
			
			double deltaXZ = Math.abs(e.getTo().getX() - e.getFrom().getX()) + Math.abs(e.getTo().getZ() - e.getFrom().getZ());
			
			PlayerData playerData = PlayerDataManager.getPlayer(p);
			if(playerData == null) return;
			
			if(deltaXZ == 0 && p.getLocation().getPitch() <= 85
					&& p.getLocation().getPitch() >= 5
					&& PlayerUtil.isValid(p)
					&& !PlayerUtil.isOnGround(p.getLocation())
					&& p.isGliding()
					&& PlayerUtil.getFallHeight(p) > 3
					&& playerData.ticksSinceRocket >= config.getDouble(path + "ticks-since-rocket")
			) {
				flag(p, "ElytraFly (B)", "(VL" + Violations.getViolations(this, p) + ")");
				ItemStack chestplate = p.getInventory().getChestplate();
				p.getInventory().setChestplate(new ItemStack(Material.AIR));
				p.getInventory().setChestplate(chestplate);
				lagBack(e);
			}
		}
	}
	
}

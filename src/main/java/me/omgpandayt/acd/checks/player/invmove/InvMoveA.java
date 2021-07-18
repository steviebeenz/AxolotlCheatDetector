package me.omgpandayt.acd.checks.player.invmove;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;

public class InvMoveA extends Check {

	public InvMoveA() {
		super("InvMoveA", false);
	}
	
	private String path = "checks.invmove.a.";
	
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if(PlayerDataManager.getPlayer((Player) e.getWhoClicked()) == null) return;
		PlayerDataManager.getPlayer((Player)e.getWhoClicked()).invOpen = true;
	}
	
	@Override
	public void onInventoryClose(InventoryCloseEvent e) {
		if(PlayerDataManager.getPlayer((Player) e.getPlayer()) == null) return;
		PlayerDataManager.getPlayer((Player) e.getPlayer()).invOpen = false;
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getPlayer());
		
		if(playerData == null) return;
		if(playerData.invMoveWaitTick) return;
		playerData.invMoveWaitTick = true;
		
		Player p = e.getPlayer();
		
		if(playerData.invOpen) {
			
			boolean dontFlag = false;
			
			for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
				if(b.isLiquid())
					dontFlag = true;
				else if (BlockUtils.isIce(b.getLocation().clone().add(0, -1, 0).getBlock()))
					dontFlag = true;
				
			}
			
			double deltaXZ = (Math.abs(e.getFrom().getX() - e.getTo().getX())) + Math.abs(e.getFrom().getZ() - e.getTo().getZ());
			
			if(!dontFlag && p.getVelocity().getY() == FlyA.STILL && playerData.ticksSinceHit >= config.getDouble(path + "ticks-since-damage") && deltaXZ > config.getDouble(path + "max-speed")) {
				flag(p, "InvMove (A)", "(VL" + (Violations.getViolations(this, p)+1) + ")");
				lagBack(e);
			}
			
		}
		
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				try {
					playerData.invMoveWaitTick = false;
				} catch(NullPointerException e) {
					// Player left
				}
			}
		};
		
		task.runTaskLater(ACD.getInstance(), 2);
	}
	
}

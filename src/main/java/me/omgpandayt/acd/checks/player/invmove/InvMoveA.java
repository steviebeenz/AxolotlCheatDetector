package me.omgpandayt.acd.checks.player.invmove;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class InvMoveA extends Check {

	public InvMoveA() {
		super("InvMoveA", false);
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked().getTargetBlockExact(5) instanceof CommandBlock)return;
		PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getWhoClicked());
		if(playerData == null) return;
		playerData.invOpen = true;
	}
	
	@Override
	public void onInventoryClose(InventoryCloseEvent e) {
		PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getPlayer());
		if(playerData == null) return;
		playerData.invOpen = false;
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		PlayerData playerData = e.getPlayerData();
		
		if(playerData == null) return;
		if(playerData.invMoveWaitTick) return;
		playerData.invMoveWaitTick = true;
		
		Player p = e.getPlayer();
		
		if(playerData.invOpen && PlayerUtil.isValid(p) && !p.isGliding() && e.getFallHeightDouble() <= 1 && playerData.ticksInventoryOpen > 20) { 
		
			
			for (Block b : e.getBlocksBelowUp()) {
				if(b.getType().isSolid())
					return;
			}
			
			double deltaXZ = (Math.abs(e.getFrom().getX() - e.getTo().getX())) + Math.abs(e.getFrom().getZ() - e.getTo().getZ());
			
			if(p.getVelocity().getY() == FlyA.STILL && playerData.ticksSinceHit >= config.getDouble(path + "ticks-since-damage") && deltaXZ > config.getDouble(path + "max-speed")) {
				playerData.invMoveALimiter++;
				if(playerData.invMoveALimiter > 2) {
					flag(p, "");
					lagBack(e);
					playerData.invMoveALimiter = 0;
				}
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

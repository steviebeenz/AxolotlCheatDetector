package me.omgpandayt.acd.checks.player.invmove;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InvMoveA extends Check {

    private static final String PATH = "checks.invmove.a.";

    public InvMoveA() {
        super("InvMoveA", false);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getWhoClicked());
        if (playerData == null) {
            return;
        }
        playerData.setInvOpen(true);
    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent e) {
        PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getPlayer());
        if (playerData == null) {
            return;
        }
        playerData.setInvOpen(true);
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent e) {
        PlayerData playerData = PlayerDataManager.getPlayer((Player) e.getPlayer());
        if (playerData == null) {
            return;
        }
        playerData.setInvOpen(false);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {
        PlayerData playerData = PlayerDataManager.getPlayer(e.getPlayer());

        if (playerData == null) return;
        if (e.getTo() == null) return;
        if (playerData.isInvMoveWaitTick()) return;
        playerData.setInvMoveWaitTick(true);

        Player p = e.getPlayer();

        if (playerData.isInvOpen()) {

            boolean dontFlag = false;

            for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
                if (b.isLiquid() || BlockUtils.isIce(b.getLocation().clone().add(0, -1, 0).getBlock())) {
                    dontFlag = true;
                }
            }

            double deltaXZ = (Math.abs(e.getFrom().getX() - e.getTo().getX())) + Math.abs(e.getFrom().getZ() - e.getTo().getZ());

            if (!dontFlag && p.getVelocity().getY() == FlyA.STILL && playerData.getTicksSinceHit() >= config.getDouble(PATH + "ticks-since-damage") && deltaXZ > config.getDouble(PATH + "max-speed")) {
                flag(p, "InvMove (A)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                lagBack(e);
            }

        }

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    playerData.setInvMoveWaitTick(false);
                } catch (NullPointerException ignored) {
                    // Player has left the game.
                }
            }
        };

        task.runTaskLater(ACD.getInstance(), 2);
    }

}

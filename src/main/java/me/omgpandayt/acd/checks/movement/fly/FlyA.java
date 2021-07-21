package me.omgpandayt.acd.checks.movement.fly;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyA extends Check {

    public static final double STILL = -0.0784000015258789;

    private static final String PATH = "checks.fly.a.";

    public FlyA() {
        super("FlyA", true);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        boolean sameY = e.getFrom().getY() == e.getTo().getY();

        int fallHeight = PlayerUtil.getFallHeight(p);
        boolean dontFlag = false;
        boolean isBouncing = p.getVelocity().getY() > FlyA.STILL;

        for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
            if (b.getType() != Material.AIR
                    || b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
                dontFlag = true;
                break;
            }
        }

        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        if (sameY && fallHeight >= 3 && !isBouncing && PlayerUtil.isValid(p) && !dontFlag && !p.isGliding()
                && !PlayerUtil.isOnGround(p.getLocation())) {
            playerData.setFlyALimiter(playerData.getFlyALimiter() + 1);
            if (playerData.getFlyALimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "Fly (A)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                lagBack(e);
                playerData.setFlyALimiter(0);
            }
        }

    }

}
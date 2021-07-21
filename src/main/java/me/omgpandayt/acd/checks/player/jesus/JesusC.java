package me.omgpandayt.acd.checks.player.jesus;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class JesusC extends Check {

    private static final String PATH = "checks.jesus.c.";

    public JesusC() {
        super("JesusC", false);
    }

    @Override
    public void onTick(Player p) {
        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        boolean dontFlag = false;

        for (Block b : BlockUtils.getBlocksBelow(p.getLocation())) {
            if (b.getType() != Material.WATER
                    || b.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.WATER) {
                dontFlag = true;
                break;
            }
        }

        if (
                p.getLocation().getBlock().getType() == Material.WATER
                        && playerData.getLastPacketY() == p.getLocation().getY()
                        && playerData.getLastLastPacketY() == p.getLocation().getY()
                        && p.getVelocity().getY() <= 0
                        && p.getLocation().clone().add(0, -1, 0).getBlock().getType() == Material.WATER
                        && !dontFlag
                        && PlayerUtil.isValid(p)
        ) {
            playerData.setJesusCLimiter(playerData.getJesusCLimiter() + 1);
            if (playerData.getJesusCLimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "Jesus (C)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                playerData.setJesusCLimiter(0);
            }
        }
    }

}

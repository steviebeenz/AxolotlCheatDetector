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

public class FlyC extends Check {

    private static final String PATH = "checks.fly.c.";

    public FlyC() {
        super("FlyC", false);
    }

    @Override
    public void onTick(Player p) {
        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        boolean dontFlag = false;

        for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
            if (b.getType() != Material.AIR) {
                dontFlag = true;
                break;
            }
        }

        if (
                !PlayerUtil.isOnGround(p.getLocation())
                        && playerData.getLastPacketY() == p.getLocation().getY()
                        && p.getVelocity().getY() <= 0
                        && PlayerUtil.getFallHeight(p) > 1
                        && !p.isFlying()
                        && !dontFlag
                        && PlayerUtil.isValid(p)
                        && !PlayerUtil.isAboveSlime(p.getLocation())
        ) {
            playerData.setFlyCLimiter(playerData.getFlyCLimiter() + 1);
            if (playerData.getFlyCLimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "Fly (C)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                playerData.setFlyCLimiter(0);
            }
        }
    }

}

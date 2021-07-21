package me.omgpandayt.acd.checks.world.impossibleactions;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class ImpossibleActionsA extends Check {

    private static final String PATH = "checks.impossibleactions.a.";

    public ImpossibleActionsA() {
        super("ImpossibleActionsA", false);
    }

    @Override
    public void onPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        Block targetBlock = p.getTargetBlockExact(5);
        Block placedBlock = e.getBlockPlaced();

        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        if (
                targetBlock == null ||
                        (targetBlock.getX() != placedBlock.getX()
                                && targetBlock.getY() != placedBlock.getY()
                                && targetBlock.getZ() != placedBlock.getZ())
        ) {
            playerData.setImpactALimiter(playerData.getImpactALimiter() + 1);
            if (playerData.getImpactALimiter() >= config.getDouble(PATH + "limiter")) {
                playerData.setImpactALimiter(0);
                flag(p, "ImpossibleActions (A)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                cancelPlace(e);
            }
        }

    }

}

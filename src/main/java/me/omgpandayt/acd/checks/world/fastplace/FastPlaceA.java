package me.omgpandayt.acd.checks.world.fastplace;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class FastPlaceA extends Check {

    private static final String PATH = "checks.fastplace.a.";

    public FastPlaceA() {
        super("FastPlaceA", false);
    }

    @Override
    public void onPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();

        PlayerData playerData = PlayerDataManager.getPlayer(p);

        if (playerData == null) return;

        playerData.setPlacedBlocks(playerData.getPlacedBlocks() + 1);

        if (playerData.getPlacedBlocks() >= config.getDouble(PATH + "maxplace")) {
            flag(p, "FastPlace (A)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
            playerData.setPlacedBlocks(0);
            cancelPlace(e);
        }

    }

}

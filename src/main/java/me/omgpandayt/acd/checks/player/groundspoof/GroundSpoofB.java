package me.omgpandayt.acd.checks.player.groundspoof;

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

public class GroundSpoofB extends Check {

    private static final String PATH = "checks.groundspoof.b.";

    public GroundSpoofB() {
        super("GroundSpoofB", false);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (e.getTo() == null) {
            return;
        }

        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        boolean dontFlag = false;

        for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 1, 0))) {
            if (b.getType() != Material.AIR) {
                dontFlag = true;
                break;
            }
        }

        if (playerData.getLastPacketFD() > 3 && playerData.getLastPacketHP() == p.getHealth() && PlayerUtil.isValid(p) && p.getFallDistance() == 0 && !dontFlag && !PlayerUtil.isAboveSlimeUnsafe(p.getLocation())) {
            playerData.setGroundSpoofBLimiter(playerData.getGroundSpoofBLimiter() + 1);
            if (playerData.getGroundSpoofBLimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "GroundSpoof (B)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                playerData.setGroundSpoofBLimiter(0);
                if (config.getBoolean("main.cancel-event")) {
                    double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
                    p.setFallDistance((float) (playerData.getLastPacketFD() + deltaY));
                    p.teleport(p.getLocation());
                }
            }
        }

        playerData.setLastPacketFD(playerData.getRealisticFD());
        playerData.setLastPacketHP((float) p.getHealth());

    }

}

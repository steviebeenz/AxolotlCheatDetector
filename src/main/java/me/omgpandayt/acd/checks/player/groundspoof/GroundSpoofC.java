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

public class GroundSpoofC extends Check {

    private static final String PATH = "checks.groundspoof.c.";
    private double notFallingVY = -0.07544406518948656;

    public GroundSpoofC() {
        super("GroundSpoofC", false);
    }

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

        if (playerData.getRealisticFD() > 0 && PlayerUtil.isValid(p) && p.getFallDistance() == 0 && !dontFlag && correctFall(p) && p.getVelocity().getY() < 0 && PlayerUtil.aboveAreAir(p)) {
            playerData.setGroundSpoofCLimiter(playerData.getGroundSpoofCLimiter() + 1);
            if (playerData.getGroundSpoofCLimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "GroundSpoof (C)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                playerData.setGroundSpoofCLimiter(0);
                if (config.getBoolean("main.cancel-event")) {
                    double deltaY = Math.abs(e.getTo().getY() - e.getFrom().getY());
                    p.setFallDistance((float) (playerData.getLastPacketFD() + deltaY));
                    p.teleport(p.getLocation());
                }
            }
        }

    }

    public boolean correctFall(Player p) {
        return p.getVelocity().getY() != notFallingVY && !PlayerUtil.isAboveSlime(p.getLocation());
    }

}

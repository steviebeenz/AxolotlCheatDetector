package me.omgpandayt.acd.checks.movement.fly;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyD extends Check {

    private static final String PATH = "checks.fly.d.";

    public FlyD() {
        super("FlyD", false);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();


        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;

        double deltaY = Math.abs(e.getFrom().getY() - e.getTo().getY());

        if (deltaY < 0.3 && p.getFallDistance() > 0.4f && !PlayerUtil.isOnGround(p.getLocation()) && PlayerUtil.isValid(p) && !p.isGliding()) {
            playerData.setFlyDLimiter(playerData.getFlyDLimiter() + 1);
            if (playerData.getFlyDLimiter() >= config.getDouble(PATH + "limiter")) {
                flag(p, "Fly (D)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                playerData.setFlyDLimiter(0);
                lagBack(e);
            }
        }

    }

}

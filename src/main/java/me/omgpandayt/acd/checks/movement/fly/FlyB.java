package me.omgpandayt.acd.checks.movement.fly;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyB extends Check {

    private static final String PATH = "checks.fly.b.";

    public FlyB() {
        super("FlyB", true);
    }

    @Override
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        double y = p.getLocation().getY();
        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;
        playerData.setLastLastPacketY(playerData.getLastPacketY());
        playerData.setLastPacketY(y);

        double lastY = playerData.getLastPacketY();
        double lastLastY = playerData.getLastLastPacketY();

        if (playerData.getLastLastPacketY() != -1 && playerData.getLastPacketY() != -1) {


            boolean dontFlag = false;

            if (PlayerUtil.isAboveSlime(p.getLocation()))
                dontFlag = true;

            for (Entity entity : p.getNearbyEntities(2, 2, 2)) {
                if (entity instanceof Boat) {
                    dontFlag = true;
                }
            }

            if (y == lastY && lastY > lastLastY && p.getVelocity().getY() < -0.1 && !dontFlag && PlayerUtil.getFallHeight(p) > 1 && !PlayerUtil.isOnGround(p.getLocation()) && PlayerUtil.aboveAreAir(p)) {
                playerData.setFlyBLimiter(playerData.getFlyBLimiter() + 1);

                if (playerData.getFlyBLimiter() >= config.getDouble(PATH + "limiter")) {
                    flag(p, "Fly (B)", "(VL" + (Violations.getViolations(this, p) + 1) + ")");
                    playerData.setFlyBLimiter(0);

                    Location loc = e.getFrom().clone();
                    loc.setY(playerData.getLastLastPacketY());

                    if (config.getBoolean("main.cancel-event"))
                        p.teleport(loc);
                }
            }
        }

    }

}

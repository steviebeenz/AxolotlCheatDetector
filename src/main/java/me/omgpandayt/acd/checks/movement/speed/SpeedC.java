package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedC extends Check implements Listener {
	
	public SpeedC(FileConfiguration config) {
		super("SpeedC", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {

		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();

		if(!PlayerUtil.isValid(p) || pd.ticksSinceEnderDragon < 170)return;
		
        double prevDeltaXZ = pd.prevDeltaXZ;
        double deltaXZ = e.getDeltaXZ();

        if (!e.isOnGroundFrom() && !p.isGliding() && pd.ticksSinceHit > 20) {
            double predicted = (prevDeltaXZ * 0.91) + 0.026;
            double dif = Math.abs(predicted - deltaXZ);
            if (dif > 1) {
                flag(p, "(MOVE " + NumberUtil.decimals(dif, 3) + ")");
            }
        }

	}

}

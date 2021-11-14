package me.omgpandayt.acd.checks.combat.aimassist;

import java.util.function.Predicate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class AimAssistA extends Check {
	
	public AimAssistA(FileConfiguration config) {
		super("AimAssistA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		
        final float deltaPitch = e.getDeltaPitch();
        final float deltaYaw = e.getDeltaYaw();


        if ((deltaPitch % 1 == 0 || deltaYaw % 1 == 0) && deltaPitch != 0 && deltaYaw != 0 && pd.sinceTeleportTicks > 20) {
            if (++pd.aimALimiter > 4) {
                flag(p, "");
                lagBack(e);
            }
        } else {
        	pd.aimALimiter -= pd.aimALimiter > 0 ? 1 : 0;
        }
		
	}

}

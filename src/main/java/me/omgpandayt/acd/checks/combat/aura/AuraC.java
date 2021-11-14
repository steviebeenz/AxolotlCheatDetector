package me.omgpandayt.acd.checks.combat.aura;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.NumberUtil;

public class AuraC extends Check {

	public AuraC(FileConfiguration config) {
		super("AuraC", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		
		if(pd.hitTicks > 1) return;

        if (e.getDeltaYaw() > 40 && e.getAccel() < 0.005 && e.getAccel() != 0 && pd.sinceWaterTicks > 10) {
            flag(p, "(MOVE " + Math.round(e.getAccel() * 10000) + ")");
        }
		
	}
	
}

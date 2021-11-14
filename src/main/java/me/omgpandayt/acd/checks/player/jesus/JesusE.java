package me.omgpandayt.acd.checks.player.jesus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;

public class JesusE extends Check {

	public JesusE(FileConfiguration config) {
		super("JesusE", false);
	}

	@Override
	public void onMove(ACDMoveEvent ev) {
		
		Player p = ev.getPlayer();
		
		if(!ev.isAboveLiquids() || !ev.isAboveLiquidsFrom())return;
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null) return;
		
		boolean flag = BlockUtils.isLiquidBlock(p.getLocation().clone().add(0, -0.3, 0).getBlock())
				&& !BlockUtils.isLiquidBlock(p.getLocation().clone().add(0, -0.2, 0).getBlock())
				&& !BlockUtils.isLiquidBlock(p.getLocation().getBlock())
				&& !p.isSwimming();
		
		if(flag) {
			
			playerData.jesusELimiter++;
			
			if(playerData.jesusELimiter >= 8) {
				playerData.jesusELimiter = 5;
				flag(p, "");
			}
		}
		
	}
	
}

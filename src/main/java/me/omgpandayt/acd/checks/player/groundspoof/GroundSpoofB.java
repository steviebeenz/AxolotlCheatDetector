package me.omgpandayt.acd.checks.player.groundspoof;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.PlayerUtil;

public class GroundSpoofB extends Check {

	public GroundSpoofB() {
		super("GroundSpoofB", false, 8);
	}
	
	@Override
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return;
		
		if(playerData.lastPacketFD > 3 && playerData.lastPacketHP == p.getHealth() && PlayerUtil.isValid(p) && p.getFallDistance() == 0) {
			ACD.logPlayers("hack");
		}
		
		playerData.lastPacketFD = (float) playerData.realisticFD;
		playerData.lastPacketHP = (float) p.getHealth();
		
	}
	
}

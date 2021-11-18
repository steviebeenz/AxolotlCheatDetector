package me.omgpandayt.acd.checks.world.badpackets; 

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class BadPacketsA extends Check {

	public double maxMove;
	
	public BadPacketsA(FileConfiguration config) {
		super("BadPacketsA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		
		PlayerData playerData = e.getPlayerData();
		if(playerData == null)return;
		
		playerData.moves++;
		playerData.movesLong++;
		
		long timeElapsed = System.currentTimeMillis() - playerData.lastTime;
		long timeElapsedLong = System.currentTimeMillis() - playerData.lastTimeLong;
		
		long maxTime = 25;
		long longTime = 1000;
		
		if(timeElapsed >= maxTime) {
			
			playerData.lastTime = System.currentTimeMillis();
		
			if(playerData.moves > 1 + Math.floor((timeElapsed - maxTime) / 50)) {
				playerData.badPacketsALimiter += 1;
				if(playerData.badPacketsALimiter > 2) {
					flag(p, "(SHORT)");
					lagBack(e);
				}
			} else {
				playerData.badPacketsALimiter -= playerData.badPacketsALimiter >= 0.01 ? 0.01 : 0;
			}
		
			playerData.moves = 0;
		}
		
		if(timeElapsedLong >= longTime) {
			
			playerData.lastTime = System.currentTimeMillis();
			
			if(playerData.movesLong > 21 + Math.floor((timeElapsed - longTime) / 50)) {
				flag(p, "(LONG)");
				lagBack(e);
			} else {
				playerData.badPacketsALimiter2 -= playerData.badPacketsALimiter2 >= 0.49 ? 0.49 : 0;
			}
		
			playerData.movesLong = 0;
			
		}
		
	}
	
}

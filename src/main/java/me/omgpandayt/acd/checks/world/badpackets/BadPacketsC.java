package me.omgpandayt.acd.checks.world.badpackets;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class BadPacketsC extends Check {

	private static BadPacketsC referTo;
	
	public BadPacketsC(FileConfiguration config) {
		super("BadPacketsC", false);
		referTo = this;
	}
	
	public static BadPacketsC getRefer() {
		return referTo;
	}
	
	public void parseData(int data, boolean flagged, ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		
		// Axolotl client and more
		if(data == 0 && flagged) { // Had this line of code used for beta testing that some cheaters used to disable the anticheat when i forgot to remove it, im gonna keep it so they dont think its something wrong with their disabler lol
			
			if(++playerData.badPacketsC_Detect1 > 4) {
				
				flag(p, "(CHEAT: DISABLER)");
				lagBack(e);
				
			}
						
		} else {
			playerData.badPacketsC_Detect1 -= playerData.badPacketsC_Detect1 >= 0.5 ? 0.5 : 0;
		}
		
	}
	
}

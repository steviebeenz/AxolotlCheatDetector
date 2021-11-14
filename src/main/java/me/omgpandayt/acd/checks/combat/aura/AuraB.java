package me.omgpandayt.acd.checks.combat.aura;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class AuraB extends Check {

	public double accel;
	
	public AuraB(FileConfiguration config) {
		
		super("AuraB", false);
		
		this.accel = config.getDouble(path + "accel");
		
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		
		double dist = e.getDeltaXZ();
		double lastDist = pd.prevDeltaXZ;
		
		if(p.isSprinting() && pd.hitTicks <= 2) {
			double accel = Math.abs(dist - lastDist);
			
			if(accel < accel) {
				if(++pd.preVLAura >= 5) {
					pd.preVLAura = 0;
					flag(p, "");
					lagBack(e);
				}
			} else {
				pd.preVLAura = 0;
			}
			
		}
		
	}
	
}

package me.omgpandayt.acd.checks.movement.aim;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;


public class AimA extends Check {
	public AimA() {
		super("AimA", false);
	}
	
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		Player p = (Player) e.getDamager();
		Location l = p.getLocation();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
		if() {
			flag(p, "Aim (A)", "");
		}
	
	}
	
	public double GCD(double a, double b) {
	    if (b == 0) {
	        return a;
	    } else {
	    	return (GCD(b, a % b));
	    }
	}
	
}

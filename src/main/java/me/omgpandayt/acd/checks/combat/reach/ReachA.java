package me.omgpandayt.acd.checks.combat.reach;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.violation.Violations;

public class ReachA extends Check {

	public ReachA() {
		super("ReachA", false, 5);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		Entity attacker = e.getDamager();
		Entity victim = e.getEntity();
		
		if(attacker instanceof Player) {
			Player a = ((Player)attacker);
			
			double r = a.getLocation().distance(victim.getLocation());
			
			if(r > 4.4 + (a.getGameMode() == GameMode.CREATIVE ? 2.6 : 0)) {
				
				flag(a, "Reach (A)", " (VL" + (Violations.getViolations(this, a) + 1) + ") (REACH " + ((Math.floor(r * 100)) / 100) + ")");
				e.setCancelled(true);
				a.teleport(a.getLocation());
				
			}
		}
		
	}
	
}

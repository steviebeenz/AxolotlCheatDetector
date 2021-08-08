package me.omgpandayt.acd.checks.combat.reach;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.violation.Violations;

public class ReachB extends Check {
	
	public ReachB() {
		super("ReachB", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		Player p = (Player)e.getDamager();
		
		Entity taking = e.getEntity();
		
		double limit = config.getDouble(path + "max-reach");
		
		if(p.getGameMode() == GameMode.CREATIVE) {
			limit += config.getDouble(path + "creative-increase");
		}
		
		Location locationDamager = p.getLocation();
		Location locationDamagee = taking.getLocation();
		
		double LocationDamagerX = locationDamager.getX();
		double LocationDamagerY = locationDamager.getY() + 1.0;
		double LocationDamagerZ = locationDamager.getZ();
		
		double LocationDamageeX = locationDamagee.getX();
		double LocationDamageeY = locationDamagee.getY();
		double LocationDamageeZ = locationDamagee.getZ();
		
		double range = Math.sqrt(Math.pow(LocationDamagerX - LocationDamageeX, 2) + Math.pow(LocationDamagerY - LocationDamageeY, 2)+ Math.pow(LocationDamagerZ - LocationDamageeZ, 2));
		
		if (range >= limit) {
			flag(p, "Reach (B)", "(VL" + (Violations.getViolations(this, p)+1) + ") (REACH " + ((Math.floor(range * 100))/100) + ")");
		}
		
	}

}

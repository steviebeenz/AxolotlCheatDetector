package me.omgpandayt.acd.checks.combat.invalidattack;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.BlockIterator;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.violation.Violations;

public class InvalidAttackA extends Check {
	
	public InvalidAttackA() {
		super("InvalidAttackA", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		
		Player p = (Player) e.getDamager();
		
		BlockIterator blocksToAdd = new BlockIterator(p.getEyeLocation(), 0.1, (int) (Math.floor(e.getDamager().getLocation().distance(e.getEntity().getLocation()) * 10)));
		double expand = 0.0D;
		
		while(blocksToAdd.hasNext()) {
			Location loc = blocksToAdd.next().getLocation();
			
			double dis = loc.distance(e.getEntity().getLocation());
			
			if(dis < config.getDouble(path + "hitbox")) {
				return;
			} else {
				if(expand == 0) expand = dis;
			}
		}
		
		expand = ((Math.floor((double)expand * 1000))/1000);
		
		flag(p, "InvalidAttack (A)", "(VL" + (Violations.getViolations(this, p)+1) + ") (EXP " + expand + ")");
		cancelDamage(e);
		
	}
	
}

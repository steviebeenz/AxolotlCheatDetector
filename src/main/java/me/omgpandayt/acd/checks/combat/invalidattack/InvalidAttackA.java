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
		double times = 0;
		Object expand = 0.0D;
		
		while(blocksToAdd.hasNext()) {
			times++;
			Location loc = blocksToAdd.next().getLocation();
			
			double dis = loc.distance(e.getEntity().getLocation());
			
			if(dis < config.getDouble(path + "hitbox")) {
				return;
			} else {
				if((double) expand == 0) expand = dis;
			}
			
			if(loc.getBlock().getType().isSolid() && times < 20) {
				expand = "BLOCK_IN_WAY";
				break;
			}
		}
		
		Object expandNum = 0;
		
		if(expand instanceof Double) {
			expandNum = ((Math.floor((double)expand * 1000))/1000);
		} else {
			expandNum = expand;
		}
		
		flag(p, "InvalidAttack (A)", "(VL" + (Violations.getViolations(this, p)+1) + ") (EXP " + expandNum + ")");
		cancelDamage(e);
		
	}
	
}

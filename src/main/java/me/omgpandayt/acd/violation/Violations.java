package me.omgpandayt.acd.violation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;

public class Violations {

	/**
	 * 
	 * @author JustDoom
	 * @author Jxydev
	 * 
	 */
	
	private static Map<UUID, Map<Check, Integer>> violations = new HashMap<>();
	
	
    public static void addViolation(Check check, Player p){

        int violation = 1;
        Map<Check, Integer> vl = new HashMap<>();
        if (violations.containsKey(p.getUniqueId())) {
            vl = violations.get(p.getUniqueId());
        }
        if (!vl.containsKey(check)) {
            vl.put(check, violation);
        } else {
            vl.put(check, vl.get(check) + violation);
        }
        violations.put(p.getUniqueId(), vl);
        if(getViolations(check, p) >= check.flagsToKick){
            check.punish(p);
        }
        
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
            	removeViolation(check,p,1);
            }
        };
        task.runTaskLater(ACD.getInstance(), (long) (20 * ACD.getInstance().getConfig().getDouble("main.punish.flag-removal-rate")));
    }
    
    public static Integer getViolations(Check check, Player p) {
        if (violations.containsKey(p.getUniqueId())) {
            if (violations.get(p.getUniqueId()).containsKey(check)) {
                return violations.get(p.getUniqueId()).get(check);
            }
        }
        return 0;
    }

	public static void clearViolations(Check check, Player p) {
		violations.get(p.getUniqueId()).remove(check);
	}
	
	public static void removeViolation(Check check, Player p, int amount) {
		int checkViolations = getViolations(check, p);
		
		if(checkViolations <= 0) return;
		
		Map<Check, Integer> vl = new HashMap<>();
		
		vl.put(check, checkViolations - 1);
		
		violations.put(p.getUniqueId(), vl);
	}

    
}

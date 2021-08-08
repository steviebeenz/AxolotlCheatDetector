package me.omgpandayt.acd.checks.combat.invalidattack;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.ACDAttack;
import me.omgpandayt.acd.violation.Violations;

public class InvalidAttackB extends Check {
	
	public InvalidAttackB() {
		super("InvalidAttackB", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		Player p = (Player) e.getDamager();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null)return;
		
		playerData.attacks.add(new ACDAttack(playerData.attackTicks));
		playerData.attackTicks = 0;
		
		
		if(playerData.attacks.size() > config.getDouble(path + "attacks-size")) {
			double attackLow = 0;
			for(ACDAttack atk : playerData.attacks) {
				if(atk.attackTime <= config.getDouble(path + "low-attack-time")) {
					attackLow++;
				}
			}
			if(attackLow > config.getDouble(path + "attack-low-to-flag")) {
				flag(p, "InvalidAttackB", "(VL" + (Violations.getViolations(this, p)+1) + ") (ATK " + attackLow + ")");
			}
		}
		
	}
	
}

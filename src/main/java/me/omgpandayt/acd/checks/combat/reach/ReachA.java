package me.omgpandayt.acd.checks.combat.reach;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class ReachA extends Check {

	public ReachA() {
		super("ReachA", false);
	}
	
	private String path = "checks.reach.a.";
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		Entity attacker = e.getDamager();
		Entity victim = e.getEntity();
		
		if(attacker instanceof Player) {
			Player a = ((Player)attacker);
			
			double r = a.getLocation().distance(victim.getLocation());
			
			PlayerData playerData = PlayerDataManager.getPlayer(a);
			if(playerData == null)return;
			
			if(playerData.lastAttack < 2) return;
			playerData.lastAttack = 0;
			
			if(r > config.getDouble(path + "max-reach") + (a.getGameMode() == GameMode.CREATIVE ? config.getDouble(path + "creative-increase") : 0)) {
				
				flag(a, "Reach (A)", "(REACH " + ((Math.floor(r * 100)) / 100) + ")");
				cancelDamage(e);
				lagBack(a.getLocation(), a);
				
			}
		}
		
	}
	
}

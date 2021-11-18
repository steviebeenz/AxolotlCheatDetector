package me.omgpandayt.acd.checks.combat.reach;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class ReachA extends Check {

	public double maxReach, creativeIncrease;
	
	public ReachA(FileConfiguration config) {
		
		super("ReachA", false);
		
		this.maxReach = config.getDouble(path + "max-reach");
		this.creativeIncrease = config.getDouble(path + "creative-increase");
		
	}
	
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
			
			if(r > maxReach + (a.getGameMode() == GameMode.CREATIVE ? creativeIncrease : 0)) {
				
				if(++playerData.reachALimiter > 7) {
					flag(a, "(REACH " + ((Math.floor(r * 100)) / 100) + ")");
					cancelDamage(e);
					lagBack(a.getLocation(), a);
					playerData.reachALimiter = 0;
				}
				
			}
		}
		
	}
	
}

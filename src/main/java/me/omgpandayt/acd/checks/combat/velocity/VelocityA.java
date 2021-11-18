package me.omgpandayt.acd.checks.combat.velocity;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class VelocityA extends Check {

	public VelocityA(FileConfiguration config) {
		super("VelocityA", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		
		
	}
	
	@Override
	public void onDamage2(EntityDamageByEntityEvent e) {
		
		if(!(e.getEntity() instanceof Player))return;
		
		Player p = (Player)e.getEntity();
		PlayerData pd = PlayerDataManager.getPlayer(p);
		pd.sinceEntityHit = 0;
		
	}
	
}

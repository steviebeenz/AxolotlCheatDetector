package me.omgpandayt.acd.checks.combat.autoclicker;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class AutoClickerA extends Check {

	public AutoClickerA(FileConfiguration config) {
		super("AutoClickerA", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
	
		Player p = (Player)e.getDamager();
		PlayerData pd = PlayerDataManager.getPlayer(p);
		if(pd==null)return;
		
		double time = System.currentTimeMillis() - pd.timeLastAttack;
		double lastTime = pd.timeLastAttack - pd.timeLastLastAttack;
		pd.timeLastLastAttack = pd.timeLastAttack;
		pd.timeLastAttack = System.currentTimeMillis();
		
		if(Math.abs(time - lastTime) <= 1 && pd.timeLastAttack != System.currentTimeMillis() && pd.timeLastLastAttack != System.currentTimeMillis()) {
			flag(p, "");
		}
		
	}
	
}

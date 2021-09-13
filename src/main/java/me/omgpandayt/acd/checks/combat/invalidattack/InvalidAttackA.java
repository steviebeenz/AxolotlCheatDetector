package me.omgpandayt.acd.checks.combat.invalidattack;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.BlockIterator;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.NumberUtil;

public class InvalidAttackA extends Check {
	
	public InvalidAttackA() {
		super("InvalidAttackA", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		
		Player p = (Player) e.getDamager();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
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
		
		playerData.invalidAttackALimiter++;
		
		if(playerData.invalidAttackALimiter > config.getDouble(path + "limiter")) {
			expand = NumberUtil.decimals(expand, 3);
			
			flag(p, "InvalidAttack (A)", "(EXP " + expand + ")");
			cancelDamage(e);
		}
		
	}
	
}

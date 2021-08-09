package me.omgpandayt.acd.checks.combat.invalidattack;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class InvalidAttackC extends Check {
	
	public InvalidAttackC() {
		super("InvalidAttackC", false);
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		
		
		Player p = (Player) e.getDamager();
		
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null)return;
		
		Block targetBlock = p.getTargetBlockExact(5);
		if(targetBlock == null || !targetBlock.getType().isSolid())return;
		
		double targetDis = targetBlock.getLocation().distance(p.getLocation());
		double entityDis = e.getEntity().getLocation().distance(p.getLocation());
		
		if(targetDis + config.getDouble(path + "block-distance-increase") < entityDis) {
		
			flag(p, "InvalidAttack (C)", "(DIST " + ((Math.floor(((entityDis - (targetDis + config.getDouble(path + "block-distance-increase")))) * 100))/100) + ")");
			cancelDamage(e);
		
		}
		
	}
	
}

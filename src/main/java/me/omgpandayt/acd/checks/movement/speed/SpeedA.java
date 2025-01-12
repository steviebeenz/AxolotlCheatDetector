package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.PlayerUtil;

public class SpeedA extends Check implements Listener {
	
	public SpeedA() {
		super("SpeedE", false);
	}
	
	/*@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData playerData = e.getPlayerData();
		if(p.isGliding() || playerData.ticksSinceEnderDragon < 170)return;
		double distX = Math.abs(e.getFrom().getX() - e.getTo().getX());
		double distZ = Math.abs(e.getFrom().getZ() - e.getTo().getZ());
		
		double maxXZMove = config.getDouble(path + "maximum-speed");
		
		if(e.getPlayerData().sinceIceTicks < 10) {
			maxXZMove += config.getDouble(path + "ice-increase");
		}
		
		ItemStack boots = p.getInventory().getBoots();
		
		if(boots != null) {
			if (boots.getItemMeta().getAttributeModifiers() != null && boots.getItemMeta().getAttributeModifiers().containsValue(Attribute.GENERIC_MOVEMENT_SPEED)) {
				return;
			}
		}
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            maxXZMove += effect.getAmplifier() / (Math.PI * Math.PI);
        }
		
		if(distZ < distX / 1.1 && Math.abs(distZ - distX) > 0.2f) {
			maxXZMove -= (distX / 2f);
			maxXZMove += 0.15f;
		}else if(distX < distZ / 1.1 && Math.abs(distX - distZ) > 0.2f) {
			maxXZMove -= (distZ / 2f);
			maxXZMove += 0.15f;
		}
		
		if(maxXZMove < 0)return;
		
		double distance = Math.floor((distX + distZ) * 100);
		double maxDistance = Math.floor(maxXZMove * 100);
		
		boolean dontFlag = false;
		
		for(Block b : e.getBlocksBelowUp()) {
			if (b.getType().isSolid()) {
				dontFlag = true;
				break;
			}
		}
		
		if(playerData.onHorseTicks < 10 || playerData.ticksSinceHit < 30)return;
		
		if(distance > maxDistance && PlayerUtil.isValid(p) && !dontFlag && !p.isGliding() && !playerData.lastPacketNearBoat && distance != 1.0D) { // If the distance is 1.0D it is probably a piston push.
			flag(p, "(MOVE " + (distance / 100) + " > " + (maxDistance/100) + ")");
			lagBack(e);
		}
		
	}*/
	
}

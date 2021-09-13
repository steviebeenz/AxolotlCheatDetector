package me.omgpandayt.acd.checks.movement.speed;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;
import me.omgpandayt.acd.violation.Violations;

public class SpeedD extends Check implements Listener {
	
	public SpeedD() {
		super("SpeedD", false);
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {

		Player p = e.getPlayer();

		double distX = Math.abs(e.getFrom().getX() - e.getTo().getX());
		double distZ = Math.abs(e.getFrom().getZ() - e.getTo().getZ());
		double dist = distX + distZ;

		PlayerData playerData = PlayerDataManager.getPlayer(p);
		
		if(playerData == null) return;
		
		ItemStack boots = p.getInventory().getBoots();
		
		if(boots != null) {
			if (boots.getItemMeta().getAttributeModifiers().containsValue(Attribute.GENERIC_MOVEMENT_SPEED)) {
				return;
			}
		}
		if(playerData.ticksSinceHit < 20)return;
		
		boolean onGround = e.isOnGround() && e.isOnGroundFrom();
		
		double lastDist = playerData.dist;

		float friction = 0.91F;
		double shiftedLastDist = lastDist * friction;
		double equalness = dist - shiftedLastDist;
		double scaledEqualness = equalness;
		
		playerData.dist = dist;
		
		double tooFast = config.getDouble(path + "too-little-friction");
		
        PotionEffect effect = p.getPotionEffect( PotionEffectType.SPEED );
        if ( effect != null )
        {
            tooFast += effect.getAmplifier() / (Math.PI * Math.PI);
        }
        
        if(playerData.onHorseTicks < 10)return;
		
		for (Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, -0.2, 0))) {
			if (BlockUtils.isIce(b)) {
				tooFast += config.getDouble(path + "ice-increase");
			} else if (b.getType() == Material.SLIME_BLOCK) {
				tooFast += config.getDouble(path + "slime-increase");
			} else if (BlockUtils.isSoil(b) && p.getInventory().getBoots() != null && p.getInventory().getBoots().containsEnchantment(Enchantment.SOUL_SPEED)) {
				tooFast += config.getDouble(path + "soul-speed-increase");
			}
			
		}

		if (onGround && scaledEqualness > tooFast && PlayerUtil.isValid(p) && !p.isGliding() && p.getVelocity().getY() == FlyA.STILL) {
			playerData.speedDLimiter++;
			if(playerData.speedDLimiter > config.getDouble(path + "limiter")) {
				double got = Math.floor(scaledEqualness * 100);
				flag(p, "Speed (E)", "(EXP " + ((Math.floor(tooFast * 100)) / 100) + ") (GOT " + (got / 100) + " (VL" + (Violations.getViolations(this, p) + 1) + ")");
				lagBack(e);
				playerData.speedDLimiter = 0;
			}
		}

	}

}

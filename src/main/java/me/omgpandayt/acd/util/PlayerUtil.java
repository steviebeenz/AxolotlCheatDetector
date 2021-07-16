package me.omgpandayt.acd.util;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerUtil {
	
	public static boolean isOnGround(Location loc) {
		double expand = 0.3;
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				if (loc.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR) {
					return true;
				}
			}
		}
		return false;
	}

	public static int getFallHeight(Player p) {
		int yHeight = (int) Math.floor(p.getPlayer().getLocation().getY());
		Location loc = p.getLocation().clone();
		loc.setY(yHeight);
		int fallHeight = 0;
		while(loc.getBlock().getType() == Material.AIR) {
			yHeight--;
			loc.setY(yHeight);
			fallHeight++;
		}
		return fallHeight;
	}

	public static boolean isValid(Player p) {
		return !p.isFlying() && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE);
	}

	public static boolean isUsingItem(Player p) {
		return p.getItemInUse() != null;
	}

}

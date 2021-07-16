package me.omgpandayt.acd.util;

import org.bukkit.Location;
import org.bukkit.Material;

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

}

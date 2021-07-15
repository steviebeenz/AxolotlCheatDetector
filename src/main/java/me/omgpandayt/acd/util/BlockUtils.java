package me.omgpandayt.acd.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockUtils {

	public static boolean isIce(Block block) {
		return block.getType() == Material.ICE || block.getType() == Material.BLUE_ICE || block.getType() == Material.PACKED_ICE;
	}

	public static boolean isPiston(Block block) {
		return block.getType() == Material.STICKY_PISTON || block.getType() == Material.PISTON;
	}

	public static Block[] getBlocksBelow(Location location) {
		Block[] blocks = new Block[9];
		
		double expand = 0.3;
		int runs = 0;
		
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				Block b = location.clone().add(x, -0.5001, z).getBlock();
				if(b != null) {
					blocks[runs] = b;
					runs++;
				}
				
			}
		}
		
		return blocks;
	}
	
}

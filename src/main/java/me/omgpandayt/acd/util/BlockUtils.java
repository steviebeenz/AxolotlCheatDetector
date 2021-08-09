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
	
	public static Block[] getBlocksBelowCustom(Location location, double expand) {
		Block[] blocks = new Block[9];
		
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

	public static boolean isFence(Block b) {
		return b.getType() == Material.OAK_FENCE || b.getType() == Material.DARK_OAK_FENCE || b.getType() == Material.SPRUCE_FENCE || b.getType() == Material.ACACIA_FENCE || b.getType() == Material.BIRCH_FENCE || b.getType() == Material.JUNGLE_FENCE;
	}

	public static boolean invalidPlace(Location add) {
		return isLiquidBlock(add.getBlock()) || add.getBlock().getType() == Material.AIR;
	}

	public static boolean isLiquidBlock(Block block) {
		return block.getType() == Material.WATER || block.getType() == Material.LAVA;
	}

	public static boolean isClimbable(Block block) {
		return block.getType() == Material.LADDER || block.getType() == Material.WEEPING_VINES || block.getType() == Material.TWISTING_VINES || block.getType() == Material.CAVE_VINES;
	}
	
}

package me.omgpandayt.acd.util;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.omgpandayt.acd.checks.PlayerDataManager;

public class PlayerUtil {
	
	public static boolean isOnGround(Location loc) {
		double expand = 0.3;
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				if (loc.clone().add(x, -0.02, z).getBlock().getType() != Material.AIR && loc.clone().add(x, 0.5001, z).getBlock().getType() == Material.AIR) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isOnGround3(Location loc) {
		double expand = 0.3;
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				if (loc.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR && loc.clone().add(x, 0.5001, z).getBlock().getType() == Material.AIR) {
					return true;
				}
			}
		}
		return false;
	}
	
    public static boolean isOnGround2(Location loc){
        int radius = 3;
        int radiusY = 2;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radiusY; y < radiusY; y++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = loc.getWorld().getBlockAt(loc.clone().add(x, y, z));
                    if (block.getType().isSolid() || block.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isOnGroundCustom(Location loc , int radius , int radiusY ) {
        for (int x = -radius; x < radius; x++) {
            for (int y = -radiusY; y < radiusY; y++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = loc.getWorld().getBlockAt(loc.clone().add(x, y, z));
                    if (block.getType().isSolid() || block.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.AIR) {
                        return true;
                    }
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
	
	public static double getFallHeightDouble(Player p) {
		double yHeight = Math.floor(p.getPlayer().getLocation().getY());
		Location loc = p.getLocation().clone();
		loc.setY(yHeight);
		double fallHeight = 0;
		while(loc.getBlock().getType() == Material.AIR) {
			yHeight-=0.1;
			loc.setY(yHeight);
			fallHeight+=0.1;
		}
		return fallHeight;
	}

	public static boolean isValid(Player p) {
		return PlayerDataManager.getPlayer(p).lastFlight > 20 && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) && !p.isDead();
	}

	public static boolean isAboveLiquids(Location location) {
		for(Block b : BlockUtils.getBlocksBelow(location)) {
			
			if(b.getType() != Material.WATER && b.getType() != Material.LAVA) {
				return false;
			}
			
		}
		return true;
	}

	public static boolean isAboveSlime(Location location) {
		for(Block b : BlockUtils.getBlocksBelow(location)) {
			if(b.getType() == Material.SLIME_BLOCK) {
				return true;
			} else {
				for(int i=0;i<10;i++) {
					if(b.getLocation().clone().add(0, -i, 0).getBlock().getType() == Material.SLIME_BLOCK) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @deprecated Goes down till it finds slime
	 * 
	 */
	
	@Deprecated
	public static boolean isAboveSlimeUnsafe(Location location) {
		for(Block b : BlockUtils.getBlocksBelowCustom(location, 1)) {
			if(b.getType() == Material.SLIME_BLOCK) {
				return true;
			} else {
				for(int i=0;i<location.getY();i++) {
					if(b.getLocation().clone().add(0, -i, 0).getBlock().getType() == Material.SLIME_BLOCK) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean aboveAreAir(Player p) {
		for(Block b : BlockUtils.getBlocksBelow(p.getLocation().clone().add(0, 3, 0))) {
			if(b.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}

	public static boolean isOnClimbable(Location from) {
		for(Block b : BlockUtils.getBlocksBelow(from)) {
			if(BlockUtils.isClimbable(b) || BlockUtils.isClimbable(b.getLocation().clone().add(0, 1, 0).getBlock())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isUsingItem(Player p) {
		return p.getItemInUse() != null;
	}

}

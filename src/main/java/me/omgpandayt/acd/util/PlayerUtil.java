package me.omgpandayt.acd.util;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;

public class PlayerUtil {
	
	public static boolean isOnGround(Location loc) {
		double expand = 0.3;
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				if (loc.clone().add(x, -0.02, z).getBlock().getType().isSolid() && !loc.clone().add(x, 0.5001, z).getBlock().getType().isSolid()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isOnGround3(Location loc) {
		if(!BlockUtils.isAir(loc.getBlock().getType()))return true;  
		double expand = 0.3;
		for(double x=-expand;x<=expand;x+=expand) {
			for(double z=-expand;z<=expand;z+=expand) {
				if (!BlockUtils.isAir(loc.clone().add(x, -0.5001, z).getBlock().getType()) || !BlockUtils.isAir(loc.clone().add(x, 0, z).getBlock().getType()) || loc.clone().add(x, -0.5001, z).getBlock().getType() == Material.LADDER) {
					return true;
				}
			}
		}
		return false;
	}
	
    public static boolean isOnGround2(Location loc){
    	if(!BlockUtils.isAir(loc.getBlock().getType()))return true;  	
        int radius = 3;
        int radiusY = 2;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radiusY; y < radiusY; y++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = loc.getWorld().getBlockAt(loc.clone().add(x, y, z));
                    if (!BlockUtils.isAir(block.getType()) || !BlockUtils.isAir(block.getLocation().clone().add(0, 1, 0).getBlock().getType())) {
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
                    if (block.getType().isSolid() || block.getLocation().clone().add(0, 1, 0).getBlock().getType().isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

	public static int getFallHeight(Player p) {
		double depth = 80;
		int yHeight = (int) Math.floor(p.getPlayer().getLocation().getY());
		Location loc = p.getLocation().clone();
		loc.setY(yHeight);
		int fallHeight = 0;
		while(!loc.getBlock().getType().isSolid() && depth != 0) {
			yHeight--;
			loc.setY(yHeight);
			fallHeight++;
			depth--;
		}
		return fallHeight;
	}
	
	public static double getFallHeightDouble(Player p) {
		double depth = 80;
		double yHeight = Math.floor(p.getPlayer().getLocation().getY());
		Location loc = p.getLocation().clone();
		loc.setY(yHeight);
		double fallHeight = 0;
		while(!loc.getBlock().getType().isSolid() && depth != 0) {
			yHeight-=0.1;
			loc.setY(yHeight);
			fallHeight+=0.1;
			depth--;
		}
		return fallHeight;
	}

	public static boolean isValid(Player p) {
		PlayerData pd = PlayerDataManager.getPlayer(p);
		return pd.lastFlight > 20 && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) && !p.isDead();
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
			if(!b.getType().isAir()) {
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

	public static boolean isOnHoney(Location to) {
		for(Block b : BlockUtils.getBlocksBelow(to)) {
			if(b.getType() == Material.HONEY_BLOCK) {
				return true;
			}
		}
		return false;
	}

	public static float getPotionLevel(Player player, PotionEffectType jump) {
		if(player.hasPotionEffect(jump)) {
			return player.getPotionEffect(jump).getAmplifier();
		}
		return 0;
	}

	public static boolean isNearStair(Location to) {
		for(Block b : BlockUtils.getBlocksBelow(to)) {
			if(b instanceof Stairs) {
				return true;
			}
		}
		return false;
	}

	public static double getBaseGroundSpeed(Player player) {
        return 0.95 + (getPotionLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }
	
	public static double getBaseSpeed(Player player) {
        return 1.22 + (getPotionLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }

	public static boolean isInLiquid(Location l) {
		for(Block b : BlockUtils.getBlocksBelow(l)) {
			if(b.getLocation().clone().add(0, 1, 0).getBlock().isLiquid()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAboveIce(Location to) {
		for(Block b : BlockUtils.getBlocksBelow(to)) {
			if(BlockUtils.isIce(b)) {
				return true;
			}
		}
		return false;
	}

	public static ACDEntity getHoveredEntity(Player player) {
        List<Entity> nearbyE = player.getNearbyEntities(5, 4, 4);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

        for (Entity e : nearbyE) {
            if (e instanceof LivingEntity) {
                livingE.add((LivingEntity) e);
            }
        }

        BlockIterator bItr = new BlockIterator(player, 4);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {
                block = bItr.next();
                bx = block.getX();
                by = block.getY();
                bz = block.getZ();
                        // check for entities near this block in the line of sight
                        for (LivingEntity e : livingE) {
                                loc = e.getLocation();
                                ex = loc.getX();
                                ey = loc.getY();
                                ez = loc.getZ();
                                if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
                                		if(ACDEntityManager.getEntity(e) != null) return ACDEntityManager.getEntity(e);
                                        return ACDEntityManager.createEntity(e);
                                }
                        }
                }
		return null;

        }
	


}

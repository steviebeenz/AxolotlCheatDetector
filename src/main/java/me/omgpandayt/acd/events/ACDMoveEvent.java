package me.omgpandayt.acd.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class ACDMoveEvent {

	private PlayerMoveEvent event;
	private Player player;
	private Block[] blocksBelow, blocksBelowFrom, blocksBelowUp, blocksBelowDown;
	private PlayerData playerData;
	private Location to,from;
	private boolean groundFrom, groundTo, aboveLiquidsFrom, aboveLiquidsTo, aboveAreAir, isAboveSlime, isOnClimbableTo, isOnClimbableFrom, isOnHoneyTo, isOnHoneyFrom, isNearStair;
	private int fallHeight;
	private double fallHeightDouble, afterJumpSpeed, velocityXZ;
	
	public ACDMoveEvent(PlayerMoveEvent e) {
		this.player = e.getPlayer();
		this.to = e.getTo();
		this.from = e.getFrom();
		this.playerData = PlayerDataManager.getPlayer(player);
		this.event = e;
		this.blocksBelow = BlockUtils.getBlocksBelow(to);
		this.blocksBelowFrom = BlockUtils.getBlocksBelow(from);
		this.blocksBelowUp = BlockUtils.getBlocksBelow(to.clone().add(0, 1, 0));
		this.blocksBelowDown = BlockUtils.getBlocksBelow(to.clone().add(0, -1, 0));
		this.groundFrom = PlayerUtil.isOnGround(from);
		this.groundTo = PlayerUtil.isOnGround(to);
		this.aboveLiquidsFrom = PlayerUtil.isAboveLiquids(from);
		this.aboveLiquidsTo = PlayerUtil.isAboveLiquids(to);
		this.fallHeight = PlayerUtil.getFallHeight(player);
		this.fallHeightDouble = PlayerUtil.getFallHeightDouble(player);
		this.aboveAreAir = PlayerUtil.aboveAreAir(player);
		this.isAboveSlime = PlayerUtil.isAboveSlime(to);
		this.isOnClimbableFrom = PlayerUtil.isOnClimbable(from);
		this.isOnClimbableTo = PlayerUtil.isOnClimbable(to);
		this.isOnHoneyFrom = PlayerUtil.isOnHoney(from);
		this.isOnHoneyFrom = PlayerUtil.isOnHoney(to);
		this.isNearStair = PlayerUtil.isNearStair(to);
		this.afterJumpSpeed = 0.62 + 0.033 * (double) (PlayerUtil.getPotionLevel(player, PotionEffectType.SPEED));
		this.velocityXZ = player.getVelocity().getX() + player.getVelocity().getZ();
	}
	
	public PlayerMoveEvent getEvent() {
		return event;
	}
	public Player getPlayer() {
		return player;
	}
	public Block[] getBlocksBelow() {
		return blocksBelow;
	}
	public PlayerData getPlayerData() {
		return playerData;
	}

	public Block[] getBlocksBelowFrom() {
		return blocksBelowFrom;
	}

	public Location getFrom() {
		return from;
	}
	public Location getTo() {
		return to;
	}

	public Block[] getBlocksBelowUp() {
		return blocksBelowUp;
	}

	public Block[] getBlocksBelowDown() {
		return blocksBelowDown;
	}

	public boolean isOnGroundFrom() {
		return groundFrom;
	}
	public boolean isOnGround() {
		return groundTo;
	}

	public boolean isAboveLiquidsFrom() {
		return aboveLiquidsFrom;
	}
	public boolean isAboveLiquids() {
		return aboveLiquidsTo;
	}

	public double getFallHeightDouble() {
		return fallHeightDouble;
	}
	public int getFallHeight() {
		return fallHeight;
	}

	public boolean aboveAreAir() {
		return aboveAreAir;
	}

	public boolean isAboveSlime() {
		return isAboveSlime;
	}

	public boolean isOnClimbableTo() {
		return isOnClimbableTo;
	}
	public boolean isOnClimbableFrom() {
		return isOnClimbableFrom;
	}

	public boolean isOnHoneyTo() {
		return isOnHoneyTo;
	}
	public boolean isOnHoneyFrom() {
		return isOnHoneyFrom;
	}

	public int getGroundTicks() {
		return getPlayerData().groundTicks;
	}

	public int getAirTicks() {
		return getPlayerData().airTicks;
	}

	public boolean getIsNearStair() {
		return isNearStair;
	}

	public int getSinceSlimeTicks() {
		return getPlayerData().sinceSlimeTicks;
	}
	
	public int getSinceIceTicks() {
		return getPlayerData().sinceIceTicks;
	}

	public double getAfterJumpSpeed() {
		return afterJumpSpeed;
	}

	public int getSinceBlocksNearHead() {
		return getPlayerData().sinceBlocksNearHead;
	}

	public double getVelocityXZ() {
		return velocityXZ;
	}

	public int getSinceTeleportTicks() {
		return getPlayerData().sinceTeleportTicks;
	}

	public boolean isTakingVelocity() {
		return velocityXZ != 0;
	}
	
}

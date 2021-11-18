package me.omgpandayt.acd.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.util.ACDEntity;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.PlayerUtil;

public class ACDMoveEvent {

	private PlayerMoveEvent event;
	private Player player;
	private Block[] blocksBelow, blocksBelowFrom, blocksBelowUp, blocksBelowDown;
	private PlayerData playerData;
	private Location to, from;
	private boolean aboveFarmland, inWeb, groundFrom, groundTo, groundTo1, groundFrom1, aboveLiquidsFrom, aboveLiquidsTo, aboveAreAir, isAboveSlime, isOnClimbableTo, isOnClimbableFrom, isOnHoneyTo, isOnHoneyFrom, isNearStair, isInLiquid, aboveIce;
	private int fallHeight;
	private float fallHeightFloat, afterJumpSpeed, velocityXZ, deltaX, deltaZ, deltaY, deltaXZ, deltaXYZ, velocityX, velocityY, velocityZ, accel;
	private float deltaYaw, deltaPitch;
	private ACDEntity hoveredEntity;
	
	public ACDMoveEvent(PlayerMoveEvent e) {
		this.player = e.getPlayer();
		//WrappedPacketInFlying packet = new WrappedPacketInFlying(event.getNMSPacket());
		this.to = e.getTo();
		this.from = e.getFrom(); // new Location(player.getWorld(), packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch())
		this.playerData = PlayerDataManager.getPlayer(player);
		this.event = e;
		this.blocksBelow = BlockUtils.getBlocksBelow(to);
		this.blocksBelowFrom = BlockUtils.getBlocksBelow(from);
		this.blocksBelowUp = BlockUtils.getBlocksBelow(to.clone().add(0, 1, 0));
		this.blocksBelowDown = BlockUtils.getBlocksBelow(to.clone().add(0, -1, 0));
		this.groundFrom = PlayerUtil.isOnGround3(from);
		this.groundTo = PlayerUtil.isOnGround3(to);
		this.groundTo1 = PlayerUtil.isOnGround(to);
		this.groundFrom1 = PlayerUtil.isOnGround(from);
		this.aboveLiquidsFrom = PlayerUtil.isAboveLiquids(from);
		this.aboveLiquidsTo = PlayerUtil.isAboveLiquids(to);
		this.fallHeight = PlayerUtil.getFallHeight(player);
		this.fallHeightFloat = PlayerUtil.getFallHeightFloat(player);
		this.aboveAreAir = PlayerUtil.aboveAreAir(player);
		this.isAboveSlime = PlayerUtil.isAboveSlime(to);
		this.isOnClimbableFrom = PlayerUtil.isOnClimbable(from);
		this.isOnClimbableTo = PlayerUtil.isOnClimbable(to);
		this.isOnHoneyFrom = PlayerUtil.isOnHoney(from);
		this.isOnHoneyFrom = PlayerUtil.isOnHoney(to);
		this.isNearStair = PlayerUtil.isNearStair(to);
		this.afterJumpSpeed = (float)(0.62 + 0.033 * (float) (PlayerUtil.getPotionLevel(player, PotionEffectType.SPEED)));
		this.velocityX = (float)player.getVelocity().getX();
		this.velocityY = (float)player.getVelocity().getY();
		this.velocityZ = (float)player.getVelocity().getZ();
		this.velocityXZ = this.velocityX + this.velocityZ;
		this.deltaX = (float)Math.abs(to.getX() - from.getX());
		this.deltaZ = (float)Math.abs(to.getZ() - from.getZ());
		this.deltaY = (float)Math.abs(to.getY() - from.getY());
		this.deltaXZ = deltaX + deltaZ;
		this.deltaXYZ = deltaXZ + deltaY;
		this.isInLiquid = PlayerUtil.isInLiquid(to);
		this.aboveIce = PlayerUtil.isAboveIce(to);
		this.hoveredEntity = PlayerUtil.getHoveredEntity(player);
		this.deltaYaw = Math.abs(e.getFrom().getYaw() % 360F - e.getTo().getYaw() % 360);
		this.deltaPitch = Math.abs(e.getFrom().getPitch() - e.getTo().getPitch());
		this.accel = deltaXZ - playerData.prevDeltaXZ; // Not abs() since that would mean when you slow down it will be positive accel rather than negative accel
		this.aboveFarmland = PlayerUtil.isAboveBlock(Material.FARMLAND, to);
		this.inWeb = PlayerUtil.isInBlock(Material.COBWEB, to);
	}
	
	public float getAccel() {
		return accel;
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
	public boolean isOnGroundFrom1() {
		return groundFrom1;
	}
	public boolean isOnGround1() {
		return groundTo1;
	}

	public boolean isAboveLiquidsFrom() {
		return aboveLiquidsFrom;
	}
	public boolean isAboveLiquids() {
		return aboveLiquidsTo;
	}

	public float getFallHeightFloat() {
		return fallHeightFloat;
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

	public float getAfterJumpSpeed() {
		return afterJumpSpeed;
	}

	public int getSinceBlocksNearHead() {
		return getPlayerData().sinceBlocksNearHead;
	}

	public float getVelocityXZ() {
		return velocityXZ;
	}

	public int getSinceTeleportTicks() {
		return getPlayerData().sinceTeleportTicks;
	}

	public boolean isTakingVelocity() {
		return velocityXZ != 0;
	}

	public float getDeltaXZ() {
		return deltaXZ;
	}
	
	public float getDeltaXYZ() {
		return deltaXYZ;
	}

	public float getDeltaX() {
		return deltaX;
	}
	public float getDeltaZ() {
		return deltaZ;
	}

	public float getDeltaY() {
		return deltaY;
	}

	public int getAirTicksBeforeGround() {
		return getPlayerData().airTicksBeforeGround;
	}

	public boolean isInLiquids() {
		return isInLiquid;
	}

	public int getSinceWaterTicks() {
		return getPlayerData().sinceWaterTicks;
	}

	public boolean isAboveIce() {
		return aboveIce;
	}

	public float getVelocityX() {
		return velocityX;
	}
	public float getVelocityZ() {
		return velocityZ;
	}

	public ACDEntity getHoveredEntity() {
		return hoveredEntity;
	}

	public float getDeltaYaw() {
		return deltaYaw;
	}
	
	public float getDeltaPitch() {
		return deltaPitch;
	}

	public boolean isAboveFarmland() {
		return aboveFarmland;
	}

	public boolean isInWeb() {
		return inWeb;
	}

	public float getVelocityY() {
		return velocityY;
	}
	
}

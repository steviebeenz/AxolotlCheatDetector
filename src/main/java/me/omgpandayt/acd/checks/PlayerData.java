package me.omgpandayt.acd.checks;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.util.ACDAttack;

public class PlayerData {

	public static double det = 0.15, det2 = 0.15;
	public boolean isOnGround = true,
				   lastOnGround = true,
				   lastLastOnGround = true,
				   lastLastLastOnGround = true,
				   lastLastLastLastOnGround = true,
				   invOpen = false;
	public double dist = 0,
			
			lastPacketY = -1,
			lastLastPacketY = -1;

	private static final int M = 250000000;
	
	public Player p;
	public int flyALimiter = 0,
			   flyBLimiter = 0,
			   ticksInventoryOpen = 0,
			   flyBNFLimiter = 0,
			   flyCLimiter = 0,
			   flyDLimiter = 0,
			   invMoveALimiter = 0,
			   ticksLived = 0,
			   invalidAttackALimiter = 0,
			   speedCLimiter = 0,
			   speedBLimiter = 0,
			   speedDLimiter = 0,
			   jesusBLimiter = 0,
			   jesusCLimiter = 0,
			   jesusDLimiter = 0,
			   jesusELimiter = 0,
			   impactALimiter = 0,
			   badPacketsALimiter = 0,
			   motionALimiter = 0,
			   motionBLimiter = 0,
			   motionCLimiter = 0,
			   motionD2Limiter = 0,
			   sinceLevitationTicks = M,
			   groundSpoofBLimiter = 0,
			   groundSpoofCLimiter = 0,
			   ticksSinceHit = M,
			   placedBlocks = 0,
			   kicks = 0,
			   attackTicks = 0,
			   lastFlight = M,
			   lastAttack = M,
			   iceTicks = 0,
			   slimeTicks = 0,
			   onHorseTicks = M,
			   jesusFtb = 0,
			   groundTicks = 0,
			   airTicks = 0,
			   sinceSlimeTicks = M,
			   sinceIceTicks = M,
			   sinceBlocksNearHead = M,
			   ticksBlocksNearHead = 0,
			   sinceTeleportTicks = M,
			   sincePlacedBlock = M,
			   sinceWaterTicks = M,
			   fastTicks = 0,
			   ticksNoMove = 0,
			   ticksSinceWaterBlock = M,
			   ticksSinceClimbable = M,
			   ticksItemInUse = 0,
			   airTicksBeforeGround = 0,
			   ticksSinceEnderDragon = M;
	public ArrayList<ACDAttack> attacks = new ArrayList<ACDAttack>();
	public float lastPacketFD = 0,
				 lastPacketHP = 0,
				 realisticFD = 0,
				 velocityV = 0,
				 aimALimiter = 0,
				 motionDLimiter = 0,
				 lastDeltaPitch = 0;
	public boolean lastPacketNearBoat = false, alerts, invMoveWaitTick = false, speedEGround = true, decreaseHops = true, decreaseHops2 = true;
	public int ticksSinceRocket = 0;
	public double lastDeltaY, lastFallHeight, lastLastFallHeight, movementPackets, lastGroundY = 1e+305, lastGroundX = 1e+305, lastGroundZ = 1e+305, lowHops, highHops, lastYaw, lastPitch;
	public double prevDeltaXZ, prevDeltaX, prevDeltaZ;
	public PlayerData(Player p) {
		this.p = p;
		alerts = p.hasPermission("acd.notify");
	}

	public Player getPlayer() {
		return p;
	}
	
}

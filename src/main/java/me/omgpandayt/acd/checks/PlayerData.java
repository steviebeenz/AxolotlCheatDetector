package me.omgpandayt.acd.checks;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.util.ACDAttack;
import me.omgpandayt.acd.util.ACDEntity;

public class PlayerData {

	public static float det = 0.15f, det2 = 0.15f;
	public boolean invOpen = false;
	public float dist = 0,
			
			lastPacketY = -1,
			lastLastPacketY = -1;

	public static final int M = 250000000;
	
	public Player p;
	public int 
	
	
	           // BUFFER/LIMITER
			   flyALimiter = 0,
			   flyBLimiter = 0,
			   flyBNFLimiter = 0,
			   flyCLimiter = 0,
			   flyDLimiter = 0,
			   
			   veloALimiter = 0,
			   
			   invMoveALimiter = 0,
			   
			   speedCLimiter = 0,
			   speedBLimiter = 0,
			   speedDLimiter = 0,
			   
			   jesusBLimiter = 0,
			   jesusCLimiter = 0,
			   jesusDLimiter = 0,
			   jesusELimiter = 0,
			   
			   impactALimiter = 0,
			   
			   badPacketsALimiter = 0,
			   badPacketsALimiter2 = 0,
			   
			   motionALimiter = 0,
			   motionBLimiter = 0,
			   motionCLimiter = 0,
			   motionD2Limiter = 0,
			   motionD3Limiter = 0,
			   
			   groundSpoofBLimiter = 0,
			   groundSpoofCLimiter = 0,
			   
			   reachALimiter = 0,
			   
			   preVLAura = 0,
			   
			   motionFLimiter = 0,
			   
			   // DATA
			   
			   ticksLived = 0,
			   
			   ticksInventoryOpen = 0,
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
			   sinceLevitationTicks = M,
			   sinceFarmLand = M,
			   sinceEntityHit = M,
			   
			   fastTicks = 0,
			   ticksNoMove = 0,
			   ticksSinceWaterBlock = M,
			   ticksSinceClimbable = M,
			   ticksItemInUse = 0,
			   airTicksBeforeGround = 0,
			   ticksSinceEnderDragon = M,
			   hitTicks = M,
			   
			   badPacketsC_Detect1 = 0,
			   badPacketsC_Detect2 = 0,
			   badPacketsC_Detect3 = 0,
			   badPacketsC_Detect4 = 0;
									   ;
	
	public ArrayList<ACDAttack> attacks = new ArrayList<ACDAttack>();
	public float lastPacketHP = 0,
				 lastFD = 0,
				 lastFDR = 0,
				 velocityV = 0,
				 aimALimiter = 0,
				 motionDLimiter = 0,
				 lastDeltaPitch = 0,
				 auraALimiter = 0;
	public boolean lastPacketNearBoat = false, alerts, invMoveWaitTick = false, speedEGround = true, decreaseHops = true, decreaseHops2 = true;
	public int ticksSinceRocket = 0;
	public float lastDeltaY, lastLastDeltaY, motionDlastDifference, motionDlastLastDifference, lastFallHeight, lastLastFallHeight, lastGroundY = M, lastGroundX = M, lastGroundZ = M, lowHops, highHops, lastYaw, lastPitch;
	public long lastTime = System.currentTimeMillis(), lastTimeLong = System.currentTimeMillis(), movesLong, moves;
	public float prevDeltaXZ, prevDeltaX, prevDeltaZ;
	public long timeLastAttack = M * 10000, timeLastLastAttack = M * 10000;
	public ACDEntity lastHovered;
	public PlayerData(Player p) {
		this.p = p;
		alerts = p.hasPermission("acd.notify");
	}

	public Player getPlayer() {
		return p;
	}
	
}

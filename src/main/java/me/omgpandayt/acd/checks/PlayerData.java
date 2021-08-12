package me.omgpandayt.acd.checks;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.omgpandayt.acd.util.ACDAttack;

public class PlayerData {

	public boolean isOnGround = true,
				   lastOnGround = true,
				   lastLastOnGround = true,
				   lastLastLastOnGround = true,
				   lastLastLastLastOnGround = true,
				   invOpen = false;
	public double dist = 0,
			
			lastPacketY = -1,
			lastLastPacketY = -1;

	public Player p;
	public int flyALimiter = 0,
			   flyBLimiter = 0,
			   flyBNFLimiter = 0,
			   flyCLimiter = 0,
			   flyDLimiter = 0,
			   flyFLimiter = 0,
			   flyGLimiter = 0,
			   ticksLived = 0,
			   invalidAttackALimiter = 0,
			   speedCLimiter = 0,
			   speedBLimiter = 0,
			   speedELimiter = 0,
			   jesusBLimiter = 0,
			   jesusCLimiter = 0,
			   jesusDLimiter = 0,
			   jesusELimiter = 0,
			   impactALimiter = 0,
			   timerALimiter = 0,
			   groundSpoofBLimiter = 0,
			   groundSpoofCLimiter = 0,
			   ticksSinceHit = 250000000,
			   placedBlocks = 0,
			   kicks = 0,
			   attackTicks = 0,
			   lastFlight = 250000000,
			   lastAttack = 250000000,
			   iceTicks = 0,
			   slimeTicks = 0,
			   onHorseTicks = 250000000,
			   jesusFtb = 0,
			   groundTicks = 0,
			   airTicks = 0,
			   sinceSlimeTicks = 250000000,
			   sinceIceTicks = 250000000,
			   sinceBlocksNearHead = 250000000,
			   ticksBlocksNearHead = 0,
			   sinceTeleportTicks = 250000000;
	public ArrayList<ACDAttack> attacks = new ArrayList<ACDAttack>();
	public float lastPacketFD = 0,
				 lastPacketHP = 0,
				 realisticFD = 0,
				 velocityV = 0;
	public boolean lastPacketNearBoat = false, alerts, invMoveWaitTick = false;
	public int ticksSinceRocket = 0;
	public double lastDeltaY, lastFallHeight, lastLastFallHeight, movementPackets, lastGroundY;
	public float speedFLimiter;
	public PlayerData(Player p) {
		this.p = p;
		alerts = p.hasPermission("acd.notify");
	}

	public Player getPlayer() {
		return p;
	}
	
}

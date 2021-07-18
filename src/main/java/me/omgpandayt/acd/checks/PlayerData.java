package me.omgpandayt.acd.checks;

import org.bukkit.entity.Player;

public class PlayerData {

	public boolean isOnGround = true,
				   lastOnGround = true,
				   lastLastOnGround = true,
				   invOpen = false;
	public double dist = 0,
			
			lastPacketY = -1,
			lastLastPacketY = -1;
	
	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public boolean isLastOnGround() {
		return lastOnGround;
	}

	public void setLastOnGround(boolean lastOnGround) {
		this.lastOnGround = lastOnGround;
	}

	public boolean isLastLastOnGround() {
		return lastLastOnGround;
	}

	public void setLastLastOnGround(boolean lastLastOnGround) {
		this.lastLastOnGround = lastLastOnGround;
	}

	public Player p;
	public boolean invMoveWaitTick = false;
	public int flyALimiter = 0,
			   flyBLimiter = 0,
			   flyCLimiter = 0,
			   flyDLimiter = 0,
			   ticksLived = 0,
			   jesusBLimiter = 0,
			   jesusCLimiter = 0,
			   impactALimiter = 0,
			   ticksSinceHit = 250000000,
			   placedBlocks = 0;
	
	public PlayerData(Player p) {
		this.p = p;
	}

	public Player getPlayer() {
		return p;
	}

	public double getLastDist() {
		return dist;
	}
	
	public void addTicksSinceHit() {
		ticksSinceHit++;
	}
	
	public void setLastDist(double dist) {
		this.dist = dist;
	}
	
}

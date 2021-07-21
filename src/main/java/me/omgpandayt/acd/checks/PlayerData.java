package me.omgpandayt.acd.checks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Setter
@Getter
public class PlayerData {

    private boolean isOnGround = true;
    private boolean lastOnGround = true;
    private boolean lastLastOnGround = true;
    private boolean invOpen = false;
    private double dist = 0;

    private double lastPacketY = -1;
    private double lastLastPacketY = -1;
    private Player player;
    private boolean invMoveWaitTick = false;
    private int flyALimiter = 0;
    private int flyBLimiter = 0;
    private int flyCLimiter = 0;
    private int flyDLimiter = 0;
    private int ticksLived = 0;
    private int jesusBLimiter = 0;
    private int jesusCLimiter = 0;
    private int jesusDLimiter = 0;
    private int impactALimiter = 0;
    private int groundSpoofBLimiter = 0;
    private int groundSpoofCLimiter = 0;
    private int ticksSinceHit = 250000000;
    private int placedBlocks = 0;
    private int kicks = 0;
    private float lastPacketFD = 0;
    private float lastPacketHP = 0;
    private float realisticFD = 0;
    private boolean lastPacketNearBoat = false;
    private int ticksSinceRocket = 0;
    private boolean alert;

    public PlayerData(Player p) {
        this.player = p;
        alert = p.hasPermission("acd.notify");
    }


}

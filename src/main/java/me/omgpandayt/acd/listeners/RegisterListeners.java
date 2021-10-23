package me.omgpandayt.acd.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.combat.criticals.CriticalsA;
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackA;
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackB;
import me.omgpandayt.acd.checks.combat.invalidattack.InvalidAttackC;
import me.omgpandayt.acd.checks.combat.reach.ReachA;
import me.omgpandayt.acd.checks.combat.reach.ReachB;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fastladder.FastLadderA;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.fly.FlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyC;
import me.omgpandayt.acd.checks.movement.jump.JumpA;
import me.omgpandayt.acd.checks.movement.motion.MotionA;
import me.omgpandayt.acd.checks.movement.motion.MotionB;
import me.omgpandayt.acd.checks.movement.motion.MotionC;
import me.omgpandayt.acd.checks.movement.motion.MotionD;
import me.omgpandayt.acd.checks.movement.motion.MotionE;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.movement.speed.SpeedC;
import me.omgpandayt.acd.checks.movement.speed.SpeedD;
import me.omgpandayt.acd.checks.movement.speed.SpeedE;
import me.omgpandayt.acd.checks.movement.step.StepA;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofC;
import me.omgpandayt.acd.checks.player.invmove.InvMoveA;
import me.omgpandayt.acd.checks.player.jesus.JesusA;
import me.omgpandayt.acd.checks.player.jesus.JesusB;
import me.omgpandayt.acd.checks.player.jesus.JesusC;
import me.omgpandayt.acd.checks.player.jesus.JesusD;
import me.omgpandayt.acd.checks.player.jesus.JesusE;
import me.omgpandayt.acd.checks.player.jesus.JesusF;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownA;
import me.omgpandayt.acd.checks.world.badpackets.BadPacketsA;
import me.omgpandayt.acd.checks.world.badpackets.BadPacketsB;
import me.omgpandayt.acd.checks.world.fastplace.FastPlaceA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsB;
import me.omgpandayt.acd.command.AlertsCommand;
import me.omgpandayt.acd.events.ACDMoveEvent;
import me.omgpandayt.acd.util.BlockUtils;
import me.omgpandayt.acd.util.DataListeners;
import me.omgpandayt.acd.util.PlayerUtil;

public class RegisterListeners implements Listener {

	public static void register(JavaPlugin jp) {
		
		jp.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), jp);
		jp.getServer().getPluginManager().registerEvents(new TeleportListener(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new RegisterListeners(), jp);
		
		jp.getCommand("alerts").setExecutor(new AlertsCommand());
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof EnderDragon && e.getEntity() instanceof Player) {
			PlayerData pd = PlayerDataManager.getPlayer((Player)e.getEntity());
			pd.ticksSinceHit = 0;
			pd.ticksSinceEnderDragon = 0;
			return;
		}
		
		if(!(e.getDamager() instanceof Player)) return;
		
		if(bypass((Player)e.getDamager())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onDamage(e);
			
		}
		
		if(!(e.getEntity() instanceof Player)) return;
		
		PlayerDataManager.getPlayer((Player)e.getEntity()).ticksSinceHit = 0;
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		
		if(bypass((Player)e.getEntity())) return;
		PlayerData pd = PlayerDataManager.getPlayer((Player)e.getEntity());
		pd.ticksSinceHit = 0;
		
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
	
		if(bypass((Player)e.getPlayer())) return;
		
		for(Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onPlace(e);
			
		}
		
	}
	
	@EventHandler
    public void onPacketPlayReceive(PlayerMoveEvent e) {
		if(bypass((Player)e.getPlayer())/* || e.getPacketId() != PacketType.Play.Client.POSITION || e.getPacketId() != PacketType.Play.Client.POSITION_LOOK*/) return;
		
		ACDMoveEvent a = new ACDMoveEvent(e);
		PlayerData playerData = a.getPlayerData();
		Player player = e.getPlayer();
		if(playerData == null) return;
		DataListeners.onMove(a);
		
		playerData.ticksSinceWaterBlock++;
        for(Block b : a.getBlocksBelowUp()) {
			if(!b.getType().isAir()) {
				playerData.ticksSinceWaterBlock = 0;
			}
		}
        playerData.ticksSinceClimbable++;
        if(a.isOnClimbableFrom() || a.isOnClimbableTo())playerData.ticksSinceClimbable = 0;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onMove(a);
		}
		

		for(Entity entity : e.getPlayer().getNearbyEntities(2, 2, 2)) {
			if(entity instanceof Boat) {
				playerData.lastPacketNearBoat = true;
			}
		}
		
        playerData.ticksSinceHit++;
        playerData.ticksLived++;
        playerData.attackTicks++;
        playerData.lastFlight++;
        playerData.lastAttack++;
        playerData.sinceTeleportTicks++;
        playerData.sincePlacedBlock++;
        playerData.ticksSinceEnderDragon++;
        if(playerData.invOpen) playerData.ticksInventoryOpen++;
        else playerData.ticksInventoryOpen = 0;
        if(PlayerUtil.isUsingItem(player)) playerData.ticksItemInUse++;
        else playerData.ticksItemInUse = 0;
        if(player.isFlying()) playerData.lastFlight = 0;
        
        if(a.isOnGround() || player.hasPotionEffect(PotionEffectType.LEVITATION)) {
        	playerData.airTicks = 0;
        	if(!player.hasPotionEffect(PotionEffectType.LEVITATION)) {
        		playerData.groundTicks++;
        		playerData.lastGroundY = player.getLocation().getY();
        		playerData.realisticFD = 0;
        	} else {
        		playerData.sinceLevitationTicks = 0;
        	}
        } else {
        	playerData.groundTicks = 0;
        	playerData.airTicks++;
        	playerData.airTicksBeforeGround = playerData.airTicks;
        	float c = playerData.realisticFD;
        	playerData.realisticFD += playerData.lastPacketY - player.getLocation().getY();
        	if(playerData.realisticFD < c) playerData.realisticFD = c;
        }
        
        double iceTicks = playerData.iceTicks,
        		slimeTicks = playerData.slimeTicks,
        		blocksNearHead = playerData.ticksBlocksNearHead;
        
        if(a.isAboveIce()) {
        	playerData.sinceIceTicks = 0;
        	playerData.iceTicks++;
        } else {
        	playerData.sinceIceTicks++;
        	playerData.iceTicks = 0;
        }
        if(a.isAboveSlime()) {
        	playerData.sinceSlimeTicks = 0;
        	playerData.slimeTicks++;
        } else {
        	playerData.sinceSlimeTicks++;
        	playerData.slimeTicks = 0;
        }
        
        for(Block b : BlockUtils.getBlocksBelow(player.getLocation().clone().add(0, 2, 0))) {
        	if(b.getType() != Material.AIR && b.getType() != Material.CAVE_AIR) {
        		playerData.sinceBlocksNearHead = 0;
        		playerData.ticksBlocksNearHead = 0;
        		break;
        	}
        }
        playerData.sinceWaterTicks++;
        if(PlayerUtil.isInLiquid(player.getLocation())) {
        	playerData.sinceWaterTicks = 0;
        }
        if(playerData.ticksBlocksNearHead == blocksNearHead) {
        	playerData.sinceBlocksNearHead++;
        	playerData.ticksBlocksNearHead = 0;
        }
        playerData.onHorseTicks++;
        if(player.isInsideVehicle())playerData.onHorseTicks = 0;
        
        
        if(playerData.ticksLived % ACD.getInstance().getConfig().getDouble("checks.badpackets.a.decrease-time") == 0) {
        	double amm = ACD.getInstance().getConfig().getDouble("checks.badpackets.a.decrease-amount");
        	if(playerData.movementPackets > amm-1) {
        		playerData.movementPackets-=amm;
        	}
        }
        if(playerData.ticksLived % 5 == 0) {
        	if(playerData.decreaseHops && playerData.lowHops > 1) {
        		playerData.lowHops = (playerData.lowHops - 1 < 0 ? 0 : playerData.lowHops - 1);
        	}
        	if(playerData.decreaseHops2 && playerData.highHops > 1) {
        		playerData.highHops = (playerData.highHops - 1 < 0 ? 0 : playerData.highHops - 1);
        	}
        }
        if(playerData.ticksLived % Math.floor(ACD.getInstance().getConfig().getDouble("checks.invalidattack.b.decrease-time") * 20) == 0) {
        	if(playerData.attacks.size() > 6) {
        		playerData.attacks.remove(playerData.attacks.size()-1);
        	}
        }
        
        if(playerData.ticksLived % (ACD.getInstance().getConfig().getDouble("main.punish.limiter-removal-rate") * 20) == 0) {
        	if(playerData.jesusELimiter > 0) {
        		playerData.jesusELimiter--;
        	}
        	if(playerData.flyALimiter > 0) {
        		playerData.flyALimiter--;
        	}
        	if(playerData.flyBLimiter > 0) {
        		playerData.flyBLimiter--;
        	}
        	if(playerData.invMoveALimiter > 0) {
        		playerData.invMoveALimiter--;
        	}
        	if(playerData.motionBLimiter > 0) {
        		playerData.motionBLimiter--;
        	}
        	if(playerData.flyBNFLimiter > 0) {
        		playerData.flyBNFLimiter--;
        	}
        	if(playerData.flyCLimiter > 0) {
        		playerData.flyCLimiter--;
        	}
           	if(playerData.flyDLimiter > 0) {
        		playerData.flyDLimiter--;
        	}
        	if(playerData.jesusBLimiter > 0) {
        		playerData.jesusBLimiter--;
        	}
        	if(playerData.jesusCLimiter > 0) {
        		playerData.jesusCLimiter--;
        	}
        	if(playerData.badPacketsALimiter > 0) {
        		playerData.badPacketsALimiter--;
        	}
        	if(playerData.invalidAttackALimiter > 0) {
        		playerData.invalidAttackALimiter--;
        	}
        	if(playerData.speedBLimiter > 0) {
        		playerData.speedBLimiter--;
        	}
        	if(playerData.impactALimiter > 0) {
        		playerData.impactALimiter--;
        	}
        	if(playerData.jesusDLimiter > 0) {
        		playerData.jesusDLimiter--;
        	}
        	if(playerData.groundSpoofBLimiter > 0) {
        		playerData.groundSpoofBLimiter--;
        	}
        	if(playerData.groundSpoofCLimiter > 0) {
        		playerData.groundSpoofCLimiter--;
        	}
        	if(playerData.speedCLimiter > 0) {
        		playerData.speedCLimiter--;
        	}
        	if(playerData.motionBLimiter > 0) {
        		playerData.motionBLimiter--;
        	}
        	if(playerData.motionCLimiter > 0) {
        		playerData.motionCLimiter--;
        	}
        	if(playerData.motionD2Limiter > 0) {
        		playerData.motionD2Limiter--;
        	}
        } else if (playerData.ticksLived % ACD.getInstance().getConfig().getDouble("checks.fastplace.a.place-removal-rate-ticks") == 0) {
        	if(playerData.placedBlocks > 0) {
        		playerData.placedBlocks--;
        	}
        }
        playerData.prevDeltaX = a.getDeltaX();
        playerData.prevDeltaZ = a.getDeltaZ();
        playerData.lastDeltaY = a.getDeltaY();
    }
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		PlayerData playerData = PlayerDataManager.getPlayer(e.getPlayer());
		if(playerData == null)return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR)return;
		if(!e.getPlayer().isGliding()) return;
		if(e.getItem().getType() == Material.FIREWORK_ROCKET) {
		
			playerData.ticksSinceRocket = 0;
		
		}
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(bypass((Player)e.getWhoClicked())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClick(e);
			
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if(bypass((Player)e.getPlayer())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onInventoryClose(e);
			
		}
	}
	
	public boolean bypass(Player p) {
		PlayerData playerData = PlayerDataManager.getPlayer(p);
		if(playerData == null) return true;
		return playerData.ticksLived <= ACD.getInstance().getConfig().getDouble("main.punish.join-bypass-ticks") || p.hasPermission("acd.bypass");
	}

	public static void loadChecks() {
		
		new SpeedA();
		new SpeedB();
		new SpeedC();
		new SpeedD();
		new SpeedE();
		
		new MotionA();
		new MotionB();
		new MotionC();
		new MotionD();
		new MotionE();
		
		new StepA();
		
		new GroundSpoofA();
		new GroundSpoofB();
		new GroundSpoofC();
		
		new FlyA();
		new FlyB();
		new FlyC();
		
		new ReachA();
		new ReachB();
		
		new CriticalsA();
		
		new NoSlowdownA();
		
		new JesusA();
		new JesusB();
		new JesusC();
		new JesusD();
		new JesusE();
		new JesusF();
		
		new JumpA();
		
		new BadPacketsA();
		new BadPacketsB();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		new ImpossibleActionsA();
		new ImpossibleActionsB();
		
		new FastLadderA();
		
		new FastPlaceA();
		
		new InvalidAttackA();
		new InvalidAttackB();
		new InvalidAttackC();
	}
	
}

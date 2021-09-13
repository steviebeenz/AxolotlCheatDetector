package me.omgpandayt.acd.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
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

import io.github.retrooper.packetevents.PacketListenerAbstract;
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
import me.omgpandayt.acd.checks.movement.aim.AimA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fastladder.FastLadderA;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.fly.FlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyC;
import me.omgpandayt.acd.checks.movement.fly.FlyD;
import me.omgpandayt.acd.checks.movement.fly.FlyE;
import me.omgpandayt.acd.checks.movement.fly.FlyF;
import me.omgpandayt.acd.checks.movement.fly.FlyG;
import me.omgpandayt.acd.checks.movement.jump.JumpA;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.movement.speed.SpeedC;
import me.omgpandayt.acd.checks.movement.speed.SpeedD;
import me.omgpandayt.acd.checks.movement.speed.SpeedE;
import me.omgpandayt.acd.checks.movement.speed.SpeedF;
import me.omgpandayt.acd.checks.movement.speed.SpeedG;
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
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownB;
import me.omgpandayt.acd.checks.world.fastplace.FastPlaceA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsB;
import me.omgpandayt.acd.checks.world.timer.TimerA;
import me.omgpandayt.acd.command.AlertsCommand;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class RegisterListeners extends PacketListenerAbstract implements Listener {

	public static void register(JavaPlugin jp) {
		
		jp.getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), jp);
		jp.getServer().getPluginManager().registerEvents(new TeleportListener(), jp);
		
		jp.getServer().getPluginManager().registerEvents(new RegisterListeners(), jp);
		
		jp.getCommand("alerts").setExecutor(new AlertsCommand());
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDamage(EntityDamageByEntityEvent e) {
		
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
		
		PlayerDataManager.getPlayer((Player)e.getEntity()).ticksSinceHit = 0;
		
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
		if(bypass((Player)e.getPlayer())) return;
		
		for (Object obj : CheckManager.getRegisteredChecks()) {
			
			Check check = (Check)obj;
			
			check.onMove(new ACDMoveEvent(e));
			
		}
		
		
		PlayerData playerData = PlayerDataManager.getPlayer(e.getPlayer());
		if(playerData == null) return;

		for(Entity entity : e.getPlayer().getNearbyEntities(2, 2, 2)) {
			if(entity instanceof Boat) {
				playerData.lastPacketNearBoat = true;
			}
		}
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
		new SpeedF();
		new SpeedG();
		
		new StepA();
		
		new GroundSpoofA();
		new GroundSpoofB();
		new GroundSpoofC();
		
		new FlyA();
		new FlyB();
		new FlyC();
		new FlyD();
		new FlyE();
		new FlyF();
		new FlyG();
		
		new ReachA();
		new ReachB();
		
		new CriticalsA();
		
		new NoSlowdownA();
		new NoSlowdownB();
		
		new JesusA();
		new JesusB();
		new JesusC();
		new JesusD();
		new JesusE();
		new JesusF();
		
		new JumpA();
		
		new TimerA();
		
		new ElytraFlyA();
		new ElytraFlyB();
		
		new InvMoveA();
		
		new ImpossibleActionsA();
		new ImpossibleActionsB();
		
		new AimA();
		
		new FastLadderA();
		
		new FastPlaceA();
		
		new InvalidAttackA();
		new InvalidAttackB();
		new InvalidAttackC();
	}
	
}

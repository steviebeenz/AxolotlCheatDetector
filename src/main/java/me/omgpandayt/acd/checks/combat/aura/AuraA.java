package me.omgpandayt.acd.checks.combat.aura;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.events.ACDMoveEvent;

public class AuraA extends Check {

	public double hoverTicks;
	
	public AuraA(FileConfiguration config) {
		
		super("AuraA", false);
		
		this.hoverTicks = config.getDouble(path + "hover-ticks");
		
	}
	
	@Override
	public void onMove(ACDMoveEvent e) {
		
		Player p = e.getPlayer();
		PlayerData pd = e.getPlayerData();
		if(e.getHoveredEntity()==null)return;
		if(e.getHoveredEntity().e instanceof Player && e.getTo().distance(e.getFrom()) > 0.1) {
			e.getHoveredEntity().ticksHovered++;
			pd.lastHovered = e.getHoveredEntity();
		} else if (pd.lastHovered != null) {
			pd.lastHovered.ticksHovered = 0;
		}
		
	}
	
	@Override
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))return;
		Player p = (Player)e.getDamager();
		Player h = (Player)e.getEntity();
		PlayerData pd = PlayerDataManager.getPlayer(p);
		if(pd==null || pd.lastHovered == null)return;
		if(pd.lastHovered.e == h && pd.lastHovered.ticksHovered > hoverTicks && PlayerDataManager.getPlayer(h).lastYaw != h.getLocation().getYaw()) {
			pd.auraALimiter += pd.prevDeltaXZ * 10;
			if(pd.auraALimiter > 30) {
				pd.auraALimiter = 6;
				flag(p, "");
				cancelDamage(e);
			}
		} else {
			if(pd.auraALimiter > 0)
				pd.auraALimiter -= 1;
		}
		
	}
	
}

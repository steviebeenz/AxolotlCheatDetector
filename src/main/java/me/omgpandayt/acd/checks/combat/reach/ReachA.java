package me.omgpandayt.acd.checks.combat.reach;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.violation.Violations;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReachA extends Check {

    private static final String PATH = "checks.reach.a.";

    public ReachA() {
        super("ReachA", false);
    }

    @Override
    public void onDamage(EntityDamageByEntityEvent e) {

        Entity attacker = e.getDamager();
        Entity victim = e.getEntity();

        if (attacker instanceof Player) {
            Player a = ((Player) attacker);

            double r = a.getLocation().distance(victim.getLocation());

            if (r > config.getDouble(PATH + "max-reach") + (a.getGameMode() == GameMode.CREATIVE ? config.getDouble(PATH + "creative-increase") : 0)) {

                flag(a, "Reach (A)", " (VL" + (Violations.getViolations(this, a) + 1) + ") (REACH " + ((Math.floor(r * 100)) / 100) + ")");
                cancelDamage(e);
                a.teleport(a.getLocation());

            }
        }

    }

}

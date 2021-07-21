package me.omgpandayt.acd.checks;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.violation.Violations;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicBoolean;

public class Check {

    public String check, PATH;
    public boolean experimental;
    public int flagsToKick;

    public FileConfiguration config;

    public Check(String check, boolean experimental) {

        if (ACD.getInstance().getConfig().getBoolean("checks." + check.substring(0, check.length() - 1).toLowerCase() + "." + check.substring(check.length() - 1, check.length()).toLowerCase() + ".enabled")) {
            this.check = check;
            this.experimental = experimental;
            CheckManager.registerCheck(this);
            this.PATH = "checks." + check.substring(0, check.length() - 1).toLowerCase() + "." + check.substring(check.length() - 1, check.length()).toLowerCase() + ".";
        }

    }

    public String getName() {
        return check;
    }

    public void sync(final Runnable runnable) {
        final AtomicBoolean waiting = new AtomicBoolean(true);
        if (ACD.getInstance().isEnabled()) {
            Bukkit.getScheduler().runTask(ACD.getInstance(), () -> {
                runnable.run();
                waiting.set(false);
            });
        }
        while (waiting.get()) {
        }
    }

    public void flag(Player player, String check, String debug) {

        ACD.logPlayers(player.getName() + " failed " + check + " - " + debug);

        Violations.addViolation(this, player);

    }

    public void lagBack(PlayerMoveEvent e) {
        if (config.getBoolean("main.cancel-event"))
            e.getPlayer().teleport(e.getFrom());
    }

    public void cancelDamage(EntityDamageByEntityEvent e) {
        if (config.getBoolean("main.cancel-event"))
            e.setCancelled(true);
    }

    public void cancelPlace(BlockPlaceEvent e) {
        if (config.getBoolean("main.cancel-event"))
            e.setCancelled(true);
    }

    public void onMove(PlayerMoveEvent e) {

    }

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    public void punish(Player p) {
        PlayerData playerData = PlayerDataManager.getPlayer(p);
        if (playerData == null) return;
        playerData.setKicks(playerData.getKicks() + 1);
        Violations.clearViolations(this, p);
        String kickMessage = ACD.getInstance().getConfig().getString("main.kick-message");
        kickMessage = kickMessage.replace("[PREFIX]", ACD.PREFIX);
        kickMessage = kickMessage.replace("[NEWLINE]", "\n");
        if (playerData.getKicks() < config.getDouble("main.kicks-to-ban")) {
            if (config.getBoolean("main.kick-player")) {
                ACD.logPlayers(p.getName() + " was kicked for cheating (" + getName() + ")");

                p.getWorld().spawnEntity(p.getLocation(), EntityType.LIGHTNING);
                Entity entity = p.getWorld().spawnEntity(p.getLocation().clone().add(0, 1, 0), EntityType.AXOLOTL);
                entity.setInvulnerable(true);

                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));

                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        ((LivingEntity) entity).setHealth(0);
                    }
                };

                task.runTaskLater(ACD.getInstance(), 20);
            } else {
                ACD.logPlayers(p.getName() + " would have been kicked for cheating.");
            }
        } else {
            playerData.setKicks(0);
            if (config.getBoolean("main.ban-player")) {
                ACD.logPlayers(p.getName() + " was banned for cheating (" + getName() + ")");

                p.getWorld().spawnEntity(p.getLocation(), EntityType.LIGHTNING);
                Entity entity = p.getWorld().spawnEntity(p.getLocation().clone().add(0, 1, 0), EntityType.AXOLOTL);
                entity.setInvulnerable(true);

                String banMessage = ACD.getInstance().getConfig().getString("main.ban-command");

                banMessage = banMessage.replace("[PREFIX]", ACD.PREFIX);
                banMessage = banMessage.replace("[NEWLINE]", "\n");
                banMessage = banMessage.replace("[NEWLINE]", "\n");
                banMessage = banMessage.replace("[PLAYER]", p.getName());
                ACD.getInstance().getServer().dispatchCommand(ACD.getInstance().getServer().getConsoleSender(), ChatColor.translateAlternateColorCodes('&', banMessage));
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', kickMessage));

                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        ((LivingEntity) entity).setHealth(0);
                    }
                };

                task.runTaskLater(ACD.getInstance(), 20);
            } else {
                ACD.logPlayers(p.getName() + " would have been banned for cheating.");
            }
        }

    }

    public void onInventoryClick(InventoryClickEvent e) {

    }

    public void onInventoryOpen(InventoryOpenEvent e) {

    }

    public void onInventoryClose(InventoryCloseEvent e) {

    }

    public void onTick(Player p) {

    }

    public void onPlace(BlockPlaceEvent e) {

    }

}

package me.omgpandayt.acd;

import me.omgpandayt.acd.checks.Check;
import me.omgpandayt.acd.checks.CheckManager;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import me.omgpandayt.acd.checks.combat.criticals.CriticalsA;
import me.omgpandayt.acd.checks.combat.reach.ReachA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyA;
import me.omgpandayt.acd.checks.movement.elytrafly.ElytraFlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyA;
import me.omgpandayt.acd.checks.movement.fly.FlyB;
import me.omgpandayt.acd.checks.movement.fly.FlyC;
import me.omgpandayt.acd.checks.movement.fly.FlyD;
import me.omgpandayt.acd.checks.movement.speed.SpeedA;
import me.omgpandayt.acd.checks.movement.speed.SpeedB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofA;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofB;
import me.omgpandayt.acd.checks.player.groundspoof.GroundSpoofC;
import me.omgpandayt.acd.checks.player.invmove.InvMoveA;
import me.omgpandayt.acd.checks.player.jesus.JesusA;
import me.omgpandayt.acd.checks.player.jesus.JesusB;
import me.omgpandayt.acd.checks.player.jesus.JesusC;
import me.omgpandayt.acd.checks.player.jesus.JesusD;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownA;
import me.omgpandayt.acd.checks.player.noslowdown.NoSlowdownB;
import me.omgpandayt.acd.checks.world.fastplace.FastPlaceA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsA;
import me.omgpandayt.acd.checks.world.impossibleactions.ImpossibleActionsB;
import me.omgpandayt.acd.listeners.RegisterListeners;
import me.omgpandayt.acd.util.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ACD extends JavaPlugin {

    public static final String PREFIX = "&7[&cACD&7]";
    public static final String STARTUP = "&cPreventing your baby axolotls from cheating!";
    public static final String REBOOT = "&cNo longer preventing your baby axolotls from cheating!";
    public static String version = "";
    private static ACD instance;

    public static void sendMessage(CommandSender sender, Object message) {
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ACD.version + " " + message));
        } else {
            ACD.log(ChatColor.translateAlternateColorCodes('&', message + ""));
        }
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', version + " " + message));
    }

    public static ACD getInstance() {
        return instance;  //  the
    }

    public static void logPlayers(Object b) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = PlayerDataManager.getPlayer(p);
            if (playerData == null) {
                return;
            }
            if (p.hasPermission("acd.notify") && playerData.isAlert()) {

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', version + " " + b));

            }

        }

    }

    @Override
    public void onEnable() {
        log(STARTUP);

        FileConfiguration config = getConfig();

        this.saveDefaultConfig();
        config.options().copyDefaults(true);
        saveConfig();

        instance = this;

        version = getDescription().getVersion();

        RegisterListeners.register(instance);


        new SpeedA();
        new SpeedB();

        new GroundSpoofA();
        new GroundSpoofB();
        new GroundSpoofC();

        new FlyA();
        new FlyB();
        new FlyC();
        new FlyD();

        new ReachA();

        new CriticalsA();

        new NoSlowdownA();
        new NoSlowdownB();

        new JesusA();
        new JesusB();
        new JesusC();
        new JesusD();

        new ElytraFlyA();
        new ElytraFlyB();

        new InvMoveA();

        new ImpossibleActionsA();
        new ImpossibleActionsB();

        new FastPlaceA();


        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerDataManager.createPlayer(p);
            PlayerData playerData = PlayerDataManager.getPlayer(p);
            if (playerData != null) {
                playerData.setTicksLived(170);
            }
        }
        try {
            URL url = new URL("https://pastebin.com/raw/Zh3qqGri");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                if (!inputLine.equalsIgnoreCase(ACD.version))
                    ACD.logPlayers("Hey! You are running ACD " + ACD.version + ", the latest is currently ACD " + inputLine + "! Download latest at https://www.spigotmc.org/resources/axolotl-cheat-detector.94494/");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Object e : CheckManager.getRegisteredChecks()) {
            Check c = ((Check) e);
            c.config = config;
            c.flagsToKick = (int) c.config.getDouble("checks." + c.getName().substring(0, c.getName().length() - 1).toLowerCase() + "." + c.getName().substring(c.getName().length() - 1).toLowerCase() + ".flags-to-kick");
        }

        Bukkit.getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = PlayerDataManager.getPlayer(player);
                if (playerData == null) return;
                playerData.setTicksSinceHit(playerData.getTicksSinceHit() + 1);
                playerData.setTicksLived(playerData.getTicksLived() + 1);

                double deltaY = Math.abs(playerData.getLastPacketY() - player.getLocation().getY());

                if (!PlayerUtil.isOnGround(player.getLocation()))
                    playerData.setRealisticFD((float) (playerData.getRealisticFD() + deltaY));
                else playerData.setRealisticFD(0F);


                if (playerData.getTicksLived() % (config.getDouble("main.limiter-removal-rate") * 20) == 0) {
                    if (playerData.getFlyALimiter() > 0) {
                        playerData.setFlyALimiter(playerData.getFlyALimiter() - 1);
                    }
                    if (playerData.getFlyBLimiter() > 0) {
                        playerData.setFlyBLimiter(playerData.getFlyBLimiter() - 1);
                    }
                    if (playerData.getFlyCLimiter() > 0) {
                        playerData.setFlyCLimiter(playerData.getFlyCLimiter() - 1);
                    }
                    if (playerData.getFlyDLimiter() > 0) {
                        playerData.setFlyDLimiter(playerData.getFlyDLimiter() - 1);
                    }
                    if (playerData.getJesusBLimiter() > 0) {
                        playerData.setJesusBLimiter(playerData.getJesusBLimiter() - 1);
                    }
                    if (playerData.getJesusCLimiter() > 0) {
                        playerData.setJesusCLimiter(playerData.getJesusCLimiter() - 1);
                    }
                    if (playerData.getImpactALimiter() > 0) {
                        playerData.setImpactALimiter(playerData.getImpactALimiter() - 1);
                    }
                    if (playerData.getJesusDLimiter() > 0) {
                        playerData.setJesusDLimiter(playerData.getJesusCLimiter() - 1);
                    }
                    if (playerData.getGroundSpoofBLimiter() > 0) {
                        playerData.setGroundSpoofBLimiter(playerData.getGroundSpoofBLimiter() - 1);
                    }
                    if (playerData.getGroundSpoofCLimiter() > 0) {
                        playerData.setGroundSpoofCLimiter(playerData.getGroundSpoofCLimiter() - 1);
                    }
                } else if (playerData.getTicksLived() % config.getDouble("checks.fastplace.a.place-removal-rate-ticks") == 0
                        || playerData.getPlacedBlocks() > 0) {
                    playerData.setPlacedBlocks(playerData.getPlacedBlocks() - 1);
                }

                for (Object c : CheckManager.getRegisteredChecks()) {
                    ((Check) c).onTick(player);
                }
            }
        }, 1, 0);


    }

    @Override
    public void onDisable() {
        log(REBOOT);
        instance = null;
    }

}

package me.omgpandayt.acd.command;

import me.omgpandayt.acd.ACD;
import me.omgpandayt.acd.checks.PlayerData;
import me.omgpandayt.acd.checks.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            PlayerData playerData = PlayerDataManager.getPlayer((Player) sender);
            if (playerData == null) return false;

            playerData.setAlert(!playerData.isAlert());
            ACD.sendMessage(sender, "The axolotl will " + (playerData.isAlert() ? "now" : "no longer") + " show you cheaters!");
        } else {
            ACD.log("You can not run this command!");
        }
        return true;
    }

}

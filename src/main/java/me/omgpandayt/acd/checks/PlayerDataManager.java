package me.omgpandayt.acd.checks;

import org.bukkit.entity.Player;

import java.util.LinkedList;

public class PlayerDataManager {

    private static LinkedList<PlayerData> playerData = new LinkedList<>();

    private PlayerDataManager() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static void createPlayer(Player p) {
        playerData.add(new PlayerData(p));
    }

    public static void deletePlayer(PlayerData pd) {
        playerData.remove(pd);
    }

    public static PlayerData getPlayer(Player p) {
        for (PlayerData pd : playerData) {
            if (pd.getPlayer() == p) {
                return pd;
            }
        }
        return null;
    }

}

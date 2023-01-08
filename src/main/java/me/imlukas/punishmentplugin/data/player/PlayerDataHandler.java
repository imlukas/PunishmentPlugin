package me.imlukas.punishmentplugin.data.player;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.punishment.Punishment;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;
import me.imlukas.punishmentplugin.utils.storage.YMLBase;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerDataHandler extends YMLBase {

    private final PunishmentPlugin plugin;

    private final PunishmentDataHandler punishmentData;

    private final ConfigurationSection players;

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerDataHandler(PunishmentPlugin plugin) {
        super(plugin, "players.yml");
        this.plugin = plugin;
        this.punishmentData = plugin.getPunishmentDataHandler();
        this.players = getConfiguration().getConfigurationSection("players");
    }

    /** Reset all the player's punishments. */
    public void resetPlayer(UUID playerUUID) {
        players.set(playerUUID.toString(), null);
        playerDataMap.remove(playerUUID);
        setupPlayer(playerUUID);
    }

    /** Remove player from data map */
    public void removePlayer(UUID playerUUID) {
        playerDataMap.remove(playerUUID);
    }

    /** Load a player's data. */
    private Map<String, Integer> loadPlayer(UUID playerUUID) {
        ConfigurationSection playerSection = players.getConfigurationSection("" + playerUUID);
        Map<String, Integer> punishments = new HashMap<>();

        for (String punishment : playerSection.getKeys(false)) {
            punishments.put(punishment, playerSection.getInt(punishment));
        }

        return punishments;
    }


    /** Adds a player to the config if he doesn't exist yet.
     *  Otherwise, it will load the player */
     public void setupPlayer(UUID uuid) {

        Map<String, Integer> punishments = new HashMap<>();

        for (Punishment punishment : punishmentData.getPunishments()) {


            if (players.contains(uuid.toString())) {
                punishments = loadPlayer(uuid);
                break;
            }

            punishments.put(punishment.getName(), 0);
            players.set(uuid + "." + punishment.getName(), 0);
        }

        PlayerData playerData = new PlayerData(plugin, uuid, punishments);

        playerDataMap.put(uuid, playerData);
        save();
    }

    /**
     * Get player data from player UUID
     * @return PlayerData
     */
    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }
    /**
     * Get player data from an offline player's UUID
     * @return PlayerData
     */
    public PlayerData getPlayerData(OfflinePlayer player) {
        return playerDataMap.get(player.getUniqueId());
    }
    /**
     * Get player data from player's UUID
     * @return PlayerData
     */
    public PlayerData getPlayerData(Player player) {
       return playerDataMap.get(player.getUniqueId());
    }

    /**
     * Gets a punishment based on punishment name
     * @see PunishmentDataHandler#getPunishment(String)
     * @return Punishment
     */
    public Punishment getPunishment(String punishmentName) {
        return punishmentData.getPunishment(punishmentName);
    }


    public void updateWarnings(UUID playerUUID, String punishmentName, int warning) {
        players.set(playerUUID + "." + punishmentName, warning);
        save();
    }
}

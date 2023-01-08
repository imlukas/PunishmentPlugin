package me.imlukas.punishmentplugin.data.player;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;

import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private final Map<String, Integer> punishments;
    private final PlayerDataHandler playerDataHandler;
    private final PunishmentDataHandler punishmentDataHandler;
    private final PunishmentPlugin plugin;

    private final UUID playerUUID;

    public PlayerData(PunishmentPlugin plugin, UUID uuid, Map<String, Integer> punishments) {
        this.plugin = plugin;
        this.playerDataHandler = plugin.getPlayerDataHandler();
        this.punishmentDataHandler = plugin.getPunishmentDataHandler();

        this.playerUUID = uuid;
        this.punishments = punishments;
    }


    public Map<String, Integer> getPunishments() {
        return punishments;
    }

    public int getWarning(String punishmentName) {
        return punishments.get(punishmentName);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setWarning(String punishmentName, int warning) {
        punishments.put(punishmentName, warning);
        playerDataHandler.updateWarnings(playerUUID, punishmentName, warning);
    }
}

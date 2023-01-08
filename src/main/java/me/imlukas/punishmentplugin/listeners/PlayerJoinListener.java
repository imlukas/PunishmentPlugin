package me.imlukas.punishmentplugin.listeners;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.player.PlayerDataHandler;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerDataHandler playerData;

    public PlayerJoinListener(PunishmentPlugin plugin) {
        this.playerData = plugin.getPlayerDataHandler();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerData.setupPlayer(player.getUniqueId());


    }
}

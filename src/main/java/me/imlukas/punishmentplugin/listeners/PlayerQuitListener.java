package me.imlukas.punishmentplugin.listeners;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.player.PlayerDataHandler;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerDataHandler playerData;

    public PlayerQuitListener(PunishmentPlugin plugin) {
        this.playerData = plugin.getPlayerDataHandler();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        playerData.removePlayer(player.getUniqueId());


    }
}

package me.imlukas.punishmentplugin.commands;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.player.PlayerData;
import me.imlukas.punishmentplugin.data.player.PlayerDataHandler;
import me.imlukas.punishmentplugin.data.punishment.Punishment;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;
import me.imlukas.punishmentplugin.utils.storage.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class PunishmentDataCommand implements CommandExecutor {

    private final PunishmentPlugin plugin;
    private final MessagesFile messages;
    private final PunishmentDataHandler punishmentDataHandler;
    private final PlayerDataHandler playerDataHandler;

    public PunishmentDataCommand(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.punishmentDataHandler = plugin.getPunishmentDataHandler();
        this.playerDataHandler = plugin.getPlayerDataHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            messages.sendMessage(sender, "command.invalid-args");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (target == null) {
            messages.sendMessage(sender, "punishment.player-not-found");
            return true;
        }

        PlayerData playerData = playerDataHandler.getPlayerData(target);

        messages.sendMessage(sender, "punishment.data.header", (message) -> message.replace("%player%", target.getName()));
        for (Map.Entry<String, Integer> entry : playerData.getPunishments().entrySet()) {
            messages.sendMessage(sender, "punishment.data.entry", (message) -> message
                    .replace("%punishment%", entry.getKey())
                    .replace("%warnings%", entry.getValue().toString()));
        }
        messages.sendMessage(sender, "punishment.data.footer", (message) -> message.replace("%player%", target.getName()));
        return true;
    }
}

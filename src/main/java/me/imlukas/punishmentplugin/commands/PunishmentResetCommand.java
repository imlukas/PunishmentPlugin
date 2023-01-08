package me.imlukas.punishmentplugin.commands;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.data.player.PlayerDataHandler;
import me.imlukas.punishmentplugin.utils.storage.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentResetCommand implements CommandExecutor {

    private final MessagesFile messages;
    private final PlayerDataHandler playerDataHandler;

    public PunishmentResetCommand(PunishmentPlugin plugin) {
        this.messages = plugin.getMessages();
        this.playerDataHandler = plugin.getPlayerDataHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            messages.sendMessage(sender, "command.invalid-args");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            messages.sendMessage(sender, "punishment.player-not-found");
            return true;
        }

        playerDataHandler.resetPlayer(target.getUniqueId());
        messages.sendMessage(sender, "punishment.reset");
        return true;
    }
}

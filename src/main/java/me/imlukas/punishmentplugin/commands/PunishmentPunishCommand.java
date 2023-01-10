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

public class PunishmentPunishCommand implements CommandExecutor {

    private final PunishmentPlugin plugin;
    private final MessagesFile messages;
    private final PunishmentDataHandler punishmentDataHandler;
    private final PlayerDataHandler playerDataHandler;

    public PunishmentPunishCommand(PunishmentPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.punishmentDataHandler = plugin.getPunishmentDataHandler();
        this.playerDataHandler = plugin.getPlayerDataHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            messages.sendMessage(sender, "command.invalid-args");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (target == null) {
            messages.sendMessage(sender, "punishment.player-not-found");
            return true;
        }

        String targetName = target.getName() + " ";
        String punishmentName = args[1];

        if (punishmentDataHandler.getPunishment(args[1]) == null) {
            messages.sendMessage(sender, "punishment.not-found");
            return true;
        }
        
        String reason = "No reason specified";
        if (args.length > 2) {
            reason = args[2];
        }

        Punishment punishment = punishmentDataHandler.getPunishment(punishmentName);

        PlayerData playerData = playerDataHandler.getPlayerData(target);


        int warning = playerData.getWarning(punishmentName);
        String type = punishment.getType(warning) + " ";
        int time = punishment.getTime(warning);
        String format = punishment.getFormat(warning) + " ";

        if (time == -1 || type.equalsIgnoreCase("kick") || type.equalsIgnoreCase("warn")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type + targetName + reason);
            return true;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type + targetName + time + format + reason);

        if (warning < 2) {
            playerData.setWarning(punishmentName, warning + 1);
        }

        sender.sendMessage("Target has been punished!");
        return true;
    }
}

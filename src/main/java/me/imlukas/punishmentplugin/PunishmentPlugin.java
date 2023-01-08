package me.imlukas.punishmentplugin;

import lombok.Getter;
import me.imlukas.punishmentplugin.commands.PunishmentDataCommand;
import me.imlukas.punishmentplugin.commands.PunishmentPunishCommand;
import me.imlukas.punishmentplugin.commands.PunishmentResetCommand;
import me.imlukas.punishmentplugin.data.player.PlayerDataHandler;
import me.imlukas.punishmentplugin.data.punishment.PunishmentDataHandler;
import me.imlukas.punishmentplugin.listeners.PlayerJoinListener;
import me.imlukas.punishmentplugin.listeners.PlayerQuitListener;
import me.imlukas.punishmentplugin.utils.command.impl.CommandManager;
import me.imlukas.punishmentplugin.utils.storage.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class PunishmentPlugin extends JavaPlugin {

    private PunishmentDataHandler punishmentDataHandler;
    private PlayerDataHandler playerDataHandler;
    private MessagesFile messages;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        messages = new MessagesFile(this);
        commandManager = new CommandManager(this);
        punishmentDataHandler = new PunishmentDataHandler(this);
        playerDataHandler = new PlayerDataHandler(this);

        System.out.println("Registered Data classes");

        Bukkit.getPluginCommand("punish").setExecutor(new PunishmentPunishCommand(this));
        Bukkit.getPluginCommand("reset").setExecutor(new PunishmentResetCommand(this));
        Bukkit.getPluginCommand("data").setExecutor(new PunishmentDataCommand(this));

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

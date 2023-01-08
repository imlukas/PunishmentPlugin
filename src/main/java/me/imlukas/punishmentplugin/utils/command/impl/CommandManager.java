package me.imlukas.punishmentplugin.utils.command.impl;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.utils.command.BaseCommand;
import me.imlukas.punishmentplugin.utils.command.SimpleCommand;
import me.imlukas.punishmentplugin.utils.command.comparison.ComparisonResult;
import me.imlukas.punishmentplugin.utils.command.comparison.ComparisonResultFull;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandManager {

    private static CommandMap commandMap;
    private static Constructor<PluginCommand> pluginCommandConstructor;

    static {
        try {
            Server server = Bukkit.getServer();
            Field commandMapField = server.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            commandMap = (CommandMap) commandMapField.get(server);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class,
                    Plugin.class);
            pluginCommandConstructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private final Set<String> registeredBaseCommands = new HashSet<>();
    private final Map<String, SimpleCommand> commands = new HashMap<>();
    private final PunishmentPlugin main;

    public CommandManager(PunishmentPlugin main) {
        this.main = main;
    }

    public void register(SimpleCommand command) {
        if (command.getIdentifier() == null) {
            System.err.println("Command " + command.getClass().getSimpleName() + " has no identifier!");
            return;
        }

        System.out.println("Registered command " + command.getClass().getSimpleName());

        if (command.getIdentifier().startsWith("*")) {
            throw new IllegalArgumentException("Command identifier cannot start with *");
        }

        commands.put(command.getIdentifier(), command);

        String base = getBaseCommand(command.getIdentifier());

        System.out.println(command.getIdentifier() + "'s base command is " + base);

        if (!registeredBaseCommands.contains(base)) {
            System.out.println(base + " was not registered as a command, registering..");

            try {
                PluginCommand pluginCommand = pluginCommandConstructor.newInstance(base, main);

                BaseCommand baseCommand = new BaseCommand(main);

                pluginCommand.setExecutor(baseCommand);
                pluginCommand.setTabCompleter(baseCommand);

                commandMap.register(base, pluginCommand);

            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }

            System.out.println(base + " was registered as a command");
            registeredBaseCommands.add(base);
        }
    }

    public ComparisonResultFull fullComparison(String identifier) {
        ComparisonResultFull full = new ComparisonResultFull(commands);
        full.match(identifier);
        return full;
    }

    public SimpleCommand get(String identifier) {
        ComparisonResultFull full = new ComparisonResultFull(commands);
        return full.match(identifier);
    }

    public List<String> tabComplete(String identifier) {
        ComparisonResult result = new ComparisonResult(commands);
        return result.tabComplete(identifier);
    }

    public SimpleCommand get(String name, String... args) {
        String identifier = String.join(".", name, String.join(".", args));

        return get(identifier);
    }

    private String getBaseCommand(String identifier) {
        int index = identifier.indexOf(".");
        return index == -1 ? identifier : identifier.substring(0, index);
    }

}

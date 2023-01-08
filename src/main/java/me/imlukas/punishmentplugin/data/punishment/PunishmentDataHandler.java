package me.imlukas.punishmentplugin.data.punishment;

import me.imlukas.punishmentplugin.PunishmentPlugin;
import me.imlukas.punishmentplugin.utils.storage.YMLBase;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class PunishmentDataHandler extends YMLBase {

    private final ConfigurationSection punishments;

    private final Map<String, Punishment> punishmentMap = new HashMap<>();


    public PunishmentDataHandler(PunishmentPlugin plugin) {
        super(plugin, "punishments.yml");
        this.punishments = getConfiguration().getConfigurationSection("punishments");
        loadPunishments();

    }

    /** Load all punishments from the config. */
    public void loadPunishments() {
        for (String key : punishments.getKeys(false)) {
            ConfigurationSection punishmentSection = punishments.getConfigurationSection(key);

            List<Integer> times = new ArrayList<>();
            List<String> formats = new ArrayList<>();
            List<String> types = new ArrayList<>();

            for (String warning : punishmentSection.getKeys(false)) {
                ConfigurationSection warningSection = punishmentSection.getConfigurationSection(warning);
                times.add(warningSection.getInt("time"));
                formats.add(warningSection.getString("format"));
                types.add(warningSection.getString("type"));

            }
            Punishment punishment = new Punishment(key, times, formats, types);
            punishmentMap.put(key, punishment);
        }
    }

    /** Get all punishments */
    public List<Punishment> getPunishments() {
        return new ArrayList<>(punishmentMap.values());
    }

    /** Get a punishment by name */
    public Punishment getPunishment(String name) {

        for (Punishment punishment : punishmentMap.values()) {
            if (punishment.getName().equalsIgnoreCase(name)) {
                return punishment;
            }
        }
        return null;
    }

    /** Get all punishments' names */
    public List<String> getPunishmentNames() {
        List<String> punishmentNames = new ArrayList<>();

        for (Punishment punishment : getPunishments()) {
            punishmentNames.add(punishment.getName());
        }

        return punishmentNames;
    }


}

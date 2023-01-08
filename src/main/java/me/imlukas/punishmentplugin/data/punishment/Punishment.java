package me.imlukas.punishmentplugin.data.punishment;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Punishment {


    private final Map<Integer, Integer> time = new HashMap<>();
    private final Map<Integer, String> format = new HashMap<>();
    private final Map<Integer, String> type = new HashMap<>();
    private final String punishmentName;

    public Punishment(String punishmentName, List<Integer> time, List<String> format, List<String> type) {
        this.punishmentName = punishmentName;
        for (int i = 0; i < time.size(); i++) {
            this.time.put(i, time.get(i));
            this.format.put(i, format.get(i));
            this.type.put(i, type.get(i));
        }
    }

    public String getName() {
        return punishmentName;
    }

    public int getTime(int warning) {
        return time.get(warning);
    }

    public String getFormat(int warning) {
        return format.get(warning);
    }

    public String getType(int warning) {
        return type.get(warning);
    }

    public int getMaxWarnings() {
        return time.size();
    }

    public Map<Map<Integer, String>, String> getAll(int warning) {

        Map<Map<Integer, String>, String> all = new HashMap<>();

        Map<Integer, String> timeAndFormat = new HashMap<>();

        timeAndFormat.put(time.get(warning), format.get(warning));

        all.put(timeAndFormat, type.get(warning));

        return all;
    }
}

package com.run4fight.client.core.triggers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.run4fight.client.model.CooldownModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CooldownData {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Path dataFile;

    private static class Entry {
        String id;
        long endMs;
    }

    private static List<Entry> entries = List.of();

    public static void init(Path configDir) {
        dataFile = configDir.resolve("beemod").resolve("cooldowns.json");
        load();
    }

    public static void save(CooldownModel model) {
        entries = entries.stream()
                .filter(e -> !e.id.equals(model.getId()))
                .toList();

        Entry entry = new Entry();
        entry.id = model.getId();
        entry.endMs = System.currentTimeMillis() + model.getRemainingMs();

        entries = new java.util.ArrayList<>(entries);
        entries.add(entry);
        write();
    }

    public static void load() {
        if (dataFile == null || !Files.exists(dataFile)) return;
        try {
            String json = Files.readString(dataFile);
            entries = GSON.fromJson(json, Entry[].class) == null
                    ? List.of()
                    : List.of(GSON.fromJson(json, Entry[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Long> getEndMs(String id) {
        long now = System.currentTimeMillis();
        return entries.stream()
                .filter(e -> e.id.equals(id) && e.endMs > now)
                .map(e -> e.endMs)
                .findFirst();
    }

    public static void remove(String id) {
        entries = entries.stream()
                .filter(e -> !e.id.equals(id))
                .toList();
        write();
    }

    private static void write() {
        try {
            Files.createDirectories(dataFile.getParent());
            Files.writeString(dataFile, GSON.toJson(entries));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
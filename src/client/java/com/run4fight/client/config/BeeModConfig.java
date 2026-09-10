package com.run4fight.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class BeeModConfig {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("beemod.json");

    private static BeeModConfig INSTANCE;

    private boolean showOverlay = true;
    private boolean showActionbar = true;

    public boolean isShowOverlay() {
        return showOverlay;
    }

    public boolean isShowActionbar() {
        return showActionbar;
    }

    public void setShowOverlay(boolean showOverlay) {
        this.showOverlay = showOverlay;
    }

    public void setShowActionbar(boolean showActionbar) {
        this.showActionbar = showActionbar;
    }

    public static BeeModConfig get() {
        if (INSTANCE == null) {
            load();
        }

        return INSTANCE;
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            INSTANCE = new BeeModConfig();
            save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            INSTANCE = GSON.fromJson(reader, BeeModConfig.class);

            if (INSTANCE == null) {
                INSTANCE = new BeeModConfig();
            }

        } catch (IOException | RuntimeException e) {
            System.err.println("Failed to load BeeMod config:");
            e.printStackTrace();

            INSTANCE = new BeeModConfig();
        }
    }

    public static void save() {
        if (INSTANCE == null) {
            return;
        }

        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(INSTANCE, writer);
            }

        } catch (IOException e) {
            System.err.println("Failed to save BeeMod config:");
            e.printStackTrace();
        }
    }
}

package com.run4fight.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BeeModConfig {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("beemod.json");

    private static BeeModConfig INSTANCE;

    // Not positional and read outside any overlay (InGameHudMixin),
    // so it stays a global flag rather than overlay settings.
    private boolean showActionbar = true;

    /** Per-overlay settings, keyed by overlay key. */
    private Map<String, OverlaySettings> overlays = new HashMap<>();

    // Pre-overlays-map layout, boxed so Gson leaves them null when
    // absent. Folded into `overlays` by migrateLegacy() on load, then
    // dropped from the file. Remove once no old config is in the wild.
    private Integer buffOverlayX;
    private Integer buffOverlayY;
    private Integer wealthClockOverlayX;
    private Integer wealthClockOverlayY;
    private Boolean showOverlay;
    private Boolean showCooldown;

    public boolean isShowActionbar() {
        return showActionbar;
    }

    public void setShowActionbar(boolean showActionbar) {
        this.showActionbar = showActionbar;
    }

    /**
     * Settings for {@code key}, created from the given defaults on first use.
     */
    public OverlaySettings overlay(String key, int defaultX, int defaultY) {
        if (overlays == null) {
            overlays = new HashMap<>();
        }

        return overlays.computeIfAbsent(
                key,
                ignored -> new OverlaySettings(defaultX, defaultY)
        );
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

        if (INSTANCE.migrateLegacy()) {
            save();
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

    /**
     * Moves values written by the pre-overlays-map layout into
     * {@link #overlays} so saved positions and toggles survive the update.
     *
     * <p>Keys are literals on purpose - config must not depend on gui.
     *
     * @return true if anything was migrated and the file should be rewritten
     */
    private boolean migrateLegacy() {
        boolean migrated = migrateInto(
                "buff",
                buffOverlayX,
                buffOverlayY,
                showOverlay
        );

        buffOverlayX = null;
        buffOverlayY = null;
        showOverlay = null;

        migrated |= migrateInto(
                "cooldown",
                wealthClockOverlayX,
                wealthClockOverlayY,
                showCooldown
        );

        wealthClockOverlayX = null;
        wealthClockOverlayY = null;
        showCooldown = null;

        return migrated;
    }

    /** No-op (and creates no entry) when there is nothing to migrate. */
    private boolean migrateInto(
            String key,
            Integer x,
            Integer y,
            Boolean enabled
    ) {
        if (x == null && y == null && enabled == null) {
            return false;
        }

        // Defaults are irrelevant here - every field is about to be
        // overwritten by the legacy value or left at the class default.
        OverlaySettings settings = overlay(key, 10, 10);

        if (x != null) {
            settings.setX(x);
        }

        if (y != null) {
            settings.setY(y);
        }

        if (enabled != null) {
            settings.setEnabled(enabled);
        }

        return true;
    }
}

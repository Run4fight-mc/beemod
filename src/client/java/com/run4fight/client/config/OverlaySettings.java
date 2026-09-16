package com.run4fight.client.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Persisted settings for a single overlay, stored in
 * {@link BeeModConfig} under the overlay's key.
 *
 * <p>Adding a new overlay needs no change here: position and the
 * enable flag are generic, and anything else the overlay wants to
 * remember goes into {@link #toggles} under a name it picks itself.
 */
public class OverlaySettings {

    private int x = 10;
    private int y = 10;

    private boolean enabled = true;

    private Map<String, Boolean> toggles = new HashMap<>();

    /**
     * Required by Gson: without it, deserialization skips the field
     * initializers above and keys missing from the file end up
     * {@code false}/{@code null} instead of defaulted.
     */
    public OverlaySettings() {
    }

    public OverlaySettings(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isToggled(String key, boolean fallback) {
        if (toggles == null) {
            return fallback;
        }

        return toggles.getOrDefault(key, fallback);
    }

    public void setToggled(String key, boolean value) {
        if (toggles == null) {
            toggles = new HashMap<>();
        }

        toggles.put(key, value);
    }
}

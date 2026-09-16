package com.run4fight.client.core.overlay;

import com.run4fight.client.config.OverlaySettings;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * A named ON/OFF setting an overlay exposes to its config screen.
 *
 * <p>Backed by arbitrary accessors, so an option can live in the
 * overlay's own {@link OverlaySettings} toggle map or on a global
 * {@code BeeModConfig} field - the screen doesn't care which.
 */
public record OverlayOption(
        String label,
        BooleanSupplier getter,
        Consumer<Boolean> setter
) {

    public boolean value() {
        return getter.getAsBoolean();
    }

    public void toggle() {
        setter.accept(!value());
    }

    /** An option stored in the overlay's own settings under {@code key}. */
    public static OverlayOption of(
            OverlaySettings settings,
            String key,
            String label,
            boolean fallback
    ) {
        return new OverlayOption(
                label,
                () -> settings.isToggled(key, fallback),
                value -> settings.setToggled(key, value)
        );
    }
}

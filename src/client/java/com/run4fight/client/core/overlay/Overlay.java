package com.run4fight.client.core.overlay;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.config.OverlaySettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Base class for every BeeMod HUD overlay.
 *
 * <p>A subclass supplies its content ({@link #draw}), its size
 * ({@link #measure}) and optionally some toggles ({@link #getOptions});
 * position storage, the enable flag, HUD registration, the config screen
 * and drag-to-position all come from the framework.
 *
 * <p>{@link #draw} is the single render path: the HUD and the config
 * screen preview both go through it, so they cannot drift apart.
 */
public abstract class Overlay implements HudOverlay {

    private final Identifier id;
    private final String key;
    private final String displayName;
    private final int defaultX;
    private final int defaultY;

    protected Overlay(
            String key,
            String displayName,
            int defaultX,
            int defaultY
    ) {
        this.key = key;
        this.displayName = displayName;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.id = Identifier.of("beemod", key + "_overlay");
    }

    @Override
    public final Identifier getId() {
        return id;
    }

    /** Label shown in the feature list and as the config screen title. */
    public final String getDisplayName() {
        return displayName;
    }

    /** Resolved lazily so overlays can be built before the config loads. */
    public final OverlaySettings settings() {
        return BeeModConfig.get().overlay(key, defaultX, defaultY);
    }

    /**
     * Draws this overlay's content anchored at {@code (x, y)}.
     *
     * @param preview true when drawing inside the config screen. Overlays
     *                with no live data should draw a placeholder so they
     *                stay visible - and therefore draggable.
     */
    protected abstract void draw(
            DrawContext context,
            TextRenderer textRenderer,
            int x,
            int y,
            boolean preview,
            double mouseX,
            double mouseY
    );

    /** Size of what {@link #draw} would paint, for hit-testing and clamping. */
    public abstract OverlayBounds measure(
            TextRenderer textRenderer,
            boolean preview
    );

    /** Extra toggles for the config screen, on top of the built-in enable one. */
    public List<OverlayOption> getOptions() {
        return List.of();
    }

    /** Extra render gate on top of {@code settings().isEnabled()}. */
    protected boolean shouldRender() {
        return true;
    }

    @Override
    public final void render(
            DrawContext context,
            RenderTickCounter tickCounter
    ) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        OverlaySettings settings = settings();

        if (!settings.isEnabled() || !shouldRender()) {
            return;
        }

        double mouseX = client.mouse.getX()
                * client.getWindow().getScaledWidth()
                / client.getWindow().getWidth();

        double mouseY = client.mouse.getY()
                * client.getWindow().getScaledHeight()
                / client.getWindow().getHeight();

        draw(
                context,
                client.textRenderer,
                settings.getX(),
                settings.getY(),
                false,
                mouseX,
                mouseY
        );
    }

    /** Draws the overlay at its configured position, ignoring the enable flag. */
    public final void renderPreview(
            DrawContext context,
            TextRenderer textRenderer,
            double mouseX,
            double mouseY
    ) {
        OverlaySettings settings = settings();

        draw(
                context,
                textRenderer,
                settings.getX(),
                settings.getY(),
                true,
                mouseX,
                mouseY
        );
    }
}

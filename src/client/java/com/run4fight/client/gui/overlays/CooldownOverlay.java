package com.run4fight.client.gui.overlays;

import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.core.overlay.Overlay;
import com.run4fight.client.core.overlay.OverlayBounds;
import com.run4fight.client.core.overlay.OverlayOption;
import com.run4fight.client.model.CooldownModel;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class CooldownOverlay extends Overlay {

    public static final String KEY = "cooldown";

    private static final int TEXT_COLOR = 0xFFFF5555;
    private static final int PLACEHOLDER_COLOR = 0xFFAAAAAA;

    private static final String PLACEHOLDER_TEXT = "⏳ Cooldown 00:00:00";

    private final TriggerHandler triggerHandler;

    public CooldownOverlay(TriggerHandler triggerHandler) {
        super(KEY, "Cooldown overlay", 10, 3);
        this.triggerHandler = triggerHandler;
    }

    /**
     * One toggle per registered cooldown, so any cooldown added in
     * {@link TriggerHandler#register()} gets a config entry for free.
     */
    @Override
    public List<OverlayOption> getOptions() {
        return triggerHandler.getRegistry().getAll().stream()
                .map(cooldown -> OverlayOption.of(
                        settings(),
                        cooldown.getId(),
                        displayNameOf(cooldown),
                        true
                ))
                .toList();
    }

    @Override
    protected void draw(
            DrawContext context,
            TextRenderer textRenderer,
            int x,
            int y,
            boolean preview
    ) {
        List<CooldownModel> visible = visibleCooldowns();

        if (visible.isEmpty()) {
            if (preview) {
                context.drawTextWithShadow(
                        textRenderer,
                        PLACEHOLDER_TEXT,
                        x,
                        y,
                        PLACEHOLDER_COLOR
                );
            }

            return;
        }

        int lineHeight = lineHeight(textRenderer);

        for (CooldownModel cooldown : visible) {
            context.drawTextWithShadow(
                    textRenderer,
                    formatLine(cooldown),
                    x,
                    y,
                    TEXT_COLOR
            );

            y += lineHeight;
        }
    }

    @Override
    public OverlayBounds measure(
            TextRenderer textRenderer,
            boolean preview
    ) {
        List<CooldownModel> visible = visibleCooldowns();

        if (visible.isEmpty()) {
            return preview
                    ? new OverlayBounds(
                            textRenderer.getWidth(PLACEHOLDER_TEXT),
                            lineHeight(textRenderer)
                    )
                    : OverlayBounds.EMPTY;
        }

        int maxWidth = 0;

        for (CooldownModel cooldown : visible) {
            maxWidth = Math.max(
                    maxWidth,
                    textRenderer.getWidth(formatLine(cooldown))
            );
        }

        return new OverlayBounds(
                maxWidth,
                visible.size() * lineHeight(textRenderer)
        );
    }

    /** Active cooldowns the player hasn't hidden. */
    private List<CooldownModel> visibleCooldowns() {
        return triggerHandler.getRegistry().getActiveCooldowns().stream()
                .filter(cooldown -> settings().isToggled(cooldown.getId(), true))
                .toList();
    }

    private static int lineHeight(TextRenderer textRenderer) {
        return textRenderer.fontHeight + 1;
    }

    private static String formatLine(CooldownModel cooldown) {
        long remaining = cooldown.getRemainingMs();

        int hours = (int) (remaining / 3_600_000L);
        int minutes = (int) ((remaining % 3_600_000L) / 60_000L);
        int seconds = (int) ((remaining % 60_000L) / 1000L);

        return String.format(
                "⏳ %s %02d:%02d:%02d",
                displayNameOf(cooldown),
                hours,
                minutes,
                seconds
        );
    }

    /** {@code wealth_clock} -> {@code Wealth Clock}. */
    private static String displayNameOf(CooldownModel cooldown) {
        String[] words = cooldown.getId().split("_");

        StringBuilder name = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            if (!name.isEmpty()) {
                name.append(' ');
            }

            name.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }

        return name.toString();
    }
}

package com.run4fight.client.gui.overlays;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.core.overlay.Overlay;
import com.run4fight.client.core.overlay.OverlayBounds;
import com.run4fight.client.core.overlay.OverlayOption;
import com.run4fight.client.model.BuffModel;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.List;

public class BuffOverlay extends Overlay {

    public static final String KEY = "buff";

    private static final int ICON_SIZE = 24;

    /** Width of the placeholder shown when no buffs are active. */
    private static final int PLACEHOLDER_SLOTS = 3;

    private final BuffManager buffManager;

    public BuffOverlay(BuffManager buffManager) {
        super(KEY, "Buff overlay", 10, 10);
        this.buffManager = buffManager;
    }

    @Override
    public List<OverlayOption> getOptions() {
        BeeModConfig config = BeeModConfig.get();

        return List.of(
                new OverlayOption(
                        "Show actionbar",
                        config::isShowActionbar,
                        config::setShowActionbar
                )
        );
    }

    @Override
    protected void draw(
            DrawContext context,
            TextRenderer textRenderer,
            int x,
            int y,
            boolean preview
    ) {
        List<BuffModel> buffs = buffManager.getBuffs();

        if (buffs.isEmpty()) {
            if (preview) {
                drawPlaceholder(context, textRenderer, x, y);
            }

            return;
        }

        for (BuffModel buff : buffs) {

            // Buff icon
            context.drawText(
                    textRenderer,
                    Text.literal(String.valueOf(buff.getIcon())),
                    x,
                    y,
                    0xFFFFFFFF,
                    false
            );

            // Buff stacks
            String stackText = "x" + formatStacks(buff.getStacks());

            float scale = stackScale(stackText);

            context.getMatrices().pushMatrix();

            context.getMatrices().scale(scale, scale);

            context.drawText(
                    textRenderer,
                    Text.literal(stackText),
                    (int) (x / scale),
                    (int) ((y + 13) / scale),
                    0xFFFFFFFF,
                    true
            );

            context.getMatrices().popMatrix();

            x += ICON_SIZE;
        }
    }

    @Override
    public OverlayBounds measure(
            TextRenderer textRenderer,
            boolean preview
    ) {
        int count = buffManager.getBuffs().size();

        if (count == 0) {
            return preview
                    ? new OverlayBounds(PLACEHOLDER_SLOTS * ICON_SIZE, ICON_SIZE)
                    : OverlayBounds.EMPTY;
        }

        return new OverlayBounds(count * ICON_SIZE, ICON_SIZE);
    }

    /**
     * Keeps the overlay grabbable in the config screen while no buffs
     * are active, at the size it will occupy once they are.
     */
    private void drawPlaceholder(
            DrawContext context,
            TextRenderer textRenderer,
            int x,
            int y
    ) {
        int width = PLACEHOLDER_SLOTS * ICON_SIZE;

        context.fill(x, y, x + width, y + ICON_SIZE, 0x33FFFFFF);

        context.drawText(
                textRenderer,
                Text.literal("Buffs"),
                x + 4,
                y + (ICON_SIZE - textRenderer.fontHeight) / 2,
                0xFFAAAAAA,
                false
        );
    }

    private static String formatStacks(double stacks) {
        String text = String.valueOf(stacks);

        if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        }

        return text;
    }

    private static float stackScale(String stackText) {
        return switch (stackText.length()) {
            case 2 -> 0.9f;
            case 3 -> 0.875f;
            case 4 -> 0.825f;
            case 5 -> 0.675f;
            default -> 0.55f;
        };
    }
}

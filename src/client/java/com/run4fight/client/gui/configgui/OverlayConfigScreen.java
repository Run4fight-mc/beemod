package com.run4fight.client.gui.configgui;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.config.OverlaySettings;
import com.run4fight.client.core.overlay.Overlay;
import com.run4fight.client.core.overlay.OverlayBounds;
import com.run4fight.client.core.overlay.OverlayOption;
import com.run4fight.client.gui.PanelScreen;
import com.run4fight.client.model.CooldownModel;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.run4fight.client.config.ChatTriggers;
import com.run4fight.client.gui.overlays.CooldownOverlay;
import com.run4fight.client.config.CooldownConfigScreen;
import java.util.ArrayList;
import java.util.List;

/**
 * Settings screen for any {@link Overlay}: an enable toggle, one button
 * per {@link Overlay#getOptions()}, and a drag-to-move live preview.
 *
 * <p>Overlays don't get their own screen class - this one adapts to
 * whatever they expose.
 */
public class OverlayConfigScreen extends PanelScreen {

    private final Overlay overlay;

    /** The enable toggle, then whatever the overlay exposes. */
    private final List<OverlayOption> rows = new ArrayList<>();

    private boolean draggingOverlay = false;

    public OverlayConfigScreen(Screen parent, Overlay overlay) {
        super(Text.literal(overlay.getDisplayName()), parent);
        this.overlay = overlay;
    }

    @Override
    protected int rowCount() {
        return rows.size();
    }

    @Override
    protected void init() {
        OverlaySettings settings = overlay.settings();

        rows.clear();

        rows.add(new OverlayOption(
                "Show overlay",
                settings::isEnabled,
                settings::setEnabled
        ));

        rows.addAll(overlay.getOptions());

        clampScroll();

        super.init();

        for (int slot = 0; slot < visibleRowCount(); slot++) {
            int index = slot + scrollOffset();

            if (overlay instanceof CooldownOverlay cooldownOverlay) {
                addCooldownButtons(
                        rows.get(index),
                        cooldownOverlay,
                        index,
                        slot
                );
            } else {
                addToggleButton(rows.get(index), slot);
            }
        }

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> this.close()
                ).dimensions(
                        this.width / 2 - 50,
                        panelBottom() - 40,
                        100,
                        ROW_BUTTON_HEIGHT
                ).build()
        );
    }

    private void addToggleButton(OverlayOption option, int slot) {
        this.addDrawableChild(
                ButtonWidget.builder(
                        toggleText(option.label(), option.value()),
                        button -> {
                            option.toggle();

                            button.setMessage(
                                    toggleText(option.label(), option.value())
                            );

                            BeeModConfig.save();
                        }
                ).dimensions(
                        rowButtonX(ROW_BUTTON_WIDTH),
                        rowY(slot),
                        ROW_BUTTON_WIDTH,
                        ROW_BUTTON_HEIGHT
                ).build()
        );
    }

    private void addCooldownButtons(
            OverlayOption option,
            CooldownOverlay cooldownOverlay,
            int index,
            int slot
    ) {
        int configureWidth = 85;
        int toggleWidth = 85;
        int gap = 10;

        int configureX = rowButtonX(configureWidth);
        int toggleX = configureX - gap - toggleWidth;

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal(option.value() ? "ON" : "OFF"),
                        button -> {
                            option.toggle();

                            button.setMessage(
                                    Text.literal(option.value() ? "ON" : "OFF")
                            );

                            BeeModConfig.save();
                        }
                ).dimensions(
                        toggleX,
                        rowY(slot),
                        toggleWidth,
                        ROW_BUTTON_HEIGHT
                ).build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Configure"),
                        button -> {
                            ChatTriggers trigger =
                                    cooldownOverlay.getTrigger(index - 1);

                            this.client.setScreen(
                                    new CooldownConfigScreen(
                                            this,
                                            trigger
                                    )
                            );
                        }
                ).dimensions(
                        configureX,
                        rowY(slot),
                        configureWidth,
                        ROW_BUTTON_HEIGHT
                ).build()
        );
    }

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {
        renderPanel(context);

        int labelX = panelLeft() + ROW_INSET;

        for (int slot = 0; slot < visibleRowCount(); slot++) {
            context.drawTextWithShadow(
                    this.textRenderer,
                    Text.literal(rows.get(slot + scrollOffset()).label()),
                    labelX,
                    rowY(slot) + 6,
                    TEXT_COLOR
            );
        }

        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("Drag the overlay to move it"),
                this.width / 2,
                panelBottom() - 58,
                MUTED_COLOR
        );

        super.render(context, mouseX, mouseY, delta);

        // Drawn last so the preview isn't hidden behind the panel.
        overlay.renderPreview(context, this.textRenderer, mouseX, mouseY);
    }

    private OverlayBounds previewBounds() {
        return overlay.measure(this.textRenderer, true);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {

        if (click.button() == 0) {

            OverlaySettings settings = overlay.settings();

            boolean hit = previewBounds().contains(
                    settings.getX(),
                    settings.getY(),
                    click.x(),
                    click.y()
            );

            if (hit) {
                draggingOverlay = true;
                return true;
            }
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseDragged(
            Click click,
            double offsetX,
            double offsetY
    ) {
        if (draggingOverlay && click.button() == 0) {

            OverlaySettings settings = overlay.settings();
            OverlayBounds bounds = previewBounds();

            settings.setX(clampToScreen(
                    settings.getX() + (int) offsetX,
                    this.width - bounds.width()
            ));

            settings.setY(clampToScreen(
                    settings.getY() + (int) offsetY,
                    this.height - bounds.height()
            ));

            return true;
        }

        return super.mouseDragged(click, offsetX, offsetY);
    }

    @Override
    public boolean mouseReleased(Click click) {

        if (click.button() == 0 && draggingOverlay) {

            draggingOverlay = false;

            BeeModConfig.save();

            return true;
        }

        return super.mouseReleased(click);
    }

    /** Keeps the overlay on screen; max can go negative if it outgrows the window. */
    private static int clampToScreen(int value, int max) {
        return Math.clamp(value, 0, Math.max(0, max));
    }
}

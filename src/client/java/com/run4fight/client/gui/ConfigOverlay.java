package com.run4fight.client.gui;

import com.run4fight.client.gui.configgui.BuffOverlayConfigScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigOverlay extends Screen {

    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 180;

    public ConfigOverlay() {
        super(Text.literal("BeeMod Config"));
    }

    @Override
    protected void init() {
        super.init();

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        // Buff Overlay - Config button
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Config"),
                        button -> {
                            this.client.setScreen(new BuffOverlayConfigScreen(this));
                        }
                ).dimensions(
                        left + 270,
                        top + 75,
                        100,
                        20
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
        context.fill(
                0,
                0,
                this.width,
                this.height,
                0xCC000000
        );


        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        // Panel
        context.fill(
                left,
                top,
                right,
                bottom,
                0xFF111111
        );

        // Border
        context.fill(left, top, right, top + 2, 0xFF555555);
        context.fill(left, bottom - 2, right, bottom, 0xFF555555);
        context.fill(left, top, left + 2, bottom, 0xFF555555);
        context.fill(right - 2, top, right, bottom, 0xFF555555);

        // Title
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("BeeMod Config"),
                this.width / 2,
                top + 20,
                0xFFFFFFFF
        );

        // Section title
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Features"),
                left + 20,
                top + 55,
                0xFFFFAA00
        );

        // Feature name
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Buff overlay"),
                left + 30,
                top + 80,
                0xFFFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }
}

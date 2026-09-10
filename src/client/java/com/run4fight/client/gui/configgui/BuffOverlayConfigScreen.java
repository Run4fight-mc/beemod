package com.run4fight.client.gui.configgui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.run4fight.client.config.BeeModConfig;

public class BuffOverlayConfigScreen extends Screen {

    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 200;

    private final Screen parent;

    private boolean showOverlay = true;
    private boolean showActionbar = true;

    private ButtonWidget showOverlayButton;
    private ButtonWidget showActionbarButton;

    public BuffOverlayConfigScreen(Screen parent) {
        super(Text.literal("Buff Overlay"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        BeeModConfig config = BeeModConfig.get();

        // Show overlay
        showOverlayButton = this.addDrawableChild(
                ButtonWidget.builder(
                        getToggleText(
                                "Show overlay",
                                config.isShowOverlay()
                        ),
                        button -> {
                            config.setShowOverlay(
                                    !config.isShowOverlay()
                            );

                            button.setMessage(
                                    getToggleText(
                                            "Show overlay",
                                            config.isShowOverlay()
                                    )
                            );

                            BeeModConfig.save();
                        }
                ).dimensions(
                        left + 240,
                        top + 65,
                        130,
                        20
                ).build()
        );

        // Show actionbar
        showActionbarButton = this.addDrawableChild(
                ButtonWidget.builder(
                        getToggleText(
                                "Show actionbar",
                                config.isShowActionbar()
                        ),
                        button -> {
                            config.setShowActionbar(
                                    !config.isShowActionbar()
                            );

                            button.setMessage(
                                    getToggleText(
                                            "Show actionbar",
                                            config.isShowActionbar()
                                    )
                            );

                            BeeModConfig.save();
                        }
                ).dimensions(
                        left + 240,
                        top + 95,
                        130,
                        20
                ).build()
        );

        // Done
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> {
                            this.client.setScreen(parent);
                        }
                ).dimensions(
                        this.width / 2 - 50,
                        top + 150,
                        100,
                        20
                ).build()
        );
    }


    private Text getToggleText(String name, boolean value) {
        return Text.literal(
                name + ": " + (value ? "ON" : "OFF")
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
                Text.literal("Buff Overlay"),
                this.width / 2,
                top + 20,
                0xFFFFFFFF
        );

        // Setting labels
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Show overlay"),
                left + 30,
                top + 71,
                0xFFFFFFFF
        );

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Show actionbar"),
                left + 30,
                top + 101,
                0xFFFFFFFF
        );

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}

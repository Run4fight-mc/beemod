package com.run4fight.client.gui.configgui;

import com.run4fight.client.BeeModClient;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.gui.BuffOverlay;
import com.run4fight.client.model.BuffModel;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.run4fight.client.config.BeeModConfig;

import java.util.List;

public class BuffOverlayConfigScreen extends Screen {

    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 200;

    private final Screen parent;

    private boolean showOverlay = true;
    private boolean showActionbar = true;

    private ButtonWidget showOverlayButton;
    private ButtonWidget showActionbarButton;

    private boolean draggingOverlay = false;

    private static final int PREVIEW_WIDTH = 120;
    private static final int PREVIEW_HEIGHT = 30;

    private final BuffOverlay buffOverlay;

    public BuffOverlayConfigScreen(
            Screen parent,
            BuffOverlay buffOverlay
    ) {
        super(Text.literal("Buff Overlay"));
        this.parent = parent;
        this.buffOverlay = buffOverlay;
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

        BeeModConfig config = BeeModConfig.get();

        BuffManager buffManager = BeeModClient.getBuffManager();
        List<BuffModel> buffs = buffManager.getBuffs();

        int previewX = config.getBuffOverlayX();
        int previewY = config.getBuffOverlayY();

        int x = previewX;
        int y = previewY;

        for (BuffModel buff : buffs) {

            String icon = String.valueOf(buff.getIcon());

            String stacks = String.valueOf(buff.getStacks());

            if (stacks.endsWith(".0")) {
                stacks = stacks.substring(0, stacks.length() - 2);
            }

            // Same icon as the real overlay
            context.drawText(
                    this.textRenderer,
                    Text.literal(icon),
                    x,
                    y,
                    0xFFFFFFFF,
                    false
            );

            String stackText = "x" + stacks;

            float scale;

            switch (stackText.length()) {
                case 2 -> scale = 0.9f;
                case 3 -> scale = 0.875f;
                case 4 -> scale = 0.825f;
                case 5 -> scale = 0.675f;
                default -> scale = 0.55f;
            }

            context.getMatrices().pushMatrix();
            context.getMatrices().scale(scale, scale);

            context.drawText(
                    this.textRenderer,
                    Text.literal(stackText),
                    (int) (x / scale),
                    (int) ((y + 13) / scale),
                    0xFFFFFFFF,
                    true
            );

            context.getMatrices().popMatrix();

            x += 24;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {

        if (click.button() == 0) { // Left mouse button

            BeeModConfig config = BeeModConfig.get();

            int overlayX = config.getBuffOverlayX();
            int overlayY = config.getBuffOverlayY();

            double mouseX = click.x();
            double mouseY = click.y();

            if (mouseX >= overlayX
                    && mouseX <= overlayX + PREVIEW_WIDTH
                    && mouseY >= overlayY
                    && mouseY <= overlayY + PREVIEW_HEIGHT) {

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

            BeeModConfig config = BeeModConfig.get();

            int newX = config.getBuffOverlayX() + (int) offsetX;
            int newY = config.getBuffOverlayY() + (int) offsetY;

            newX = Math.max(
                    0,
                    Math.min(
                            newX,
                            this.width - PREVIEW_WIDTH
                    )
            );

            newY = Math.max(
                    0,
                    Math.min(
                            newY,
                            this.height - PREVIEW_HEIGHT
                    )
            );

            config.setBuffOverlayX(newX);
            config.setBuffOverlayY(newY);

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

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}

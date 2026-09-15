package com.run4fight.client.gui.configgui;

import com.run4fight.client.BeeModClient;
import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.model.CooldownModel;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.run4fight.client.config.BeeModConfig;

import java.util.List;

public class WealthClockOverlayConfigScreen extends Screen {

    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 160;

    private final Screen parent;

    private ButtonWidget showCooldownButton;

    private boolean draggingOverlay = false;

    public WealthClockOverlayConfigScreen(Screen parent) {
        super(Text.literal("Wealth Clock Overlay"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        BeeModConfig config = BeeModConfig.get();

        // Show cooldown
        showCooldownButton = this.addDrawableChild(
                ButtonWidget.builder(
                        getToggleText("Show cooldown", config.isShowCooldown()),
                        button -> {
                            config.setShowCooldown(!config.isShowCooldown());
                            button.setMessage(
                                    getToggleText("Show cooldown", config.isShowCooldown())
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

        // Done
        this.addDrawableChild(
                ButtonWidget.builder(
                        Text.literal("Done"),
                        button -> {
                            this.client.setScreen(parent);
                        }
                ).dimensions(
                        this.width / 2 - 50,
                        top + 120,
                        100,
                        20
                ).build()
        );
    }

    private Text getToggleText(String name, boolean value) {
        return Text.literal(name + ": " + (value ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xCC000000);

        BeeModConfig config = BeeModConfig.get();

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        // Panel
        context.fill(left, top, right, bottom, 0xFF111111);

        // Border
        context.fill(left, top, right, top + 2, 0xFF555555);
        context.fill(left, bottom - 2, right, bottom, 0xFF555555);
        context.fill(left, top, left + 2, bottom, 0xFF555555);
        context.fill(right - 2, top, right, bottom, 0xFF555555);

        // Title
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.literal("Wealth Clock Overlay"),
                this.width / 2,
                top + 20,
                0xFFFFFFFF
        );

        // Setting label
        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Show cooldown"),
                left + 30,
                top + 71,
                0xFFFFFFFF
        );

        TriggerHandler triggerHandler = BeeModClient.getTriggerHandler();

        List<CooldownModel> cooldowns =
                triggerHandler.getRegistry().getActiveCooldowns();

        int previewX = config.getWealthClockOverlayX();
        int previewY = config.getWealthClockOverlayY();

        int y = previewY;

        for (CooldownModel cd : cooldowns) {

            long remaining = cd.getRemainingMs();

            int h = (int) (remaining / 3_600_000L);
            int m = (int) ((remaining % 3_600_000L) / 60_000L);
            int s = (int) ((remaining % 60_000L) / 1000L);

            context.drawTextWithShadow(
                    this.textRenderer,
                    String.format("⏳ %02d:%02d:%02d", h, m, s),
                    previewX,
                    y,
                    0xFFFF5555
            );

            y += 5;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private int getOverlayWidth() {
        TriggerHandler triggerHandler = BeeModClient.getTriggerHandler();

        List<CooldownModel> cooldowns =
                triggerHandler.getRegistry().getActiveCooldowns();

        int maxWidth = 0;

        for (CooldownModel cd : cooldowns) {
            long remaining = cd.getRemainingMs();

            int h = (int) (remaining / 3_600_000L);
            int m = (int) ((remaining % 3_600_000L) / 60_000L);
            int s = (int) ((remaining % 60_000L) / 1000L);

            String text = String.format(
                    "⏳ %02d:%02d:%02d",
                    h,
                    m,
                    s
            );

            maxWidth = Math.max(
                    maxWidth,
                    this.textRenderer.getWidth(text)
            );
        }

        return maxWidth;
    }

    private int getOverlayHeight() {
        TriggerHandler triggerHandler = BeeModClient.getTriggerHandler();

        List<CooldownModel> cooldowns =
                triggerHandler.getRegistry().getActiveCooldowns();

        return Math.max(5, cooldowns.size() * 5);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {

        if (click.button() == 0) {

            BeeModConfig config = BeeModConfig.get();

            int overlayX = config.getWealthClockOverlayX();
            int overlayY = config.getWealthClockOverlayY();

            int overlayWidth = getOverlayWidth();
            int overlayHeight = getOverlayHeight();

            double mouseX = click.x();
            double mouseY = click.y();

            if (mouseX >= overlayX
                    && mouseX <= overlayX + overlayWidth
                    && mouseY >= overlayY
                    && mouseY <= overlayY + overlayHeight) {

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

            int newX = config.getWealthClockOverlayX()
                    + (int) offsetX;

            int newY = config.getWealthClockOverlayY()
                    + (int) offsetY;

            newX = Math.max(
                    0,
                    Math.min(
                            newX,
                            this.width - getOverlayWidth()
                    )
            );

            newY = Math.max(
                    0,
                    Math.min(
                            newY,
                            this.height - getOverlayHeight()
                    )
            );

            config.setWealthClockOverlayX(newX);
            config.setWealthClockOverlayY(newY);

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
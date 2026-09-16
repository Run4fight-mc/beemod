package com.run4fight.client.gui.configgui;

import com.run4fight.client.core.overlay.Overlay;
import com.run4fight.client.core.overlay.OverlayRegistry;
import com.run4fight.client.gui.PanelScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

/**
 * BeeMod's root settings screen: one row per registered overlay, built
 * from OverlayRegistry so registering an overlay is enough to
 * make it configurable.
 */
public class BeeModConfigScreen extends PanelScreen {

    private static final int CONFIG_BUTTON_WIDTH = 80;
    private static final int CONFIG_BUTTON_HEIGHT = 15;

    private List<Overlay> overlays = List.of();

    public BeeModConfigScreen() {
        super(Text.literal("BeeMod Config"), null);
    }

    @Override
    protected int rowCount() {
        return overlays.size();
    }

    @Override
    protected void init() {
        overlays = OverlayRegistry.all();

        clampScroll();

        super.init();

        for (int slot = 0; slot < visibleRowCount(); slot++) {
            Overlay overlay = overlays.get(slot + scrollOffset());

            this.addDrawableChild(
                    ButtonWidget.builder(
                            Text.literal("Config"),
                            button -> this.client.setScreen(
                                    new OverlayConfigScreen(this, overlay)
                            )
                    ).dimensions(
                            rowButtonX(CONFIG_BUTTON_WIDTH),
                            rowY(slot),
                            CONFIG_BUTTON_WIDTH,
                            CONFIG_BUTTON_HEIGHT
                    ).build()
            );
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

    @Override
    public void render(
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
    ) {
        renderPanel(context);

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Features"),
                panelLeft() + 20,
                panelTop() + CONTENT_TOP - 10,
                ACCENT_COLOR
        );

        for (int slot = 0; slot < visibleRowCount(); slot++) {
            context.drawTextWithShadow(
                    this.textRenderer,
                    Text.literal(overlays.get(slot + scrollOffset()).getDisplayName()),
                    panelLeft() + ROW_INSET,
                    rowY(slot) + 4,
                    TEXT_COLOR
            );
        }

        super.render(context, mouseX, mouseY, delta);
    }
}

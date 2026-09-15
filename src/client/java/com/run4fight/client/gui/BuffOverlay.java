package com.run4fight.client.gui;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.core.handlers.HudOverlay;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.model.BuffModel;
import com.run4fight.client.model.EffectModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class BuffOverlay implements HudOverlay {

    public static final Identifier ID =
            Identifier.of("beemod", "buff_overlay");

    private final BuffManager buffManager;

    private static final int ICON_SIZE = 24;

    public BuffOverlay(BuffManager buffManager) {
        this.buffManager = buffManager;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void render(
            DrawContext context,
            RenderTickCounter tickCounter
    ) {
        MinecraftClient client =
                MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        BeeModConfig config = BeeModConfig.get();

        if (!config.isShowOverlay()) {
            return;
        }

        renderBuffs(
                context,
                config.getBuffOverlayX(),
                config.getBuffOverlayY()
        );
    }

    public void renderBuffs(
            DrawContext context,
            int startX,
            int startY
    ) {
        MinecraftClient client =
                MinecraftClient.getInstance();

        List<BuffModel> buffs = buffManager.getBuffs();

        int x = startX;
        int y = startY;

        for (BuffModel buff : buffs) {

            String icon = String.valueOf(buff.getIcon());

            String stacks = String.valueOf(buff.getStacks());

            if (stacks.endsWith(".0")) {
                stacks = stacks.substring(
                        0,
                        stacks.length() - 2
                );
            }

            // Buff icon
            context.drawText(
                    client.textRenderer,
                    Text.literal(icon),
                    x,
                    y,
                    0xFFFFFFFF,
                    false
            );

            // Buff stacks
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

            context.getMatrices().scale(
                    scale,
                    scale
            );

            context.drawText(
                    client.textRenderer,
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
}

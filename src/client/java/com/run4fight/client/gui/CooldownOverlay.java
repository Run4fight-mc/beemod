package com.run4fight.client.gui;

import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.core.handlers.HudOverlay;
import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.model.CooldownModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class CooldownOverlay implements HudOverlay {

    public static final Identifier ID = Identifier.of("beemod", "cooldown_overlay");

    private final TriggerHandler triggerHandler;

    public CooldownOverlay(TriggerHandler triggerHandler) {
        this.triggerHandler = triggerHandler;
    }

    @Override
    public Identifier getId() { return ID; }

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        if (!BeeModConfig.get().isShowCooldown()) return;

        int y = 3;
        for (CooldownModel cd : triggerHandler.getRegistry().getActiveCooldowns()) {
            long remaining = cd.getRemainingMs();
            int h = (int) (remaining / 3_600_000L);
            int m = (int) ((remaining % 3_600_000L) / 60_000L);
            int s = (int) ((remaining % 60_000L) / 1000L);

            context.drawTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    String.format("⏳ %02d:%02d:%02d", h, m, s),
                    10, y, 0xFFFF5555
            );
            y += 5;
        }
    }
}
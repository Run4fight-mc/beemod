package com.run4fight.client.core.overlay;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public interface HudOverlay {
    Identifier getId();
    void render(DrawContext context, RenderTickCounter tickCounter);
}

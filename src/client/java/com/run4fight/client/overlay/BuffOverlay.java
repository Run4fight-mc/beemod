package com.run4fight.client.overlay;

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

public class BuffOverlay {

    public static final Identifier ID =
            Identifier.of("beemod", "buff_overlay");

    private final BuffManager buffManager;

    private static final int ICON_SIZE = 24;
    private static final int START_X = 10;
    private static final int START_Y = 10;

    public BuffOverlay(BuffManager buffManager) {
        this.buffManager = buffManager;
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        List<BuffModel> buffs = buffManager.getBuffs();

        int x = 10;
        int y = 10;
        int iconSize = 24;

        BuffModel hoveredBuff = null;

        // Render icons + detect hover
        for (BuffModel buff : buffs) {

            String icon = String.valueOf(buff.getIcon());
            String stacks = String.valueOf(buff.getStacks());

            context.drawText(
                    client.textRenderer,
                    Text.literal(icon+"\uE00D\uE00E"+stacks),
                    x,
                    y,
                    0xFFFFFFFF,
                    true
            );

            // Mouse position in GUI coordinates
            double mouseX = client.mouse.getX()
                    * client.getWindow().getScaledWidth()
                    / client.getWindow().getWidth();

            double mouseY = client.mouse.getY()
                    * client.getWindow().getScaledHeight()
                    / client.getWindow().getHeight();

            if (mouseX >= x &&
                    mouseX < x + iconSize &&
                    mouseY >= y &&
                    mouseY < y + iconSize) {

                hoveredBuff = buff;
            }

            x += iconSize;
        }

        // Render tooltip
        if (hoveredBuff != null) {

            List<Text> tooltip = new ArrayList<>();

            tooltip.add(
                    Text.literal(
                            hoveredBuff.getName()
                                    + " x" + hoveredBuff.getStacks()
                                    + " (" + hoveredBuff.getDuration() + ")"
                    )
            );

            for (EffectModel effect : hoveredBuff.getEffects()) {

                tooltip.add(
                        Text.literal(
                                "- " +
                                        effect.getValue() +
                                        " " +
                                        effect.getEffectName()
                        )
                );
            }

            double mouseX = client.mouse.getX()
                    * client.getWindow().getScaledWidth()
                    / client.getWindow().getWidth();

            double mouseY = client.mouse.getY()
                    * client.getWindow().getScaledHeight()
                    / client.getWindow().getHeight();

            context.drawTooltip(
                    client.textRenderer,
                    tooltip,
                    (int) mouseX,
                    (int) mouseY
            );
        }
    }
}

package com.run4fight.client.gui;

import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.model.BuffModel;
import com.run4fight.client.model.EffectModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.run4fight.client.config.BeeModConfig;

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

        if (!BeeModConfig.get().isShowOverlay()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) {
            return;
        }

        List<BuffModel> buffs = buffManager.getBuffs();

        int x = 10;
        int y = 10;
        int iconSize = 24;

        BuffModel hoveredBuff = null;

        for (BuffModel buff : buffs) {

            String icon = String.valueOf(buff.getIcon());
            String stacks = String.valueOf(buff.getStacks());
            if (stacks.endsWith(".0")){
                stacks = stacks.substring(0, stacks.length()-2);
            }

            //Buff icon
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
                case 2 -> scale = 0.9f; // x1
                case 3 -> scale = 0.875f; //x10
                case 4 -> scale = 0.825f;
                case 5 -> scale = 0.675f; //x1.02
                default -> scale = 0.55f;
            }

            context.getMatrices().pushMatrix();
            context.getMatrices().scale(scale, scale);

            //System.out.println(stackText);

            context.drawText(
                    client.textRenderer,
                    Text.literal(stackText),
                    (int) (x / scale),
                    (int) ((y + 13) / scale),
                    0xFFFFFFFF,
                    true
            );

            context.getMatrices().popMatrix();


            // Hovering detection
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

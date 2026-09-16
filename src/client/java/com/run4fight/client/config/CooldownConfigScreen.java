package com.run4fight.client.config;

import com.run4fight.client.gui.PanelScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CooldownConfigScreen extends PanelScreen {

    private final ChatTriggers trigger;

    public CooldownConfigScreen(
            Screen parent,
            ChatTriggers trigger
    ) {
        super(
                Text.literal(displayNameOf(trigger)),
                parent
        );

        this.trigger = trigger;
    }

    @Override
    protected int rowCount() {
        return 2;
    }

    @Override
    protected void init() {
        super.init();

        ChatTriggerSettings settings =
                BeeModConfig.get().chatTrigger(trigger);

        addToggleButton(
                "Sound on start",
                settings.isSoundOnStart(),
                settings::setSoundOnStart,
                0
        );

        addToggleButton(
                "Sound on end",
                settings.isSoundOnEnd(),
                settings::setSoundOnEnd,
                1
        );

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

    private void addToggleButton(
            String label,
            boolean value,
            java.util.function.Consumer<Boolean> setter,
            int slot
    ) {
        this.addDrawableChild(
                ButtonWidget.builder(
                        toggleText(label, value),
                        button -> {
                            boolean newValue = !value;

                            setter.accept(newValue);

                            button.setMessage(
                                    toggleText(label, newValue)
                            );

                            BeeModConfig.save();
                        }
                ).dimensions(
                        rowButtonX(ROW_BUTTON_WIDTH),
                        rowY(slot),
                        ROW_BUTTON_WIDTH,
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

        int labelX = panelLeft() + ROW_INSET;

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Sound on start"),
                labelX,
                rowY(0) + 6,
                TEXT_COLOR
        );

        context.drawTextWithShadow(
                this.textRenderer,
                Text.literal("Sound on end"),
                labelX,
                rowY(1) + 6,
                TEXT_COLOR
        );

        super.render(context, mouseX, mouseY, delta);
    }

    private static String displayNameOf(ChatTriggers trigger) {
        String[] words = trigger.getCooldownName().split("_");

        StringBuilder name = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            if (!name.isEmpty()) {
                name.append(' ');
            }

            name.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));
        }

        return name.toString();
    }
}
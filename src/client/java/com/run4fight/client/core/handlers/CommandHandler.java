package com.run4fight.client.core.handlers;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandHandler {

    private final ActionBarHandler actionBarHandler;

    public CommandHandler(ActionBarHandler actionBarHandler) {
        this.actionBarHandler = actionBarHandler;
    }

    public void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            dispatcher.register(
                    literal("beemod")
                            .then(
                                    literal("buff")
                                            .executes(this::executeBuff)
                            )
            );

        });
    }

    private int executeBuff(
            CommandContext<FabricClientCommandSource> context
    ) {
        Text actionBar = actionBarHandler.getRawActionBarData();

        if (actionBar == null) {
            context.getSource().sendFeedback(
                    Text.literal("No actionbar data.")
            );
            return 0;
        }

        context.getSource().sendFeedback(actionBar);

        return 1;
    }
}
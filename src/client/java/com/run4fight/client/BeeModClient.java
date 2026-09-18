package com.run4fight.client;

import com.run4fight.client.core.handlers.ActionBarHandler;
import com.run4fight.client.core.handlers.CommandHandler;
import com.run4fight.client.core.handlers.HandlersImplementation;
import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.core.overlay.OverlayRegistry;
import com.run4fight.client.core.triggers.CooldownData;
import com.run4fight.client.gui.overlays.BuffOverlay;
import com.run4fight.client.gui.overlays.CooldownOverlay;
import net.fabricmc.api.ClientModInitializer;
import com.run4fight.client.config.BeeModConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class BeeModClient implements ClientModInitializer {
	private static BuffManager buffManager;

	@Override
	public void onInitializeClient() {
		BeeModConfig.load();

		buffManager = new BuffManager();

		ActionBarHandler actionBarHandler = new ActionBarHandler(buffManager);

		CommandHandler commandHandler = new CommandHandler(actionBarHandler);
		commandHandler.register();

		CooldownData.init(FabricLoader.getInstance().getConfigDir());

		TriggerHandler triggerHandler = new TriggerHandler();
		triggerHandler.register();
		ClientTickEvents.END_CLIENT_TICK.register(client -> triggerHandler.tick());

		HandlersImplementation.register(actionBarHandler);

		OverlayRegistry.register(new BuffOverlay(buffManager));
		OverlayRegistry.register(new CooldownOverlay(triggerHandler));
	}

	public static BuffManager getBuffManager() {
		return buffManager;
	}
}

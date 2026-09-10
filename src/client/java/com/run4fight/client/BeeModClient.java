package com.run4fight.client;

import com.run4fight.client.core.handlers.ActionBarHandler;
import com.run4fight.client.core.handlers.CommandHandler;
import com.run4fight.client.core.handlers.HandlersImplementation;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.gui.BuffOverlay;
import net.fabricmc.api.ClientModInitializer;
import com.run4fight.client.config.BeeModConfig;

public class BeeModClient implements ClientModInitializer {
	private static BuffManager buffManager;

	@Override
	public void onInitializeClient() {
		buffManager = new BuffManager();

		ActionBarHandler actionBarHandler =
				new ActionBarHandler(buffManager);

		BuffOverlay buffOverlay =
				new BuffOverlay(buffManager);

		HandlersImplementation.register(actionBarHandler);
		HandlersImplementation.registerOverlay(buffOverlay);

		CommandHandler commandHandler =
				new CommandHandler(actionBarHandler);
		commandHandler.register();

		BeeModConfig.load();
	}

	public static BuffManager getBuffManager() {
		return buffManager;
	}
}

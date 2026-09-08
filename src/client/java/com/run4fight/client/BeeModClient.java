package com.run4fight.client;

import com.run4fight.client.handlers.ActionBarHandler;
import com.run4fight.client.handlers.HandlersImplementation;
import com.run4fight.client.managers.BuffManager;
import com.run4fight.client.overlay.BuffOverlay;
import net.fabricmc.api.ClientModInitializer;

public class BeeModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BuffManager buffManager = new BuffManager();

		ActionBarHandler actionBarHandler =
				new ActionBarHandler(buffManager);

		BuffOverlay buffOverlay =
				new BuffOverlay(buffManager);

		HandlersImplementation.register(actionBarHandler);
		HandlersImplementation.registerOverlay(buffOverlay);
	}
}

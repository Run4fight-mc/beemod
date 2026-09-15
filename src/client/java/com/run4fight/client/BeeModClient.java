package com.run4fight.client;

import com.run4fight.client.core.handlers.ActionBarHandler;
import com.run4fight.client.core.handlers.CommandHandler;
import com.run4fight.client.core.handlers.HandlersImplementation;
import com.run4fight.client.core.handlers.TriggerHandler;
import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.core.triggers.CooldownData;
import com.run4fight.client.gui.BuffOverlay;
import com.run4fight.client.gui.CooldownOverlay;
import net.fabricmc.api.ClientModInitializer;
import com.run4fight.client.config.BeeModConfig;
import net.fabricmc.loader.api.FabricLoader;

public class BeeModClient implements ClientModInitializer {
	private static BuffManager buffManager;
	private static BuffOverlay buffOverlay;
	private static TriggerHandler triggerHandler;

	@Override
	public void onInitializeClient() {
		buffManager = new BuffManager();

		ActionBarHandler actionBarHandler = new ActionBarHandler(buffManager);
		buffOverlay = new BuffOverlay(buffManager);

		CommandHandler commandHandler = new CommandHandler(actionBarHandler);
		commandHandler.register();

		CooldownData.init(FabricLoader.getInstance().getConfigDir());
		triggerHandler = new TriggerHandler();
		triggerHandler.register();

		CooldownOverlay cooldownOverlay = new CooldownOverlay(triggerHandler);
		HandlersImplementation.registerOverlay(cooldownOverlay);

		HandlersImplementation.register(actionBarHandler);
		HandlersImplementation.registerOverlay(buffOverlay);

		BeeModConfig.load();
	}

	public static BuffManager getBuffManager() {
		return buffManager;
	}
	public static BuffOverlay getBuffOverlay() {
		return buffOverlay;
	}
	public static TriggerHandler getTriggerHandler() {
		return triggerHandler;
	}
}

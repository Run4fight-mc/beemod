package com.run4fight.client.handlers;

import com.run4fight.client.managers.BuffManager;
import com.run4fight.client.mixin.InGameHudAccessor;
import com.run4fight.client.model.BuffModel;
import com.run4fight.client.parser.ActionBarParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import java.util.List;


public class ActionBarHandler {

    private final BuffManager buffManager;

    public ActionBarHandler(BuffManager buffManager) {
        this.buffManager = buffManager;
    }

    public void onTick(MinecraftClient client) {

        InGameHud hud = client.inGameHud;
        Text actionBarText = ((InGameHudAccessor) hud).getOverlayMessage();

        if (actionBarText == null) {
            return;
        }

        String actionBarRaw = actionBarText.getString();

        List<BuffModel> buffs = ActionBarParser.getBuffs(actionBarRaw);

        buffManager.update(buffs);
    }
}


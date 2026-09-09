package com.run4fight.client.core.handlers;

import com.run4fight.client.core.managers.BuffManager;
import com.run4fight.client.core.mixin.InGameHudAccessor;
import com.run4fight.client.model.BuffModel;
import com.run4fight.client.core.parser.ActionBarParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import java.util.List;


public class ActionBarHandler {
    private static Text rawActionBarData;
    private final BuffManager buffManager;

    public ActionBarHandler(BuffManager buffManager) {
        this.buffManager = buffManager;
    }

    public void onTick(MinecraftClient client) {
        InGameHud hud = client.inGameHud;
        rawActionBarData = ((InGameHudAccessor) hud).getOverlayMessage();

        if (rawActionBarData == null) {
            return;
        }

        String actionBarRaw = rawActionBarData.getString();

        List<BuffModel> buffs = ActionBarParser.getBuffs(actionBarRaw);

        buffManager.update(buffs);
    }

    public Text getRawActionBarData(){
        return rawActionBarData;
    }
}


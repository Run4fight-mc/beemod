package com.run4fight.client.core.mixin;

import com.run4fight.client.BeeModClient;
import com.run4fight.client.config.BeeModConfig;
import com.run4fight.client.model.BuffModel;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(
            method = "renderOverlayMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void beemod$hideActionbar(
            DrawContext context,
            RenderTickCounter tickCounter,
            CallbackInfo ci
    ) {
        List<BuffModel> buffs = BeeModClient.getBuffManager().getBuffs();

        if (!buffs.isEmpty() && !BeeModConfig.get().isShowActionbar()) {
            ci.cancel();
        }
    }
}

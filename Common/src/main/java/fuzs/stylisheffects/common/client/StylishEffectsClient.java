package fuzs.stylisheffects.common.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.common.api.client.event.v1.ClientLifecycleEvents;
import fuzs.puzzleslib.common.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.PrepareInventoryMobEffectsCallback;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenEvents;
import fuzs.puzzleslib.common.api.client.event.v1.gui.ScreenOpeningCallback;
import fuzs.stylisheffects.common.StylishEffects;
import fuzs.stylisheffects.common.client.handler.EffectDurationHandler;
import fuzs.stylisheffects.common.client.handler.EffectScreenHandler;
import fuzs.stylisheffects.common.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class StylishEffectsClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ClientLifecycleEvents.STARTED.register(EffectScreenHandler::rebuildGuiRenderer);
        ScreenEvents.afterInit(AbstractContainerScreen.class).register(EffectScreenHandler::onAfterInit);
        ScreenEvents.remove(AbstractContainerScreen.class).register(EffectScreenHandler::onRemove);
        ScreenEvents.afterBackground(AbstractContainerScreen.class).register(EffectScreenHandler::onAfterBackground);
        PrepareInventoryMobEffectsCallback.EVENT.register(EffectScreenHandler::onPrepareInventoryMobEffects);
        ScreenOpeningCallback.EVENT.register(EffectScreenHandler::onScreenOpening);
        ClientTickEvents.START.register(EffectDurationHandler::onStartClientTick);
    }

    @Override
    public void onClientSetup() {
        StylishEffects.CONFIG.getHolder(ClientConfig.class)
                .addCallback(() -> EffectScreenHandler.rebuildGuiRenderer(Minecraft.getInstance()));
    }

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.replaceGuiLayer(GuiLayersContext.STATUS_EFFECTS, (GuiLayersContext.Layer layer) -> {
            return EffectScreenHandler::renderStatusEffects;
        });
    }
}

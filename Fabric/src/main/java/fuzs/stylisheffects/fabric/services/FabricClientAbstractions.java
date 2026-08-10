package fuzs.stylisheffects.fabric.services;

import fuzs.puzzleslib.fabric.api.client.event.v1.FabricGuiEvents;
import fuzs.stylisheffects.common.services.ClientAbstractions;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public final class FabricClientAbstractions implements ClientAbstractions {
    @Override
    public boolean extractInventoryText(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int color) {
        return false;
    }

    @Override
    public boolean extractInventoryIcon(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int color) {
        return false;
    }

    @Override
    public boolean extractHudIcon(MobEffectInstance mobEffect, Hud hud, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int color) {
        return false;
    }

    @Override
    public void onGatherEffectScreenTooltip(AbstractContainerScreen<?> screen, MobEffectInstance mobEffect, List<Component> tooltipLines) {
        FabricGuiEvents.GATHER_EFFECT_SCREEN_TOOLTIP.invoker()
                .onGatherEffectScreenTooltip(screen, mobEffect, tooltipLines);
    }
}

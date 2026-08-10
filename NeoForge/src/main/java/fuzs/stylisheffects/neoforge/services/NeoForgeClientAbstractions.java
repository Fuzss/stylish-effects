package fuzs.stylisheffects.neoforge.services;

import fuzs.stylisheffects.common.services.ClientAbstractions;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.event.GatherEffectScreenTooltipsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

public final class NeoForgeClientAbstractions implements ClientAbstractions {
    @Override
    public boolean extractInventoryText(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int color) {
        return IClientMobEffectExtensions.of(mobEffect)
                .extractInventoryText(mobEffect, screen, guiGraphics, x, y, width, color);
    }

    @Override
    public boolean extractInventoryIcon(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int color) {
        return IClientMobEffectExtensions.of(mobEffect)
                .extractInventoryIcon(mobEffect, screen, guiGraphics, x, y, width, height, color);
    }

    @Override
    public boolean extractHudIcon(MobEffectInstance mobEffect, Hud hud, GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int color) {
        return IClientMobEffectExtensions.of(mobEffect)
                .extractHudIcon(mobEffect, hud, guiGraphics, x, y, width, height, color);
    }

    @Override
    public void onGatherEffectScreenTooltip(AbstractContainerScreen<?> screen, MobEffectInstance mobEffect, List<Component> tooltipLines) {
        NeoForge.EVENT_BUS.post(new GatherEffectScreenTooltipsEvent(screen, mobEffect, tooltipLines));
    }
}

package fuzs.stylisheffects.common.services;

import fuzs.puzzleslib.common.api.core.v1.ServiceProviderHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public interface ClientAbstractions {
    ClientAbstractions INSTANCE = ServiceProviderHelper.load(ClientAbstractions.class);

    boolean renderInventoryText(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int blitOffset);

    boolean renderInventoryIcon(MobEffectInstance mobEffect, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int blitOffset);

    boolean renderGuiIcon(MobEffectInstance mobEffect, Gui gui, GuiGraphicsExtractor guiGraphics, int x, int y, float z, float alpha);

    void onGatherEffectScreenTooltip(AbstractContainerScreen<?> screen, MobEffectInstance mobEffect, List<Component> tooltipLines);
}

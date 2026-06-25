package fuzs.stylisheffects.client.gui.effects;

import fuzs.puzzleslib.api.chat.v1.ComponentHelper;
import fuzs.stylisheffects.StylishEffects;
import fuzs.stylisheffects.api.client.stylisheffects.v1.MobEffectWidgetContext;
import fuzs.stylisheffects.client.core.ClientAbstractions;
import fuzs.stylisheffects.client.handler.EffectRendererEnvironment;
import fuzs.stylisheffects.client.util.ColorUtil;
import fuzs.stylisheffects.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.effect.MobEffectInstance;

public class InventoryFullSizeEffectRenderer extends AbstractEffectRenderer {

    public InventoryFullSizeEffectRenderer(EffectRendererEnvironment environment) {
        super(environment);
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    protected int getBackgroundTextureX() {
        return 0;
    }

    @Override
    protected int getBackgroundTextureY() {
        return 0;
    }

    @Override
    protected int getSpriteOffsetX() {
        return 7;
    }

    @Override
    protected int getSpriteOffsetY(boolean withoutDuration) {
        return 7;
    }

    @Override
    public MobEffectWidgetContext.Renderer getEffectRenderer() {
        return MobEffectWidgetContext.Renderer.INVENTORY_FULL_SIZE;
    }

    @Override
    public EffectRendererEnvironment.Factory getFallbackRenderer() {
        return InventoryCompactEffectRenderer::new;
    }

    @Override
    protected ClientConfig.InventoryFullSizeWidgetConfig widgetConfig() {
        return StylishEffects.CONFIG.get(ClientConfig.class).inventoryFullSizeWidget();
    }

    @Override
    protected void drawEffectText(GuiGraphics guiGraphics, int posX, int posY, Minecraft minecraft, MobEffectInstance mobEffect) {
        // disable font shadows, they force text to render on top above everything else, which breaks the widget layering
        if (!(this.screen instanceof EffectRenderingInventoryScreen<?> effectInventoryScreen) || !ClientAbstractions.INSTANCE.renderInventoryText(
                mobEffect, effectInventoryScreen, guiGraphics, posX, posY, 0)) {
            Component component = this.getEffectDisplayName(mobEffect);
            int nameColor = ColorUtil.getEffectColor(this.widgetConfig().nameColor, mobEffect);
            int minX = posX + 12 + 18;
            int maxX = posX + this.getWidth() - 7;
            int alpha = (int) (this.rendererConfig().widgetAlpha * 255.0F) << 24;
            guiGraphics.drawString(minecraft.font, this.processEffectDisplayName(minecraft.font, component, maxX - minX),
                    minX, posY + 6 + (!this.widgetConfig().ambientDuration && mobEffect.isAmbient() ? 4 : 0), alpha | nameColor, false);
            if (this.widgetConfig().ambientDuration || !mobEffect.isAmbient()) {
                this.getEffectDuration(mobEffect).ifPresent(duration -> {
                    int durationColor = ColorUtil.getEffectColor(this.widgetConfig().durationColor, mobEffect);
                    guiGraphics.drawString(minecraft.font, duration,
                            minX, posY + 7 + 11, alpha | durationColor, false);
                });
            }
        }
    }

    private Component processEffectDisplayName(Font font, Component component, int maxWidth) {
        int width = font.width(component);
        if (width > maxWidth) {
            FormattedText formattedText = font.substrByWidth(component,
                    maxWidth - font.width(CommonComponents.ELLIPSIS));
            return ComponentHelper.toComponent(formattedText).copy().append(CommonComponents.ELLIPSIS);
        } else {
            return component;
        }
    }

    @Override
    protected boolean isInfiniteDuration(MobEffectInstance mobEffectInstance) {
        return mobEffectInstance.isInfiniteDuration();
    }
}

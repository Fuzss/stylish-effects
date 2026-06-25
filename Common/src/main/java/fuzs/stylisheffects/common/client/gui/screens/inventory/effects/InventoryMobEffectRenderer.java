package fuzs.stylisheffects.common.client.gui.screens.inventory.effects;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import fuzs.puzzleslib.common.api.util.v1.ComponentHelper;
import fuzs.stylisheffects.common.StylishEffects;
import fuzs.stylisheffects.common.config.BarPosition;
import fuzs.stylisheffects.common.config.WidgetType;
import fuzs.stylisheffects.common.services.ClientAbstractions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public abstract class InventoryMobEffectRenderer extends AbstractMobEffectRenderer {
    protected static final Identifier EFFECT_BACKGROUND_SPRITE = Identifier.withDefaultNamespace(
            "container/inventory/effect_background");
    protected static final Identifier EFFECT_BACKGROUND_AMBIENT_SPRITE = Identifier.withDefaultNamespace(
            "container/inventory/effect_background_ambient");
    protected static final Map<BarPosition, Identifier> EFFECT_BACKGROUND_OVERLAY_SPRITES = Maps.immutableEnumMap(
            ImmutableMap.<BarPosition, Identifier>builder()
                    .put(BarPosition.CENTER, StylishEffects.id("container/inventory/effect_background_overlay"))
                    .put(BarPosition.TOP, StylishEffects.id("container/inventory/effect_background_overlay_top"))
                    .put(BarPosition.RIGHT, StylishEffects.id("container/inventory/effect_background_overlay_right"))
                    .put(BarPosition.BOTTOM, StylishEffects.id("container/inventory/effect_background_overlay_bottom"))
                    .put(BarPosition.LEFT, StylishEffects.id("container/inventory/effect_background_overlay_left"))
                    .build());

    public InventoryMobEffectRenderer(Either<Gui, AbstractContainerScreen<?>> environment) {
        super(environment);
    }

    @Override
    public int getWidth() {
        return 32;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    protected int getSpriteOffsetY(boolean withoutDuration) {
        return 7;
    }

    @Override
    protected int getBorderSize() {
        return 4;
    }

    @Override
    protected int getAmplifierOffsetX() {
        return 3;
    }

    @Override
    protected int getAmplifierOffsetY() {
        return 3;
    }

    @Override
    protected int getDurationOffsetY() {
        return this.getHeight() - 13;
    }

    @Override
    protected Identifier getEffectBackgroundSprite(boolean isAmbient) {
        return isAmbient ? EFFECT_BACKGROUND_AMBIENT_SPRITE : EFFECT_BACKGROUND_SPRITE;
    }

    @Override
    protected Identifier getEffectBarSprite(BarPosition barPosition) {
        return EFFECT_BACKGROUND_OVERLAY_SPRITES.get(barPosition);
    }

    public static class Small extends InventoryMobEffectRenderer {

        public Small(Either<Gui, AbstractContainerScreen<?>> environment) {
            super(environment);
        }

        @Override
        protected int getSpriteOffsetY(boolean withoutDuration) {
            return withoutDuration ? super.getSpriteOffsetY(true) : 6;
        }

        @Override
        public WidgetType.Factory getFallbackRenderer() {
            return GuiMobEffectRenderer.Large::new;
        }
    }

    public static class Large extends InventoryMobEffectRenderer {

        public Large(Either<Gui, AbstractContainerScreen<?>> environment) {
            super(environment);
        }

        @Override
        public int getWidth() {
            return 120;
        }

        @Override
        protected int getSpriteOffsetX() {
            return 7;
        }

        @Override
        public WidgetType.Factory getFallbackRenderer() {
            return Small::new;
        }

        @Override
        protected void renderLabels(GuiGraphicsExtractor guiGraphics, int posX, int posY, MobEffectInstance mobEffect) {
            if (!this.renderCustomLabels(guiGraphics, posX, posY, mobEffect)) {
                int minX = posX + 12 + 18;
                int maxX = posX + this.getWidth() - 7;
                Component component = this.getEffectDuration(mobEffect, maxX - minX);
                int minY = posY + 6 + (component == null ? 4 : 0);
                int maxY = minY + Minecraft.getInstance().font.lineHeight;
                ActiveTextCollector activeTextCollector = this.activeTextCollector(guiGraphics);
                this.extractDisplayName(activeTextCollector, mobEffect, minX, maxX, minY, maxY);
                this.extractDuration(activeTextCollector, mobEffect, component, minX, minY);
            }
        }

        private void extractDisplayName(ActiveTextCollector activeTextCollector, MobEffectInstance mobEffect, int minX, int maxX, int minY, int maxY) {
            Font font = Minecraft.getInstance().font;
            Component component = this.getEffectDisplayName(mobEffect, false);
            Style style = this.config.nameColor.getMobEffectStyle(mobEffect);
            int width = font.width(component);
            int maxWidth = maxX - minX;
            if (width > maxWidth) {
                // The scissor area is messed up when the scale is smaller than one; might be a vanilla bug.
                // So, we work around that by just adding an ellipsis.
                if (this.getWidgetScale() < 1.0F) {
                    FormattedText formattedText = font.substrByWidth(component,
                            maxWidth - font.width(CommonComponents.ELLIPSIS));
                    component = ComponentHelper.getAsComponent(formattedText);
                    activeTextCollector.accept(minX,
                            minY + 1,
                            Component.empty().append(component).append(CommonComponents.ELLIPSIS).setStyle(style));
                } else {
                    activeTextCollector.acceptScrollingWithDefaultCenter(ComponentUtils.mergeStyles(component, style),
                            minX,
                            maxX,
                            minY,
                            maxY);
                }
            } else {
                // The text renderer defaults to centering in the middle when the text fits; we do not want that.
                activeTextCollector.accept(minX, minY + 1, ComponentUtils.mergeStyles(component, style));
            }
        }

        private void extractDuration(ActiveTextCollector activeTextCollector, MobEffectInstance mobEffect, @Nullable Component component, int posX, int posY) {
            if (component != null) {
                Style durationStyle = this.config.effectDuration.durationColor.getMobEffectStyle(mobEffect);
                activeTextCollector.accept(posX, posY + 1 + 11, ComponentUtils.mergeStyles(component, durationStyle));
            }
        }

        protected boolean renderCustomLabels(GuiGraphicsExtractor guiGraphics, int posX, int posY, MobEffectInstance mobEffect) {
            return this.environment.right().map((AbstractContainerScreen<?> screen) -> {
                return ClientAbstractions.INSTANCE.renderInventoryText(mobEffect, screen, guiGraphics, posX, posY, 0);
            }).orElse(Boolean.FALSE);
        }
    }
}

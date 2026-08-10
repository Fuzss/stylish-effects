package fuzs.stylisheffects.common.config;

import com.mojang.datafixers.util.Either;
import fuzs.stylisheffects.common.client.gui.screens.inventory.effects.AbstractMobEffectExtractor;
import fuzs.stylisheffects.common.client.gui.screens.inventory.effects.GuiMobEffectExtractor;
import fuzs.stylisheffects.common.client.gui.screens.inventory.effects.InventoryMobEffectExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.util.function.Function;

/**
 * type of renderer that is used
 */
public enum WidgetType {
    /**
     * nothing is rendered
     */
    NONE((Either<Hud, AbstractContainerScreen<?>> either) -> {
        throw new IllegalStateException("Cannot create effect renderer");
    }),
    /**
     * vanilla's native effect rendering on the in-game gui
     */
    GUI_SQUARE(GuiMobEffectExtractor.Small::new),
    /**
     * our default rendering, similar to {@link #GUI_SQUARE}, just slightly larger with more information
     */
    GUI_RECTANGLE(GuiMobEffectExtractor.Large::new),
    /**
     * vanilla's compact inventory widgets
     */
    INVENTORY_SQUARE(InventoryMobEffectExtractor.Small::new),
    /**
     * vanilla's full sized inventory widgets
     */
    INVENTORY_RECTANGLE(InventoryMobEffectExtractor.Large::new);

    public final Factory factory;

    WidgetType(Factory factory) {
        this.factory = factory;
    }

    @FunctionalInterface
    public interface Factory extends Function<Either<Hud, AbstractContainerScreen<?>>, AbstractMobEffectExtractor> {

    }
}

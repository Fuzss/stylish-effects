package fuzs.stylisheffects.common.mixin.integration.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.plugin.client.exclusionzones.DefaultPotionEffectExclusionZones;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Collections;

@Pseudo
@Mixin(DefaultPotionEffectExclusionZones.class)
abstract class DefaultPotionEffectExclusionZonesMixin {

    @Inject(method = "provide(Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;)Ljava/util/Collection;",
            at = @At("HEAD"),
            cancellable = true)
    public void provide(CallbackInfoReturnable<Collection<Rectangle>> callback) {
        // We handle this ourselves by providing a separate REI plugin.
        callback.setReturnValue(Collections.emptyList());
    }
}

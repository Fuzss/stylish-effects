package fuzs.stylisheffects.neoforge;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.stylisheffects.common.StylishEffects;
import net.neoforged.fml.common.Mod;

@Mod(StylishEffects.MOD_ID)
public class StylishEffectsNeoForge {

    public StylishEffectsNeoForge() {
        ModConstructor.construct(StylishEffects.MOD_ID, StylishEffects::new);
    }
}

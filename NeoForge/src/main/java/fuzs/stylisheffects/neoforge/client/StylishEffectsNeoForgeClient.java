package fuzs.stylisheffects.neoforge.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import fuzs.stylisheffects.common.StylishEffects;
import fuzs.stylisheffects.common.client.StylishEffectsClient;
import fuzs.stylisheffects.common.data.client.ModLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = StylishEffects.MOD_ID, dist = Dist.CLIENT)
public class StylishEffectsNeoForgeClient {

    public StylishEffectsNeoForgeClient() {
        ClientModConstructor.construct(StylishEffects.MOD_ID, StylishEffectsClient::new);
        DataProviderBuilder.of(StylishEffects.MOD_ID).addProvider(ModLanguageProvider::new);
    }
}

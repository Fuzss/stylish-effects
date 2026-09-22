package fuzs.stylisheffects.common.data.client;

import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.stylisheffects.common.client.handler.EffectScreenHandler;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.add(EffectScreenHandler.KEY_DEBUG_MENU_TYPE, "Menu Type: %s");
    }
}

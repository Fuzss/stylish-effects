plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-fabric")
}

dependencies {
    modApi(sharedLibs.fabricapi.fabric)
    modApi(sharedLibs.puzzleslib.fabric)
    modCompileOnly(sharedLibs.jeiapi.common)
    modLocalRuntime(sharedLibs.jei.fabric)
//    modCompileOnly(sharedLibs.reiapi.fabric)
//    modCompileOnly(sharedLibs.reidefaultplugin.fabric)
//    modLocalRuntime(sharedLibs.bundles.rei.fabric)
}

multiloader {
    modFile {
        json {
            entrypoint(
                "jei_mod_plugin",
                "${project.group}.integration.jei.StylishEffectsJeiPlugin"
            )
//            entrypoint(
//                "rei_client",
//                "${project.group}.integration.rei.StylishEffectsReiClientPlugin"
//            )
        }
    }
}

plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
    modCompileOnly(sharedLibs.jeiapi.common)
//    modCompileOnly(sharedLibs.reiapi.common)
//    modCompileOnly(sharedLibs.reidefaultplugin.common)
}

plugins {
    id("fuzs.multiloader.multiloader-convention-plugins-common")
}

dependencies {
    modCompileOnlyApi(sharedLibs.puzzleslib.common)
    compileOnly(sharedLibs.jeiapi.common)
//    compileOnly(sharedLibs.reiapi.common)
//    compileOnly(sharedLibs.reidefaultplugin.common)
}

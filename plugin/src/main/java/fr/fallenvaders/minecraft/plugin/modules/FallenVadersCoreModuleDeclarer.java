package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.core.FallenVadersCore;

public class FallenVadersCoreModuleDeclarer extends ModuleDeclarer {

    public static final String MODULE_NAME = "fallenvaders-core";

    public FallenVadersCoreModuleDeclarer() {
        super(MODULE_NAME);
    }

    @Override
    public void onEnable() {
        FallenVadersCore.enableModule(getPlugin());
    }

    @Override
    public void onDisable() {
        FallenVadersCore.disableModule(getPlugin());
    }
}

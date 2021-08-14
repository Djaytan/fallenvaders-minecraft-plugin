package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.core.FallenVadersCore;

public class JusticeHandsModuleDeclarer extends ModuleDeclarer {

    public static final String MODULE_NAME = "justice-hands";

    public JusticeHandsModuleDeclarer() {
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

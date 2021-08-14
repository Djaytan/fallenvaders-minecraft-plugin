package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.justice_hands.JusticeHands;

public class JusticeHandsModuleDeclarer extends ModuleDeclarer {

    public static final String MODULE_NAME = "justice-hands";

    public JusticeHandsModuleDeclarer() {
        super(MODULE_NAME);
    }

    @Override
    public void onEnable() {
        JusticeHands.enableModule(getPlugin());
    }

    @Override
    public void onDisable() {
        JusticeHands.disableModule(getPlugin());
    }
}

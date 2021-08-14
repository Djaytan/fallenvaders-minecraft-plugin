package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;

public class MinecraftEnhanceModuleDeclarer extends ModuleDeclarer {

    public static final String MODULE_NAME = "minecraft-enhancer";

    public MinecraftEnhanceModuleDeclarer() {
        super(MODULE_NAME);
    }

    @Override
    public void onEnable() {
        System.out.println("Module: TEST");
        getPlugin().getCommand("ping").setExecutor(new PingCmd());
    }
}

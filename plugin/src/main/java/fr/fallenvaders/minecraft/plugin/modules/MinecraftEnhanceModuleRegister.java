package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.mc_enhancer.controller.commands.PingCmd;

public class MinecraftEnhanceModuleRegister extends ModuleRegister {

    public static final String MODULE_NAME = "minecraft-enhancer";

    public MinecraftEnhanceModuleRegister() {
        super(MODULE_NAME);
    }

    @Override
    public void onEnable() {
        System.out.println("Module: TEST");
        getPlugin().getCommand("ping").setExecutor(new PingCmd());
    }
}

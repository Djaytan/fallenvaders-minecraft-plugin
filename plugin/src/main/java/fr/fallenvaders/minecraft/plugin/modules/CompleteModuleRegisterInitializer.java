package fr.fallenvaders.minecraft.plugin.modules;

public class CompleteModuleRegisterInitializer implements ModuleRegisterInitializer {

    @Override
    public ModuleRegister initialize() throws ModuleRegisterException {
        ModuleRegister register = new ModuleRegister();
        register.registerModule(new MinecraftEnhanceModuleDeclarer());
        return register;
    }
}

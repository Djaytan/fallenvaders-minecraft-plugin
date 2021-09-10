package fr.fallenvaders.minecraft.plugin.modules;

public class CompleteModuleRegisterInitializer implements ModuleRegisterInitializer {

  @Override
  public ModuleRegister initialize() throws ModuleRegisterException {
    ModuleRegister register = new ModuleRegister();
    register.registerModule(new FallenVadersCoreModuleDeclarer());
    register.registerModule(new JusticeHandsModuleDeclarer());
    register.registerModule(new MailBoxModuleDeclarer());
    register.registerModule(new MinecraftEnhanceModuleDeclarer());
    register.registerModule(new MiniEventsModuleDeclarer());
    return register;
  }
}
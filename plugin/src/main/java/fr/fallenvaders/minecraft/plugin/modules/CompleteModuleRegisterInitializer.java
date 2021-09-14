package fr.fallenvaders.minecraft.plugin.modules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

@Singleton
public final class CompleteModuleRegisterInitializer implements ModuleRegisterInitializer {

  private final Logger logger;
  private final ModuleRegister moduleRegister;

  @Inject
  public CompleteModuleRegisterInitializer(@NotNull Logger logger, @NotNull ModuleRegister moduleRegister) {
    Objects.requireNonNull(logger);
    Objects.requireNonNull(moduleRegister);

    this.logger = logger;
    this.moduleRegister = moduleRegister;
  }

  @Override
  public ModuleRegister initialize() throws ModuleRegisterException {
    logger.info("Start modules registration.");
    moduleRegister.registerModule(new FallenVadersCoreModuleDeclarer());
    moduleRegister.registerModule(new JusticeHandsModuleDeclarer());
    moduleRegister.registerModule(new MailBoxModuleDeclarer());
    moduleRegister.registerModule(new MinecraftEnhanceModuleDeclarer());
    moduleRegister.registerModule(new MiniEventsModuleDeclarer());
    logger.info("Modules registration done.");
    return moduleRegister;
  }
}

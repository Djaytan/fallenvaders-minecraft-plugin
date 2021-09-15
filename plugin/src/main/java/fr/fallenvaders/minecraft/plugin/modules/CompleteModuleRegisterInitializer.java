package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.modules.declarers.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

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
  public void initialize() throws ModuleRegisterException {
    logger.info("Start modules registration.");
    moduleRegister.registerModule(new FallenVadersCoreModuleDeclarer());
    moduleRegister.registerModule(new JusticeHandsModuleDeclarer());
    moduleRegister.registerModule(new MailBoxModuleDeclarer());
    moduleRegister.registerModule(new MinecraftEnhanceModuleDeclarer());
    moduleRegister.registerModule(new MiniEventsModuleDeclarer());
    logger.info("Modules registration done.");
  }
}

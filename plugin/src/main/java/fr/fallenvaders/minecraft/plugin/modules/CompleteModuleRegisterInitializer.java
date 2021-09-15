package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.plugin.modules.declarers.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is an implementation class of {@link ModuleRegisterInitializer} interface. The purpose of
 * this initializer is to assemble a full version of the plugin by injection all the existing {@link
 * ModuleDeclarer}s.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public final class CompleteModuleRegisterInitializer implements ModuleRegisterInitializer {

  private final Logger logger;
  private final ModuleRegisterService moduleRegisterService;

  @Inject
  public CompleteModuleRegisterInitializer(
      @NotNull Logger logger, @NotNull ModuleRegisterService moduleRegisterService) {
    Objects.requireNonNull(logger);
    Objects.requireNonNull(moduleRegisterService);

    this.logger = logger;
    this.moduleRegisterService = moduleRegisterService;
  }

  @Override
  public void initialize() throws ModuleRegisterException {
    logger.info("Start modules registration.");
    moduleRegisterService.registerModule(new FallenVadersCoreModuleDeclarer());
    moduleRegisterService.registerModule(new JusticeHandsModuleDeclarer());
    moduleRegisterService.registerModule(new MailBoxModuleDeclarer());
    moduleRegisterService.registerModule(new MinecraftEnhanceModuleDeclarer());
    moduleRegisterService.registerModule(new MiniEventsModuleDeclarer());
    logger.info("Modules registration done.");
  }
}

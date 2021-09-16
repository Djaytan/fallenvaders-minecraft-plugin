package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * This is a singleton class which manage registration of {@link ModuleDeclarer}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class ModuleRegisterService {

  private final Logger logger;
  private final ModuleDeclarerFactory moduleDeclarerFactory;
  private final ModuleRegisterContainer moduleRegisterContainer;

  /**
   * Constructor.
   *
   * @param logger The SLF4J project's logger instance.
   * @param moduleDeclarerFactory The {@link ModuleDeclarer} factory.
   * @param moduleRegisterContainer The container associated to this service.
   */
  @Inject
  public ModuleRegisterService(
      @NotNull Logger logger,
      @NotNull ModuleDeclarerFactory moduleDeclarerFactory,
      @NotNull ModuleRegisterContainer moduleRegisterContainer) {
    Objects.requireNonNull(logger);
    Objects.requireNonNull(moduleDeclarerFactory);
    Objects.requireNonNull(moduleRegisterContainer);
    this.logger = logger;
    this.moduleDeclarerFactory = moduleDeclarerFactory;
    this.moduleRegisterContainer = moduleRegisterContainer;
  }

  /**
   * Registers a {@link ModuleDeclarer}. If modules registration has already been launched, then an
   * exception is thrown.
   *
   * @param moduleDeclarer The module to register.
   * @throws ModuleRegisterException if modules registration has already been launched or the {@link
   *     ModuleDeclarer} is already registered.
   */
  public void registerModule(@NotNull ModuleDeclarer moduleDeclarer)
      throws ModuleRegisterException {
    Objects.requireNonNull(moduleDeclarer);

    if (moduleRegisterContainer.isHasLaunched()) {
      throw new ModuleRegisterException(
          "Module registration rejected: the module registration process has already been launched.");
    }
    moduleRegisterContainer.addModule(moduleDeclarer);
  }

  /**
   * Registers a {@link ModuleDeclarer}. If modules registration has already been launched, then an
   * exception is thrown.
   *
   * @param moduleEnum The module to register.
   * @throws ModuleRegisterException if modules registration has already been launched or the {@link
   *     ModuleDeclarer} is already registered.
   */
  public void registerModule(@NotNull ModuleEnum moduleEnum) throws ModuleRegisterException {
    Objects.requireNonNull(moduleEnum);
    ModuleDeclarer moduleDeclarer = moduleDeclarerFactory.createModule(moduleEnum);
    registerModule(moduleDeclarer);
  }

  /**
   * Enables all registered {@link ModuleDeclarer} by calling the {@link ModuleDeclarer#onEnable()}
   * method for each of them. After that, modules registration is considered has "launched" and none
   * new registrations are allowed anymore.
   */
  public void enableModules() {
    moduleRegisterContainer
        .getModules()
        .forEach(
            module -> {
              module.onEnable();
              logger.info("Module {} enabled.", module.getModuleName());
            });
    moduleRegisterContainer.setHasLaunched(true);
  }

  /**
   * Disables all registered {@link ModuleDeclarer} by calling the {@link
   * ModuleDeclarer#onDisable()} method for each of them.
   */
  public void disableModules() {
    moduleRegisterContainer
        .getModules()
        .forEach(
            module -> {
              module.onDisable();
              logger.info("Module {} disabled.", module.getModuleName());
            });
  }
}

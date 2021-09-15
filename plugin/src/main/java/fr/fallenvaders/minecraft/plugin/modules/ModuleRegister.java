package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: rename to ModuleRegisterService & create ModuleRegisterContainer

/**
 * This is a singleton class which manage registration of {@link ModuleDeclarer}.
 *
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public class ModuleRegister {

  private final Logger logger;

  private final List<ModuleDeclarer> modules = new ArrayList<>();
  private boolean hasLaunched = false;

  @Inject
  public ModuleRegister(@NotNull Logger logger) {
    Objects.requireNonNull(logger);

    this.logger = logger;
  }

  /**
   * Registers a {@link ModuleDeclarer}. If the {@link ModuleRegister} has already been launched
   * an exception is thrown.
   *
   * @param moduleDeclarer The {@link ModuleDeclarer} to register.
   * @throws ModuleRegisterException if the {@link ModuleRegister} has already been launched.
   */
  public void registerModule(@NotNull ModuleDeclarer moduleDeclarer) throws ModuleRegisterException {
    Objects.requireNonNull(moduleDeclarer);

    if (hasLaunched) {
      throw new ModuleRegisterException(
          "Module registration rejected: the module registration process has already been launched.");
    }
    modules.add(moduleDeclarer);
  }

  /**
   * Enables all registered {@link ModuleDeclarer} by calling the {@link ModuleDeclarer#onEnable()} method
   * for each of them. After that, the {@link ModuleRegister} is considered has "launched" and does not accept
   * new registrations anymore.
   */
  public void enableModules() {
    modules.forEach(
        module -> {
          module.onEnable();
          logger.info("Module {} enabled.", module.getModuleName());
        });
    hasLaunched = true;
  }

  /**
   * Disables all registered {@link ModuleDeclarer} by calling the {@link ModuleDeclarer#onDisable()} method
   * for each of them.
   */
  public void disableModules() {
    modules.forEach(
        module -> {
          module.onDisable();
          logger.info("Module {} disabled.", module.getModuleName());
        });
  }

  /**
   * Returns {@link Boolean#TRUE} if the {@link ModuleRegister} has already been launched.
   *
   * @return {@link Boolean#TRUE} if the {@link ModuleRegister} has already been launched.
   */
  public boolean hasLaunched() {
    return hasLaunched;
  }

  /**
   * Returns the {@link List} of {@link ModuleDeclarer} actually successfully registered.
   *
   * @return the {@link List} of {@link ModuleDeclarer} actually successfully registered.
   */
  public @NotNull List<@NotNull ModuleDeclarer> getModules() {
    // TODO: return clone version of the list
    return modules;
  }
}

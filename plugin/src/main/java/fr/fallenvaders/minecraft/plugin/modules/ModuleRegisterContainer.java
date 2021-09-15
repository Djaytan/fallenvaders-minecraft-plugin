package fr.fallenvaders.minecraft.plugin.modules;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class represents the container for the {@link ModuleRegisterService}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public class ModuleRegisterContainer {

  private List<ModuleDeclarer> modules;
  private boolean hasLaunched;

  /**
   * The constructor of the {@link ModuleRegisterContainer} singleton instance.
   */
  public ModuleRegisterContainer() {
    modules = new ArrayList<>();
    hasLaunched = false;
  }

  /**
   * Adds a module in the module register. If a module with the same name of the new one exists, then
   * the operation is cancelled and an exception is thrown.
   *
   * @param module The module to add into the register.
   * @throws ModuleRegisterException if a module with the same name of the new on already exists.
   */
  public void addModule(@NotNull ModuleDeclarer module) throws ModuleRegisterException {
    if (!modules.contains(module)) {
      modules.add(module);
    } else {
      throw new ModuleRegisterException(
        String.format("The module with the name %s already exists in the register.", module.getModuleName()));
    }
  }

  /**
   * Tries to find the registered {@link ModuleDeclarer} which match with the given module's name.
   *
   * @param moduleName The name of the sought module.
   * @return The registered {@link ModuleDeclarer} which match with the given module's name if it exists.
   */
  public @Nullable ModuleDeclarer getModule(@NotNull String moduleName) {
    return modules.stream()
      .filter(module -> module.getModuleName().equals(moduleName))
      .findFirst()
      .orElse(null);
  }

  /**
   * Returns the list of {@link ModuleDeclarer} registered.
   *
   * @return The list of {@link ModuleDeclarer} registered.
   */
  public List<ModuleDeclarer> getModules() {
    return modules;
  }

  /**
   * Defines the new list of {@link ModuleDeclarer} to register.
   *
   * @param modules The new list of {@link ModuleDeclarer} to register.
   */
  public void setModules(List<ModuleDeclarer> modules) {
    this.modules = modules;
  }

  /**
   * Returns {@link Boolean#TRUE} if the registration has been launched.
   *
   * @return {@link Boolean#TRUE} if the registration has been launched.
   */
  public boolean isHasLaunched() {
    return hasLaunched;
  }

  /**
   * Defines if the registration has been launched or no.
   *
   * @param hasLaunched {@link Boolean#TRUE} if the registration has been launched, {@link Boolean#FALSE} otherwise.
   */
  public void setHasLaunched(boolean hasLaunched) {
    this.hasLaunched = hasLaunched;
  }
}
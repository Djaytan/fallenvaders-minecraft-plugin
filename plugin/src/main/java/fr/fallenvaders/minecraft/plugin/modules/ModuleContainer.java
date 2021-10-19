/*
 *  This file is part of the FallenVaders distribution (https://github.com/FallenVaders).
 *  Copyright © 2021 Loïc DUBOIS-TERMOZ.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package fr.fallenvaders.minecraft.plugin.modules;

import fr.fallenvaders.minecraft.commons.FvModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class represents the container for the {@link ModuleService}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class ModuleContainer {

  private final List<FvModule> modules = new ArrayList<>();
  private PluginModulesState state = null;

  /**
   * Adds a module in the module container. If a module with the same name of the new one exists,
   * then the operation is cancelled and an exception is thrown.
   *
   * @param module The module to add into the container.
   * @throws ModuleException if a module with the same name of the new on already exists or the
   *     loading process as already been launched.
   */
  public void addModule(@NotNull FvModule module) throws ModuleException {
    if (state == null) {
      if (!isExist(module.getModuleName())) {
        modules.add(module);
      } else {
        throw new ModuleException(
            String.format(
                "The module with the name %s already exists in the register.",
                module.getModuleName()));
      }
    } else {
      throw new ModuleException(
          String.format(
              "Impossible to load the module '%s': the loading as already been launched.",
              module.getModuleName()));
    }
  }

  /**
   * Tries to find the registered {@link FvModule} which match with the given module's name.
   *
   * @param moduleName The name of the sought module.
   * @return The registered module which match with the given module's name if it exists.
   */
  @Nullable
  public FvModule getModule(@NotNull String moduleName) {
    return modules.stream()
        .filter(module -> module.getModuleName().equals(moduleName))
        .findFirst()
        .orElse(null);
  }

  /**
   * Returns the list of {@link FvModule} registered.
   *
   * @return The list of {@link FvModule} registered.
   */
  @NotNull
  public List<FvModule> getModules() {
    return modules;
  }

  /**
   * Getter.
   *
   * @return The plugin's modules state.
   */
  public @Nullable PluginModulesState getState() {
    return state;
  }

  /**
   * Setter.
   *
   * @param state The new plugin's modules state.
   * @throws ModuleException if the new state can't follow the previous one.
   */
  public void setState(@NotNull PluginModulesState state) throws ModuleException {
    if (isExpectedNextStep(state)) {
      this.state = state;
    } else {
      throw new ModuleException(
          String.format(
              "Wrong new plugin's modules state: '%s' state can't follow the '%s' one.",
              state.name(), this.state.name()));
    }
  }

  private boolean isExist(@NotNull String moduleName) {
    return getModule(moduleName) != null;
  }

  private boolean isExpectedNextStep(@NotNull PluginModulesState state) {
    int expectedNewStateOrder = (this.state == null) ? 1 : this.state.getStateOrder() + 1;
    return expectedNewStateOrder == state.getStateOrder();
  }
}

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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This singleton class represents the container for the {@link ModuleRegisterService}.
 *
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class ModuleRegisterContainer {

  private final List<FvModule> modules;
  private boolean hasLaunched;

  /** Constructor. */
  @Inject
  public ModuleRegisterContainer() {
    modules = new ArrayList<>();
    hasLaunched = false;
  }

  /**
   * Adds a module in the module register. If a module with the same name of the new one exists,
   * then the operation is cancelled and an exception is thrown.
   *
   * @param module The module to add into the register.
   * @throws ModuleRegisterException if a module with the same name of the new on already exists.
   */
  public void addModule(@NotNull FvModule module) throws ModuleRegisterException {
    Objects.requireNonNull(module);

    FvModule existingModule = getModule(module.getModuleName());

    if (existingModule == null) {
      modules.add(module);
    } else {
      throw new ModuleRegisterException(
          String.format(
              "The module with the name %s already exists in the register.",
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
    Objects.requireNonNull(moduleName);
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
   * @param hasLaunched {@link Boolean#TRUE} if the registration has been launched, {@link
   *     Boolean#FALSE} otherwise.
   */
  public void setHasLaunched(boolean hasLaunched) {
    this.hasLaunched = hasLaunched;
  }
}

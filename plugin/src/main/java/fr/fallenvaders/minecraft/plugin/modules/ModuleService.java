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
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This is a singleton class which manage FallenVaders' modules.
 *
 * @author FallenVaders' dev team
 * @since 0.2.0
 */
@Singleton
public final class ModuleService {

  private final Logger logger;
  private final ModuleContainer moduleContainer;

  /**
   * Constructor.
   *
   * @param logger The SLF4J logger instance.
   * @param moduleContainer The container associated to this service.
   */
  @Inject
  public ModuleService(@NotNull Logger logger, @NotNull ModuleContainer moduleContainer) {
    this.logger = logger;
    this.moduleContainer = moduleContainer;
  }

  /**
   * Registers a {@link FvModule}. If modules registration has already been launched, then an
   * exception is thrown.
   *
   * @param fvModule The FallenVaders' module to register.
   * @throws ModuleException if modules registration has already been launched or the {@link
   *     FvModule} is already registered.
   */
  public void registerModule(@NotNull FvModule fvModule) throws ModuleException {
    if (moduleContainer.isHasLaunched()) {
      throw new ModuleException(
          String.format(
              "Module registration of '%s' rejected: the module registration process has already been launched.",
              fvModule.getModuleName()));
    }
    moduleContainer.addModule(fvModule);
  }

  // TODO: FV-123 - add loadModules method and replace "hasLaunched" by "loaded"

  /**
   * Enables all registered {@link FvModule} by calling the {@link FvModule#onEnable()} method for
   * each of them. After that, modules registration is considered has "launched" and none new
   * registrations are allowed anymore.
   */
  public void enableModules() {
    moduleContainer
        .getModules()
        .forEach(
            module -> {
              module.onEnable();
              logger.info("Module {} enabled.", module.getModuleName());
            });
    moduleContainer.setHasLaunched(true);
  }

  /**
   * Disables all registered {@link FvModule} by calling the {@link FvModule#onDisable()} method for
   * each of them.
   */
  public void disableModules() {
    moduleContainer
        .getModules()
        .forEach(
            module -> {
              module.onDisable();
              logger.info("Module {} disabled.", module.getModuleName());
            });
  }
}

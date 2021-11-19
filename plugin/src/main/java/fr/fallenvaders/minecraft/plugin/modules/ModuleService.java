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
 * @author Voltariuss
 * @since 0.2.0
 */
@Singleton
public final class ModuleService {

  private final Logger logger;
  private final ModuleContainer moduleContainer;

  /**
   * Constructor.
   *
   * @param logger The {@link Logger}.
   * @param moduleContainer The {@link ModuleContainer}.
   */
  @Inject
  public ModuleService(@NotNull Logger logger, @NotNull ModuleContainer moduleContainer) {
    this.logger = logger;
    this.moduleContainer = moduleContainer;
  }

  /**
   * Registers a module. If modules registration has already been launched, then an
   * exception is thrown.
   *
   * @param fvModule The FallenVaders' module to register.
   */
  public void registerModule(@NotNull FvModule fvModule) {
    try {
      moduleContainer.addModule(fvModule);
      logger.info("Module '{}' registered.", fvModule.getModuleName());
    } catch (ModuleException e) {
      logger.error(String.format("Module registration of '%s' rejected.", fvModule.getModuleName()), e);
    }
  }

  /**
   * Loads all registered modules by calling the {@link FvModule#onLoad()} method for
   * each of them. After that, modules registration is considered has "loaded" and none new
   * registrations are allowed anymore.
   */
  public void loadModules() {
    manipulateModules(PluginModulesState.LOADED);
  }

  /**
   * Enables all registered modules by calling the {@link FvModule#onEnable()} method for
   * each of them.
   */
  public void enableModules() {
    manipulateModules(PluginModulesState.ENABLED);
  }

  /**
   * Disables all registered modules by calling the {@link FvModule#onDisable()} method for
   * each of them.
   */
  public void disableModules() {
    manipulateModules(PluginModulesState.DISABLED);
  }

  private void manipulateModules(@NotNull PluginModulesState state) {
    try {
      String stateName = state.name().toLowerCase();
      moduleContainer.setState(state);
      for (FvModule module : moduleContainer.getModules()) {
        switch (state) {
          case UNLOADED -> {/* Nothing to do */}
          case LOADED -> module.onLoad();
          case ENABLED -> module.onEnable();
          case DISABLED -> module.onDisable();
        }
        logger.info("Module '{}' {}.", module.getModuleName(), stateName);
      }
      logger.info("FallenVaders modules successfully {}.", stateName);
    } catch (ModuleException e) {
      logger.error("Fail during modules manipulation ({} phase).", state.name(), e);
    }
  }
}

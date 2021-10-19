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
import fr.fallenvaders.minecraft.plugin.FallenVadersPlugin;

/**
 * This class is in charge to initialize the {@link FallenVadersPlugin} by creating the {@link
 * ModuleService} singleton instance.
 *
 * <p>This interface allows to initialize a full version of the plugin or alternative ones.
 *
 * @author FallenVaders' dev team
 * @since 0.1.0
 */
public interface PluginInitializer {

  /**
   * Initializes {@link ModuleService} singleton instance with {@link FvModule} instances.
   *
   * @throws ModuleException if the initialization into the {@link ModuleService} fail.
   */
  void initialize() throws ModuleException;
}

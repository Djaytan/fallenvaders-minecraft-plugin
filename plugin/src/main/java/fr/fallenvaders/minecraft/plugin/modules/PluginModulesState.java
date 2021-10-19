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
 * This enum represents the state of {@link FvModule} of the {@link FallenVadersPlugin}.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public enum PluginModulesState {
  LOADED(1),
  ENABLED(2),
  DISABLED(3);

  private final int stateOrder;

  /**
   * Constructor.
   *
   * @param stateOrder The state order number.
   */
  PluginModulesState(int stateOrder) {
    this.stateOrder = stateOrder;
  }

  /**
   * Getter.
   *
   * @return The state order number.
   */
  public int getStateOrder() {
    return stateOrder;
  }
}

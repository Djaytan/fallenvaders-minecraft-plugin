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

package fr.fallenvaders.minecraft.commons;

import org.jetbrains.annotations.NotNull;

/**
 * This is the abstraction of a FallenVaders' module. It is mandatory to implement it in
 * each module of the project in order to make possible the registration of the module in the plugin
 * properly.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public abstract class FvModule {

  private final String moduleName;

  /**
   * Constructor.
   *
   * @param moduleName The module's name.
   */
  protected FvModule(@NotNull String moduleName) {
    this.moduleName = moduleName;
  }

  /**
   * Defines the behavior involved when the FallenVaders' module is loaded.
   *
   * <p>Note: this method is called before {@link FvModule#onEnable()}.
   *
   * <p>The implementation of this method is optional.
   */
  public void onLoad() {}

  /**
   * Defines the logic needed to initialize and make operational the FallenVaders' module.
   *
   * <p>The implementation of this method is optional.
   */
  public void onEnable() {}

  /**
   * Defines the logic needed to properly disable the FallenVaders' module.
   *
   * <p>The implementation of this method is optional.
   */
  public void onDisable() {}

  /**
   * Getter.
   *
   * @return The module's name.
   */
  public final @NotNull String getModuleName() {
    return moduleName;
  }
}

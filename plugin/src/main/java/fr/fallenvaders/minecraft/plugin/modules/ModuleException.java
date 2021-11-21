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

import org.jetbrains.annotations.NotNull;

/**
 * This exception is thrown when a problem occurs during the registration process of a module (for
 * example: register a module with the {@link ModuleService} was launched).
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class ModuleException extends Exception {

  /**
   * Constructor.
   *
   * @param message The mandatory exception message.
   */
  public ModuleException(@NotNull String message) {
    super(message);
  }
}

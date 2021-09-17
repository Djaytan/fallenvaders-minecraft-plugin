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

import java.util.Objects;

/**
 * This exception is thrown when a problem occurs during the registration process of a module (e.g.
 * register a module avec the {@link ModuleRegisterService} was launched).
 *
 * @author Voltariuss
 * @since 0.1.0
 */
public class ModuleRegisterException extends Exception {

  /**
   * Constructor.
   *
   * @param message The mandatory exception message.
   */
  public ModuleRegisterException(@NotNull String message) {
    super(message);
    Objects.requireNonNull(message);
  }

  /**
   * Constructor.
   *
   * @param message The mandatory exception message.
   * @param cause The cause exception.
   */
  public ModuleRegisterException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
    Objects.requireNonNull(message);
    Objects.requireNonNull(cause);
  }
}

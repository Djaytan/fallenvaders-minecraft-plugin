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

package fr.fallenvaders.minecraft.test_server.command;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Exception when an error occurs during command execution.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public class CommandExecutionException extends Exception {

  /**
   * Constructor.
   *
   * @param message The mandatory message to describe the exception.
   */
  public CommandExecutionException(@NotNull String message) {
    super(message);
    Objects.requireNonNull(message);
  }

  /**
   * Constructor.
   *
   * @param message The mandatory message to describe the exception.
   * @param cause The cause of this exception.
   */
  public CommandExecutionException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
    Objects.requireNonNull(message);
    Objects.requireNonNull(cause);
  }
}

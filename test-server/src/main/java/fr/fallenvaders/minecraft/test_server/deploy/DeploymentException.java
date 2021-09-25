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

package fr.fallenvaders.minecraft.test_server.deploy;

import org.jetbrains.annotations.NotNull;

/**
 * Exception class throwable during deployment processes.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
public class DeploymentException extends Exception {

  /**
   * Constructor.
   *
   * @param message The mandatory description message of the exception.
   */
  public DeploymentException(@NotNull String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message The mandatory description message of the exception.
   * @param cause The cause of the exception.
   */
  public DeploymentException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
  }
}

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

package fr.fallenvaders.minecraft.commons.sql;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents the DAO exception which may be thrown during an operation of a {@link
 * FvDao} implementation.
 *
 * <p>The use of a custom exception permit to make an abstraction which prevent the service layer to
 * depend on specific dependencies of DAO layer (e.g. DBMS, FileConfiguration, ...).
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class FvDaoException extends Exception {

  /**
   * Constructor.
   *
   * @param message The exception's description message.
   */
  public FvDaoException(@NotNull String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message The exception's description message.
   * @param cause The cause of the exception.
   */
  public FvDaoException(@NotNull String message, @NotNull Throwable cause) {
    super(message, cause);
  }
}

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

package fr.fallenvaders.minecraft.justice_hands.model.database;

import fr.fallenvaders.minecraft.commons.sql.FvDao;

import java.sql.SQLException;

/**
 * The JusticeHands {@link SQLException} class. It corresponds to a special case of Dao operations
 * typically when the {@link FvDao#delete(Object)} method is called whereas the specified object
 * isn't registered.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class JhSqlException extends SQLException {

  /**
   * Constructor.
   *
   * @param message The exception message.
   */
  public JhSqlException(String message) {
    super(message);
  }
}

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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * This is a DAO interface which ask setting up at least read operations for a given entity type in
 * order to manage the model by abstracting storage processes.
 *
 * For more information, see {@link FvDao}.
 *
 * @author Voltariuss
 * @since 0.3.0
 * @param <T> The type of the entity involved in the DAO-implementation.
 * @see FvDao
 */
public interface FvReadOnlyDao<T> {

  /**
   * Reads a specific entity instance {@link T} in the model according to the specified ID argument.
   *
   * @param connection The connection to the DBMS.
   * @param id The ID of the entity instance in the model.
   * @return The entity instance corresponding to the specified ID if it exists.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @NotNull
  Optional<T> get(@NotNull Connection connection, @NotNull String id) throws SQLException;

  /**
   * Reads all the existing entity instances {@link T} in the model.
   *
   * @param connection The connection to the DBMS.
   * @return The list of existing entity instances in the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  @NotNull
  Set<T> getAll(@NotNull Connection connection) throws SQLException;
}

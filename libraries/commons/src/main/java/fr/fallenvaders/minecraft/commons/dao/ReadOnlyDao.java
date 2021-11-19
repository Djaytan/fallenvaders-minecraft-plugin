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

package fr.fallenvaders.minecraft.commons.dao;

import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * This is a DAO interface which ask setting up at least read operations for a given entity type in
 * order to manage the model by abstracting storage processes.
 *
 * <p>For more information, see {@link Dao}.
 *
 * @param <T> The type of the entity involved in the DAO-implementation.
 * @author Voltariuss
 * @see Dao
 * @since 0.3.0
 */
public interface ReadOnlyDao<T> {

  /**
   * Reads a specific entity instance {@link T} in the model according to the specified ID argument.
   *
   * @param id The ID of the entity instance in the model.
   * @return The entity instance corresponding to the specified ID if it exists.
   * @throws DaoException If the read of the entity fail.
   */
  @NotNull
  // TODO: FV-142 - create DaoId class instead with implementations like StringDaoId, IntDaoId, ...
  Optional<T> get(@NotNull String id) throws DaoException;

  /**
   * Reads all the existing entity instances {@link T} in the model.
   *
   * @return The list of existing entity instances in the model.
   * @throws DaoException If the read of the entities fail.
   */
  @NotNull
  Set<T> getAll() throws DaoException;
}

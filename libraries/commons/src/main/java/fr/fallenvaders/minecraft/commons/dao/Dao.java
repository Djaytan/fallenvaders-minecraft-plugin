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

import org.jetbrains.annotations.NotNull;

/**
 * This is a DAO interface which ask setting up at least CRUD operations for a given entity type in
 * order to manage the model by abstracting storage processes. This logic permits to make the model
 * domain of the application agnostic about the database or other storage methods chose.
 *
 * <p>An entity instance is an object which may represents a database's table line for example.
 *
 * <p>The DAO pattern, which means Data Access Object, allows stating the use of cache system in
 * order to increase performances by omitting requested the database when it's not relevant.
 *
 * @param <T> The type of the entity involved in the DAO-implementation.
 * @author Voltariuss
 * @since 0.3.0
 */
public interface Dao<T> extends ReadOnlyDao<T> {

  /**
   * Saves a new entity instance {@link T} in the model.
   *
   * @param t The entity to save in the model.
   * @throws DaoException If the save of the entity fail.
   */
  void save(@NotNull T t) throws DaoException;

  /**
   * Updates the entity instance {@link T} in the model.
   *
   * @param t The updated entity's instance to replicate in the model.
   * @throws DaoException If the update of the entity fail.
   */
  void update(@NotNull T t) throws DaoException;

  /**
   * Deletes the entity instance {@link T} from the model.
   *
   * @param t The entity to delete from the model.
   * @throws DaoException If the deletion of the entity fail.
   */
  void delete(@NotNull T t) throws DaoException;
}

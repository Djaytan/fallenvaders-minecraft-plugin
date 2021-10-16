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

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
 * @author Voltariuss
 * @since 0.3.0
 * @param <T> The type of the entity involved in the DAO-implementation.
 */
public interface FvDao<T> {

  /**
   * Reads a specific entity instance {@link T} in the model according to the specified ID argument.
   *
   * @param id The ID of the entity instance in the model.
   * @return The entity instance corresponding to the specified ID if it exists.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  Optional<T> get(String id) throws SQLException;

  /**
   * Reads all the existing entity instances {@link T} in the model.
   *
   * @return The list of existing entity instances in the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  List<T> getAll() throws SQLException;

  /**
   * Saves a new entity instance {@link T} in the model.
   *
   * @param t The entity instance to save in the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  void save(T t) throws SQLException;

  /**
   * Updates the entity instance {@link T} in the model.
   *
   * @param t The entity instance to update in the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  void update(T t) throws SQLException;

  /**
   * Deletes the entity instance {@link T} from the model.
   *
   * @param t The entity instance to delete from the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  void delete(T t) throws SQLException;
}

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

package fr.fallenvaders.minecraft.justice_hands.model.database.service;

import fr.fallenvaders.minecraft.justice_hands.model.database.JhSqlException;
import fr.fallenvaders.minecraft.justice_hands.model.database.dao.JhPlayerDao;
import fr.fallenvaders.minecraft.justice_hands.model.database.entities.JhPlayer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class which offers services about the manipulation of {@link JhPlayer}s in the model.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class JhPlayerService {

  private final Logger logger;
  private final JhPlayerDao jhPlayerDao;

  /**
   * Constructor.
   *
   * @param jhPlayerDao The DAO for the JusticeHands' player entity class.
   */
  @Inject
  public JhPlayerService(@NotNull JhPlayerDao jhPlayerDao, @NotNull Logger logger) {
    this.jhPlayerDao = jhPlayerDao;
    this.logger = logger;
  }

  /**
   * Gets and returns the {@link JhPlayer} associated with the specified {@link UUID}.
   *
   * @param uuid The UUID of the {@link JhPlayer} to seek.
   * @return The {@link JhPlayer} associated with the specified {@link UUID}.
   */
  @NotNull
  public Optional<JhPlayer> getJhPlayer(@NotNull UUID uuid) {
    logger.info("Seek of the JusticeHands' player associated with UUID '{}'.", uuid);
    JhPlayer jhPlayer = null;
    try {
      jhPlayer = jhPlayerDao.get(uuid.toString()).orElse(null);
      if (jhPlayer != null) {
        logger.info("JusticeHands' player found for the UUID '{}': {}", uuid, jhPlayer);
      } else {
        logger.info("No JusticeHands' player found for UUID '{}'", uuid);
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during JusticeHands' player seek.", e);
    }
    return Optional.ofNullable(jhPlayer);
  }

  /**
   * Gets and returns all existing {@link JhPlayer}s of the model.
   *
   * @return All the existing {@link JhPlayer}s of the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   */
  public List<JhPlayer> getJhPlayers() throws SQLException {
    return jhPlayerDao.getAll();
  }

  /**
   * Saves the specified {@link JhPlayer}.
   *
   * @param jhPlayer The FallenVaders' player to save into the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   * @throws JhSqlException if the player is already registered in the model.
   */
  public void saveJhPlayer(@NotNull JhPlayer jhPlayer) throws SQLException {
    jhPlayerDao.save(jhPlayer);
  }

  /**
   * Updates the specified {@link JhPlayer}.
   *
   * @param jhPlayer The FallenVaders' player to update in the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   * @throws JhSqlException if the player isn't registered in the model.
   */
  public void updateJhPlayer(@NotNull JhPlayer jhPlayer) throws SQLException {
    jhPlayerDao.update(jhPlayer);
  }

  /**
   * Deletes the specified {@link JhPlayer}.
   *
   * @param jhPlayer The FallenVaders' player to delete from the model.
   * @throws SQLException if something went wrong during database access or stuffs like this.
   * @throws JhSqlException if the player isn't already registered in the model.
   */
  public void deleteJhPlayer(@NotNull JhPlayer jhPlayer) throws SQLException {
    jhPlayerDao.delete(jhPlayer);
  }
}

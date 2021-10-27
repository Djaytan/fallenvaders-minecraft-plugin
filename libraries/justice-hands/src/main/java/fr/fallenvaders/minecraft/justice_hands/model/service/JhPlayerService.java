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

package fr.fallenvaders.minecraft.justice_hands.model.service;

import fr.fallenvaders.minecraft.commons.sql.FvDataSource;
import fr.fallenvaders.minecraft.justice_hands.model.dao.JhPlayerDao;
import fr.fallenvaders.minecraft.justice_hands.model.dao.JhSanctionDao;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhPlayer;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhSanction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
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
  private final JhSanctionDao jhSanctionDao;
  private final FvDataSource fvDataSource;

  /**
   * Constructor.
   *
   * @param logger The logger of the FallenVaders' plugin.
   * @param jhPlayerDao The {@link JhPlayer} DAO.
   * @param jhSanctionDao The {@link JhSanction} DAO.
   */
  @Inject
  public JhPlayerService(
      @NotNull Logger logger,
      @NotNull JhPlayerDao jhPlayerDao,
      @NotNull JhSanctionDao jhSanctionDao,
      @NotNull FvDataSource fvDataSource) {
    this.logger = logger;
    this.jhPlayerDao = jhPlayerDao;
    this.jhSanctionDao = jhSanctionDao;
    this.fvDataSource = fvDataSource;
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
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhPlayer = jhPlayerDao.get(connection, uuid.toString()).orElse(null);
        connection.commit();
        if (jhPlayer != null) {
          logger.info("JusticeHands' player found for the UUID '{}': {}", uuid, jhPlayer);
        } else {
          logger.warn("No JusticeHands' player found for UUID '{}'", uuid);
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of a JusticeHands' player.", e);
    }
    return Optional.ofNullable(jhPlayer);
  }

  /**
   * Gets and returns all existing {@link JhPlayer}s of the model.
   *
   * @return All the existing {@link JhPlayer}s of the model.
   */
  public List<JhPlayer> getJhPlayers() {
    logger.info("Seek of all JusticeHands' players.");
    List<JhPlayer> jhPlayers = Collections.emptyList();
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhPlayers = jhPlayerDao.getAll(connection);
        connection.commit();
        if (!jhPlayers.isEmpty()) {
          logger.info("JusticeHands' players found: {}", jhPlayers);
        } else {
          logger.warn("No JusticeHands' player found.");
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of all JusticeHands' players.", e);
    }
    return jhPlayers;
  }

  /**
   * Saves the specified {@link JhPlayer} into the model.
   *
   * @param jhPlayer The JusticeHands' player to save into the model.
   */
  public void saveJhPlayer(@NotNull JhPlayer jhPlayer) {
    logger.info("Try to save the following new JusticeHands' player: {}", jhPlayer);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhPlayerDao.save(connection, jhPlayer);
        connection.commit();
        logger.info("The JusticeHands' player registered successfully.");
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to save a JusticeHands' player.", e);
    }
  }

  /**
   * Updates the specified {@link JhPlayer} in the model.
   *
   * @param jhPlayer The JusticeHands' player to update in the model.
   */
  public void updateJhPlayer(@NotNull JhPlayer jhPlayer) {
    logger.info(
        "Try to update the JusticeHands' player with UUID '{}' with this following new value: {}",
        jhPlayer.getUuid(),
        jhPlayer);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhPlayerDao.update(connection, jhPlayer);
        connection.commit();
        logger.info("The JusticeHands' player updated successfully.");
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to update a JusticeHands' player.", e);
    }
  }

  /**
   * Deletes the specified {@link JhPlayer} from the model.
   *
   * <p>By deleting a {@link JhPlayer}, all the associated {@link JhSanction} are deleted too.
   *
   * <p>IMPORTANT: should be used only under exceptional circumstances.
   *
   * @param jhPlayer The JusticeHands' player to delete from the model.
   */
  public void deleteJhPlayer(@NotNull JhPlayer jhPlayer) {
    logger.info("Try to delete the following JusticeHands' player: {}", jhPlayer);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhPlayerDao.delete(connection, jhPlayer);
        connection.commit();
        logger.info("The JusticeHands' player deleted successfully.");
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to delete a JusticeHands' player.", e);
    }
  }
}

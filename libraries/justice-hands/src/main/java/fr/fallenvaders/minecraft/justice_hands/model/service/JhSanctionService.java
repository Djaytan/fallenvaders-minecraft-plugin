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
import fr.fallenvaders.minecraft.justice_hands.JusticeHandsException;
import fr.fallenvaders.minecraft.justice_hands.model.dao.JhSanctionDao;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhSanction;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Class which offers services about the manipulation of {@link JhSanction}s in the model.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class JhSanctionService {

  private final Logger logger;
  private final JhSanctionDao jhSanctionDao;
  private final FvDataSource fvDataSource;

  /**
   * Constructor.
   *
   * @param logger The logger of FallenVaders' plugin.
   * @param jhSanctionDao The {@link JhSanction} DAO.
   * @param fvDataSource The FallenVaders' data source for DBMS.
   */
  @Inject
  public JhSanctionService(
      @NotNull Logger logger,
      @NotNull JhSanctionDao jhSanctionDao,
      @NotNull FvDataSource fvDataSource) {
    this.logger = logger;
    this.jhSanctionDao = jhSanctionDao;
    this.fvDataSource = fvDataSource;
  }

  /**
   * Gets and returns the {@link JhSanction} associated with the specified ID.
   *
   * @param id The ID of the {@link JhSanction} to seek.
   * @return The {@link JhSanction} associated with the specified ID.
   */
  @NotNull
  public Optional<JhSanction> getJhSanction(int id) {
    logger.info("Seek of the JusticeHands' sanction associated with ID '{}'.", id);
    JhSanction jhSanction = null;
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhSanction = jhSanctionDao.get(connection, Integer.toString(id)).orElse(null);
        connection.commit();
        if (jhSanction != null) {
          logger.info("JusticeHands' sanction found for the ID '{}': {}", id, jhSanction);
        } else {
          logger.warn("No JusticeHands' sanction found for ID '{}'", id);
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of a JusticeHands' sanction.", e);
    }
    return Optional.ofNullable(jhSanction);
  }

  /**
   * Gets and returns all existing {@link JhSanction}s of the model.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last *
   * position.
   *
   * @return All the existing {@link JhSanction}s of the model.
   */
  public Set<JhSanction> getJhSanctions() {
    logger.info("Seek of all JusticeHands' sanctions.");
    Set<JhSanction> jhSanctions = Collections.emptySet();
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        jhSanctions = jhSanctionDao.getAll(connection);
        connection.commit();
        if (!jhSanctions.isEmpty()) {
          logger.info("JusticeHands' sanctions found: {}", jhSanctions);
        } else {
          logger.warn("No JusticeHands' sanction found.");
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of all JusticeHands' sanctions.", e);
    }
    return jhSanctions;
  }

  /**
   * Gets and returns all existing {@link JhSanction} of the specified {@link OfflinePlayer} where
   * he is designed as an inculpated one.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @return All the {@link JhSanction} of the specified {@link OfflinePlayer}.
   */
  public Set<JhSanction> getPlayerJhSanctions() {}

  /**
   * Saves the specified {@link JhSanction} into the model.
   *
   * @param jhSanction The JusticeHands' sanction to save into the model.
   */
  public void saveJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info("Try to save the following new JusticeHands' sanction: {}", jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        int rowCount = jhSanctionDao.save(connection, jhSanction);
        connection.commit();
        if (rowCount > 0) {
          logger.info("The JusticeHands' sanction have been registered successfully.");
          if (rowCount > 1) {
            logger.warn("More than one sanction have been registered...");
          }
        } else {
          throw new JusticeHandsException("Failed to register the new sanction into the model.");
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (Exception e) {
      logger.error("An error occurs when trying to save a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to register the new following sanction: %s", jhSanction));
    }
  }

  /**
   * Updates the specified {@link JhSanction} in the model.
   *
   * @param jhSanction The JusticeHands' sanction to update in the model.
   * @throws JusticeHandsException if the specified sanction can't be updated in the model.
   */
  public void updateJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info(
        "Try to update the JusticeHands' sanction with ID '{}' with this following new value: {}",
        jhSanction.getSctnId(),
        jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        int rowCount = jhSanctionDao.update(connection, jhSanction);
        connection.commit();
        if (rowCount > 0) {
          logger.info("The JusticeHands' sanction have been updated successfully.");
          if (rowCount > 1) {
            logger.warn("More than one sanction have been updated...");
          }
        } else {
          throw new JusticeHandsException(
              String.format(
                  "The JusticeHands' sanction with ID '%d' doesn't exist and then can't be updated.",
                  jhSanction.getSctnId()));
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (Exception e) {
      logger.error("An error occurs when trying to update a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to update the sanction with ID '%s'.", jhSanction.getSctnId()));
    }
  }

  /**
   * Deletes the specified {@link JhSanction} from the model.
   *
   * @param jhSanction The JusticeHands' sanction to delete from the model.
   * @throws JusticeHandsException if the specified sanction can't be updated in the model.
   */
  public void deleteJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info("Try to delete the following JusticeHands' sanction: {}", jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      try {
        int rowCount = jhSanctionDao.delete(connection, jhSanction);
        connection.commit();
        if (rowCount > 0) {
          logger.info("The JusticeHands' sanction have been deleted successfully.");
          if (rowCount > 1) {
            logger.warn("More than one sanction have been deleted...");
          }
        } else {
          throw new JusticeHandsException(
              String.format(
                  "The JusticeHands' sanction with ID '%d' doesn't exist and then can't be deleted.",
                  jhSanction.getSctnId()));
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (Exception e) {
      logger.error("An error occurs when trying to delete a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to delete the sanction with ID '%s'.", jhSanction.getSctnId()));
    }
  }
}

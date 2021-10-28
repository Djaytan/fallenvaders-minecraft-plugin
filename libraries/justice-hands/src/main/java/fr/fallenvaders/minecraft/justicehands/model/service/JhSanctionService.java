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

package fr.fallenvaders.minecraft.justicehands.model.service;

import fr.fallenvaders.minecraft.commons.sql.FvDataSource;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.dao.JhSanctionDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.JhSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
   * @throws JusticeHandsException if the sought sanction fail to be found in the model.
   */
  public @NotNull Optional<JhSanction> getJhSanction(int id) throws JusticeHandsException {
    logger.info("Seek of the JusticeHands' sanction associated with the ID '{}'.", id);
    try (Connection connection = fvDataSource.getConnection()) {
      Optional<JhSanction> jhSanction = jhSanctionDao.get(connection, Integer.toString(id));
      if (jhSanction.isPresent()) {
        logger.info("JusticeHands' sanction found for the ID '{}': {}", id, jhSanction);
      } else {
        logger.warn("No JusticeHands' sanction found for ID '{}'.", id);
      }
      return jhSanction;
    } catch (SQLException e) {
      logger.error("An error occurs preventing the seek of a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to seek the JusticeHands' sanction with ID '%d'.", id));
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction}s of the model.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @return All the existing {@link JhSanction}s of the model.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<JhSanction> getJhSanctions() throws JusticeHandsException {
    logger.info("Seek of all existing JusticeHands' sanctions.");
    try (Connection connection = fvDataSource.getConnection()) {
      Set<JhSanction> jhSanctions = jhSanctionDao.getAll(connection);
      if (!jhSanctions.isEmpty()) {
        logger.info("JusticeHands' sanctions found: {}", jhSanctions);
      } else {
        logger.warn("No JusticeHands' sanction found.");
      }
      return jhSanctions;
    } catch (SQLException e) {
      logger.error("An error occurs preventing the seek of all JusticeHands' sanctions.", e);
      throw new JusticeHandsException("Failed to seek all the existing JusticeHands' sanctions.");
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction} of the specified {@link OfflinePlayer} where
   * he is assigned as an inculpated one.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @return All the {@link JhSanction} of the specified {@link OfflinePlayer}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<JhSanction> getPlayerJhSanctions(@NotNull OfflinePlayer offlinePlayer)
      throws JusticeHandsException {
    logger.info(
        "Seek all JusticeHands' sanctions of the inculpated player '{}' with UUID '{}'.",
        offlinePlayer.getName(),
        offlinePlayer.getUniqueId());
    try (Connection connection = fvDataSource.getConnection()) {
      Set<JhSanction> jhSanctions = jhSanctionDao.getFromPlayer(connection, offlinePlayer);
      if (!jhSanctions.isEmpty()) {
        logger.info("JusticeHands' sanctions found: {}", jhSanctions);
      } else {
        logger.warn("No JusticeHands' sanction found.");
      }
      return jhSanctions;
    } catch (SQLException e) {
      logger.error(
          "An error occurs preventing the seek of all JusticeHands' sanctions for the specified player.",
          e);
      throw new JusticeHandsException(
          String.format(
              "Failed to seek the sanctions of the inculpated player '%s' with UUID '%s'.",
              offlinePlayer.getName(), offlinePlayer.getUniqueId()));
    }
  }

  /**
   * Gets and returns all existing {@link JhSanction} of the specified {@link OfflinePlayer} where
   * he is assigned as an inculpated one and where the {@link SanctionType} match with the given
   * one.
   *
   * @return All the {@link JhSanction} of the specified {@link OfflinePlayer} and {@link
   *     SanctionType}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<JhSanction> getPlayerJhSanctions(
      @NotNull OfflinePlayer offlinePlayer, @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    Set<JhSanction> jhSanctions = getPlayerJhSanctions(offlinePlayer);
    return jhSanctions.stream()
        .filter(jhSanction -> jhSanction.getSctnType() == sanctionType)
        .collect(Collectors.toSet());
  }

  /**
   * Gets and returns all active existing {@link JhSanction} of the specified {@link OfflinePlayer}
   * where he is assigned as an inculpated one.
   *
   * @return All the {@link JhSanction} of the specified {@link OfflinePlayer}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<JhSanction> getActivePlayerJhSanctions(@NotNull OfflinePlayer offlinePlayer)
      throws JusticeHandsException {
    Set<JhSanction> jhSanctions = getPlayerJhSanctions(offlinePlayer);
    return jhSanctions.stream()
        .filter(jhSanction -> jhSanction.getSctnEndingDate().toInstant().isAfter(Instant.now()))
        .collect(Collectors.toSet());
  }

  /**
   * Saves the specified {@link JhSanction} into the model.
   *
   * @param jhSanction The JusticeHands' sanction to save into the model.
   * @throws JusticeHandsException if the specified sanction fail to be registered in the model.
   */
  public void saveJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info("Try to save the following new JusticeHands' sanction: {}", jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      int rowCount = jhSanctionDao.save(connection, jhSanction);
      if (rowCount > 0) {
        logger.info("The JusticeHands' sanction have been registered successfully.");
        if (rowCount > 1) {
          logger.warn("More than one sanction have been registered...");
        }
      } else {
        throw new JusticeHandsException("Failed to register the new sanction into the model.");
      }
    } catch (SQLException | JusticeHandsException e) {
      logger.error("An error occurs preventing the save of a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to register the new following sanction: %s", jhSanction));
    }
  }

  /**
   * Updates the specified {@link JhSanction} in the model.
   *
   * @param jhSanction The JusticeHands' sanction to update in the model.
   * @throws JusticeHandsException if the specified sanction fail to be updated in the model.
   */
  public void updateJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info(
        "Try to update the JusticeHands' sanction with ID '{}' with this following new value: {}",
        jhSanction.getSctnId(),
        jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      int rowCount = jhSanctionDao.update(connection, jhSanction);
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
    } catch (SQLException | JusticeHandsException e) {
      logger.error("An error occurs preventing the update of a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to update the sanction with ID '%s'.", jhSanction.getSctnId()));
    }
  }

  /**
   * Deletes the specified {@link JhSanction} from the model.
   *
   * @param jhSanction The JusticeHands' sanction to delete from the model.
   * @throws JusticeHandsException if the specified sanction fail to be deleted in the model.
   */
  public void deleteJhSanction(@NotNull JhSanction jhSanction) throws JusticeHandsException {
    logger.info("Try to delete the following JusticeHands' sanction: {}", jhSanction);
    try (Connection connection = fvDataSource.getConnection()) {
      int rowCount = jhSanctionDao.delete(connection, jhSanction);
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
    } catch (SQLException | JusticeHandsException e) {
      logger.error("An error occurs preventing the delete of a JusticeHands' sanction.", e);
      throw new JusticeHandsException(
          String.format("Failed to delete the sanction with ID '%s'.", jhSanction.getSctnId()));
    }
  }
}

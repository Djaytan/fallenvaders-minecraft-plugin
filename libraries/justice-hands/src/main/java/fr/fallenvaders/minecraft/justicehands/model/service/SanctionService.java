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

import fr.fallenvaders.minecraft.commons.dao.DaoException;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.dao.SanctionDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Class which offers services about the manipulation of {@link Sanction}s in the model.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionService {

  private final Logger logger;
  private final SanctionDao sanctionDao;

  /**
   * Constructor.
   *
   * @param logger The logger of FallenVaders' plugin.
   * @param sanctionDao The {@link Sanction} DAO.
   */
  @Inject
  public SanctionService(@NotNull Logger logger, @NotNull SanctionDao sanctionDao) {
    this.logger = logger;
    this.sanctionDao = sanctionDao;
  }

  /**
   * Gets and returns the {@link Sanction} associated with the specified ID.
   *
   * @param id The ID of the {@link Sanction} to seek.
   * @return The {@link Sanction} associated with the specified ID.
   * @throws JusticeHandsException if the sought sanction fail to be found in the model.
   */
  public @NotNull Optional<Sanction> getSanction(int id) throws JusticeHandsException {
    logger.info("Seek of the JusticeHands' sanction associated with the ID '{}'.", id);
    try {
      Optional<Sanction> jhSanction = sanctionDao.get(Integer.toString(id));
      if (jhSanction.isPresent()) {
        logger.info("JusticeHands' sanction found for the ID '{}': {}", id, jhSanction);
      } else {
        logger.warn("No JusticeHands' sanction found for ID '{}'.", id);
      }
      return jhSanction;
    } catch (DaoException e) {
      // TODO: remove all unnecessary JusticeHands'
      logger.error("An error occurs preventing the seek of the JusticeHands' sanction.");
      throw new JusticeHandsException(
          String.format("Failed to seek the JusticeHands' sanction with ID '%d'.", id), e);
    }
  }

  /**
   * Gets and returns all existing {@link Sanction}s of the model.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @return All the existing {@link Sanction}s of the model.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getSanctions() throws JusticeHandsException {
    logger.info("Seek of all existing JusticeHands' sanctions.");
    try {
      Set<Sanction> sanctions = sanctionDao.getAll();
      if (!sanctions.isEmpty()) {
        logger.info("JusticeHands' sanctions found: {}", sanctions);
      } else {
        logger.warn("No JusticeHands' sanction found.");
      }
      return sanctions;
    } catch (DaoException e) {
      logger.error("An error occurs preventing the seek of all JusticeHands' sanctions.");
      throw new JusticeHandsException(
          "Failed to seek all the existing JusticeHands' sanctions.", e);
    }
  }

  /**
   * Gets and returns all existing {@link Sanction} of the specified {@link OfflinePlayer} where he
   * is assigned as an inculpated one.
   *
   * <p>The set is ordered from the older sanction in first position to the youngest in the last
   * position.
   *
   * @param player The player.
   * @return All the {@link Sanction} of the specified {@link OfflinePlayer}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    logger.info(
        "Seek all JusticeHands' sanctions of the inculpated player '{}' with UUID '{}'.",
        player.getName(),
        player.getUniqueId());
    try {
      Set<Sanction> sanctions = sanctionDao.getFromPlayer(player);
      if (!sanctions.isEmpty()) {
        logger.info("JusticeHands' sanctions found: {}", sanctions);
      } else {
        logger.warn("No JusticeHands' sanction found.");
      }
      return sanctions;
    } catch (DaoException e) {
      logger.error(
          "An error occurs preventing the seek of all JusticeHands' sanctions for the specified player.");
      throw new JusticeHandsException(
          String.format(
              "Failed to seek the sanctions of the inculpated player '%s' with UUID '%s'.",
              player.getName(), player.getUniqueId()),
          e);
    }
  }

  /**
   * Registers the specified {@link Sanction} into the model.
   *
   * @param sanction The JusticeHands' sanction to save into the model.
   * @throws JusticeHandsException if the specified sanction fail to be registered in the model.
   */
  public void registerSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info("Try to register the following new JusticeHands' sanction: {}", sanction);
    try {
      int rowCount = sanctionDao.save(sanction);
      if (rowCount > 0) {
        logger.info("The JusticeHands' sanction have been registered successfully.");
        if (rowCount > 1) {
          logger.warn("More than one sanction have been registered...");
        }
      } else {
        // TODO: remove this DAO logic from service layer
        throw new JusticeHandsException("Failed to register the new sanction into the model.");
      }
    } catch (DaoException | JusticeHandsException e) {
      logger.error("An error occurs preventing the register of a JusticeHands' sanction.");
      throw new JusticeHandsException(
          String.format("Failed to register the new following sanction: %s", sanction), e);
    }
  }

  /**
   * Updates the specified {@link Sanction} in the model.
   *
   * @param sanction The JusticeHands' sanction to update in the model.
   * @throws JusticeHandsException if the specified sanction fail to be updated in the model.
   */
  public void updateSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info(
        "Try to update the JusticeHands' sanction with ID '{}' with this following new value: {}",
        sanction.getId(),
        sanction);
    try {
      int rowCount = sanctionDao.update(sanction);
      if (rowCount > 0) {
        logger.info("The JusticeHands' sanction have been updated successfully.");
        if (rowCount > 1) {
          logger.warn("More than one sanction have been updated...");
        }
      } else {
        throw new JusticeHandsException(
            String.format(
                "The JusticeHands' sanction with ID '%d' doesn't exist and then can't be updated.",
                sanction.getId()));
      }
    } catch (DaoException | JusticeHandsException e) {
      logger.error("An error occurs preventing the update of a JusticeHands' sanction.");
      throw new JusticeHandsException(
          String.format("Failed to update the sanction with ID '%s'.", sanction.getId()), e);
    }
  }

  /**
   * Deletes the specified {@link Sanction} from the model.
   *
   * @param sanction The JusticeHands' sanction to delete from the model.
   * @throws JusticeHandsException if the specified sanction fail to be deleted in the model.
   */
  public void deleteSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info("Try to delete the following JusticeHands' sanction: {}", sanction);
    try {
      int rowCount = sanctionDao.delete(sanction);
      if (rowCount > 0) {
        logger.info("The JusticeHands' sanction have been deleted successfully.");
        if (rowCount > 1) {
          logger.warn("More than one sanction have been deleted...");
        }
      } else {
        throw new JusticeHandsException(
            String.format(
                "The JusticeHands' sanction with ID '%d' doesn't exist and then can't be deleted.",
                sanction.getId()));
      }
    } catch (DaoException | JusticeHandsException e) {
      logger.error("An error occurs preventing the delete of a JusticeHands' sanction.");
      throw new JusticeHandsException(
          String.format("Failed to delete the sanction with ID '%s'.", sanction.getId()), e);
    }
  }
}

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

import com.google.common.base.Preconditions;
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
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class SanctionService {

  private final Logger logger;
  private final SanctionDao sanctionDao;

  /**
   * Constructor.
   *
   * @param logger The {@link Logger}.
   * @param sanctionDao The {@link SanctionDao}.
   */
  @Inject
  public SanctionService(@NotNull Logger logger, @NotNull SanctionDao sanctionDao) {
    this.logger = logger;
    this.sanctionDao = sanctionDao;
  }

  /**
   * Gets and returns the sanction associated with the specified ID.
   *
   * @param id The ID of the sanction to seek.
   * @return The sanction associated with the specified ID.
   * @throws JusticeHandsException If the sought sanction fail to be found in the model.
   */
  public @NotNull Optional<Sanction> getSanction(int id) throws JusticeHandsException {
    Preconditions.checkArgument(
        id >= 1, "The ID of the sought sanction must be strictly higher than 0.");

    logger.info("Seek of the sanction associated with the ID '{}'.", id);
    try {
      Optional<Sanction> sanction = sanctionDao.get(Integer.toString(id));
      if (sanction.isPresent()) {
        logger.info("Sanction found for the ID '{}': {}", id, sanction);
      } else {
        logger.info("No sanction found for ID '{}'.", id);
      }
      return sanction;
    } catch (DaoException e) {
      logger.error("An error occurs preventing the seek of the sanction.");
      throw new JusticeHandsException(
          String.format("Failed to seek the sanction with ID '%d'.", id), e);
    }
  }

  /**
   * Gets and returns all existing sanctions of the model.
   *
   * @return All the existing sanctions of the model.
   * @throws JusticeHandsException If the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getSanctions() throws JusticeHandsException {
    logger.info("Seek of all existing sanctions.");
    try {
      Set<Sanction> sanctions = sanctionDao.getAll();
      if (!sanctions.isEmpty()) {
        logger.info("Sanctions found: {}", sanctions);
      } else {
        /*
         * Because in production we expect this list to be never empty, this log may uncover an
         * error.
         */
        logger.warn("No sanction found (maybe an error?).");
      }
      return sanctions;
    } catch (DaoException e) {
      logger.error("An error occurs preventing the seek of all sanctions.");
      throw new JusticeHandsException("Failed to seek all the existing sanctions.", e);
    }
  }

  /**
   * Gets and returns all existing sanctions of the specified player where he is assigned as an
   * inculpated one.
   *
   * @param player The player.
   * @return All the sanctions of the specified player.
   * @throws JusticeHandsException If the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    logger.info(
        "Seek all sanctions of the inculpated player '{}' with UUID '{}'.",
        player.getName(),
        player.getUniqueId());
    try {
      Set<Sanction> sanctions = sanctionDao.getFromPlayer(player);
      if (!sanctions.isEmpty()) {
        logger.info("Sanctions found: {}", sanctions);
      } else {
        logger.info("No sanction found.");
      }
      return sanctions;
    } catch (DaoException e) {
      logger.error(
          "An error occurs preventing the seek of all sanctions for the specified player.");
      throw new JusticeHandsException(
          String.format(
              "Failed to seek the sanctions of the inculpated player '%s' with UUID '%s'.",
              player.getName(), player.getUniqueId()),
          e);
    }
  }

  /**
   * Registers the specified sanction into the model.
   *
   * @param sanction The sanction to save into the model.
   * @throws JusticeHandsException If the specified sanction fail to be registered in the model.
   */
  public void registerSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info("Try to register the following new sanction: {}", sanction);
    try {
      sanctionDao.save(sanction);
      logger.info("The sanction have been registered successfully.");
    } catch (DaoException e) {
      logger.error("An error occurs preventing the register of a sanction.");
      throw new JusticeHandsException(
          String.format("Failed to register the new following sanction: %s", sanction), e);
    }
  }

  /**
   * Updates the specified sanction in the model.
   *
   * @param sanction The sanction to update in the model.
   * @throws JusticeHandsException If the specified sanction fail to be updated in the model.
   */
  public void updateSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info(
        "Try to update the sanction with ID '{}' with this following new value: {}",
        sanction.id(),
        sanction);
    try {
      sanctionDao.update(sanction);
      logger.info("The sanction have been updated successfully.");
    } catch (DaoException e) {
      logger.error("An error occurs preventing the update of a sanction.");
      throw new JusticeHandsException(
          String.format("Failed to update the sanction with ID '%s'.", sanction.id()), e);
    }
  }

  /**
   * Deletes the specified sanction from the model.
   *
   * @param sanction The sanction to delete from the model.
   * @throws JusticeHandsException If the specified sanction fail to be deleted in the model.
   */
  public void deleteSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info("Try to delete the following sanction: {}", sanction);
    try {
      sanctionDao.delete(sanction);
      logger.info("The sanction have been deleted successfully.");
    } catch (DaoException e) {
      logger.error("An error occurs preventing the delete of a sanction.");
      throw new JusticeHandsException(
          String.format("Failed to delete the sanction with ID '%s'.", sanction.id()), e);
    }
  }
}

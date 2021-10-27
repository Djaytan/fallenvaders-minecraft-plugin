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
import fr.fallenvaders.minecraft.justice_hands.model.dao.JhSanctionDao;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhPlayer;
import fr.fallenvaders.minecraft.justice_hands.model.entities.JhSanction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    try {
      jhSanction = jhSanctionDao.get(Integer.toString(id)).orElse(null);
      if (jhSanction != null) {
        logger.info("JusticeHands' sanction found for the ID '{}': {}", id, jhSanction);
      } else {
        logger.warn("No JusticeHands' sanction found for ID '{}'", id);
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of a JusticeHands' sanction.", e);
    }
    return Optional.ofNullable(jhSanction);
  }

  /**
   * Gets and returns all existing {@link JhSanction}s of the model.
   *
   * @return All the existing {@link JhSanction}s of the model.
   */
  public List<JhSanction> getJhSanctions() {
    logger.info("Seek of all JusticeHands' sanctions.");
    List<JhSanction> jhSanctions = Collections.emptyList();
    try {
      jhSanctions = jhSanctionDao.getAll();
      if (!jhSanctions.isEmpty()) {
        logger.info("JusticeHands' sanctions found: {}", jhSanctions);
      } else {
        logger.warn("No JusticeHands' sanction found.");
      }
    } catch (SQLException e) {
      logger.error("An SQL error occurs during the seek of all JusticeHands' sanctions.", e);
    }
    return jhSanctions;
  }

  /**
   * Saves the specified {@link JhSanction} into the model.
   *
   * @param jhSanction The JusticeHands' sanction to save into the model.
   */
  public void saveJhSanction(@NotNull JhSanction jhSanction) {
    logger.info("Try to save the following new JusticeHands' sanction: {}", jhSanction);
    try {
      jhSanctionDao.save(jhSanction);
      logger.info("The JusticeHands' sanction registered successfully.");
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to save a JusticeHands' sanction.", e);
    }
  }

  /**
   * Updates the specified {@link JhSanction} in the model.
   *
   * @param jhSanction The JusticeHands' sanction to update in the model.
   */
  public void updateJhSanction(@NotNull JhSanction jhSanction) {
    logger.info(
        "Try to update the JusticeHands' sanction with ID '{}' with this following new value: {}",
        jhSanction.getSctnId(),
        jhSanction);
    try {
      jhSanctionDao.update(jhSanction);
      logger.info("The JusticeHands' sanction updated successfully.");
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to update a JusticeHands' sanction.", e);
    }
  }

  /**
   * Deletes the specified {@link JhSanction} from the model.
   *
   * @param jhSanction The JusticeHands' sanction to delete from the model.
   */
  public void deleteJhSanction(@NotNull JhSanction jhSanction) {
    logger.info("Try to delete the following JusticeHands' sanction: {}", jhSanction);
    try {
      jhSanctionDao.delete(jhSanction);
      logger.info("The JusticeHands' sanction deleted successfully.");
    } catch (SQLException e) {
      logger.error("An SQL error occurs when trying to delete a JusticeHands' sanction.", e);
    }
  }
}

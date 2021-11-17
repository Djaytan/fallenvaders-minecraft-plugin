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
import fr.fallenvaders.minecraft.justicehands.model.dao.PredefinedSanctionDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Services about {@link PredefinedSanction} manipulations.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class PredefinedSanctionService {

  private final Logger logger;
  private final PredefinedSanctionDao predefinedSanctionDao;

  /**
   * Constructor.
   *
   * @param logger The logger.
   * @param predefinedSanctionDao The {@link PredefinedSanction} DAO.
   */
  @Inject
  public PredefinedSanctionService(
      @NotNull Logger logger, @NotNull PredefinedSanctionDao predefinedSanctionDao) {
    this.logger = logger;
    this.predefinedSanctionDao = predefinedSanctionDao;
  }

  /**
   * Recovers and returns the corresponding {@link PredefinedSanction} from the model which match
   * with the specified ID.
   *
   * @param id The ID of the targeted {@link PredefinedSanction}.
   * @return The {@link PredefinedSanction} from the model which match with the specified ID.
   * @throws JusticeHandsException if a problem occurs during the search in the model.
   */
  public @NotNull Optional<PredefinedSanction> getPredefinedSanction(@NotNull String id)
      throws JusticeHandsException {
    Objects.requireNonNull(id);

    try {
      Optional<PredefinedSanction> predefinedSanction = predefinedSanctionDao.get(id);
      logger.info("Predefined sanction with ID '{}' found.", id);
      logger.debug("Predefined sanction found: {}", predefinedSanction.orElse(null));
      return predefinedSanction;
    } catch (DaoException e) {
      throw new JusticeHandsException(
          String.format(
              "An error prevent the recovering of the predefined sanction with ID '%s'", id),
          e);
    }
  }

  /**
   * Recovers and returns all the existing {@link PredefinedSanction}s from the model.
   *
   * @return All the existing {@link PredefinedSanction}s from the model.
   * @throws JusticeHandsException if a problem occurs during the search in the model.
   */
  public @NotNull Set<PredefinedSanction> getPredefinedSanctions() throws JusticeHandsException {
    try {
      Set<PredefinedSanction> predefinedSanctions = predefinedSanctionDao.getAll();
      logger.info("All the predefined sanction have been recovered.");
      logger.debug("Sanction categories found: {}", predefinedSanctions);
      return predefinedSanctions;
    } catch (DaoException e) {
      throw new JusticeHandsException("An error prevent the recovering of predefined sanction.", e);
    }
  }
}

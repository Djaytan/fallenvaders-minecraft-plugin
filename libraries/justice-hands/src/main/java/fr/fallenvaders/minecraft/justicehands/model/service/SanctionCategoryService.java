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
import fr.fallenvaders.minecraft.justicehands.model.dao.SanctionCategoryDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Service class about {@link SanctionCategory}s manipulations.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class SanctionCategoryService {

  private final Logger logger;
  private final SanctionCategoryDao sanctionCategoryDao;

  /**
   * Constructor.
   *
   * @param logger The {@link Logger}.
   * @param sanctionCategoryDao The {@link SanctionCategoryDao}.
   */
  @Inject
  public SanctionCategoryService(
      @NotNull Logger logger, @NotNull SanctionCategoryDao sanctionCategoryDao) {
    this.logger = logger;
    this.sanctionCategoryDao = sanctionCategoryDao;
  }

  /**
   * Recovers and returns the corresponding category of sanctions from the model which match with
   * the specified ID.
   *
   * @param id The ID of the targeted category of sanctions.
   * @return The category of sanctions from the model which match with the specified ID.
   * @throws JusticeHandsException If a problem occurs during the search in the model.
   */
  public @NotNull Optional<SanctionCategory> getCategory(@NotNull String id)
      throws JusticeHandsException {
    try {
      Optional<SanctionCategory> sanctionCategory = sanctionCategoryDao.get(id);
      logger.info("Sanction category with ID '{}' found.", id);
      logger.debug("Sanction category found: {}", sanctionCategory.orElse(null));
      return sanctionCategory;
    } catch (DaoException e) {
      throw new JusticeHandsException(
          String.format(
              "An error prevent the recovering of the sanction category with ID '%s'", id),
          e);
    }
  }

  /**
   * Recovers and returns all the existing categories of sanctions from the model.
   *
   * @return All the existing categories of sanctions from the model.
   * @throws JusticeHandsException If a problem occurs during the search in the model.
   */
  public @NotNull Set<SanctionCategory> getCategories() throws JusticeHandsException {
    try {
      Set<SanctionCategory> sanctionCategories = sanctionCategoryDao.getAll();
      logger.info("All the sanction categories have been recovered.");
      logger.debug("Sanction categories found: {}", sanctionCategories);
      return sanctionCategories;
    } catch (DaoException e) {
      throw new JusticeHandsException("An error prevent the recovering of sanction categories.", e);
    }
  }
}

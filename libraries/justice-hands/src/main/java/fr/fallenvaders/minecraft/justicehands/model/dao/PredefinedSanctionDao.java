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

package fr.fallenvaders.minecraft.justicehands.model.dao;

import fr.fallenvaders.minecraft.commons.dao.DaoException;
import fr.fallenvaders.minecraft.commons.dao.ReadOnlyDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * DAO class about manipulation of {@link PredefinedSanction}s in the model.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 * @see ReadOnlyDao
 */
@Singleton
public class PredefinedSanctionDao implements ReadOnlyDao<PredefinedSanction> {

  private final SanctionCategoryDao sanctionCategoryDao;

  /**
   * Constructor.
   *
   * @param sanctionCategoryDao The {@link SanctionCategory} DAO.
   */
  @Inject
  public PredefinedSanctionDao(@NotNull SanctionCategoryDao sanctionCategoryDao) {
    this.sanctionCategoryDao = sanctionCategoryDao;
  }

  @Override
  public @NotNull Optional<PredefinedSanction> get(@NotNull String id) throws DaoException {
    return getPredefinedSanctions().stream()
        .filter(predefinedSanction -> predefinedSanction.id().equals(id))
        .findFirst();
  }

  @Override
  public @NotNull Set<PredefinedSanction> getAll() throws DaoException {
    return getPredefinedSanctions();
  }

  private @NotNull Set<PredefinedSanction> getPredefinedSanctions() throws DaoException {
    Set<PredefinedSanction> predefinedSanctions = new LinkedHashSet<>();
    sanctionCategoryDao
        .getAll()
        .forEach(
            sanctionCategory -> predefinedSanctions.addAll(sanctionCategory.predefinedSanctions()));
    return predefinedSanctions;
  }
}

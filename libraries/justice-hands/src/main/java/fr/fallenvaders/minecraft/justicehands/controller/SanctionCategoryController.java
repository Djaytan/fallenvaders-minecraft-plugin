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

package fr.fallenvaders.minecraft.justicehands.controller;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.fallenvaders.minecraft.justicehands.model.service.SanctionCategoryService;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link SanctionCategory} controller.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class SanctionCategoryController {

  private final SanctionCategoryService sanctionCategoryService;

  /**
   * Constructor.
   *
   * @param sanctionCategoryService The {@link SanctionCategoryService}.
   */
  @Inject
  public SanctionCategoryController(@NotNull SanctionCategoryService sanctionCategoryService) {
    this.sanctionCategoryService = sanctionCategoryService;
  }

  /**
   * Returns all existing {@link SanctionCategory}s.
   *
   * @return All existing {@link SanctionCategory}s.
   * @throws JusticeHandsException if something went wrong during the recovering.
   */
  public @NotNull Set<SanctionCategory> getCategories() throws JusticeHandsException {
    return sanctionCategoryService.getCategories();
  }
}

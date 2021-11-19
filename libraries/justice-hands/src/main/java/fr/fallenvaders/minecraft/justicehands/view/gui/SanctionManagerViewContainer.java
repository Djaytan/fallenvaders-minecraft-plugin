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

package fr.fallenvaders.minecraft.justicehands.view.gui;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionCategoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * The container of {@link SanctionManagerView}.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class SanctionManagerViewContainer {

  private final SanctionCategoryController sanctionCategoryController;

  private SanctionCategory currentSanctionCategory;

  /**
   * Constructor.
   *
   * @param sanctionCategoryController The {@link SanctionCategoryController}.
   * @throws JusticeHandsException If the categories of sanctions can't be recovered.
   */
  @Inject
  public SanctionManagerViewContainer(
      @NotNull SanctionCategoryController sanctionCategoryController) throws JusticeHandsException {
    this.sanctionCategoryController = sanctionCategoryController;
    currentSanctionCategory =
        sanctionCategoryController.getCategories().stream()
            .findFirst()
            .orElseThrow(
                () ->
                    new JusticeHandsException(
                        "At least one sanction category must be defined in the model."));
  }

  /**
   * Getter.
   *
   * @return The current category of sanctions consulted in the view.
   */
  public @NotNull SanctionCategory getCurrentSanctionCategory() {
    return currentSanctionCategory;
  }

  /**
   * Setter.
   *
   * @param currentSanctionCategory The current category of sanctions consulted in the view.
   */
  public void setCurrentSanctionCategory(@NotNull SanctionCategory currentSanctionCategory)
      throws JusticeHandsException {
    Optional<SanctionCategory> found =
        sanctionCategoryController.getCategories().stream()
            .filter(sc -> sc.equals(currentSanctionCategory))
            .findFirst();

    if (found.isEmpty()) {
      throw new JusticeHandsException(
          "The specified sanction category is not part of the ones registered in the model.");
    }

    this.currentSanctionCategory = currentSanctionCategory;
  }
}

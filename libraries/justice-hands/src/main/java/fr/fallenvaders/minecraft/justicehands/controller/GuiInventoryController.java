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
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiItem;
import fr.fallenvaders.minecraft.justicehands.model.service.GuiService;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * {@link GuiInventory} controller class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class GuiInventoryController {

  private final GuiService guiService;

  /**
   * Constructor.
   *
   * @param guiService The {@link GuiService}.
   */
  @Inject
  public GuiInventoryController(@NotNull GuiService guiService) {
    this.guiService = guiService;
  }

  /**
   * Recovers the corresponding {@link GuiInventory} which match with the given ID. Otherwise, raise
   * an exception.
   *
   * @param id The ID of the targeted GUI inventory.
   * @return The sought GUI inventory.
   * @throws JusticeHandsException If an error occurs or the specified ID is wrong.
   */
  public @NotNull GuiInventory getGuiInventory(@NotNull String id) throws JusticeHandsException {
    return guiService
        .getGuiInventory(id)
        .orElseThrow(() -> new JusticeHandsException("Wrong ID specified."));
  }

  /**
   * Recovers the corresponding generic {@link GuiItem} which match with the given ID.
   * Otherwise, raise an exception.
   *
   * <p>A generic GUI item is not linked to a specific inventory (refer you to config file).
   *
   * @param id The ID of the targeted generic GUI item.
   * @return The sought generic
   * @throws JusticeHandsException If an error occurs or the specified ID is wrong.
   */
  public @NotNull GuiItem getGenericGuiItem(@NotNull String id)
      throws JusticeHandsException {
    return guiService
        .getGenericGuiItem(id)
        .orElseThrow(() -> new JusticeHandsException("Wrong ID specified."));
  }
}

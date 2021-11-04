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
import fr.fallenvaders.minecraft.justicehands.model.service.GuiInventoryService;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * {@link GuiInventory} controller class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class GuiInventoryController {

  private final GuiInventoryService guiInventoryService;

  /**
   * Constructor.
   *
   * @param guiInventoryService The {@link GuiInventoryService}.
   */
  @Inject
  public GuiInventoryController(@NotNull GuiInventoryService guiInventoryService) {
    this.guiInventoryService = guiInventoryService;
  }

  /**
   * Recovers the corresponding {@link GuiInventory} which match with the given ID. Otherwise, raise
   * an exception.
   *
   * @param id The ID of the targeted {@link GuiInventory}.
   * @return The expected {@link GuiInventory}.
   * @throws JusticeHandsException if an error occurs or the specified ID is wrong.
   */
  public GuiInventory getInventory(@NotNull String id) throws JusticeHandsException {
    return guiInventoryService
        .getGuiInventory(id)
        .orElseThrow(() -> new JusticeHandsException("Wrong ID specified."));
  }
}
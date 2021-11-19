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
import fr.fallenvaders.minecraft.justicehands.model.dao.GenericGuiItemDao;
import fr.fallenvaders.minecraft.justicehands.model.dao.GuiInventoryDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiItem;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

/**
 * GUI service class.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class GuiService {

  private final GenericGuiItemDao genericGuiItemDao;
  private final GuiInventoryDao guiInventoryDao;
  private final Logger logger;

  /**
   * Constructor.
   *
   * @param genericGuiItemDao The {@link GuiService}.
   * @param guiInventoryDao The {@link GuiInventoryDao}.
   * @param logger The {@link Logger}.
   */
  @Inject
  public GuiService(
      @NotNull GenericGuiItemDao genericGuiItemDao,
      @NotNull GuiInventoryDao guiInventoryDao,
      @NotNull Logger logger) {
    this.guiInventoryDao = guiInventoryDao;
    this.genericGuiItemDao = genericGuiItemDao;
    this.logger = logger;
  }

  /**
   * Recovers and returns the corresponding GUI inventory from the model which match with the
   * specified ID.
   *
   * @param id The ID of the targeted GUI inventory.
   * @return The GUI inventory from the model which match with the specified ID.
   * @throws JusticeHandsException If a problem occurs during the search in the model.
   */
  public @NotNull Optional<GuiInventory> getGuiInventory(@NotNull String id) throws JusticeHandsException {
    try {
      Optional<GuiInventory> guiInventory = guiInventoryDao.get(id);
      logger.info("GUI inventory with ID '{}' found.", id);
      logger.debug("GUI inventory found: {}", guiInventory);
      return guiInventory;
    } catch (DaoException e) {
      throw new JusticeHandsException(
          String.format("An error prevent the recovering of the GUI inventory with ID '%s'.", id),
          e);
    }
  }

  /**
   * Recovers and returns the corresponding generic GUI item from the model which match with the
   * specified ID.
   *
   * @param id The ID of the targeted generic GUI item.
   * @return The generic GUI item from the model which match with the specified ID.
   * @throws JusticeHandsException If a problem occurs during the search in the model.
   */
  public @NotNull Optional<GuiItem> getGenericGuiItem(@NotNull String id)
      throws JusticeHandsException {
    try {
      Optional<GuiItem> genericGuiItem = genericGuiItemDao.get(id);
      logger.info("GUI inventory item with ID '{}' found.", id);
      logger.debug("GUI inventory item found: {}", genericGuiItem);
      return genericGuiItem;
    } catch (DaoException e) {
      throw new JusticeHandsException(
          String.format(
              "An error prevent the recovering of the GUI inventory item with ID '%s'.", id),
          e);
    }
  }
}

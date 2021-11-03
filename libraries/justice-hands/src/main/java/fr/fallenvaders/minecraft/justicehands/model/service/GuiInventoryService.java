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
import fr.fallenvaders.minecraft.justicehands.model.dao.GuiInventoryDao;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * {@link GuiInventory} service class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class GuiInventoryService {

  private final GuiInventoryDao guiInventoryDao;
  private final Logger logger;

  /**
   * Constructor.
   *
   * @param guiInventoryDao The {@link GuiInventory} DAO.
   * @param logger The logger.
   */
  @Inject
  public GuiInventoryService(@NotNull GuiInventoryDao guiInventoryDao, @NotNull Logger logger) {
    this.guiInventoryDao = guiInventoryDao;
    this.logger = logger;
  }

  /**
   * Recovers and returns the corresponding {@link GuiInventory} from the model which match with the
   * specified ID.
   *
   * @param id The ID of the targeted {@link GuiInventory}.
   * @return The {@link GuiInventory} from the model which match with the specified ID.
   * @throws JusticeHandsException if a problem occurs during the search in the model.
   */
  public Optional<GuiInventory> getGuiInventory(@NotNull String id) throws JusticeHandsException {
    Objects.requireNonNull(id);

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
}

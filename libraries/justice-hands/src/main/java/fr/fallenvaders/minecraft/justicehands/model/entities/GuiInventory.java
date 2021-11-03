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

package fr.fallenvaders.minecraft.justicehands.model.entities;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * GUI inventory entity.
 *
 * @param id The ID of the GUI inventory.
 * @param nbLines The number of lines of the GUI inventory.
 * @param items The {@link GuiInventoryItem}s embedded in the GUI inventory.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public record GuiInventory (@NotNull String id, int nbLines, @NotNull List<GuiInventoryItem> items) {

  /**
   * Returns the {@link GuiInventoryItem} which match with the specified ID.
   *
   * @param id The ID of the {@link GuiInventoryItem}.
   * @return The corresponding {@link GuiInventoryItem} if exists.
   */
  public Optional<GuiInventoryItem> getItem(@NotNull String id) {
    return items.stream()
      .filter(item -> item.id().equals(id))
      .findFirst();
  }
}

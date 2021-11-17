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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * GUI inventory entity.
 *
 * @param id The ID of the GUI inventory.
 * @param nbLines The number of lines of the GUI inventory.
 * @param items The {@link GuiItem}s embedded in the GUI inventory.
 * @author Voltariuss
 * @since 0.3.0
 */
public record GuiInventory (@NotNull String id, int nbLines, @NotNull List<GuiItem> items) {

  /**
   * Returns the GUI item which match with the specified ID.
   *
   * @param id The ID of the GUI item.
   * @return The corresponding GUI item if exists.
   */
  public Optional<GuiItem> getItem(@NotNull String id) {
    return items.stream()
      .filter(item -> item.id().equals(id))
      .findFirst();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

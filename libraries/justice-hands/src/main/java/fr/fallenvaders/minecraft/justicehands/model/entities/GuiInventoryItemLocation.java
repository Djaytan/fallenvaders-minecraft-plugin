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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * {@link GuiInventoryItem} location.
 *
 * @param line The line location of the {@link GuiInventoryItem}.
 * @param column The column location of the {@link GuiInventoryItem}.
 * @author Voltariuss
 * @since 0.3.0
 */
// TODO: rename it GuiLocation (shorter)
public record GuiInventoryItemLocation(int line, int column) {

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

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
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link GuiInventory} item entity.
 *
 * @param id The ID of the {@link GuiInventory} item.
 * @param location The location of the {@link GuiInventory} item.
 * @param item The {@link ItemStack} which represent the {@link GuiInventory} item.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public record GuiInventoryItem (
    @NotNull String id,
    @Nullable GuiInventoryItemLocation location,
    @Nullable ItemStack item) {

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

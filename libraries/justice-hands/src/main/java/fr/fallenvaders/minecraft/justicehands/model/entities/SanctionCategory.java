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

import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * This entity represent a category of {@link PredefinedSanction}s.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 *
 * @param id The ID of the category.
 * @param name The name of the category.
 * @param description The description of the category.
 * @param predefinedSanctions The {@link PredefinedSanction}s nested the category.
 */
public record SanctionCategory(
    @NotNull String id,
    @NotNull String name,
    @NotNull String description,
    @NotNull Set<PredefinedSanction> predefinedSanctions) {

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

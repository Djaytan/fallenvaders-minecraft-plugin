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

import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import org.jetbrains.annotations.NotNull;

/**
 * This entity represent a predefined sanction.
 *
 * <p>Predefined sanctions have only the purpose to create {@link Sanction} by replicating
 * information. After the creation of this one, no binding are kept between them. This approach
 * permit letting predefined sanctions evolved independently of {@link Sanction}s. This is
 * particularly useful in case predefined sanctions are regularly review to adapt points,
 * type, description, ...
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 *
 * @param id The id of the predefined sanction.
 * @param name The name of the predefined sanction.
 * @param description The description of the predefined sanction.
 * @param points The number of points associated with the predefined sanction.
 * @param type The type of the predefined sanction.
 */
public record PredefinedSanction(
    @NotNull String id,
    @NotNull String name,
    @NotNull String description,
    int points,
    @NotNull SanctionType type) {}

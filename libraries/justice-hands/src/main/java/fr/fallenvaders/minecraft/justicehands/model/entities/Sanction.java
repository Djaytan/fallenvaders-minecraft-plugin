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
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;

/**
 * This entity represents a sanction.
 *
 * <p>Most of the time, a sanction should be created based on an existing
 * {@link PredefinedSanction}. This ensures a good follow of sanctions scales defined by
 * FallenVaders' staff team.
 *
 * <p>When no predefined sanction fit the current case from which the moderator must give a
 * sanction, it's possible to directly create one. However, this usage must be limited as
 * much as possible by created new predefined as soon as possible.
 *
 * @param id The ID of the sanction.
 * @param inculpatedPlayer The inculpated player of the sanction.
 * @param name The name of the sanction.
 * @param reason The reason of the sanction.
 * @param points The number of points of the sanction.
 * @param beginningDate The beginning date of the sanction.
 * @param endingDate The ending date of the sanction (null in cases of kick or ban def for example).
 * @param authorPlayer The author of the sanction (null means console is the author).
 * @param type The type of sanction.
 * @author Voltariuss
 * @since 0.3.0
 */
public record Sanction (
    int id,
    @NotNull OfflinePlayer inculpatedPlayer,
    @NotNull String name,
    @NotNull String reason,
    int points,
    @NotNull Timestamp beginningDate,
    @Nullable Timestamp endingDate,
    @Nullable OfflinePlayer authorPlayer,
    @NotNull SanctionType type) {

  /**
   * Constructor.
   *
   * @param inculpatedPlayer The inculpated player of the sanction.
   * @param name The name of the sanction.
   * @param reason The reason of the sanction.
   * @param points The number of points of the sanction.
   * @param beginningDate The beginning date of the sanction.
   * @param endingDate The ending date of the sanction (null in cases of kick or ban def for
   *    example).
   * @param authorPlayer The author of the sanction (null means console is the author).
   * @param type The type of sanction.
   */
  public Sanction(
    @NotNull OfflinePlayer inculpatedPlayer,
    @NotNull String name,
    @NotNull String reason,
    int points,
    @NotNull Timestamp beginningDate,
    @Nullable Timestamp endingDate,
    @Nullable OfflinePlayer authorPlayer,
    @NotNull SanctionType type) {
    this(-1, inculpatedPlayer, name, reason, points, beginningDate, endingDate, authorPlayer, type);
  }
}

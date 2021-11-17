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

package fr.fallenvaders.minecraft.justicehands.view.viewmodel.builders;

import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities.PlayerSanctionTypeStatistics;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link PlayerSanctionTypeStatistics}'s builder class.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class PlayerSanctionTypeStatisticsBuilder {

  /** Constructor. */
  @Inject
  public PlayerSanctionTypeStatisticsBuilder() {}

  /**
   * Builds the statistics data of a given sanction type of player's sanctions.
   *
   * @param allSanctions All the sanctions recorded for the targeted player until now.
   * @param sanctionType The sanction type filter to apply.
   * @return The statistics data of a given sanction type of player's sanctions.
   */
  public @NotNull PlayerSanctionTypeStatistics build(
      Set<Sanction> allSanctions, SanctionType sanctionType) {
    Set<Sanction> filteredSanctions =
        allSanctions.stream()
            .filter(sanction -> Objects.equals(sanction.type(), sanctionType))
            .collect(Collectors.toSet());
    return new PlayerSanctionTypeStatistics(
        sanctionType,
        filteredSanctions.size(),
        getFirstSanction(filteredSanctions),
        getLastSanction(filteredSanctions));
  }

  private @Nullable Sanction getFirstSanction(@NotNull Set<Sanction> sanctions) {
    return sanctions.stream().min(Comparator.comparing(Sanction::beginningDate)).orElse(null);
  }

  private @Nullable Sanction getLastSanction(@NotNull Set<Sanction> sanctions) {
    return sanctions.stream().max(Comparator.comparing(Sanction::beginningDate)).orElse(null);
  }
}

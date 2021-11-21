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

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionController;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities.PlayerSanctionTypeStatistics;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities.PlayerSanctionsStatistics;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The {@link PlayerSanctionsStatistics}'s builder class.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class PlayerSanctionsStatisticsBuilder {

  private final PlayerSanctionTypeStatisticsBuilder playerSanctionTypeStatisticsBuilder;
  private final SanctionController sanctionController;

  /**
   * Constructor.
   *
   * @param playerSanctionTypeStatisticsBuilder The {@link PlayerSanctionTypeStatisticsBuilder}.
   * @param sanctionController The {@link SanctionController}.
   */
  @Inject
  public PlayerSanctionsStatisticsBuilder(
      @NotNull PlayerSanctionTypeStatisticsBuilder playerSanctionTypeStatisticsBuilder,
      @NotNull SanctionController sanctionController) {
    this.playerSanctionTypeStatisticsBuilder = playerSanctionTypeStatisticsBuilder;
    this.sanctionController = sanctionController;
  }

  /**
   * Builds the statistics of sanctions of the targeted player.
   *
   * @param target The target player for statistics build.
   * @return The statistics of sanctions of the targeted player.
   */
  public @NotNull PlayerSanctionsStatistics build(@NotNull OfflinePlayer target)
      throws JusticeHandsException {
    Set<Sanction> sanctions = sanctionController.getPlayerSanctions(target);
    Sanction lastSanction =
        sanctions.stream().max(Comparator.comparing(Sanction::beginningDate)).orElse(null);
    int currentPointsAmount = sanctions.stream().mapToInt(Sanction::points).sum();
    Set<PlayerSanctionTypeStatistics> sanctionTypeStatistics =
        new LinkedHashSet<>(SanctionType.values().length);
    for (SanctionType sanctionType : SanctionType.values()) {
      sanctionTypeStatistics.add(
          playerSanctionTypeStatisticsBuilder.build(sanctions, sanctionType));
    }
    // TODO: FV-139 - Store and manage currentPointsAmount
    return new PlayerSanctionsStatistics(
        sanctionTypeStatistics,
        lastSanction,
        sanctions.size(),
        currentPointsAmount,
        currentPointsAmount);
  }
}

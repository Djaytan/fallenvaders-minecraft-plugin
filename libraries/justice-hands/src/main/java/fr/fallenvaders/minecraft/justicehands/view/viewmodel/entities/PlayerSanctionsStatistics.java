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

package fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities;

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A sanctions' statistics class for a given player.
 *
 * @author Glynix
 * @author Voltariuss
 * @since 0.3.0
 */
public class PlayerSanctionsStatistics {

  private final Set<PlayerSanctionTypeStatistics> sanctionTypeStatistics;
  private final Sanction lastSanction;
  private final int nbSanctions;
  private final int currentPointsAmount;
  private final int totalPointsAmount;

  /**
   * Constructor.
   *
   * <p>Statistics for each type of sanction must be defined one and only one time, otherwise an
   * exception is thrown. The same happens if the amount of current points is higher than the total
   * amount of points recorded.
   *
   * @param sanctionTypeStatistics The statistics for each existing type of sanction.
   * @param lastSanction The last sanction recorded for the player.
   * @param nbSanctions The number of sanctions recorded for the player.
   * @param currentPointsAmount The current amount of points applicable for the player.
   * @param totalPointsAmount The total amount of points received by the player historically.
   */
  public PlayerSanctionsStatistics(
      @NotNull Set<PlayerSanctionTypeStatistics> sanctionTypeStatistics,
      @Nullable Sanction lastSanction,
      int nbSanctions,
      int currentPointsAmount,
      int totalPointsAmount) {
    checkIntegrity(
        sanctionTypeStatistics, lastSanction, nbSanctions, currentPointsAmount, totalPointsAmount);
    this.sanctionTypeStatistics = sanctionTypeStatistics;
    this.lastSanction = lastSanction;
    this.nbSanctions = nbSanctions;
    this.currentPointsAmount = currentPointsAmount;
    this.totalPointsAmount = totalPointsAmount;
  }

  private void checkIntegrity(
      @NotNull Set<PlayerSanctionTypeStatistics> sanctionTypeStatistics,
      @Nullable Sanction lastSanction,
      int nbSanctions,
      int currentPointsAmount,
      int totalPointsAmount) {
    Preconditions.checkArgument(
        nbSanctions >= 0, "The number of sanctions must be higher or equal to 0.");
    Preconditions.checkArgument(
        currentPointsAmount >= 0, "The current amount of points must be higher or equal to 0.");
    Preconditions.checkArgument(
        totalPointsAmount >= 0, "The total amount of points must be higher or equal to 0.");
    Preconditions.checkArgument(
        nbSanctions > 0 && lastSanction != null || nbSanctions == 0 && lastSanction == null,
        "The last sanction must be defined if the number of sanctions is higher to 0. "
            + "The last sanction mustn't be defined if the number of sanctions is equal to 0.");
    Preconditions.checkArgument(
        currentPointsAmount <= totalPointsAmount,
        "The current amount of points can't be higher than the total amount of points.");
    Preconditions.checkArgument(
        sanctionTypeStatistics.size() == SanctionType.values().length,
        "Statistics of all existing SanctionTypes must be defined, and only one time.");
    for (SanctionType sanctionType : SanctionType.values()) {

      Set<PlayerSanctionTypeStatistics> typeStatsSet =
          sanctionTypeStatistics.stream()
              .filter(
                  sanctionTypeStatistic ->
                      Objects.equals(sanctionTypeStatistic.getSanctionType(), sanctionType))
              .collect(Collectors.toSet());
      Preconditions.checkArgument(
          typeStatsSet.size() == 1,
          "The stats of sanction type '%s' must be defined one and only one time.");
      if (nbSanctions == 0) {
        PlayerSanctionTypeStatistics typeStats = typeStatsSet.iterator().next();
        Preconditions.checkArgument(
            typeStats.getNbSanctions() == 0,
            "If the total number of sanctions is equal to 0, the number of sanctions of one type must equal to 0 too.");
      }
    }
  }

  /**
   * Gets and returns the PlayerSanctionTypeStatistics which match with the given sanction type.
   *
   * @param sanctionType The type of sanction of the sought PlayerSanctionTypeStatistics.
   * @return The PlayerSanctionTypeStatistics which match with the given sanction type.
   * @throws IllegalStateException If an exceptionnel state error occurs (should never happen).
   */
  public @NotNull PlayerSanctionTypeStatistics getSanctionTypeStatistics(
      @NotNull SanctionType sanctionType) {
    return sanctionTypeStatistics.stream()
        .filter(
            playerSanctionTypeStatistics ->
                Objects.equals(playerSanctionTypeStatistics.getSanctionType(), sanctionType))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "This stats instance was not properly initialized... Not all stats for each existing sanction type is defined (this exception should never been raised."));
  }

  /**
   * Getter.
   *
   * @return The statistics for each existing type of sanction.
   */
  public @NotNull Set<PlayerSanctionTypeStatistics> getSanctionTypeStatistics() {
    return sanctionTypeStatistics;
  }

  /**
   * Getter.
   *
   * @return The last sanction recorded for the player.
   */
  public @Nullable Sanction getLastSanction() {
    return lastSanction;
  }

  /**
   * Getter.
   *
   * @return The number of sanctions recorder for the player.
   */
  public int getNbSanctions() {
    return nbSanctions;
  }

  /**
   * Getter.
   *
   * @return The current amount of points applicable for the player.
   */
  public int getCurrentPointsAmount() {
    return currentPointsAmount;
  }

  /**
   * Getter.
   *
   * @return The total amount of points received by the player historically.
   */
  public int getTotalPointsAmount() {
    return totalPointsAmount;
  }
}

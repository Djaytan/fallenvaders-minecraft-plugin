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
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PlayerSanctionsStatisticsItemBuilder;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The sanctions' statistics for a given type of sanction for a specific player.
 *
 * @author Glynix
 * @author Voltariuss
 * @see PlayerSanctionsStatisticsItemBuilder The PlayerSanctionsStatisticsItemBuilder class as an
 *     example of usage.
 * @since 0.3.0
 */
public class PlayerSanctionTypeStatistics {

  private final SanctionType sanctionType;
  private final int nbSanctions;
  private final Sanction firstSanction;
  private final Sanction lastSanction;

  /**
   * Constructor.
   *
   * <p>Checks the integrity of the object at the creation. If the first sanction argument is
   * defined, the second one must be defined too otherwise an exception is thrown. If the first
   * sanction is the last one too, it's the responsibility of the caller to define both arguments.
   *
   * <p>The number of sanctions must be coherent with the definition of first and last sanctions. If
   * the number is superior to 0, other arguments can't be null. On the opposite side, if the number
   * is equal to 0, others argument must be null. Of course, if the number of sanctions is negative
   * an exception is thrown.
   *
   * <p>The sanctionType argument has only as purpose to check integrity of first and last
   * sanctions.
   *
   * @param sanctionType The expected type of sanction of first and last ones.
   * @param nbSanctions The number of sanctions registered.
   * @param firstSanction The first recorded sanction.
   * @param lastSanction The last recorded sanction.
   * @throws IllegalArgumentException If integrity is jeopardized.
   */
  @Contract("_, _, !null, null -> fail; _, _, null, !null -> fail")
  public PlayerSanctionTypeStatistics(
      @NotNull SanctionType sanctionType,
      int nbSanctions,
      @Nullable Sanction firstSanction,
      @Nullable Sanction lastSanction) {
    checkIntegrity(sanctionType, nbSanctions, firstSanction, lastSanction);
    this.sanctionType = sanctionType;
    this.nbSanctions = nbSanctions;
    this.firstSanction = firstSanction;
    this.lastSanction = lastSanction;
  }

  private void checkIntegrity(
      @NotNull SanctionType sanctionType,
      int nbSanctions,
      @Nullable Sanction firstSanction,
      @Nullable Sanction lastSanction) {
    Preconditions.checkNotNull(sanctionType);
    Preconditions.checkArgument(
        nbSanctions >= 0, "The number of sanctions can't be a negative number.");
    Preconditions.checkArgument(
        firstSanction == null && lastSanction == null
            || firstSanction != null && lastSanction != null,
        "If the first sanction is defined, the second one must be defined too. "
            + "If the first sanction isn't defined, the second one mustn't be defined too.");
    Preconditions.checkArgument(
        nbSanctions == 0 && firstSanction == null || nbSanctions > 0 && firstSanction != null,
        "If the number of sanctions is equal to 0, first and last sanctions can't be defined. "
            + "If the number of sanctions is superior to 0, first and last sanctions must be defined.");
    if (firstSanction != null) {
      Preconditions.checkArgument(
          Objects.equals(firstSanction.type(), lastSanction.type()),
          "The type of the last sanction must be the same as the first one.");
      Preconditions.checkArgument(
          Objects.equals(firstSanction.type(), sanctionType),
          "The sanction type of first and last sanctions must match with the expected one.");
    }
  }

  /**
   * Getter.
   *
   * @return The type of sanction of this statistics.
   */
  public @NotNull SanctionType getSanctionType() {
    return sanctionType;
  }

  /**
   * Getter.
   *
   * @return The number of sanctions considered in this statistics.
   */
  public int getNbSanctions() {
    return nbSanctions;
  }

  /**
   * Getter.
   *
   * @return The first sanction considered in this statistics if exists.
   */
  public @Nullable Sanction getFirstSanction() {
    return firstSanction;
  }

  /**
   * Getter.
   *
   * @return The last sanction considered in this statistics if exists.
   */
  public @Nullable Sanction getLastSanction() {
    return lastSanction;
  }
}

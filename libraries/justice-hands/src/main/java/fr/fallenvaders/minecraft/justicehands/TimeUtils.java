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

package fr.fallenvaders.minecraft.justicehands;

import java.util.StringJoiner;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

/**
 * Time utils class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class TimeUtils {

  /* Millisecond conversion constants */
  private static final long NB_MS_IN_SECOND = 1000L;
  private static final long NB_MS_IN_MINUTE = NB_MS_IN_SECOND * 60L;
  private static final long NB_MS_IN_HOUR = NB_MS_IN_MINUTE * 60L;
  private static final long NB_MS_IN_DAY = NB_MS_IN_HOUR * 60L;
  private static final long NB_MS_IN_MONTH = NB_MS_IN_DAY * 30L;

  /**
   * States the remaining time in a French string format.
   *
   * <p>Example: 1 mois, 12 jours, 13 heures, 56 secondes.
   *
   * @param remainingTime The remaining time in milliseconds.
   * @return The remaining time in string format.
   */
  public String remainingTime(long remainingTime) {
    if (remainingTime < 0) {
      remainingTime = 0;
    }

    int nbRemainingMonths = (int) (remainingTime / NB_MS_IN_MONTH);
    int nbRemainingDays = (int) ((remainingTime % NB_MS_IN_MONTH) / NB_MS_IN_DAY);
    int nbRemainingHours = (int) ((remainingTime % NB_MS_IN_DAY) / NB_MS_IN_HOUR);
    int nbRemainingMinutes = (int) ((remainingTime % NB_MS_IN_HOUR) / NB_MS_IN_MINUTE);
    int nbRemainingSeconds = (int) ((remainingTime % NB_MS_IN_MINUTE) / NB_MS_IN_SECOND);

    StringJoiner remainingTimeStr = new StringJoiner(", ");
    remainingTimeStr.add(getSubRemainingTimeStr(nbRemainingMonths, "mois"));
    remainingTimeStr.add(getSubRemainingTimeStr(nbRemainingDays, "jour"));
    remainingTimeStr.add(getSubRemainingTimeStr(nbRemainingHours, "heure"));
    remainingTimeStr.add(getSubRemainingTimeStr(nbRemainingMinutes, "minute"));
    remainingTimeStr.add(getSubRemainingTimeStr(nbRemainingSeconds, "seconde"));

    return remainingTimeStr.toString();
  }

  private @NotNull String getSubRemainingTimeStr(int amount, @NotNull String timeUnit) {
    String subRemainingTimeStr = "";
    if (amount > 0) {
      subRemainingTimeStr = amount + " " + timeUnit;
      if (amount > 1 && !timeUnit.equals("mois")) {
        subRemainingTimeStr += "s";
      }
    }
    return subRemainingTimeStr;
  }
}

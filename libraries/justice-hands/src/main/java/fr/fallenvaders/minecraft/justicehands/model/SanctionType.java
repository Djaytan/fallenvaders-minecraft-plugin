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

package fr.fallenvaders.minecraft.justicehands.model;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * The enum of type of sanctions.
 *
 * @author Glynix
 * @author Voltariuss
 * @since 0.1.0
 */
public enum SanctionType {
  KICK("Kick", ChatColor.GREEN),
  MUTE("Mute", ChatColor.BLUE),
  BAN("Bannissement", ChatColor.GOLD);

  private final String visualName;
  private final ChatColor visualColor;

  /**
   * Constructor.
   *
   * @param visualName The visual name of the type of sanction.
   * @param visualColor The visual color of the type of sanction.
   */
  SanctionType(@NotNull String visualName, @NotNull ChatColor visualColor) {
    this.visualName = visualName;
    this.visualColor = visualColor;
  }

  /**
   * Getter.
   *
   * @return The visual name of the type of sanction.
   */
  public @NotNull String getVisualName() {
    return visualName;
  }

  /**
   * Getter.
   *
   * @return The visual color of the type of sanction.
   */
  public @NotNull ChatColor getVisualColor() {
    return visualColor;
  }
}

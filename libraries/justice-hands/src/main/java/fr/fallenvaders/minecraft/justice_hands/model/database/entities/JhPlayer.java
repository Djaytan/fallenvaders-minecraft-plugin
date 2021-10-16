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

package fr.fallenvaders.minecraft.justice_hands.model.database.entities;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * This entity represents a JusticeHands' player.
 *
 * <p>Note: a big amount of points means that the player has make lots of offenses on the server.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class JhPlayer {

  private UUID uuid;
  private int points;

  /**
   * Constructor.
   *
   * @param uuid The UUID of the player.
   */
  public JhPlayer(@NotNull UUID uuid) {
    this(uuid, 0);
  }

  /**
   * Constructor.
   *
   * @param uuid The UUID of the player.
   * @param points The number of points in the criminal record of the player.
   */
  public JhPlayer(@NotNull UUID uuid, int points) {
    this.uuid = uuid;
    this.points = points;
  }

  /**
   * Getter.
   *
   * @return The UUID of the player.
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * Getter.
   *
   * @return The number of points in the criminal record of the player.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Setter.
   *
   * @param uuid The UUID of the player.
   */
  public void setUuid(@NotNull UUID uuid) {
    this.uuid = uuid;
  }

  /**
   * Setter.
   *
   * @param points The number of points in the criminal record of the player
   */
  public void setPoints(int points) {
    this.points = points;
  }
}

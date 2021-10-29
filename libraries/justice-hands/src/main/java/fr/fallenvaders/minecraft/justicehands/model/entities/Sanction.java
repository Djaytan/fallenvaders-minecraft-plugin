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

import java.sql.Timestamp;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This entity represents a JusticeHands' sanction.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class Sanction {

  private final int id;
  private OfflinePlayer inculpatedPlayer;
  private String name;
  private String reason;
  private int points;
  private Timestamp beginningDate;
  private Timestamp endingDate;
  private OfflinePlayer authorPlayer;
  private SanctionType type;

  /**
   * Constructor.
   *
   * <p>Note: this one must be used only at the registration of a new sanction.
   */
  public Sanction() {
    this(-1);
  }

  /**
   * Constructor.
   *
   * @param id The ID of the sanction.
   */
  public Sanction(int id) {
    this.id = id;
  }

  /**
   * Getter.
   *
   * @return The ID of the sanction.
   */
  public int getId() {
    return id;
  }

  /**
   * Getter.
   *
   * @return The inculpated player targeted by the sanction.
   */
  public @NotNull OfflinePlayer getInculpatedPlayer() {
    return inculpatedPlayer;
  }

  /**
   * Setter.
   *
   * @param inculpatedPlayer The inculpated player targeted by the sanction.
   */
  public void setInculpatedPlayer(@NotNull OfflinePlayer inculpatedPlayer) {
    this.inculpatedPlayer = inculpatedPlayer;
  }

  /**
   * Getter.
   *
   * @return The name of the sanction.
   */
  public @NotNull String getName() {
    return name;
  }

  /**
   * Setter.
   *
   * @param name The name of the sanction.
   */
  public void setName(@NotNull String name) {
    this.name = name;
  }

  /**
   * Getter.
   *
   * @return The reason of the sanction.
   */
  public @NotNull String getReason() {
    return reason;
  }

  /**
   * Setter.
   *
   * @param reason The reason of the sanction.
   */
  public void setReason(@NotNull String reason) {
    this.reason = reason;
  }

  /**
   * Getter.
   *
   * @return The points of the sanction.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Setter.
   *
   * @param points The points of the sanction.
   */
  public void setPoints(int points) {
    this.points = points;
  }

  /**
   * Getter.
   *
   * @return The beginning date of the sanction.
   */
  public @NotNull Timestamp getBeginningDate() {
    return beginningDate;
  }

  /**
   * Setter.
   *
   * @param beginningDate The beginning date of the sanction.
   */
  public void setBeginningDate(@NotNull Timestamp beginningDate) {
    this.beginningDate = beginningDate;
  }

  /**
   * Getter.
   *
   * @return The ending date of the sanction.
   */
  public @Nullable Timestamp getEndingDate() {
    return endingDate;
  }

  /**
   * Setter.
   *
   * @param endingDate The ending date of the sanction.
   */
  public void setEndingDate(@Nullable Timestamp endingDate) {
    this.endingDate = endingDate;
  }

  /**
   * Getter.
   *
   * @return The author's UUID of the sanction.
   */
  public @Nullable OfflinePlayer getAuthorPlayer() {
    return authorPlayer;
  }

  /**
   * Setter.
   *
   * @param authorPlayer The author's UUID of the sanction.
   */
  public void setAuthorPlayer(@Nullable OfflinePlayer authorPlayer) {
    this.authorPlayer = authorPlayer;
  }

  /**
   * Getter.
   *
   * @return The type of the sanction (mute, ban, ...).
   */
  public @NotNull SanctionType getType() {
    return type;
  }

  /**
   * Setter.
   *
   * @param type The type of the sanction (mute, ban, ...).
   */
  public void setType(@NotNull SanctionType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}

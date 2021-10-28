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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;

/**
 * This entity represents a JusticeHands' sanction.
 *
 * <p>Note: "sctn" means "sanction".
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class JhSanction {

  private final int sctnId;
  private OfflinePlayer sctnInculpatedPlayer;
  private String sctnName;
  private String sctnReason;
  private int sctnPoints;
  private Timestamp sctnBeginningDate;
  private Timestamp sctnEndingDate;
  private OfflinePlayer sctnAuthorPlayer;
  private SanctionType sctnType;

  /**
   * Constructor.
   *
   * @param sctnId The ID of the sanction.
   */
  public JhSanction(int sctnId) {
    this.sctnId = sctnId;
  }

  /**
   * Getter.
   *
   * @return The ID of the sanction.
   */
  public int getSctnId() {
    return sctnId;
  }

  /**
   * Getter.
   *
   * @return The inculpated player targeted by the sanction.
   */
  public @NotNull OfflinePlayer getSctnInculpatedPlayer() {
    return sctnInculpatedPlayer;
  }

  /**
   * Setter.
   *
   * @param sctnInculpatedPlayer The inculpated player targeted by the sanction.
   */
  public void setSctnInculpatedPlayer(@NotNull OfflinePlayer sctnInculpatedPlayer) {
    this.sctnInculpatedPlayer = sctnInculpatedPlayer;
  }

  /**
   * Getter.
   *
   * @return The name of the sanction.
   */
  public @NotNull String getSctnName() {
    return sctnName;
  }

  /**
   * Setter.
   *
   * @param sctnName The name of the sanction.
   */
  public void setSctnName(@NotNull String sctnName) {
    this.sctnName = sctnName;
  }

  /**
   * Getter.
   *
   * @return The reason of the sanction.
   */
  public @NotNull String getSctnReason() {
    return sctnReason;
  }

  /**
   * Setter.
   *
   * @param sctnReason The reason of the sanction.
   */
  public void setSctnReason(@NotNull String sctnReason) {
    this.sctnReason = sctnReason;
  }

  /**
   * Getter.
   *
   * @return The points of the sanction.
   */
  public int getSctnPoints() {
    return sctnPoints;
  }

  /**
   * Setter.
   *
   * @param sctnPoints The points of the sanction.
   */
  public void setSctnPoints(int sctnPoints) {
    this.sctnPoints = sctnPoints;
  }

  /**
   * Getter.
   *
   * @return The beginning date of the sanction.
   */
  public @NotNull Timestamp getSctnBeginningDate() {
    return sctnBeginningDate;
  }

  /**
   * Setter.
   *
   * @param sctnBeginningDate The beginning date of the sanction.
   */
  public void setSctnBeginningDate(@NotNull Timestamp sctnBeginningDate) {
    this.sctnBeginningDate = sctnBeginningDate;
  }

  /**
   * Getter.
   *
   * @return The ending date of the sanction.
   */
  public @Nullable Timestamp getSctnEndingDate() {
    return sctnEndingDate;
  }

  /**
   * Setter.
   *
   * @param sctnEndingDate The ending date of the sanction.
   */
  public void setSctnEndingDate(@Nullable Timestamp sctnEndingDate) {
    this.sctnEndingDate = sctnEndingDate;
  }

  /**
   * Getter.
   *
   * @return The author's UUID of the sanction.
   */
  public @NotNull OfflinePlayer getSctnAuthorPlayer() {
    return sctnAuthorPlayer;
  }

  /**
   * Setter.
   *
   * @param sctnAuthorPlayer The author's UUID of the sanction.
   */
  public void setSctnAuthorPlayer(@NotNull OfflinePlayer sctnAuthorPlayer) {
    this.sctnAuthorPlayer = sctnAuthorPlayer;
  }

  /**
   * Getter.
   *
   * @return The type of the sanction (mute, ban, ...).
   */
  public @NotNull SanctionType getSctnType() {
    return sctnType;
  }

  /**
   * Setter.
   *
   * @param sctnType The type of the sanction (mute, ban, ...).
   */
  public void setSctnType(@NotNull SanctionType sctnType) {
    this.sctnType = sctnType;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
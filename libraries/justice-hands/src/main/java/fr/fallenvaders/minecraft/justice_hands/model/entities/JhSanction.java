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

package fr.fallenvaders.minecraft.justice_hands.model.entities;

import fr.fallenvaders.minecraft.justice_hands.SanctionType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * This entity represents a JusticeHands' sanction.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
public class JhSanction {

  private int id;
  private UUID playerUuid;
  private String sanctionName;
  private String sanctionReason;
  private int sanctionPoints;
  private Timestamp sanctionBeginningDate;
  private Timestamp sanctionEndingDate;
  private UUID sanctionAuthorUuid;
  private SanctionType sanctionType;
  private String sanctionState;

  /**
   * Constructor.
   *
   * @param id The ID of the sanction.
   */
  public JhSanction(int id) {
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
   * @return The player's UUID targeted by the sanction.
   */
  public UUID getPlayerUuid() {
    return playerUuid;
  }

  /**
   * Setter.
   *
   * @param playerUuid The player's UUID targeted by the sanction.
   */
  public void setPlayerUuid(UUID playerUuid) {
    this.playerUuid = playerUuid;
  }

  /**
   * Getter.
   *
   * @return The name of the sanction.
   */
  // TODO: replace this one by a reference to an existing sanction_type.
  public String getSanctionName() {
    return sanctionName;
  }

  /**
   * Setter.
   *
   * @param sanctionName The name of the sanction.
   */
  public void setSanctionName(String sanctionName) {
    this.sanctionName = sanctionName;
  }

  /**
   * Getter.
   *
   * @return The reason of the sanction.
   */
  public String getSanctionReason() {
    return sanctionReason;
  }

  /**
   * Setter.
   *
   * @param sanctionReason The reason of the sanction.
   */
  public void setSanctionReason(String sanctionReason) {
    this.sanctionReason = sanctionReason;
  }

  /**
   * Getter.
   *
   * @return The points of the sanction.
   */
  public int getSanctionPoints() {
    return sanctionPoints;
  }

  /**
   * Setter.
   *
   * @param sanctionPoints The points of the sanction.
   */
  public void setSanctionPoints(int sanctionPoints) {
    this.sanctionPoints = sanctionPoints;
  }

  /**
   * Getter.
   *
   * @return The beginning date of the sanction.
   */
  public Timestamp getSanctionBeginningDate() {
    return sanctionBeginningDate;
  }

  /**
   * Setter.
   *
   * @param sanctionBeginningDate The beginning date of the sanction.
   */
  public void setSanctionBeginningDate(Timestamp sanctionBeginningDate) {
    this.sanctionBeginningDate = sanctionBeginningDate;
  }

  /**
   * Getter.
   *
   * @return The ending date of the sanction.
   */
  public Timestamp getSanctionEndingDate() {
    return sanctionEndingDate;
  }

  /**
   * Setter.
   *
   * @param sanctionEndingDate The ending date of the sanction.
   */
  public void setSanctionEndingDate(Timestamp sanctionEndingDate) {
    this.sanctionEndingDate = sanctionEndingDate;
  }

  /**
   * Getter.
   *
   * @return The author's UUID of the sanction.
   */
  public UUID getSanctionAuthorUuid() {
    return sanctionAuthorUuid;
  }

  /**
   * Setter.
   *
   * @param sanctionAuthorUuid The author's UUID of the sanction.
   */
  public void setSanctionAuthorUuid(UUID sanctionAuthorUuid) {
    this.sanctionAuthorUuid = sanctionAuthorUuid;
  }

  /**
   * Getter.
   *
   * @return The type of the sanction (mute, ban, ...).
   */
  public SanctionType getSanctionType() {
    return sanctionType;
  }

  /**
   * Setter.
   *
   * @param sanctionType The type of the sanction (mute, ban, ...).
   */
  public void setSanctionType(SanctionType sanctionType) {
    this.sanctionType = sanctionType;
  }

  /**
   * Getter.
   *
   * @return The state of the sanction.
   */
  // TODO: remove it
  public String getSanctionState() {
    return sanctionState;
  }

  /**
   * Setter.
   *
   * @param sanctionState The state of the sanction.
   */
  public void setSanctionState(String sanctionState) {
    this.sanctionState = sanctionState;
  }
}

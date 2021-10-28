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

package fr.fallenvaders.minecraft.justicehands.view.viewmodel;

public class Sanction implements Cloneable {
  private String sanctionName;
  private String sanctionReason;
  private int sanctionPoints;
  private String sanctionType;

  // Constructeur vide
  public Sanction() {}

  // Clone une sanction
  @Override
  public Object clone() {
    Sanction sanction = new Sanction();
    sanction.sanctionName = new String(this.sanctionName);
    sanction.sanctionPoints = this.sanctionPoints;
    sanction.sanctionReason = new String(this.sanctionReason);
    sanction.sanctionType = new String(this.sanctionType);
    return sanction;
  }

  // *** MUTATEURS ***//
  public void setName(String sanctionName) {
    this.sanctionName = sanctionName;
  }

  public void setReason(String sanctionReason) {
    this.sanctionReason = sanctionReason;
  }

  public void setPoints(int sanctionPoints) {
    this.sanctionPoints = sanctionPoints;
  }

  public void setInitialType(String sanctionType) {
    this.sanctionType = sanctionType;
  }

  // *** ACCESSEURS ***//
  public String getName() {
    return sanctionName;
  }

  public String getReason() {
    return sanctionReason;
  }

  public int getPoints() {
    return sanctionPoints;
  }

  public String getInitialType() {
    return sanctionType;
  }
}

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

import java.util.ArrayList;

public class Categorie {

  private String categoryName;
  private String categoryDesc;
  private ArrayList<Sanction> sanctionsList = new ArrayList<>();
  private int categoryLineSlot;
  private int categoryColumSlot;

  // Constructeur vide
  public Categorie() {}

  // *** MUTATEURS ***//
  public void setName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public void setLineSlot(int categoryLineSlot) {
    this.categoryLineSlot = categoryLineSlot;
  }

  public void setColumSlot(int categoryColumSlot) {
    this.categoryColumSlot = categoryColumSlot;
  }

  public void addSanction(Sanction sanction) {
    sanctionsList.add(sanction);
  }

  // *** ACCESSEURS ***//
  public String getName() {
    return this.categoryName;
  }

  public String getDesc() {
    return this.categoryDesc;
  }

  public int getLineSlot() {
    return this.categoryLineSlot;
  }

  public int getColumSlot() {
    return this.categoryColumSlot;
  }

  public ArrayList<Sanction> getSanctionsList() {
    return this.sanctionsList;
  }
}

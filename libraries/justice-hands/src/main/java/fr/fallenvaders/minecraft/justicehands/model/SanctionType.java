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
import org.bukkit.Material;

public enum SanctionType {
  KICK("kick", "Kick ", ChatColor.GREEN, Material.GREEN_CONCRETE),
  MUTE("mute", "Mute ", ChatColor.BLUE, Material.BLUE_CONCRETE),
  BAN("ban", "Bannissement ", ChatColor.GOLD, Material.ORANGE_CONCRETE);

  // Atrtibuts
  private String configName;
  private String visualName;
  private ChatColor visualColor;
  private Material clayColor;

  // Constructeur
  private SanctionType(
      final String configName,
      final String visualName,
      final ChatColor visualColor,
      final Material clayColor) {
    this.configName = configName;
    this.visualName = visualName;
    this.visualColor = visualColor;
    this.clayColor = clayColor;
  }

  // On récupère le type de sanction via le nom de la config
  public static SanctionType getType(String configName) {
    SanctionType[] typeList = SanctionType.values();
    for (SanctionType type : typeList) {
      if (type.getConfigName().equalsIgnoreCase(configName)) {
        return type;
      }
    }
    return null;
  }

  // Récupère le nom côté config
  public String getConfigName() {
    return this.configName;
  }

  // Récupère le nom côté visuel (visible par tous)
  public String getVisualName() {
    return this.visualName;
  }

  // Récupère la couleur en lien avec le nom visuel
  public ChatColor getVisualColor() {
    return this.visualColor;
  }

  // Récupère le bloc représentant la sanction
  public Material getClayColor() {
    return this.clayColor;
  }
}

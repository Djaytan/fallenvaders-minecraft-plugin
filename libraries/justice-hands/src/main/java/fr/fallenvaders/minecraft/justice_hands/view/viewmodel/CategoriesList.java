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

package fr.fallenvaders.minecraft.justice_hands.view.viewmodel;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class CategoriesList {

  private static Categorie categorie;
  private static Sanction sanction;
  private static ArrayList<Categorie> categoriesList = new ArrayList<>();

  // Méthode permettant de récupérer la totalités des informations de chaque
  // sanctions du config.yml
  public static void getSanctionsConfig(FileConfiguration config) {
    // Chemin d'accès vers la liste des catégories dans le config.yml
    final String pathToCat = "justicehands.sanctionmanager.categories";

    // Récupèration du nom de chaque catégorie existante dans le config.yml
    for (String nameC : config.getConfigurationSection(pathToCat).getKeys(false)) {

      // Chemin vers les informations de la catégorie.
      final String pathCP = (pathToCat + "." + nameC);

      // Récupération des informations de la catégorie.
      String descC = config.getString(pathCP + ".description");
      int lineSlotC = config.getInt(pathCP + ".main-line-slot");
      int columSlotC = config.getInt(pathCP + ".main-colum-slot");

      // Attribution des informations à la catégorie.
      categorie = new Categorie();
      categorie.setName(nameC);
      categorie.setDesc(descC);
      categorie.setLineSlot(lineSlotC);
      categorie.setColumSlot(columSlotC);

      // Chemin d'accès vers la liste des sanctions de la catégorie.
      final String pathSN = (pathToCat + "." + nameC + ".sanctions");

      // Récupèration du nombre de chaque sanction existante dans la catégorie.
      for (String sanctionNumber : config.getConfigurationSection(pathSN).getKeys(false)) {

        // Chemin vers les informations de la sanction.
        final String pathSP = pathSN + "." + sanctionNumber;

        // Récupération des informations de la sanction.
        String nameS = config.getString(pathSP + ".name");
        String reasonS = config.getString(pathSP + ".reason");
        int pointsS = config.getInt(pathSP + ".points");
        String initialTypeS = config.getString(pathSP + ".initial-type");
        if (!(initialTypeS.equals("kick")
            || initialTypeS.equals("mute")
            || initialTypeS.equals("ban")
            || initialTypeS.equals("bandef"))) {
          System.out.println(
              GeneralUtils.getPrefix("SM")
                  + "§c§lERREUR DE GENERATION: §r§cUn type de sanction rentré dans le fichier config.yml est pas reconnu ! (§b"
                  + initialTypeS
                  + "§c)");
          System.out.println(
              "§c["
                  + nameC
                  + ":"
                  + nameS
                  + "/"
                  + reasonS
                  + "/"
                  + pointsS
                  + "/"
                  + initialTypeS
                  + "]");
        } else {
          // Attribution des informations à la sanction.
          sanction = new Sanction();
          sanction.setName(nameS);
          sanction.setReason(reasonS);
          sanction.setPoints(pointsS);
          sanction.setInitialType(initialTypeS.toLowerCase());

          // Ajout de la sanction à la catégorie.
          categorie.addSanction(sanction);
        }
      }
      categoriesList.add(
          categorie); // On ajoute la catégorie avec toutes les sanctions dans la la liste des
      // catégories.
    }
  }

  // Retourne la liste des catégories, mais du coup aussi la liste des sanctions de chaque catégorie
  public static ArrayList<Categorie> getCategoriesList() {
    return categoriesList;
  }
}

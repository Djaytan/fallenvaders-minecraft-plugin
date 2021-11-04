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

package fr.fallenvaders.minecraft.justicehands.view.gui;

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsModule;
import fr.minuskube.inv.InventoryManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.UUID;

public class InventoryBuilderSM {

  // Ouverture de l'inventaire des catégories
  public static void openMainMenu(Player moderator, UUID targetUUID, FileConfiguration config) {

    // Récupération des informations de la config du plugin
    String mainMenuPath = "justicehands.sanctionmanager.MainMenu";
    int menuLines = config.getInt(mainMenuPath + ".lines");
    int targetHeadLine = config.getInt(mainMenuPath + ".playerhead-line");
    int targetHeadColum = config.getInt(mainMenuPath + ".playerhead-colum");

    // Création de l'inventaire
    InventoryManager inventoryManager = new InventoryManager(JusticeHandsModule.PLUGIN);
    inventoryManager.init();

    SmartInventory.Builder builder = SmartInventory.builder();
    builder.title(
        "§7[§9" + Bukkit.getPlayer(targetUUID).getName() + "§7]" + "§7> §cMenu principal");
    builder.provider(new MainInventoryProvider(targetHeadLine, targetHeadColum));
    builder.size(menuLines, 9);
    builder.id(targetUUID.toString());
    builder.type(InventoryType.CHEST);
    builder.manager(inventoryManager);
    SmartInventory inventory = builder.build();
    inventory.open(moderator);
  }

  // Ouverture de l'inventaire des sanction de la catégorie voulue
  public static void openCategoryMenu(Category category, Player moderator, Player target) {

    // Création de l'inventaire
    InventoryManager inventoryManager = new InventoryManager(JusticeHandsModule.PLUGIN);
    inventoryManager.init();

    SmartInventory.Builder builder = SmartInventory.builder();
    builder.title(GeneralUtils.getPrefix("SM") + "§7> §c" + category.getName());
    builder.provider(new CategoryInventorySM(category));
    builder.size(6, 9);
    builder.id(target.getUniqueId().toString());
    builder.type(InventoryType.CHEST);
    builder.manager(inventoryManager);
    SmartInventory inventory = builder.build();
    inventory.open(moderator);
  }
}

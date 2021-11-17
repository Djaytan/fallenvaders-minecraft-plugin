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

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.GuiInventoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.fallenvaders.minecraft.justicehands.view.gui.inventories.CriminalRecordProvider;
import fr.minuskube.inv.SmartInventory;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The criminal record view.
 *
 * <p>The purpose of this view is to permit the creation of menus about visualization and management
 * of existing {@link Sanction}s of the specified player.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class CriminalRecordView {

  private final CriminalRecordProvider criminalRecordProvider;
  private final GuiInventoryController guiInventoryController;
  private final InteractiveInventoryBuilder interactiveInventoryBuilder;

  /**
   * Constructor.
   *
   * @param criminalRecordProvider The {@link CriminalRecordProvider}.
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param interactiveInventoryBuilder The {@link InteractiveInventoryBuilder}.
   */
  @Inject
  public CriminalRecordView(
      @NotNull CriminalRecordProvider criminalRecordProvider,
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull InteractiveInventoryBuilder interactiveInventoryBuilder) {
    this.criminalRecordProvider = criminalRecordProvider;
    this.guiInventoryController = guiInventoryController;
    this.interactiveInventoryBuilder = interactiveInventoryBuilder;
  }

  /**
   * Opens the main menu of the corresponding criminal record.
   *
   * @param opener The player opener of the menu.
   * @param target The target player needed to open the menu.
   * @throws JusticeHandsException if the recovering of sanctions fail.
   */
  public void openMainMenu(@NotNull Player opener, @NotNull OfflinePlayer target)
      throws JusticeHandsException {
    Preconditions.checkNotNull(opener);
    Preconditions.checkNotNull(target);

    GuiInventory mainGuiInventory =
        guiInventoryController.getGuiInventory(CriminalRecordProvider.GUI_INVENTORY_ID);
    int nbLines = mainGuiInventory.nbLines();

    String id = target.getUniqueId().toString();
    String title = String.format("%s§c%s", ViewUtils.PREFIX_CR, target.getName());

    SmartInventory menu =
        interactiveInventoryBuilder.build(criminalRecordProvider, id, title, nbLines);
    menu.open(opener);
  }
}

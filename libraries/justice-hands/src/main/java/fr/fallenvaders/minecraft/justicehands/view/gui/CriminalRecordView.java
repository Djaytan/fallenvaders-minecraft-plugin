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
import fr.fallenvaders.minecraft.justicehands.controller.SanctionController;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.minuskube.inv.SmartInventory;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The criminal record view.
 *
 * <p>The purpose of this view is to permit the creation of menus about visualization and management
 * of existing {@link Sanction}s of the specified {@link OfflinePlayer}.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class CriminalRecordView {

  private final GuiInventoryController guiInventoryController;
  private final InteractiveInventoryBuilder interactiveInventoryBuilder;
  private final SanctionController sanctionController;

  /**
   * Constructor.
   *
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param interactiveInventoryBuilder The {@link InteractiveInventoryBuilder}.
   * @param sanctionController The {@link SanctionController}.
   */
  @Inject
  public CriminalRecordView(
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull InteractiveInventoryBuilder interactiveInventoryBuilder,
      @NotNull SanctionController sanctionController) {
    this.guiInventoryController = guiInventoryController;
    this.interactiveInventoryBuilder = interactiveInventoryBuilder;
    this.sanctionController = sanctionController;
  }

  /**
   * Opens the main menu of the corresponding criminal record.
   *
   * @param opener The {@link Player} opener of the menu.
   * @param target The target {@link OfflinePlayer} needed to open the menu.
   * @throws JusticeHandsException if the recovering of {@link Sanction}s fail.
   */
  public void openMainMenu(@NotNull Player opener, @NotNull OfflinePlayer target)
      throws JusticeHandsException {
    Preconditions.checkNotNull(opener);
    Preconditions.checkNotNull(target);

    Set<Sanction> sanctions = sanctionController.getPlayerSanctions(target);

    GuiInventory mainGuiInventory = null; // TODO
    int nbLines = mainGuiInventory.nbLines();

    String id = target.getUniqueId().toString();
    String title = String.format("%s§c%s", ViewUtils.PREFIX_CR, target.getName());

    SmartInventory menu = null; // TODO
    menu.open(opener);
  }
}

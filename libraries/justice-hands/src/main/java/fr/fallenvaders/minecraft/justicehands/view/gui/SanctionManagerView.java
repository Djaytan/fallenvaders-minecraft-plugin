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
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.fallenvaders.minecraft.justicehands.view.gui.inventories.SanctionManagerCategoryProvider;
import fr.fallenvaders.minecraft.justicehands.view.gui.inventories.SanctionManagerMainProvider;
import fr.minuskube.inv.SmartInventory;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The sanction manager view.
 *
 * <p>The purpose of this view is to permit the creation of menus about navigation and visualization
 * of {@link PredefinedSanction}s which are embedded in {@link SanctionCategory}s. This one permit
 * as well to dispatch these {@link PredefinedSanction}s to players in order to register a new
 * {@link Sanction} in their criminal record.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class SanctionManagerView {

  private final GuiInventoryController guiInventoryController;
  private final InteractiveInventoryBuilder interactiveInventoryBuilder;

  /* Inventory Providers */
  private final SanctionManagerMainProvider sanctionManagerMainProvider;

  /**
   * Constructor.
   *
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param interactiveInventoryBuilder The {@link InteractiveInventoryBuilder}.
   * @param sanctionManagerMainProvider The {@link SanctionManagerMainProvider}.
   */
  @Inject
  public SanctionManagerView(
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull InteractiveInventoryBuilder interactiveInventoryBuilder,
      @NotNull SanctionManagerMainProvider sanctionManagerMainProvider) {
    this.guiInventoryController = guiInventoryController;
    this.interactiveInventoryBuilder = interactiveInventoryBuilder;
    this.sanctionManagerMainProvider = sanctionManagerMainProvider;
  }

  /**
   * Opens the main menu of the sanction manager system.
   *
   * @param opener The {@link Player} opener of the menu.
   * @param target The target {@link OfflinePlayer} needed to open the menu.
   * @throws JusticeHandsException if the recovering of the corresponding {@link GuiInventory} fail.
   */
  public void openMainMenu(@NotNull Player opener, @NotNull OfflinePlayer target)
      throws JusticeHandsException {
    Preconditions.checkNotNull(opener);
    Preconditions.checkNotNull(target);

    GuiInventory mainGuiInventory =
        guiInventoryController.getGuiInventory(SanctionManagerMainProvider.GUI_INVENTORY_ID);
    int nbLines = mainGuiInventory.nbLines();

    String id = target.getUniqueId().toString();
    String title = String.format("§7[§9%s§7]" + "§7> §cMenu principal", target.getName());

    SmartInventory menu =
        interactiveInventoryBuilder.build(sanctionManagerMainProvider, id, title, nbLines);
    menu.open(opener);
  }

  /**
   * Opens the specific view of the corresponding {@link SanctionCategory}.
   *
   * @param opener The {@link Player} opener of the menu.
   * @param target The target {@link OfflinePlayer} needed to open the menu.
   * @param sanctionCategory The {@link SanctionCategory} to use in the menu creation.
   * @throws JusticeHandsException if the recovering of the corresponding {@link GuiInventory} fail.
   */
  public void openCategoryMenu(
      @NotNull Player opener,
      @NotNull OfflinePlayer target,
      @NotNull SanctionCategory sanctionCategory)
      throws JusticeHandsException {
    Preconditions.checkNotNull(opener);
    Preconditions.checkNotNull(target);
    Preconditions.checkNotNull(sanctionCategory);

    GuiInventory mainGuiInventory =
        guiInventoryController.getGuiInventory(SanctionManagerCategoryProvider.GUI_INVENTORY_ID);
    int nbLines = mainGuiInventory.nbLines();

    String id = target.getUniqueId().toString();
    String title = String.format("%s§7> §c%s", ViewUtils.PREFIX_SM, sanctionCategory.name());

    SmartInventory menu =
        interactiveInventoryBuilder.build(sanctionManagerMainProvider, id, title, nbLines);
    menu.open(opener);
  }
}

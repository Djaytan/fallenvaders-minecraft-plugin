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

package fr.fallenvaders.minecraft.justicehands.view.gui.inventories;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionCategoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.fallenvaders.minecraft.justicehands.view.gui.SanctionManagerViewContainer;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PaginationItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PlayerHeadItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PredefinedSanctionItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.SanctionCategoryItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Sanction manager's category inventory provider class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionManagerCategoryProvider implements InventoryProvider {

  private final Logger logger;
  private final PaginationItemBuilder paginationItemBuilder;
  private final PlayerHeadItemBuilder playerHeadItemBuilder;
  private final PredefinedSanctionItemBuilder predefinedSanctionItemBuilder;
  private final SanctionCategoryController sanctionCategoryController;
  private final SanctionCategoryItemBuilder sanctionCategoryItemBuilder;
  private final SanctionManagerViewContainer sanctionManagerViewContainer;
  private final Server server;
  private final ViewUtils viewUtils;

  /**
   * Constructor.
   *
   * @param logger The {@link Logger}.
   * @param paginationItemBuilder The {@link PaginationItemBuilder}.
   * @param playerHeadItemBuilder The {@link PlayerHeadItemBuilder}.
   * @param predefinedSanctionItemBuilder The {@link PredefinedSanctionItemBuilder}.
   * @param sanctionCategoryController The {@link SanctionCategoryController}.
   * @param sanctionCategoryItemBuilder The {@link SanctionCategoryItemBuilder}.
   * @param sanctionManagerViewContainer The {@link SanctionManagerViewContainer}.
   * @param server The {@link Server}.
   * @param viewUtils The {@link ViewUtils}.
   */
  @Inject
  public SanctionManagerCategoryProvider(
      @NotNull Logger logger,
      @NotNull PaginationItemBuilder paginationItemBuilder,
      @NotNull PlayerHeadItemBuilder playerHeadItemBuilder,
      @NotNull PredefinedSanctionItemBuilder predefinedSanctionItemBuilder,
      @NotNull SanctionCategoryController sanctionCategoryController,
      @NotNull SanctionCategoryItemBuilder sanctionCategoryItemBuilder,
      @NotNull SanctionManagerViewContainer sanctionManagerViewContainer,
      @NotNull Server server,
      @NotNull ViewUtils viewUtils) {
    this.logger = logger;
    this.paginationItemBuilder = paginationItemBuilder;
    this.playerHeadItemBuilder = playerHeadItemBuilder;
    this.predefinedSanctionItemBuilder = predefinedSanctionItemBuilder;
    this.sanctionCategoryController = sanctionCategoryController;
    this.sanctionCategoryItemBuilder = sanctionCategoryItemBuilder;
    this.sanctionManagerViewContainer = sanctionManagerViewContainer;
    this.server = server;
    this.viewUtils = viewUtils;
  }

  @Override
  public void init(@NotNull Player opener, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();
    UUID uuidTarget = UUID.fromString(inventory.getId());
    OfflinePlayer target = server.getOfflinePlayer(uuidTarget);

    try {
      contents.set(0, 0, playerHeadItemBuilder.build(target));
      setSanctionCategories(opener, target, contents);
      contents.fillRow(1, ClickableItem.empty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)));
      setPredefinedSanctions(opener, target, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load the sanction manager category GUI inventory.", e);
      // TODO: feedback user
    }
  }

  @Override
  public void update(Player player, InventoryContents inventoryContents) {
    // Nothing to do
  }

  private void setSanctionCategories(
      @NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    Set<SanctionCategory> sanctionCategories = sanctionCategoryController.getCategories();

    int column = 9;
    if (sanctionCategories.size() > 7) {
      throw new JusticeHandsException("Too many sanction categories (max 7 allowed).");
    }
    for (SanctionCategory sanctionCategory : sanctionCategories) {
      ClickableItem clickableItem =
          sanctionCategoryItemBuilder.build(opener, target, sanctionCategory);
      contents.set(0, column--, clickableItem);
    }
  }

  private void setPredefinedSanctions(
      @NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    SanctionCategory currentSanctionCategory =
        sanctionManagerViewContainer.getCurrentSanctionCategory();
    int nbPredefinedSanctions = currentSanctionCategory.predefinedSanctions().size();
    if (nbPredefinedSanctions > 0) {
      List<ClickableItem> predefinedSanctions = new ArrayList<>(nbPredefinedSanctions);

      for (PredefinedSanction predefinedSanction : currentSanctionCategory.predefinedSanctions()) {
        predefinedSanctions.add(
            predefinedSanctionItemBuilder.build(opener, target, predefinedSanction));
      }

      Pagination pagination = contents.pagination();
      pagination.setItems(predefinedSanctions.toArray(new ClickableItem[0]));
      pagination.setItemsPerPage(27);
      pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 2, 0));

      if (!pagination.isFirst()) {
        contents.set(5, 0, paginationItemBuilder.buildPreviousPage(opener, contents));
      }
      if (!pagination.isLast()) {
        contents.set(5, 0, paginationItemBuilder.buildNextPage(opener, contents));
      }
    } else {
      contents.set(3, 4, ClickableItem.empty(viewUtils.emptySanctionCategory()));
    }
  }
}

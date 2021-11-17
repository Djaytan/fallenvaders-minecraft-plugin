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
import fr.fallenvaders.minecraft.justicehands.controller.GuiInventoryController;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionCategoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventory;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventoryItem;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventoryItemLocation;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PlayerHeadItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.SanctionCategoryItemBuilder;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Sanction manager's main inventory provider class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionManagerMainProvider implements InventoryProvider {

  public static final String GUI_INVENTORY_ID = "sm-main";

  private static final String PLAYER_HEAD_ITEM_ID = "player-head";

  private final GuiInventory guiInventory;
  private final Logger logger;
  private final PlayerHeadItemBuilder playerHeadItemBuilder;
  private final SanctionCategoryController sanctionCategoryController;
  private final SanctionCategoryItemBuilder sanctionCategoryItemBuilder;
  private final Server server;

  /**
   * Constructor.
   *
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param logger The {@link Logger}.
   * @param playerHeadItemBuilder The {@link PlayerHeadItemBuilder}.
   * @param sanctionCategoryController The {@link SanctionCategoryController}.
   * @param sanctionCategoryItemBuilder The {@link SanctionCategoryItemBuilder}.
   * @param server The {@link Server}.
   */
  @Inject
  public SanctionManagerMainProvider(
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull Logger logger,
      @NotNull PlayerHeadItemBuilder playerHeadItemBuilder,
      @NotNull SanctionCategoryController sanctionCategoryController,
      @NotNull SanctionCategoryItemBuilder sanctionCategoryItemBuilder,
      @NotNull Server server)
      throws JusticeHandsException {
    this.guiInventory = guiInventoryController.getGuiInventory(GUI_INVENTORY_ID);
    this.logger = logger;
    this.playerHeadItemBuilder = playerHeadItemBuilder;
    this.sanctionCategoryController = sanctionCategoryController;
    this.sanctionCategoryItemBuilder = sanctionCategoryItemBuilder;
    this.server = server;
  }

  @Override
  public void init(@NotNull Player opener, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();
    UUID uuidTarget = UUID.fromString(inventory.getId());
    OfflinePlayer target = server.getOfflinePlayer(uuidTarget);

    try {
      setPlayerHead(target, contents);
      setCategories(opener, target, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load sanction manager main GUI inventory.", e);
      // TODO: notification to player
    }
  }

  @Override
  public void update(@NotNull Player opener, @NotNull InventoryContents contents) {
    // Nothing to do
  }

  private void setPlayerHead(@NotNull OfflinePlayer target, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    GuiInventoryItem item =
        guiInventory
            .getItem(PLAYER_HEAD_ITEM_ID)
            .orElseThrow(() -> new JusticeHandsException("Failed to create the GUI inventory."));
    GuiInventoryItemLocation location = Objects.requireNonNull(item.location());
    contents.set(location.line(), location.column(), playerHeadItemBuilder.build(target));
  }

  private void setCategories(
      @NotNull Player moderator, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    int column = 0;
    for (SanctionCategory sanctionCategory : sanctionCategoryController.getCategories()) {
      contents.set(
          0, column++, sanctionCategoryItemBuilder.build(moderator, target, sanctionCategory));
    }
  }
}

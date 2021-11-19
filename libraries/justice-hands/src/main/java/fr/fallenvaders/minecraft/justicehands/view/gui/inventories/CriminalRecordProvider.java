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

import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionController;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.fallenvaders.minecraft.justicehands.view.gui.CriminalRecordViewContainer;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PlayerHeadItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PlayerSanctionsStatisticsItemBuilder;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.SanctionItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Criminal record inventory provider class.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class CriminalRecordProvider implements InventoryProvider {

  public static final String GUI_INVENTORY_ID = "sm-main";

  private final ComponentHelper componentHelper;
  private final CriminalRecordViewContainer criminalRecordViewContainer;
  private final Logger logger;
  private final PlayerHeadItemBuilder playerHeadItemBuilder;
  private final PlayerSanctionsStatisticsItemBuilder playerSanctionsStatisticsItemBuilder;
  private final SanctionController sanctionController;
  private final SanctionItemBuilder sanctionItemBuilder;
  private final Server server;
  private final ViewUtils viewUtils;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param criminalRecordViewContainer The {@link CriminalRecordViewContainer}.
   * @param logger The {@link Logger}.
   * @param playerHeadItemBuilder The {@link PlayerHeadItemBuilder}.
   * @param playerSanctionsStatisticsItemBuilder The {@link PlayerSanctionsStatisticsItemBuilder}.
   * @param sanctionController The {@link SanctionController}.
   * @param sanctionItemBuilder The {@link SanctionItemBuilder}.
   * @param server The {@link Server}.
   * @param viewUtils The {@link ViewUtils}.
   */
  @Inject
  public CriminalRecordProvider(
      @NotNull ComponentHelper componentHelper,
      @NotNull CriminalRecordViewContainer criminalRecordViewContainer,
      @NotNull Logger logger,
      @NotNull PlayerHeadItemBuilder playerHeadItemBuilder,
      @NotNull PlayerSanctionsStatisticsItemBuilder playerSanctionsStatisticsItemBuilder,
      @NotNull SanctionController sanctionController,
      @NotNull SanctionItemBuilder sanctionItemBuilder,
      @NotNull Server server,
      @NotNull ViewUtils viewUtils) {
    this.componentHelper = componentHelper;
    this.criminalRecordViewContainer = criminalRecordViewContainer;
    this.logger = logger;
    this.playerHeadItemBuilder = playerHeadItemBuilder;
    this.playerSanctionsStatisticsItemBuilder = playerSanctionsStatisticsItemBuilder;
    this.sanctionController = sanctionController;
    this.sanctionItemBuilder = sanctionItemBuilder;
    this.server = server;
    this.viewUtils = viewUtils;
  }

  @Override
  public void init(@NotNull Player opener, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();
    UUID uuidTarget = UUID.fromString(inventory.getId());
    OfflinePlayer target = server.getOfflinePlayer(uuidTarget);

    contents.fillRow(1, ClickableItem.empty(new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)));
    contents.fillRow(5, ClickableItem.empty(new ItemStack(Material.AIR)));
    contents.set(0, 0, playerHeadItemBuilder.build(target));

    try {
      setSanctions(opener, target, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load sanctions in criminal record GUI inventory.", e);
      // TODO: feedback to player
      // TODO: better error management
    }

    try {
      setCounters(opener, target, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load counters in criminal record GUI inventory.", e);
      // TODO: feedback to player
      // TODO: better error management
    }

    try {
      setStatistics(opener, target, contents);
    } catch (JusticeHandsException e) {
      logger.error("Failed to load statistics in criminal record GUI inventory.", e);
      // TODO: feedback to player
      // TODO: better error management
    }
  }

  @Override
  public void update(@NotNull Player opener, @NotNull InventoryContents contents) {
    // Nothing to do
  }

  private void setSanctions(
      @NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
      throws JusticeHandsException {
    Set<Sanction> sanctions = sanctionController.getPlayerSanctions(target);
    SanctionType filter = criminalRecordViewContainer.getFilter();
    Set<Sanction> visibleSanctions =
        sanctions.stream()
            .filter(sanction -> filter == null || sanction.type().equals(filter))
            .collect(Collectors.toSet());

    if (!visibleSanctions.isEmpty()) {
      List<ClickableItem> clickableItems = new ArrayList<>(visibleSanctions.size());

      for (Sanction sanction : visibleSanctions) {
        clickableItems.add(sanctionItemBuilder.build(sanction));
      }

      viewUtils.setPagination(opener, contents, clickableItems, 27);
    } else {
      contents.set(3, 4, ClickableItem.empty(viewUtils.emptyCriminalRecord(filter)));
    }
  }

  ///// COUNTERS

  private void setCounters(@NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    setSanctionCounter(opener, target, contents);
    setBanCounter(opener, target, contents);
    setMuteCounter(opener, target, contents);
    setKickCounter(opener, target, contents);
  }

  private void setSanctionCounter(@NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    setCounter(opener, target, contents, null, 8);
  }

  private void setBanCounter(@NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    setCounter(opener, target, contents, SanctionType.BAN, 7);
  }

  private void setMuteCounter(@NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    setCounter(opener, target, contents, SanctionType.MUTE, 6);
  }

  private void setKickCounter(
      @NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    setCounter(opener, target, contents, SanctionType.KICK, 5);
  }

  private void setCounter(
      @NotNull Player opener,
      @NotNull OfflinePlayer target,
      @NotNull InventoryContents contents,
      @Nullable SanctionType sanctionType,
      int column) throws JusticeHandsException {
    int count = (int) sanctionController.getPlayerSanctions(target).stream().filter(sanction -> sanctionType == null || Objects.equals(sanction.type(), sanctionType)).count();
    Pagination pagination = contents.pagination();
    SanctionType filter = criminalRecordViewContainer.getFilter();

    ItemStack counterItem =
        new ItemStack(getSanctionBanner(sanctionType), viewUtils.normalizeItemAmount(count));
    ItemMeta counterItemMeta = counterItem.getItemMeta();
    counterItemMeta.displayName(
        componentHelper.getComponent(
            String.format(
                "§2Nombre total de %ss du joueur: §a%d",
              (sanctionType != null ? sanctionType.name().toLowerCase() : "sanction"), count)));
    counterItem.setItemMeta(counterItemMeta);
    contents.set(
        0,
        column,
        ClickableItem.of(
            counterItem,
            e -> {
              if (!Objects.equals(filter, sanctionType)) {
                criminalRecordViewContainer.setFilter(sanctionType);
                pagination.page(0);
                init(opener, contents);
              }
            }));
  }

  private @NotNull Material getSanctionBanner(@Nullable SanctionType sanctionType) {
    if (sanctionType == null) {
      return Material.BLACK_BANNER;
    }
    return switch (sanctionType) {
      case BAN -> Material.ORANGE_BANNER;
      case MUTE -> Material.BLUE_BANNER;
      case KICK -> Material.GREEN_BANNER;
    };
  }

  ///// STATISTICS

  public void setStatistics(@NotNull Player opener, @NotNull OfflinePlayer target, @NotNull InventoryContents contents)
    throws JusticeHandsException {
    contents.set(
      0,
      2,
      playerSanctionsStatisticsItemBuilder.build(opener, target));
  }
}

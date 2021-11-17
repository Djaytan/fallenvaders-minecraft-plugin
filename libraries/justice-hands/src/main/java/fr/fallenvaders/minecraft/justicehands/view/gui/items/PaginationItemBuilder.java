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

package fr.fallenvaders.minecraft.justicehands.view.gui.items;

import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The builder class of pagination items.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class PaginationItemBuilder {

  private static final Material CHANGE_PAGE_ITEM = Material.ARROW;

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   */
  @Inject
  public PaginationItemBuilder(@NotNull ComponentHelper componentHelper) {
    this.componentHelper = componentHelper;
  }

  /**
   * Builds the previous page {@link ClickableItem}.
   *
   * @param opener The opener {@link Player} of the menu.
   * @param contents The {@link InventoryContents} of the menu.
   * @return The previous page {@link ClickableItem}.
   */
  public @NotNull ClickableItem buildPreviousPage(
      @NotNull Player opener, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();
    Pagination pagination = contents.pagination();

    ItemStack previousPageItem = new ItemStack(CHANGE_PAGE_ITEM);
    ItemMeta meta = previousPageItem.getItemMeta();
    meta.displayName(componentHelper.getComponent("§7Page précédente"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("   §cCLIC DROIT"));
    lore.add(componentHelper.getComponent("§f<<Page précédente"));
    meta.lore(lore);
    previousPageItem.setItemMeta(meta);
    return ClickableItem.of(
        previousPageItem, e -> inventory.open(opener, pagination.previous().getPage()));
  }

  /**
   * Builds the next page {@link ClickableItem}.
   *
   * @param opener The opener {@link Player} of the menu.
   * @param contents The {@link InventoryContents} of the menu.
   * @return The next page {@link ClickableItem}.
   */
  public @NotNull ClickableItem buildNextPage(
      @NotNull Player opener, @NotNull InventoryContents contents) {
    SmartInventory inventory = contents.inventory();
    Pagination pagination = contents.pagination();

    ItemStack nextPageItem = new ItemStack(CHANGE_PAGE_ITEM);
    ItemMeta meta = nextPageItem.getItemMeta();
    meta.displayName(componentHelper.getComponent("§7Page suivante"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("   §cCLIC DROIT"));
    lore.add(componentHelper.getComponent("§f<<Page suivante"));
    meta.lore(lore);
    nextPageItem.setItemMeta(meta);
    return ClickableItem.of(nextPageItem, e -> inventory.open(opener, pagination.next().getPage()));
  }
}

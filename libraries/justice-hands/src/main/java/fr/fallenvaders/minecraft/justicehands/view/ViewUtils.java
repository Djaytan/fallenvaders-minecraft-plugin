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

package fr.fallenvaders.minecraft.justicehands.view;

import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.view.gui.items.PaginationItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
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
import org.jetbrains.annotations.Nullable;

/**
 * Utils class about components creations (inventory buttons, stringify some data like boolean,
 * ...).
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class ViewUtils {

  /* Prefixes of the module */
  public static final String PREFIX_CR = "§7[§6CriminalRecords§7] §r";
  public static final String PREFIX_MT = "§7[§cModeratorTools§7] §r";
  public static final String PREFIX_SM = "§7[§9SanctionManager§7] §r";
  public static final String PREFIX_KK = "§7[§4KeysKeeper§7] §r";

  private final ComponentHelper componentHelper;
  private final PaginationItemBuilder paginationItemBuilder;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param paginationItemBuilder The {@link PaginationItemBuilder}.
   */
  @Inject
  public ViewUtils(
      @NotNull ComponentHelper componentHelper,
      @NotNull PaginationItemBuilder paginationItemBuilder) {
    this.componentHelper = componentHelper;
    this.paginationItemBuilder = paginationItemBuilder;
  }

  /**
   * Provides an item stack which indicate that the criminal record is empty (i.e. no sanctions
   * recorded until the call of this method).
   *
   * <p>If the sanction type is specified, it only indicates that no sanctions of this type have
   * been recorded.
   *
   * @param sanctionType (optional) The type of sanction sought.
   * @return The item indicator of an empty criminal record.
   */
  public @NotNull ItemStack emptyCriminalRecord(@Nullable SanctionType sanctionType) {
    ItemStack item = new ItemStack(Material.BARRIER);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(componentHelper.getComponent("§cCasier judiciaire vide"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("§7Le casier judiciaire du joueur"));
    if (sanctionType == null) {
      lore.add(componentHelper.getComponent("§7est vide, aucune sanction."));
    } else {
      lore.add(
          componentHelper.getComponent(
              String.format("§7est vide, aucun %s.", sanctionType.name().toLowerCase())));
    }
    meta.lore(lore);
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Provides an item stack which indicate that the sanction category is empty.
   *
   * @return The item indicator of an empty sanction category.
   */
  public @NotNull ItemStack emptySanctionCategory() {
    ItemStack item = new ItemStack(Material.BARRIER);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(componentHelper.getComponent("§cCatégorie vide"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("§7La catégorie ne contient"));
    lore.add(componentHelper.getComponent("§7aucune sanction."));
    meta.lore(lore);
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Stringify a boolean value.
   *
   * @param b The boolean value.
   * @return The string boolean value.
   */
  public @NotNull String stringify(boolean b) {
    return b ? "§aOui" : "§cNon";
  }

  /**
   * Normalizes the amount of item stack by ensuring the retuning value is between 1 and 64.
   *
   * @param amount The initial amount to normalize.
   * @return The normalizes amount between 1 and 64 for item stack.
   */
  public int normalizeItemAmount(int amount) {
    return Math.min(Math.max(amount, 1), 64);
  }

  /**
   * Sets pagination in the menu.
   *
   * @param opener The player opener of the menu.
   * @param contents The contents of the menu.
   * @param clickableItems The clickable items to paginate in the menu.
   * @param itemPerPage The number of items per page.
   */
  public void setPagination(
      @NotNull Player opener,
      @NotNull InventoryContents contents,
      @NotNull List<ClickableItem> clickableItems,
      int itemPerPage) {
    Pagination pagination = contents.pagination();
    pagination.setItems(clickableItems.toArray(new ClickableItem[0]));
    pagination.setItemsPerPage(itemPerPage);
    pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 2, 0));

    if (!pagination.isFirst()) {
      contents.set(5, 0, paginationItemBuilder.buildPreviousPage(opener, contents));
    }
    if (!pagination.isLast()) {
      contents.set(5, 0, paginationItemBuilder.buildNextPage(opener, contents));
    }
  }
}

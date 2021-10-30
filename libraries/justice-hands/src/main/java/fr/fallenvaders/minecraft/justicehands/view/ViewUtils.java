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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Utils class about components creations (inventory buttons, stringify some data like boolean,
 * ...).
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class ViewUtils {

  /** Prefixes of the module */
  public static final String PREFIX_CR = "§7[§6CriminalRecords§7] §r";
  public static final String PREFIX_MT = "§7[§cModeratorTools§7] §r";
  public static final String PREFIX_SM = "§7[§9SanctionManager§7] §r";
  public static final String PREFIX_KK = "§7[§4KeysKeeper§7] §r";

  private static final Material CHANGE_PAGE_ITEM = Material.ARROW;

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The component helper.
   */
  @Inject
  public ViewUtils(@NotNull ComponentHelper componentHelper) {
    this.componentHelper= componentHelper;
  }

  /**
   * Provides the previous page button for GUI.
   *
   * @return The previous page button.
   */
  public @NotNull ItemStack previousPage() {
    ItemStack item = new ItemStack(CHANGE_PAGE_ITEM);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(componentHelper.getComponent("§7Page précédente"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("   §cCLIC DROIT"));
    lore.add(componentHelper.getComponent("§f<<Page précédente"));
    meta.lore(lore);
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Provides the next page button for GUI.
   *
   * @return The next page button.
   */
  public ItemStack nextPage() {
    ItemStack item = new ItemStack(CHANGE_PAGE_ITEM);
    ItemMeta meta = item.getItemMeta();
    meta.displayName(componentHelper.getComponent("§7Page suivante"));
    List<Component> lore = new ArrayList<>(3);
    lore.add(componentHelper.getComponent(""));
    lore.add(componentHelper.getComponent("   §cCLIC DROIT"));
    lore.add(componentHelper.getComponent("§f<<Page suivante"));
    meta.lore(lore);
    item.setItemMeta(meta);
    return item;
  }
}

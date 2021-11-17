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

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.GuiInventoryController;
import fr.fallenvaders.minecraft.justicehands.model.entities.GuiInventoryItem;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionCategory;
import fr.fallenvaders.minecraft.justicehands.view.gui.SanctionManagerView;
import fr.fallenvaders.minecraft.justicehands.view.gui.SanctionManagerViewContainer;
import fr.minuskube.inv.ClickableItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * The builder class of {@link SanctionCategory} items.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class SanctionCategoryItemBuilder {

  private static final String CATEGORY_ITEM_ID = "category";

  private final ComponentHelper componentHelper;
  private final GuiInventoryController guiInventoryController;
  private final Logger logger;
  private final SanctionManagerView sanctionManagerView;
  private final SanctionManagerViewContainer sanctionManagerViewContainer;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param guiInventoryController The {@link GuiInventoryController}.
   * @param logger The {@link Logger}.
   * @param sanctionManagerView The {@link SanctionManagerView}.
   * @param sanctionManagerViewContainer The {@link SanctionManagerViewContainer}.
   */
  @Inject
  public SanctionCategoryItemBuilder(
      @NotNull ComponentHelper componentHelper,
      @NotNull GuiInventoryController guiInventoryController,
      @NotNull Logger logger,
      @NotNull SanctionManagerView sanctionManagerView,
      @NotNull SanctionManagerViewContainer sanctionManagerViewContainer) {
    this.componentHelper = componentHelper;
    this.guiInventoryController = guiInventoryController;
    this.logger = logger;
    this.sanctionManagerView = sanctionManagerView;
    this.sanctionManagerViewContainer = sanctionManagerViewContainer;
  }

  /**
   * Builds the {@link ClickableItem} of the specified category of sanctions.
   *
   * @param sanctionCategory The category of sanctions used to build the clickable item.
   * @return The clickable item of the specified category of sanctions.
   * @throws JusticeHandsException if something went wrong when providing item.
   */
  public @NotNull ClickableItem build(
      @NotNull Player opener,
      @NotNull OfflinePlayer target,
      @NotNull SanctionCategory sanctionCategory)
      throws JusticeHandsException {
    Preconditions.checkNotNull(opener);
    Preconditions.checkNotNull(target);
    Preconditions.checkNotNull(sanctionCategory);

    GuiInventoryItem categoryGuiItem = guiInventoryController.getGenericGuiItem(CATEGORY_ITEM_ID);
    ItemStack categoryItem = Objects.requireNonNull(categoryGuiItem.item());

    ItemMeta meta = categoryItem.getItemMeta();
    meta.displayName(componentHelper.getComponent("§4Catégorie: §c" + sanctionCategory.name()));

    List<String> strLore = new ArrayList<>(2);
    strLore.add("");
    strLore.add(sanctionCategory.description());
    List<Component> lore =
        strLore.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    meta.lore(lore);

    if (sanctionManagerViewContainer
        .getCurrentSanctionCategory()
        .name()
        .equals(sanctionCategory.name())) {
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    categoryItem.setItemMeta(meta);
    return ClickableItem.of(
        categoryItem,
        event -> {
          if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
            try {
              sanctionManagerViewContainer.setCurrentSanctionCategory(sanctionCategory);
              sanctionManagerView.openCategoryMenu(opener, target, sanctionCategory);
            } catch (JusticeHandsException e) {
              logger.error("Failed to change the current sanction category.", e);
              // TODO: feedback to player
            }
          }
        });
  }
}

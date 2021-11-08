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
import fr.fallenvaders.minecraft.justicehands.model.entities.PredefinedSanction;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.minuskube.inv.ClickableItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The builder class of {@link PredefinedSanction} items.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class PredefinedSanctionItemBuilder {

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   */
  @Inject
  public PredefinedSanctionItemBuilder(@NotNull ComponentHelper componentHelper) {
    this.componentHelper = componentHelper;
  }

  /**
   * Builds the {@link ClickableItem} of the specified {@link PredefinedSanction}.
   *
   * @param opener The opener {@link Player} of the menu.
   * @param target The target {@link OfflinePlayer} of the {@link PredefinedSanction}.
   * @param predefinedSanction The {@link PredefinedSanction} of the target {@link OfflinePlayer}.
   * @return The {@link ClickableItem} of the specified {@link PredefinedSanction}.
   */
  public @NotNull ClickableItem build(
      @NotNull Player opener,
      @NotNull OfflinePlayer target,
      @NotNull PredefinedSanction predefinedSanction) {
    ItemStack predefinedSanctionItem = new ItemStack(Material.GLOBE_BANNER_PATTERN, 1);

    ItemMeta meta = predefinedSanctionItem.getItemMeta();
    meta.displayName(componentHelper.getComponent("§cSanction: §7" + predefinedSanction.name()));

    List<String> strLore = new ArrayList<>(4);
    strLore.add("");
    strLore.add("§7Description: §8" + predefinedSanction.description());
    strLore.add("§7Points de sanction: §7" + predefinedSanction.points());
    strLore.add("");
    List<Component> lore =
        strLore.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    predefinedSanctionItem.lore(lore);

    predefinedSanctionItem.setItemMeta(meta);

    return ClickableItem.of(
        predefinedSanctionItem,
        e -> {
          if (e.isLeftClick()) {
            if (opener.hasPermission(
                "justicehands.sm." + predefinedSanction.type().name().toLowerCase())) {
              // TODO: define behavior
              // SanctionsAlgo.generateSanction(sanction, opener, target);
              // KeysKeeperBot.kickPlayer(
              //    player, JusticeHandsModule.getSqlSM().getLastSanction(player));
              opener.closeInventory();
            } else {
              Component message =
                  componentHelper.getComponent(
                      String.format(
                          "%s§cTu n'as pas la permission d'attribuer un %s%s",
                          ViewUtils.PREFIX_SM,
                          predefinedSanction.type().getVisualColor(),
                          predefinedSanction.type().getVisualName()));
              opener.sendMessage(message);
            }
          }
        });
  }
}

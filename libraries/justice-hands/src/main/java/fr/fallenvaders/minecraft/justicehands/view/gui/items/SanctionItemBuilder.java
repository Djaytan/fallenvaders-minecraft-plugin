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
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.minuskube.inv.ClickableItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The builder class of {@link Sanction} items.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class SanctionItemBuilder {

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   */
  @Inject
  public SanctionItemBuilder(@NotNull ComponentHelper componentHelper) {
    this.componentHelper = componentHelper;
  }

  /**
   * Builds the {@link ClickableItem} of the specified sanction.
   *
   * @param sanction The sanction used to build the clickable item.
   * @return The clickable item of the specified sanction.
   */
  public @NotNull ClickableItem build(@NotNull Sanction sanction) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    ItemStack sanctionItem = new ItemStack(Material.CLAY);
    sanctionItem.setType(sanction.type().getClayColor());

    ItemMeta meta = sanctionItem.getItemMeta();
    meta.displayName(
        componentHelper.getComponent(
            sanction.type().getVisualColor()
                + sanction.type().getVisualName()
                + ChatColor.GRAY
                + sdf.format(sanction.beginningDate())));

    List<String> strLore = new ArrayList<>(7);
    strLore.add("§fID: " + ChatColor.GRAY + sanction.id());
    strLore.add("§fNom: " + ChatColor.GRAY + sanction.name());
    strLore.add("§fRaison: " + ChatColor.GRAY + sanction.reason());
    strLore.add("§fPoints: " + ChatColor.GRAY + sanction.points());
    strLore.add("§fJoueur sanctionné: " + ChatColor.GRAY + sanction.inculpatedPlayer().getName());
    strLore.add(
        "§fModérateur: "
            + ChatColor.GRAY
            + (sanction.authorPlayer() != null ? sanction.authorPlayer().getName() : "@Console"));

    if (sanction.endingDate() != null) {
      final long currentTime = System.currentTimeMillis();
      if (currentTime >= sanction.endingDate().getTime()) {
        strLore.add(
            ChatColor.GREEN
                + "(Terminée) Date d'expiration: §7"
                + sdf.format(sanction.endingDate()));
      } else {
        strLore.add(
            ChatColor.RED
                + "(En cours) Date d'expiriation: §7"
                + sdf.format(sanction.endingDate()));
      }
    }

    List<Component> lore =
        strLore.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    meta.lore(lore);
    sanctionItem.setItemMeta(meta);
    return ClickableItem.empty(sanctionItem);
  }
}

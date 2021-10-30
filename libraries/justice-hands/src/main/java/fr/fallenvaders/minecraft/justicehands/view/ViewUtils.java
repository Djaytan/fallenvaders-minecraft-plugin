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
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utils class about components creations (inventory buttons, stringify some data like boolean,
 * ...).
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class ViewUtils {

  /* Prefixes of the module */
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
    this.componentHelper = componentHelper;
  }

  /**
   * Provides the target {@link Player}'s head.
   *
   * @param target The target player.
   * @param totalSanctionsPoints The total number of sanctions points of the player.
   * @return The head of the targeted {@link Player}.
   */
  public @NotNull ItemStack getHead(@NotNull Player target, int totalSanctionsPoints) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String targetStatus = stringify(target.isOnline());
    String firstPlayed = sdf.format(new Date(target.getFirstPlayed()));
    String lastPlayed =
        target.isOnline() ? sdf.format(new Date(target.getLastLogin())) : "connecté";

    ItemStack targetHead = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta headMeta = (SkullMeta) targetHead.getItemMeta();
    headMeta.setOwningPlayer(target);
    headMeta.displayName(componentHelper.getComponent("§cInformations sur le joueur:"));

    List<String> loreStr = new ArrayList<>(7);
    loreStr.add("§7Pseudo: §f" + target.getName());
    loreStr.add("§7Grade: §fTODO"); // TODO: complete
    loreStr.add("§7Points de sanctions: §6" + totalSanctionsPoints);
    loreStr.add("§7AchievementPoints: §fTODO"); // TODO: complete
    loreStr.add("§7Connecté: " + targetStatus);
    loreStr.add("§7Première connexion: §f" + firstPlayed);
    loreStr.add("§7Dernière déconnexion : §f" + lastPlayed);

    List<Component> lore =
        loreStr.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    headMeta.lore(lore);
    targetHead.setItemMeta(headMeta);

    return targetHead;
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
  public @NotNull ItemStack nextPage() {
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

  /**
   * Provides an {@link ItemStack} which indicate that the criminal record is empty (i.e. no
   * sanctions recorded until the call of this method).
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
   * Provides an {@link ItemStack} which indicate that the sanction category is empty.
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
}

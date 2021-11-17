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
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.minuskube.inv.ClickableItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The builder class of player head items.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class PlayerHeadItemBuilder {

  private final ComponentHelper componentHelper;
  private final ViewUtils viewUtils;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param viewUtils The {@link ViewUtils}.
   */
  @Inject
  public PlayerHeadItemBuilder(
      @NotNull ComponentHelper componentHelper, @NotNull ViewUtils viewUtils) {
    this.componentHelper = componentHelper;
    this.viewUtils = viewUtils;
  }

  /**
   * Builds the player's head.
   *
   * @param player The player used to create the head.
   * @return The item head of the specified player.
   */
  public @NotNull ClickableItem build(@NotNull OfflinePlayer player) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String targetStatus = viewUtils.stringify(player.isOnline());
    String firstPlayed = sdf.format(new Date(player.getFirstPlayed()));
    String lastPlayed =
        player.isOnline() ? sdf.format(new Date(player.getLastLogin())) : "connecté";

    ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta headMeta = (SkullMeta) playerHead.getItemMeta();
    headMeta.setOwningPlayer(player);
    headMeta.displayName(componentHelper.getComponent("§cInformations sur le joueur:"));

    List<String> loreStr = new ArrayList<>(7);
    loreStr.add("§7Pseudo: §f" + player.getName());
    loreStr.add("§7Grade: §fTODO"); // TODO: complete
    loreStr.add("§7Points de sanctions: §6TODO"); // TODO: complete
    loreStr.add("§7AchievementPoints: §fTODO"); // TODO: complete
    loreStr.add("§7Connecté: " + targetStatus);
    loreStr.add("§7Première connexion: §f" + firstPlayed);
    loreStr.add("§7Dernière déconnexion : §f" + lastPlayed);

    List<Component> lore =
        loreStr.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    headMeta.lore(lore);
    playerHead.setItemMeta(headMeta);

    return ClickableItem.empty(playerHead);
  }
}

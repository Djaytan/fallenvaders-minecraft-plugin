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
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.builders.PlayerSanctionsStatisticsBuilder;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities.PlayerSanctionTypeStatistics;
import fr.fallenvaders.minecraft.justicehands.view.viewmodel.entities.PlayerSanctionsStatistics;
import fr.minuskube.inv.ClickableItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The builder class of player sanctions statistics.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class PlayerSanctionsStatisticsItemBuilder {

  private static final String NON_EXISTENT = "§7§oInexistant";

  private final ComponentHelper componentHelper;
  private final PlayerSanctionsStatisticsBuilder playerSanctionsStatisticsBuilder;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   * @param playerSanctionsStatisticsBuilder The {@link PlayerSanctionsStatisticsBuilder}.
   */
  @Inject
  public PlayerSanctionsStatisticsItemBuilder(
      @NotNull ComponentHelper componentHelper,
      @NotNull PlayerSanctionsStatisticsBuilder playerSanctionsStatisticsBuilder) {
    this.componentHelper = componentHelper;
    this.playerSanctionsStatisticsBuilder = playerSanctionsStatisticsBuilder;
  }

  /**
   * Builds the statistics clickable item about target's recorded sanctions.
   *
   * @param opener The opener player of the menu.
   * @param target The target player of statistics.
   * @return The statistics clickable item about target's recorded sanctions.
   */
  public @NotNull ClickableItem build(@NotNull Player opener, @NotNull OfflinePlayer target)
      throws JusticeHandsException {
    ItemStack stats = new ItemStack(Material.KNOWLEDGE_BOOK);
    ItemMeta infos = stats.getItemMeta();
    infos.displayName(
        componentHelper.getComponent(
            "§c§lStatistiques du joueur §7" + target.getName() + " §c§l:"));
    infos.lore(getLore(target));
    stats.setItemMeta(infos);
    return ClickableItem.of(
        stats,
        e -> {
          if (e.getCurrentItem() == null) {
            return;
          }
          if (e.isRightClick()) {
            opener.sendMessage("------------------------------------------");
            opener.sendMessage(e.getCurrentItem().getItemMeta().displayName());
            List<Component> lore = e.getCurrentItem().getItemMeta().lore();
            for (int i = 0; i < 3; i++) {
              lore.remove(lore.size() - 1);
            }
            for (Component line : lore) {
              opener.sendMessage(" " + line);
            }
            opener.sendMessage("------------------------------------------");
          }
        });
  }

  private @NotNull List<Component> getLore(@NotNull OfflinePlayer target)
      throws JusticeHandsException {
    PlayerSanctionsStatistics stats = playerSanctionsStatisticsBuilder.build(target);

    PlayerSanctionTypeStatistics banStats = stats.getSanctionTypeStatistics(SanctionType.BAN);
    PlayerSanctionTypeStatistics muteStats = stats.getSanctionTypeStatistics(SanctionType.MUTE);
    PlayerSanctionTypeStatistics kickStats = stats.getSanctionTypeStatistics(SanctionType.KICK);

    String prcBan = getPercentage(stats.getNbSanctions(), banStats.getNbSanctions());
    String prcMute = getPercentage(stats.getNbSanctions(), muteStats.getNbSanctions());
    String prcKick = getPercentage(stats.getNbSanctions(), kickStats.getNbSanctions());

    List<String> strLore = new ArrayList<>();
    strLore.add("§6Nombre de bannissements: §e" + banStats.getNbSanctions() + " (" + prcBan + "%)");
    strLore.add("§3Nombre de mutes: §b" + muteStats.getNbSanctions() + " (" + prcMute + "%)");
    strLore.add("§2Nombre de kicks: §a" + kickStats.getNbSanctions() + " (" + prcKick + "%)");
    strLore.add("");
    strLore.add("§fDernière sanction:");
    strLore.add("§7  - ID: §8" + getId(stats.getLastSanction()));
    strLore.add("§7  - Date: §8" + getDate(stats.getLastSanction()));
    strLore.add("§7  - Type: " + getType(stats.getLastSanction()));
    strLore.add("");
    strLore.add(
        "§6Premier bannissement: §7"
            + getDate(banStats.getFirstSanction())
            + " §8§o("
            + getId(banStats.getFirstSanction())
            + ")");
    strLore.add(
        "§3Premier mute: §7"
            + getDate(muteStats.getFirstSanction())
            + " §8§o("
            + getId(muteStats.getFirstSanction())
            + ")");
    strLore.add(
        "§2Premier kick: §7"
            + getDate(kickStats.getFirstSanction())
            + " §8§o("
            + getId(kickStats.getFirstSanction())
            + ")");
    strLore.add("");
    strLore.add(
        "§6Dernier bannissement: §7"
            + getDate(banStats.getLastSanction())
            + " §8§o("
            + getId(banStats.getLastSanction())
            + ")");
    strLore.add(
        "§3Dernier mute: §7"
            + getDate(muteStats.getLastSanction())
            + " §8§o("
            + getId(muteStats.getLastSanction())
            + ")");
    strLore.add(
        "§2Dernier kick: §7"
            + getDate(kickStats.getLastSanction())
            + " §8§o("
            + getId(kickStats.getLastSanction())
            + ")");
    strLore.add("");
    strLore.add("§fNombre actuel de points: §6" + stats.getCurrentPointsAmount());
    strLore.add("§fNombre total de points obtenus: §e" + stats.getTotalPointsAmount());
    strLore.add("");
    strLore.add("§4CLIC DROIT §cpour imprimer dans le tchat.");
    strLore.add("§7(!) §oRécupération possible via les logs.");

    return strLore.stream().map(componentHelper::getComponent).toList();
  }

  private @NotNull String getPercentage(int nbSanctions, long nbSanctionsType) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(nbSanctionsType * 100.0D / nbSanctions);
  }

  private @NotNull String getId(@Nullable Sanction sanction) {
    return sanction != null ? Integer.toString(sanction.id()) : NON_EXISTENT;
  }

  private @NotNull String getDate(@Nullable Sanction sanction) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    return sanction != null ? sdf.format(sanction.beginningDate()) : NON_EXISTENT;
  }

  private @NotNull String getType(@Nullable Sanction sanction) {
    return sanction != null
        ? sanction.type().getVisualColor() + sanction.type().getVisualName()
        : NON_EXISTENT;
  }
}

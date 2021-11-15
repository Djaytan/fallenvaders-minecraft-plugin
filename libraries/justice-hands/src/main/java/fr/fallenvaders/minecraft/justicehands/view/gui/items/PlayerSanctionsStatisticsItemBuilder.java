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
import fr.fallenvaders.minecraft.justicehands.JusticeHandsModule;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.minuskube.inv.ClickableItem;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * The builder class of player {@link Sanction}s statistics.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class PlayerSanctionsStatisticsItemBuilder {

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The {@link ComponentHelper}.
   */
  public PlayerSanctionsStatisticsItemBuilder(@NotNull ComponentHelper componentHelper) {
    this.componentHelper = componentHelper;
  }

  public @NotNull ClickableItem build(@NotNull OfflinePlayer target) {
    List<CJSanction> activeSanctionList = new ArrayList<>();
    List<CJSanction> bansList = new ArrayList<>();
    List<CJSanction> mutesList = new ArrayList<>();
    List<CJSanction> kicksList = new ArrayList<>();

    // Récupération du nombre de sanction de chaque type en utilisant les listes
    double nbrSanction = activeSanctionList.size();
    double nbrBans = bansList.size();
    double nbrMutes = mutesList.size();
    double nbrKicks = kicksList.size();

    // Ajout d'un pourcentage selon les sanctions
    DecimalFormat df = new DecimalFormat("#.##");
    String prcBan;
    if (nbrBans > 0) prcBan = df.format(nbrBans * 100 / nbrSanction);
    else prcBan = "0";

    String prcMute;
    if (nbrMutes > 0) prcMute = df.format(nbrMutes * 100 / nbrSanction);
    else prcMute = "0";

    String prcKick;
    if (nbrKicks > 0) prcKick = df.format(nbrKicks * 100 / nbrSanction);
    else prcKick = "0";

    // Format de date et heure
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // Derniere sanction (Valeurs par défaut)
    String lastSanctionID = "§7§oInexistant";
    String lastSanctionDate = "§7§oInexistante";
    String lastSanctionType = "§7§oInexistant";

    if (playerAllSanctionList.size() > 0) {
      CJSanction lastSanction = playerAllSanctionList.get(0);
      lastSanctionID = lastSanction.getID();
      lastSanctionDate = sdf.format(lastSanction.getTSDate());
      lastSanctionType =
          (SanctionType.getType(lastSanction.getType()).getVisualColor()
              + SanctionType.getType(lastSanction.getType()).getVisualName());
    }

    // Récupération premiere et dernière sanction de chaque type (Valeurs par défaut)
    String firstBanDate = "Aucun", firstMuteDate = "Aucun", firstKickDate = "Aucun";
    String lastBanDate = "Aucun", lastMuteDate = "Aucun", lastKickDate = "Aucun";
    String firstBanID = "-", firstMuteID = "-", firstKickID = "-";
    String lastBanID = "-", lastMuteID = "-", lastKickID = "-";

    if (!bansList.isEmpty()) {
      firstBanDate = sdf.format(bansList.get(bansList.size() - 1).getTSDate());
      firstBanID = bansList.get(0).getID();

      lastBanDate = sdf.format(bansList.get(0).getTSDate());
      lastBanID = bansList.get(bansList.size() - 1).getID();
    }

    if (!mutesList.isEmpty()) {
      firstMuteDate = sdf.format(mutesList.get(mutesList.size() - 1).getTSDate());
      firstMuteID = mutesList.get(0).getID();

      lastMuteDate = sdf.format(mutesList.get(0).getTSDate());
      lastMuteID = mutesList.get(mutesList.size() - 1).getID();
    }

    if (!kicksList.isEmpty()) {
      firstKickDate = sdf.format(kicksList.get(kicksList.size() - 1).getTSDate());
      firstKickID = kicksList.get(0).getID();

      lastKickDate = sdf.format(kicksList.get(0).getTSDate());
      lastKickID = kicksList.get(kicksList.size() - 1).getID();
    }

    // Récupération des points actuel du joueur
    int playerActualPoints = JusticeHandsModule.getSqlPA().getPoints(target.getUniqueId());

    // Total des points obtenus depuis sa première connexion sur le serveur
    int playerAllPoints = 0;
    for (CJSanction sanction : playerAllSanctionList) {
      playerAllPoints += sanction.getPoints();
    }

    ItemStack stats = new ItemStack(Material.KNOWLEDGE_BOOK);
    ItemMeta infos = stats.getItemMeta();
    infos.displayName(
        componentHelper.getComponent(
            "§c§lStatistiques du joueur §7" + target.getName() + " §c§l:"));

    List<String> strLore = new ArrayList<>();
    strLore.add("§6Nombre de bannissements: §e" + (int) nbrBans + " (" + prcBan + "%)");
    strLore.add("§3Nombre de mutes: §b" + (int) nbrMutes + " (" + prcMute + "%)");
    strLore.add("§2Nombre de kicks: §a" + (int) nbrKicks + " (" + prcKick + "%)");
    strLore.add("");
    strLore.add("§fDernière sanction:");
    strLore.add("§7  - ID: §8" + lastSanctionID);
    strLore.add("§7  - Date: §8" + lastSanctionDate);
    strLore.add("§7  - Type: " + lastSanctionType);
    strLore.add("");
    strLore.add("§6Premier bannissement: §7" + firstBanDate + " §8§o(" + firstBanID + ")");
    strLore.add("§3Premier mute: §7" + firstMuteDate + " §8§o(" + firstMuteID + ")");
    strLore.add("§2Premier kick: §7" + firstKickDate + " §8§o(" + firstKickID + ")");
    strLore.add("");
    strLore.add("§6Dernier bannissement: §7" + lastBanDate + " §8§o(" + lastBanID + ")");
    strLore.add("§3Dernier mute: §7" + lastMuteDate + " §8§o(" + lastMuteID + ")");
    strLore.add("§2Dernier kick: §7" + lastKickDate + " §8§o(" + lastKickID + ")");
    strLore.add("");
    strLore.add("§fNombre actuel de points: §6" + playerActualPoints);
    strLore.add("§fNombre total de points obtenus: §e" + playerAllPoints);
    strLore.add("");
    strLore.add("§4CLIC DROIT §cpour imprimer dans le tchat.");
    strLore.add("§7(!) §oRécupération possible via les logs.");

    List<Component> lore =
        strLore.stream().map(componentHelper::getComponent).collect(Collectors.toList());
    infos.lore(lore);
    stats.setItemMeta(infos);
    return stats;
  }

  private @NotNull String getPercentage(
      @NotNull Set<Sanction> sanctions, @NotNull SanctionType sanctionType) {
    DecimalFormat df = new DecimalFormat("#.##");
    long nbSanctionsType =
            sanctions.stream()
                .filter(sanction -> Objects.equals(sanction.type(), sanctionType))
                .count();
    return df.format(nbSanctionsType * 100 / sanctions.size());
  }
}

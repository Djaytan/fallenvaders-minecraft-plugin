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
import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.entities.JhSanction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Keys Keeper component class.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class KeysKeeperComponentBuilder {

  public static final String DAYTIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
  public static final SimpleDateFormat SDF =
      new SimpleDateFormat(DAYTIME_PATTERN, new Locale("fr", "FR"));

  public static final String EJECTING_MESSAGE = """
    %s
    §cVous avez été éjecté du serveur pour la raison suivante :
    §7ID de Sanction : %s§f - §7%s
    
    §cLa réalité de cette infraction a été établie, conformément au règlement du serveur que vous avez précédemment lu et approuvé.
    §eCette sanction vous apportera donc §7%s points de sanction §eet sera enregistrée dans votre casier judiciaire.
    §7Date de l'infraction : %s
    """;
  public static final String LOGIN_BAN_DEF_MESSAGE = """
    %s
    §cVous avez été banni définitivement du serveur pour la raison suivante :
        
    §bID de Sanction : §7%s§b - §7%s
    §bRaison : §7%s
    §bDate : §7%s
    """;
  public static final String LOGIN_BAN_MESSAGE = """
    "%s
    §cVous avez été banni temporairement du serveur pour la raison suivante :
    
    §bID de Sanction : §7%s§b - §7%s
    §bRaison : §7%s
    §bDate : §7%s §b- §7%s
    §bTemps restant : §7%s
    """;

  private final ComponentHelper componentHelper;

  /**
   * Constructor.
   *
   * @param componentHelper The component helper.
   */
  @Inject
  public KeysKeeperComponentBuilder(@NotNull ComponentHelper componentHelper) {
    this.componentHelper = componentHelper;
  }

  public Component ejectingMessageCpnt(CJSanction sanction) {
    String date = SDF.format(sanction.getTSDate().getTime());

    final Component cpnt =
        LegacyComponentSerializer.legacyAmpersand()
            .deserialize(
                String.format(
                    "%s\n§cVous avez été éjecté du serveur pour la raison suivante : \n§7ID de Sanction : %s§f - §7%s\n\n§cLa réalité de cette infraction a été établie, conformément au réglèment du serveur\nque vous avez précédement lu et approuvé.\n§eCette sanction vous apportera donc §7%d points de sanction §eet sera enregistrée dans \nvotre casier judiciaire.\n§7Date de l'infraction : %s",
                    GeneralUtils.getPrefix("kk"),
                    sanction.getID(),
                    sanction.getName(),
                    sanction.getPoints(),
                    date));

    return cpnt;
  }

  public Component loginBanComponent(JhSanction jhSanction, boolean isBanDef)
      throws JusticeHandsException {
    List<String> data = new ArrayList<>();
    data.add(GeneralUtils.getPrefix("kk")); // prefix
    data.add(Integer.toString(jhSanction.getSctnId())); // sanction ID
    data.add(jhSanction.getSctnName()); // sanction name
    data.add(jhSanction.getSctnReason()); // sanction reason
    data.add(SDF.format(jhSanction.getSctnBeginningDate().getTime())); // sanction beginning date

    String banMessage;
    if (isBanDef) {
      banMessage = LOGIN_BAN_DEF_MESSAGE;
    } else {
      if (jhSanction.getSctnEndingDate() != null) {
        data.add(SDF.format(jhSanction.getSctnEndingDate().getTime())); // sanction ending date
        data.add(
            GeneralUtils.timeRemaining(
                jhSanction.getSctnEndingDate().getTime()
                    - System.currentTimeMillis())); // sanction remaining time
        banMessage = LOGIN_BAN_MESSAGE;
      } else {
        throw new JusticeHandsException(
            "The ending date for a non-definitive ban mustn't be null.");
      }
    }

    return componentHelper.getComponent(String.format(banMessage, data));
  }
}

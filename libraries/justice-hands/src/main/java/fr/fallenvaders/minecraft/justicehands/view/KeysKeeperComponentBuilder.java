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
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
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

  /**
   * Provides an ejecting message {@link Component}.
   *
   * @param jhSanction The JusticeHands' sanction to be used for the ejecting message.
   * @return The ejecting message component.
   */
  public Component ejectingMessageComponent(JhSanction jhSanction) {
    List<String> data = new ArrayList<>();
    data.add(GeneralUtils.getPrefix("kk"));
    data.add(Integer.toString(jhSanction.getSctnId()));
    data.add(jhSanction.getSctnName());
    data.add(Integer.toString(jhSanction.getSctnPoints()));
    data.add(SDF.format(jhSanction.getSctnBeginningDate().getTime()));
    return componentHelper.getComponent(String.format(EJECTING_MESSAGE, data.toArray()));
  }

  /**
   * Provides a ban message {@link Component}.
   *
   * @param jhSanction The JusticeHands' sanction to be used for the ban message.
   * @param isBanDef Tells if the ban is definitive or not.
   * @return The ban message component.
   * @throws JusticeHandsException if the ending date is null for a non-def ban.
   */
  public Component banMessageComponent(JhSanction jhSanction, boolean isBanDef)
      throws JusticeHandsException {
    List<String> data = new ArrayList<>();
    data.add(GeneralUtils.getPrefix("kk"));
    data.add(Integer.toString(jhSanction.getSctnId()));
    data.add(jhSanction.getSctnName());
    data.add(jhSanction.getSctnReason());
    data.add(SDF.format(jhSanction.getSctnBeginningDate().getTime()));

    String banMessage;
    if (isBanDef) {
      banMessage = LOGIN_BAN_DEF_MESSAGE;
    } else {
      if (jhSanction.getSctnEndingDate() != null) {
        data.add(SDF.format(jhSanction.getSctnEndingDate().getTime()));
        data.add(
            GeneralUtils.timeRemaining(
                jhSanction.getSctnEndingDate().getTime() - System.currentTimeMillis()));
        banMessage = LOGIN_BAN_MESSAGE;
      } else {
        throw new JusticeHandsException(
            "The ending date for a non-definitive ban mustn't be null.");
      }
    }

    return componentHelper.getComponent(String.format(banMessage, data.toArray()));
  }
}

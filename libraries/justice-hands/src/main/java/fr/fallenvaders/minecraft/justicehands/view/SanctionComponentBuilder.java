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
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.TimeUtils;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The builder of sanction's components.
 *
 * @author Voltariuss
 * @author Glynix
 * @since 0.3.0
 */
@Singleton
public class SanctionComponentBuilder {

  public static final String DAYTIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
  public static final SimpleDateFormat SDF =
      new SimpleDateFormat(DAYTIME_PATTERN, new Locale("fr", "FR"));

  public static final String KICK_MESSAGE = """
    %s
    §cVous avez été éjecté du serveur pour la raison suivante :
    §7ID de Sanction : %s§f - §7%s
    
    §cLa réalité de cette infraction a été établie, conformément au règlement du serveur que vous avez précédemment lu et approuvé.
    §eCette sanction vous apportera donc §7%s points de sanction §eet sera enregistrée dans votre casier judiciaire.
    §7Date de l'infraction : %s
    """;
  public static final String MUTE_MESSAGE = "%s§cTu ne peux pas parler, tu es réduit au silence pendant encore §b%s§c.";
  public static final String BAN_DEF_MESSAGE = """
    %s
    §cVous avez été banni définitivement du serveur pour la raison suivante :
        
    §bID de Sanction : §7%s§b - §7%s
    §bRaison : §7%s
    §bDate : §7%s
    """;
  public static final String BAN_MESSAGE = """
    "%s
    §cVous avez été banni temporairement du serveur pour la raison suivante :
    
    §bID de Sanction : §7%s§b - §7%s
    §bRaison : §7%s
    §bDate : §7%s §b- §7%s
    §bTemps restant : §7%s
    """;

  private final ComponentHelper componentHelper;
  private final TimeUtils timeUtils;

  /**
   * Constructor.
   *
   * @param componentHelper The component helper.
   * @param timeUtils The time utils.
   */
  @Inject
  public SanctionComponentBuilder(@NotNull ComponentHelper componentHelper, @NotNull TimeUtils timeUtils) {
    this.componentHelper = componentHelper;
    this.timeUtils = timeUtils;
  }

  /**
   * Provides a kick message {@link Component}.
   *
   * @param kick The kick sanction to be used for the kick message.
   * @return The kick message component.
   */
  public @NotNull Component kickMessage(@NotNull Sanction kick) {
    List<String> data = new ArrayList<>();
    data.add(ViewUtils.PREFIX_KK);
    data.add(Integer.toString(kick.id()));
    data.add(kick.name());
    data.add(Integer.toString(kick.points()));
    data.add(SDF.format(kick.beginningDate().getTime()));
    return componentHelper.getComponent(String.format(KICK_MESSAGE, data.toArray()));
  }

  /**
   * Provides a ban message {@link Component}.
   *
   * @param ban The ban sanction to be used for the ban message.
   * @return The ban message component.
   */
  public @NotNull Component banMessage(@NotNull Sanction ban) {
    List<String> data = new ArrayList<>();
    data.add(ViewUtils.PREFIX_KK);
    data.add(Integer.toString(ban.id()));
    data.add(ban.name());
    data.add(ban.reason());
    data.add(SDF.format(ban.beginningDate().getTime()));

    String banMessage;
    boolean isBanDef = ban.endingDate() == null;
    if (isBanDef) {
      banMessage = BAN_DEF_MESSAGE;
    } else {
      data.add(SDF.format(ban.endingDate().getTime()));
      data.add(timeUtils.remainingTime(ban.endingDate().getTime() - System.currentTimeMillis()));
      banMessage = BAN_MESSAGE;
    }

    return componentHelper.getComponent(String.format(banMessage, data.toArray()));
  }

  /**
   * Provides a mute message {@link Component}.
   *
   * @param mute The mute sanction.
   * @return The mute message component.
   * @throws JusticeHandsException If the mute sanction isn't properly defined.
   */
  public @NotNull Component muteMessage(@NotNull Sanction mute) throws JusticeHandsException {
    if (mute.endingDate() == null) {
      throw new JusticeHandsException("The ending date of a mute sanction can't be null.");
    }
    long remainingTime = mute.endingDate().getTime() - System.currentTimeMillis();
    List<Object> data = new ArrayList<>(2);
    data.add(ViewUtils.PREFIX_KK);
    data.add(timeUtils.remainingTime(remainingTime));
    return componentHelper.getComponent(String.format(MUTE_MESSAGE, data.toArray()));
  }
}

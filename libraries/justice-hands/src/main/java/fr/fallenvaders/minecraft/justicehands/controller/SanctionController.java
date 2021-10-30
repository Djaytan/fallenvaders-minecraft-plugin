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

package fr.fallenvaders.minecraft.justicehands.controller;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.SanctionDispatcher;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.service.SanctionService;
import fr.fallenvaders.minecraft.justicehands.view.SanctionComponentBuilder;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

/**
 * This class lead sanctions attribution over server.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionController {

  private final SanctionComponentBuilder sanctionComponentBuilder;
  private final Logger logger;
  private final SanctionDispatcher sanctionDispatcher;
  private final SanctionService sanctionService;

  /**
   * Constructor.
   *
   * @param sanctionComponentBuilder The Keys Keeper component builder.
   * @param logger The logger.
   * @param sanctionDispatcher The sanction dispatcher.
   * @param sanctionService The sanction service.
   */
  @Inject
  public SanctionController(
      @NotNull SanctionComponentBuilder sanctionComponentBuilder,
      @NotNull Logger logger,
      @NotNull SanctionDispatcher sanctionDispatcher,
      @NotNull SanctionService sanctionService) {
    this.sanctionComponentBuilder = sanctionComponentBuilder;
    this.logger = logger;
    this.sanctionDispatcher = sanctionDispatcher;
    this.sanctionService = sanctionService;
  }

  /**
   * Gets and returns all existing {@link Sanction} of the specified {@link OfflinePlayer} where he
   * is assigned as an inculpated one.
   *
   * @param player The player.
   * @return All the {@link Sanction} of the specified {@link OfflinePlayer}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    return sanctionService.getPlayerSanctions(player);
  }

  /**
   * Gets and returns all existing {@link Sanction} of the specified {@link OfflinePlayer} where he
   * is assigned as an inculpated one and where the {@link SanctionType} match with the given one.
   *
   * @param player The player.
   * @param sanctionType The type of sanction.
   * @return All the {@link Sanction} of the specified {@link OfflinePlayer} and {@link
   *     SanctionType}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(
      @NotNull OfflinePlayer player, @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    Set<Sanction> sanctions = getPlayerSanctions(player);
    return sanctions.stream()
        .filter(sanction -> sanction.getType() == sanctionType)
        .collect(Collectors.toSet());
  }

  /**
   * Gets and returns all active existing {@link Sanction} of the specified {@link OfflinePlayer}
   * where he is assigned as an inculpated one.
   *
   * @param player The player.
   * @return All the {@link Sanction} of the specified {@link OfflinePlayer}.
   * @throws JusticeHandsException if the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getActivePlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    Set<Sanction> sanctions = getPlayerSanctions(player);
    return sanctions.stream().filter(this::isActiveSanction).collect(Collectors.toSet());
  }

  /**
   * Gets and returns the active existing {@link Sanction} of the specified {@link OfflinePlayer}
   * where he is assigned as an inculpated one and where the {@link SanctionType} match with the
   * given one.
   *
   * @param player The player.
   * @param sanctionType The type of sanction.
   * @return The {@link Sanction} of the specified {@link OfflinePlayer} and {@link SanctionType}.
   * @throws JusticeHandsException if the sought sanction fail to be found in the model.
   */
  public @NotNull Optional<Sanction> getActivePlayerSanction(
      @NotNull OfflinePlayer player, @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    return getPlayerSanctions(player).stream()
        .filter(sanction -> sanction.getType() == sanctionType)
        .findFirst();
  }

  /**
   * Kicks the specified {@link Player} from server by creating a {@link Sanction}.
   *
   * @param player The player to kick from the server.
   */
  public void kickPlayer(
      @NotNull Player player,
      @NotNull String name,
      @NotNull String reason,
      int points,
      @NotNull CommandSender author) {
    try {
      Sanction sanction =
          sanctionPlayer(player, name, reason, points, null, author, SanctionType.KICK);
    } catch (JusticeHandsException e) {
      logger.error("Failed to kick player.", e);
      // TODO: show error message in view
    }
  }

  private @NotNull Sanction sanctionPlayer(
      @NotNull Player player,
      @NotNull String name,
      @NotNull String reason,
      int points,
      @Nullable Duration duration,
      @NotNull CommandSender author,
      @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    checkAuthorValidity(author);
    Timestamp beginningDate = new Timestamp(System.currentTimeMillis());
    Timestamp endingDate = null;
    if (duration != null) {
      endingDate = new Timestamp(System.currentTimeMillis() + duration.toMillis());
    }

    Sanction sanction = new Sanction();
    sanction.setInculpatedPlayer(player);
    sanction.setName(name);
    sanction.setReason(reason);
    sanction.setPoints(points);
    sanction.setBeginningDate(beginningDate);
    sanction.setEndingDate(endingDate);
    sanction.setAuthorPlayer(convertAuthor(author));
    sanction.setType(sanctionType);

    /*
     * The priority is the registration of the sanction in model before the dispatch.
     * If dispatch fail, the user can opt to alternative solutions (send message to player and then
     * use Minecraft vanilla kick command if needed in case of kick or ban).
     * Finally, the dispatch mustn't fail because we expect here that the sanction is
     * well-defined.
     */
    sanctionService.registerSanction(sanction);
    sanctionDispatcher.dispatchSanction(sanction);
    return sanction;
  }

  private void checkAuthorValidity(@NotNull CommandSender author) throws JusticeHandsException {
    boolean isValid = author instanceof Player || author instanceof ConsoleCommandSender;
    if (!isValid) {
      throw new JusticeHandsException(
          "Invalid sanction: only Player and ConsoleCommandSender can be an author.");
    }
  }

  private @Nullable OfflinePlayer convertAuthor(@NotNull CommandSender author) {
    OfflinePlayer player = null;
    if (author instanceof Player) {
      player = (Player) author;
    }
    return player;
  }

  private boolean isActiveSanction(@NotNull Sanction sanction) {
    boolean isBanDef = sanction.getType() == SanctionType.BAN && sanction.getEndingDate() == null;
    boolean isActive =
        sanction.getEndingDate() != null
            && sanction.getEndingDate().toInstant().isAfter(Instant.now());
    return isBanDef || isActive;
  }
}

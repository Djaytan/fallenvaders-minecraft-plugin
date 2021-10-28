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
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.service.SanctionService;
import fr.fallenvaders.minecraft.justicehands.view.KeysKeeperComponentBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Duration;

/**
 * This class lead sanctions attribution over server.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionController {

  private final Logger logger;

  private final KeysKeeperComponentBuilder keysKeeperComponentBuilder;
  private final SanctionService sanctionService;

  /**
   * Constructor.
   *
   * @param logger The logger.
   * @param keysKeeperComponentBuilder The Keys Keeper component builder.
   * @param sanctionService The sanction service.
   */
  @Inject
  public SanctionController(
      @NotNull Logger logger,
      @NotNull KeysKeeperComponentBuilder keysKeeperComponentBuilder,
      @NotNull SanctionService sanctionService) {
    this.logger = logger;
    this.keysKeeperComponentBuilder = keysKeeperComponentBuilder;
    this.sanctionService = sanctionService;
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
      checkAuthorValidity(author);
      Timestamp beginningDate = new Timestamp(System.currentTimeMillis());

      // TODO: use builder class instead
      Sanction sanction = new Sanction();
      sanction.setInculpatedPlayer(player);
      sanction.setName(name);
      sanction.setReason(reason);
      sanction.setPoints(points);
      sanction.setBeginningDate(beginningDate);
      sanction.setEndingDate(null);
      sanction.setAuthorPlayer(convertAuthor(author));
      sanction.setType(SanctionType.KICK);
      sanctionService.registerSanction(sanction);
      player.kick(keysKeeperComponentBuilder.ejectingMessageComponent(sanction));
    } catch (JusticeHandsException e) {
      logger.error("Failed to kick player.", e);
      // TODO: show error message in view
    }
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
}

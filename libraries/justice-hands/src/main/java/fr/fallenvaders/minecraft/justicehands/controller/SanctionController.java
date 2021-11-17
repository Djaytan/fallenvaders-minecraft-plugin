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

import com.google.common.base.Preconditions;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.service.SanctionService;
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
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class SanctionController {

  private final Logger logger;
  private final SanctionDispatcher sanctionDispatcher;
  private final SanctionService sanctionService;

  /**
   * Constructor.
   *
   * @param logger The {@link Logger}.
   * @param sanctionDispatcher The {@link SanctionDispatcher}.
   * @param sanctionService The {@link SanctionService}.
   */
  @Inject
  public SanctionController(
      @NotNull Logger logger,
      @NotNull SanctionDispatcher sanctionDispatcher,
      @NotNull SanctionService sanctionService) {
    this.logger = logger;
    this.sanctionDispatcher = sanctionDispatcher;
    this.sanctionService = sanctionService;
  }

  /**
   * Gets and returns all existing sanctions of the specified player where he is assigned as an
   * inculpated one.
   *
   * @param player The player.
   * @return All the sanctions of the specified player.
   * @throws JusticeHandsException If the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    return sanctionService.getPlayerSanctions(player);
  }

  /**
   * Gets and returns all existing sanctions of the specified player where he is assigned as an
   * inculpated one and where the type of sanction match with them.
   *
   * @param player The player.
   * @param sanctionType The type of sanction.
   * @return All sanctions of the specified player which match with the given type of sanction.
   * @throws JusticeHandsException If the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getPlayerSanctions(
      @NotNull OfflinePlayer player, @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    Set<Sanction> sanctions = getPlayerSanctions(player);
    return sanctions.stream()
        .filter(sanction -> sanction.type() == sanctionType)
        .collect(Collectors.toSet());
  }

  /**
   * Gets and returns all active sanctions of the specified player where he is assigned as an
   * inculpated one.
   *
   * @param player The player.
   * @return All active sanctions of the specified player.
   * @throws JusticeHandsException If the sought sanctions fail to be found in the model.
   */
  public @NotNull Set<Sanction> getActivePlayerSanctions(@NotNull OfflinePlayer player)
      throws JusticeHandsException {
    Set<Sanction> sanctions = getPlayerSanctions(player);
    return sanctions.stream().filter(this::isActiveSanction).collect(Collectors.toSet());
  }

  /**
   * Gets and returns active existing sanctions of the specified player where he is assigned as
   * an inculpated one and where the type of sanction match with them.
   *
   * @param player The player.
   * @param sanctionType The type of sanction.
   * @return All active sanctions of the specified player which match with the given type of
   *    sanction.
   * @throws JusticeHandsException If the sought sanction fail to be found in the model.
   */
  public @NotNull Optional<Sanction> getActivePlayerSanction(
      @NotNull OfflinePlayer player, @NotNull SanctionType sanctionType)
      throws JusticeHandsException {
    return getPlayerSanctions(player).stream()
        .filter(sanction -> sanction.type() == sanctionType)
        .findFirst();
  }

  /**
   * Kicks the specified player from server by creating a sanction.
   *
   * @param inculpated The inculpated player to kick from the server.
   */
  public void kickPlayer(
      @NotNull Player inculpated,
      @NotNull String name,
      @NotNull String reason,
      int points,
      @NotNull CommandSender author) {
    try {
      sanctionPlayer(inculpated, name, reason, points, null, author, SanctionType.KICK);
    } catch (JusticeHandsException e) {
      logger.error("Failed to kick player.", e);
      // TODO: show error message in view
    }
  }

  private void sanctionPlayer(
      @NotNull Player inculpated,
      @NotNull String name,
      @NotNull String reason,
      int points,
      @Nullable Duration duration,
      @NotNull CommandSender author,
      @NotNull SanctionType type)
      throws JusticeHandsException {
    Preconditions.checkArgument(points >= 0,
      "The number of points of the sanction can't be lower than 0.");
    checkAuthorValidity(author);

    Timestamp beginningDate = new Timestamp(System.currentTimeMillis());
    Timestamp endingDate = null;
    if (duration != null) {
      endingDate = new Timestamp(System.currentTimeMillis() + duration.toMillis());
    }
    OfflinePlayer authorPlayer = convertAuthor(author);

    Sanction sanction =
        new Sanction(
            inculpated, name, reason, points, beginningDate, endingDate, authorPlayer, type);

    /*
     * The priority is the registration of the sanction in model before the dispatch.
     * If dispatch fail, the author can opt to alternative solutions (send message to player and
     * then use Minecraft vanilla kick command if needed in case of kick or ban). Then, the program
     * can take the relay when player try to reconnect when he is banned.
     * Furthermore, the dispatch mustn't fail because we expect here that the sanction is
     * well-defined and shouldn't throw exception in this case.
     */
    sanctionService.registerSanction(sanction);
    sanctionDispatcher.dispatchSanction(sanction);
  }

  private void checkAuthorValidity(@NotNull CommandSender author) {
    Preconditions.checkArgument(author instanceof Player || author instanceof ConsoleCommandSender,
      "Invalid sanction: only Player and ConsoleCommandSender can be an author.");
  }

  private @Nullable OfflinePlayer convertAuthor(@NotNull CommandSender author) {
    OfflinePlayer player = null;
    if (author instanceof Player p) {
      player = p;
    }
    return player;
  }

  private boolean isActiveSanction(@NotNull Sanction sanction) {
    boolean isBanDef = sanction.type() == SanctionType.BAN && sanction.endingDate() == null;
    boolean isActive =
        sanction.endingDate() != null
            && sanction.endingDate().toInstant().isAfter(Instant.now());
    return isBanDef || isActive;
  }
}

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

package fr.fallenvaders.minecraft.justicehands.view.listeners;

import fr.fallenvaders.minecraft.commons.ComponentHelper;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.service.SanctionService;
import fr.fallenvaders.minecraft.justicehands.view.KeysKeeperComponentBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

/**
 * Listener of {@link PlayerLoginEvent} in order to control player connections and prevents the ones
 * where the player in banned.
 *
 * @author FallenVaders' dev team
 * @version 0.1.0
 */
@Singleton
public class PlayerLoginListener implements Listener {

  private final ComponentHelper componentHelper;
  private final SanctionService sanctionService;
  private final KeysKeeperComponentBuilder keysKeeperComponentBuilder;
  private final Logger logger;

  /**
   * Constructor.
   *
   * @param componentHelper The component helper.
   * @param sanctionService The JusticeHands' sanction service.
   * @param keysKeeperComponentBuilder The Keys Keeper component builder.
   * @param logger The logger.
   */
  @Inject
  public PlayerLoginListener(
      @NotNull ComponentHelper componentHelper,
      @NotNull SanctionService sanctionService,
      @NotNull KeysKeeperComponentBuilder keysKeeperComponentBuilder,
      @NotNull Logger logger) {
    this.componentHelper = componentHelper;
    this.sanctionService = sanctionService;
    this.keysKeeperComponentBuilder = keysKeeperComponentBuilder;
    this.logger = logger;
  }

  /**
   * Listen of the {@link PlayerLoginEvent} in order to prevent the connections of banned players.
   *
   * <p>Note: JusticeHands have the last word concerning the connection of a player or not and must
   * override previous allow/disallow decisions and messages. So it's why no check of {@link
   * PlayerLoginEvent.Result} state are realized.
   *
   * @param ple The Bukkit player login event.
   */
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerLogin(@NotNull PlayerLoginEvent ple) {
    try {
      Set<Sanction> sanctions =
          sanctionService.getActivePlayerSanctions(ple.getPlayer(), SanctionType.BAN);
      if (!sanctions.isEmpty()) {
        Sanction ban = sanctions.iterator().next();
        Component loginBanComponent = keysKeeperComponentBuilder.banMessageComponent(ban);
        ple.disallow(PlayerLoginEvent.Result.KICK_BANNED, loginBanComponent);
      }
    } catch (JusticeHandsException e) {
      logger.error("Failed to connect player on server.", e);
      Component errorComponent = componentHelper.getErrorComponent();
      ple.disallow(PlayerLoginEvent.Result.KICK_OTHER, errorComponent);
    }
  }
}

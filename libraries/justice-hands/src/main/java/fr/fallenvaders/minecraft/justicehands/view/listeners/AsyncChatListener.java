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
import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.controller.SanctionController;
import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.view.SanctionComponentBuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Listener of {@link AsyncChatEvent} in order to control player's sending message in chat and to
 * prevent them when player is muted.
 *
 * @author FallenVaders' dev team
 * @version 0.1.0
 */
@Singleton
public class AsyncChatListener implements Listener {

  private final ComponentHelper componentHelper;
  private final Logger logger;
  private final SanctionComponentBuilder sanctionComponentBuilder;
  private final SanctionController sanctionController;

  /**
   * Constructor.
   *
   * @param componentHelper The component helper.
   * @param logger The logger.
   * @param sanctionComponentBuilder The sanction component builder.
   * @param sanctionController The sanction controller.
   */
  @Inject
  public AsyncChatListener(
      @NotNull ComponentHelper componentHelper,
      @NotNull Logger logger,
      @NotNull SanctionComponentBuilder sanctionComponentBuilder,
      @NotNull SanctionController sanctionController) {
    this.componentHelper = componentHelper;
    this.logger = logger;
    this.sanctionComponentBuilder = sanctionComponentBuilder;
    this.sanctionController = sanctionController;
  }

  /**
   * Listen of the {@link AsyncChatEvent} in order to prevent the messages of muted players.
   *
   * <p>Note: JusticeHands have the last word concerning the sending or not of a message in chat of
   * a player and must override previous allow/disallow decisions and messages in case where the
   * player is muted.
   *
   * <p>Note 2: If a problem occurs during the recovering of the active mute sanction, the message
   * is blocked.
   *
   * @param event The async chat event.
   */
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onChat(AsyncChatEvent event) {
    boolean initCancelState = event.isCancelled();
    event.setCancelled(true);
    Player player = event.getPlayer();
    try {
      Sanction mute =
          sanctionController.getActivePlayerSanction(player, SanctionType.MUTE).orElse(null);
      if (mute != null) {
        Component muteMessage = sanctionComponentBuilder.muteMessage(mute);
        player.sendMessage(muteMessage);
      } else {
        event.setCancelled(initCancelState);
      }
    } catch (JusticeHandsException e) {
      logger.error("Failed to send player's message on chat.", e);
      Component errorMessage = componentHelper.getErrorComponent();
      player.sendMessage(errorMessage);
    }
  }
}

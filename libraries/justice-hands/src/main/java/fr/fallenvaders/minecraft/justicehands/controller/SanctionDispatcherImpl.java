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
import fr.fallenvaders.minecraft.justicehands.view.SanctionComponentBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class is an implementation of the {@link SanctionDispatcher} interface.
 *
 * @author Voltariuss
 * @since 0.3.0
 */
@Singleton
public class SanctionDispatcherImpl implements SanctionDispatcher {

  private final SanctionComponentBuilder sanctionComponentBuilder;
  private final Logger logger;

  /**
   * Constructor.
   *
   * @param sanctionComponentBuilder The {@link SanctionComponentBuilder}.
   * @param logger The {@link Logger}.
   */
  @Inject
  public SanctionDispatcherImpl(
      @NotNull SanctionComponentBuilder sanctionComponentBuilder, @NotNull Logger logger) {
    this.sanctionComponentBuilder = sanctionComponentBuilder;
    this.logger = logger;
  }

  @Override
  public void dispatchSanction(@NotNull Sanction sanction) throws JusticeHandsException {
    logger.info("Dispatch sanction: {}", sanction);
    try {
      switch (sanction.type()) {
        case KICK -> kick(sanction);
        case MUTE -> mute(sanction);
        case BAN -> ban(sanction);
      }
    } catch (JusticeHandsException e) {
      logger.error("Failed to dispatch sanction.", e);
      throw new JusticeHandsException("Failed to dispatch sanction.");
    }
  }

  private void kick(@NotNull Sanction kick) {
    OfflinePlayer player = kick.inculpatedPlayer();
    if (player.isOnline()) {
      Player p = (Player) player;
      p.kick(sanctionComponentBuilder.kickMessage(kick));
      logger.info("Inculpated player kicked.");
    } else {
      // TODO: two options: make an exception in this case, or convert kick to a warning which can kick the player under certain criteria.
      logger.info("Failed to kick player: he is currently disconnected.");
    }
  }

  private void mute(@NotNull Sanction mute) throws JusticeHandsException {
    OfflinePlayer player = mute.inculpatedPlayer();
    if (player.isOnline()) {
      Player p = (Player) player;
      Component muteMessage = sanctionComponentBuilder.muteMessage(mute);
      p.sendMessage(muteMessage);
    }
  }

  private void ban(@NotNull Sanction ban) {
    OfflinePlayer player = ban.inculpatedPlayer();
    if (player.isOnline()) {
      Player p = (Player) player;
      p.kick(sanctionComponentBuilder.banMessage(ban));
    }
    logger.info("Inculpated player banned.");
  }
}

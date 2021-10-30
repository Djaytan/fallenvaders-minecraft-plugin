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

package fr.fallenvaders.minecraft.justicehands.model;

import fr.fallenvaders.minecraft.justicehands.model.entities.Sanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.view.SanctionComponentBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This class is an implementation of the {@link SanctionDispatcher} interface.
 *
 * @author FallenVaders' dev team
 * @since 0.3.0
 */
@Singleton
public class SanctionDispatcherImpl implements SanctionDispatcher {

  private final SanctionComponentBuilder sanctionComponentBuilder;
  private final Logger logger;

  /**
   * Constructor.
   *
   * @param sanctionComponentBuilder The Keys Keeper component builder.
   * @param logger The logger.
   */
  @Inject
  public SanctionDispatcherImpl(@NotNull SanctionComponentBuilder sanctionComponentBuilder, @NotNull Logger logger) {
    this.sanctionComponentBuilder = sanctionComponentBuilder;
    this.logger = logger;
  }

  @Override
  public void dispatchSanction(@NotNull Sanction sanction) {
    logger.info("Dispatch sanction: {}", sanction);
    if (sanction.getType() == SanctionType.KICK) {
      kick(sanction);
    } else if (sanction.getType() == SanctionType.BAN) {
      ban(sanction);
    }
  }

  private void kick(@NotNull Sanction sanction) {
    OfflinePlayer player = sanction.getInculpatedPlayer();
    if (player.isOnline()) {
      Player p = (Player) player;
      p.kick(sanctionComponentBuilder.kickMessage(sanction));
      logger.info("Inculpated player kicked.");
    } else {
      logger.info("Failed to kick player: he is currently disconnected.");
    }
  }

  private void ban(@NotNull Sanction sanction) {
    OfflinePlayer player = sanction.getInculpatedPlayer();
    if (player.isOnline()) {
      Player p = (Player) player;
      p.kick(sanctionComponentBuilder.banMessage(sanction));
    }
    logger.info("Inculpated player banned.");
  }
}

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

package fr.fallenvaders.minecraft.justicehands.controller.listeners;

import fr.fallenvaders.minecraft.justicehands.model.service.JhSanctionService;
import fr.fallenvaders.minecraft.justicehands.model.service.KeysKeeperBot;
import fr.fallenvaders.minecraft.justicehands.view.KeysKeeperComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Listener of {@link PlayerLoginEvent} in order to control player connections and prevents the ones
 * where the player in banned.
 *
 * @author FallenVaders' dev team
 * @version 0.1.0
 */
public class PlayerLoginListener implements Listener {

  private final JhSanctionService jhSanctionService;

  /**
   * Constructor.
   *
   * @param jhSanctionService The JusticeHands' sanction service.
   */
  @Inject
  public PlayerLoginListener(@NotNull JhSanctionService jhSanctionService) {
    this.jhSanctionService = jhSanctionService;
  }

  /**
   * Listen of the {@link PlayerLoginEvent} in order to prevent the connections of banned players.
   *
   * @param ple The Bukkit player login event.
   */
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerLogin(@NotNull PlayerLoginEvent ple) {
    try {
      CJSanction playerActiveBan = KeysKeeperBot.getPlayerActiveBan(ple.getPlayer());

      boolean isBanDef = false;
      if ("bandef".equals(playerActiveBan.getType())) {
        isBanDef = true;
      }

      if (isBanDef) {
        ple.disallow(
            PlayerLoginEvent.Result.KICK_BANNED,
            KeysKeeperComponent.loginBanDefMessage(playerActiveBan));
      } else {
        ple.disallow(
            PlayerLoginEvent.Result.KICK_BANNED,
            KeysKeeperComponent.loginBanMessage(playerActiveBan));
      }
    } catch (NullPointerException e) {
      // Si la fonction playerActiveBan retourne un null, le joueur n'a pas de ban actif
      return;
    }
  }
}

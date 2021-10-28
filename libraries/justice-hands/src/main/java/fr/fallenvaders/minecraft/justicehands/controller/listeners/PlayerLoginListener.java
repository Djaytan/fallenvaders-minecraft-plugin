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

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.model.entities.JhSanction;
import fr.fallenvaders.minecraft.justicehands.model.entities.SanctionType;
import fr.fallenvaders.minecraft.justicehands.model.service.JhSanctionService;
import fr.fallenvaders.minecraft.justicehands.view.KeysKeeperComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

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
   * <p>Note: JusticeHands have the last word concerning the connection of a player or not and must
   * override previous allow/disallow decisions and messages. So it's why no check of {@link
   * PlayerLoginEvent.Result} state are realized.
   *
   * @param ple The Bukkit player login event.
   */
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerLogin(@NotNull PlayerLoginEvent ple) {
    try {
      Set<JhSanction> jhSanctions =
          jhSanctionService.getActivePlayerJhSanctions(ple.getPlayer(), SanctionType.BAN);
      if (!jhSanctions.isEmpty()) {
        JhSanction ban = jhSanctions.iterator().next();
        boolean isBanDef = ban.getSctnEndingDate() == null;
        Component loginBanComponent = KeysKeeperComponent.loginBanComponent(ban, isBanDef);
        ple.disallow(PlayerLoginEvent.Result.KICK_BANNED, loginBanComponent);
      }
    } catch (JusticeHandsException e) {
      // TODO
    }
  }
}

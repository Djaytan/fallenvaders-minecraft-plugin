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

import fr.fallenvaders.minecraft.justicehands.GeneralUtils;
import fr.fallenvaders.minecraft.justicehands.model.service.KeysKeeperBot;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

  @EventHandler
  public void AsyncChatEvent(AsyncChatEvent e) {
    Long unmuteDate = KeysKeeperBot.getPlayerMuteDate(e.getPlayer());

    // Vérification si le joueur peut parler
    if (unmuteDate > System.currentTimeMillis()) {
      e.getPlayer()
          .sendMessage(
              GeneralUtils.getPrefix("kk")
                  + "§cTu ne peux pas parler, tu es réduit au silence pendant encore §b"
                  + GeneralUtils.timeRemaining(unmuteDate - System.currentTimeMillis())
                  + "§c.");
      e.setCancelled(true);
    }
  }
}

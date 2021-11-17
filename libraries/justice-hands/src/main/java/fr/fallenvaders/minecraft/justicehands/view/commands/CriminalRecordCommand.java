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

package fr.fallenvaders.minecraft.justicehands.view.commands;

import fr.fallenvaders.minecraft.justicehands.JusticeHandsException;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import fr.fallenvaders.minecraft.justicehands.view.gui.CriminalRecordView;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents the criminal record {@link CommandExecutor}.
 *
 * @author Glynix
 * @author Voltariuss
 * @since 0.1.0
 */
@Singleton
public class CriminalRecordCommand implements CommandExecutor {

  private final CriminalRecordView criminalRecordView;
  private final Server server;

  /**
   * Constructor.
   *
   * @param criminalRecordView The {@link CriminalRecordView}.
   * @param server The {@link Server}.
   */
  @Inject
  public CriminalRecordCommand(
      @NotNull CriminalRecordView criminalRecordView, @NotNull Server server) {
    this.criminalRecordView = criminalRecordView;
    this.server = server;
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command cmd,
      @NotNull String label,
      @NotNull String[] args) {
    if (sender.hasPermission("justicehands.cr")) {
      if (sender instanceof Player) {
        Player moderator = (Player) sender;
        if (args.length == 0) {
          moderator.sendMessage(
              ViewUtils.PREFIX_CR + "§cSyntaxe incomplète, il manque un argument.");
          moderator.sendMessage("     §7/" + cmd.getName().toLowerCase() + " §7<joueur>");
        } else if (args.length >= 1) {
          String playerName = args[0];
          OfflinePlayer player = getPlayer(playerName);
          if (player != null) {
            try {
              criminalRecordView.openMainMenu(moderator, player);
            } catch (JusticeHandsException e) {
              moderator.sendMessage(
                  ViewUtils.PREFIX_CR
                      + "§cUne erreur est survenue au moment de l'ouverture du menu.");
            }
          } else {
            moderator.sendMessage(
                ViewUtils.PREFIX_CR + "§cCe joueur ne s'est jamais connecté sur le serveur.");
          }
        }
      } else {
        sender.sendMessage(
            ViewUtils.PREFIX_CR + "§cTu dois être sur le serveur pour exécuter cette commande.");
      }
    } else {
      sender.sendMessage(ViewUtils.PREFIX_CR + "§cTu n'as pas accès à cette commande.");
    }
    return true;
  }

  private @Nullable OfflinePlayer getPlayer(String playerName) {
    OfflinePlayer player = server.getPlayer(playerName);
    if (player == null) {
      player = server.getOfflinePlayerIfCached(playerName);
    }
    // TODO: FV-134 - store in database pair playerName-playerUuid
    return player;
  }
}

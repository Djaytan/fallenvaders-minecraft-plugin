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

import fr.fallenvaders.minecraft.justicehands.JusticeHandsModule;
import fr.fallenvaders.minecraft.justicehands.view.InventoryBuilderCR;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import java.util.UUID;
import javax.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the criminal record {@link CommandExecutor}.
 *
 * @author FallenVaders' dev team
 * @since 0.1.0
 */
@Singleton
public class CriminalRecordCommand implements CommandExecutor {

  /**
   * This is the execution definition of the criminal record {@link Command}.
   *
   * @param sender The sender of the command.
   * @param cmd The command sent.
   * @param label The label of the command.
   * @param args The command arguments.
   * @return "true" if the command is a valid command, "false" otherwise which will send the "usage"
   *     message specified in the plugin.yml file.
   */
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
          moderator.sendMessage(
              "     §7/" + cmd.getName().toString().toLowerCase() + " §7<joueur>");
        } else if (args.length == 1 && args[0] != null) {
          try {
            UUID playerUUID = Bukkit.getPlayer(args[0]).getUniqueId();
            if (JusticeHandsModule.getSqlPA().hasAccount(playerUUID)) {
              UUID targetUUID =
                  JusticeHandsModule.getSqlPA().getAccount(Bukkit.getPlayer(args[0]).getUniqueId());

              InventoryBuilderCR.openMainMenu(
                  moderator, targetUUID); // Ouverture de l'inventaire SM du joueur target.
            }
          } catch (Exception e) {
            moderator.sendMessage(
                ViewUtils.PREFIX_CR + "§cCe joueur ne s'est jamais connecté sur le serveur.");
          }
        }
        return true;
      } else {
        sender.sendMessage(
            ViewUtils.PREFIX_CR + "§cTu dois être sur le serveur pour éxécuter cette commande.");
      }
    } else {
      sender.sendMessage(ViewUtils.PREFIX_CR + "§cTu n'as pas accès à cette commande.");
    }
    return false;
  }
}

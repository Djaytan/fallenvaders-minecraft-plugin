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
import fr.fallenvaders.minecraft.justicehands.view.InventoryBuilderSM;
import fr.fallenvaders.minecraft.justicehands.view.ViewUtils;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * This class represents the sanction manager {@link CommandExecutor}.
 *
 * @author FallenVaders' dev team
 * @since 0.1.0
 */
@Singleton
public class SanctionManagerCommand implements CommandExecutor {

  private FileConfiguration config;

  /**
   * Constructor.
   *
   * @param config The plugin config.
   */
  @Inject
  public SanctionManagerCommand(FileConfiguration config) {
    this.config = config;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender.hasPermission("justicehands.sm.use")) {
      if (sender instanceof Player) {
        Player moderator = (Player) sender;
        if (args.length == 0) {
          moderator.sendMessage(
              GeneralUtils.getPrefix("SM") + "§cSyntaxe incomplète, il manque un argument.");
          moderator.sendMessage(
              "     §7/" + cmd.getName().toString().toLowerCase() + " §7<joueur>");
        } else if (args.length == 1 && args[0] != null) {
          if (JusticeHandsModule.getSqlPA().hasAccount(Bukkit.getPlayer(args[0]).getUniqueId())) {
            UUID targetUUID =
                JusticeHandsModule.getSqlPA().getAccount(Bukkit.getPlayer(args[0]).getUniqueId());

            InventoryBuilderSM.openMainMenu(
                moderator, targetUUID, config); // Ouverture de l'inventaire SM du joueur target.
          } else {
            moderator.sendMessage(
                GeneralUtils.getPrefix("SM")
                    + "§cCe joueur ne s'est jamais connecté sur le serveur.");
          }
        }
        return true;
      } else {
        sender.sendMessage(
            GeneralUtils.getPrefix("SM")
                + "§cTu dois être sur le serveur pour éxécuter cette commande.");
      }
    } else {
      sender.sendMessage(GeneralUtils.getPrefix("SM") + "§cTu n'as pas accès à cette commande.");
    }
    return false;
  }
}

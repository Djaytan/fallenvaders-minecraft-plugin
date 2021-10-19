package fr.fallenvaders.minecraft.justice_hands.criminalrecords;

import fr.fallenvaders.minecraft.justice_hands.GeneralUtils;
import fr.fallenvaders.minecraft.justice_hands.JusticeHandsModule;
import fr.fallenvaders.minecraft.justice_hands.criminalrecords.invmanager.InventoryBuilderCR;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class CommandCR implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender.hasPermission("justicehands.cr")) {
      if (sender instanceof Player) {
        Player moderator = (Player) sender;
        if (args.length == 0) {
          moderator.sendMessage(
              GeneralUtils.getPrefix("CR") + "§cSyntaxe incomplète, il manque un argument.");
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
                GeneralUtils.getPrefix("CR")
                    + "§cCe joueur ne s'est jamais connecté sur le serveur.");
          }
        }
        return true;
      } else {
        sender.sendMessage(
            GeneralUtils.getPrefix("CR")
                + "§cTu dois être sur le serveur pour éxécuter cette commande.");
      }
    } else {
      sender.sendMessage(GeneralUtils.getPrefix("CR") + "§cTu n'as pas accès à cette commande.");
    }
    return false;
  }
}

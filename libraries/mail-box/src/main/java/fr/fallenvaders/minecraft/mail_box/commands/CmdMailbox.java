package fr.fallenvaders.minecraft.mail_box.commands;

import fr.fallenvaders.minecraft.mail_box.data_manager.*;
import fr.fallenvaders.minecraft.mail_box.inventory.inventories.MailBoxInventory;
import fr.fallenvaders.minecraft.mail_box.player_manager.PlayerManager;
import fr.fallenvaders.minecraft.mail_box.utils.LangManager;
import fr.fallenvaders.minecraft.mail_box.utils.MessageLevel;
import fr.fallenvaders.minecraft.mail_box.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdMailbox implements CommandExecutor {

  public static final String CMD_LABEL = "mailbox";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    boolean res = false;

    if (sender instanceof Player player) {
      if (args.length == 0) {
        res = true;

        if (player.hasPermission("mailbox.openmenu.self")) {
          MailBoxInventory mailBox = new MailBoxInventory(MailBoxController.getDataHolder(player.getUniqueId()));
          mailBox.openInventory(player);

        } else {
          MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_permission_needed"));
        }

      } else if (args.length == 1) {
        if (args[0].equalsIgnoreCase("check")) {
          res = true;

          if (player.hasPermission("mailbox.check.self")) {
            DataHolder pHolder = MailBoxController.getDataHolder(player.getUniqueId());
            int nLetter = DataManager.getTypeData(pHolder, LetterData.class).size();
            int nItem = DataManager.getTypeData(pHolder, ItemData.class).size();
            MessageUtils.sendMessage(player, MessageLevel.INFO, LangManager.getValue("result_command_check_self", nLetter, nItem));

          } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_permission_needed"));
          }

        } else if (args[0].equalsIgnoreCase("open")) {
          res = true;
          MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_command_usage", "/mailbox open <joueur>"));

        }
      } else if (args.length == 2) {
        if (args[0].equalsIgnoreCase("check")) {
          res = true;
          UUID sourceUuid = PlayerManager.getInstance().getUUID(args[1]);

          if (sourceUuid != null) {
            boolean playerIsOwner = sourceUuid.equals(player.getUniqueId());

            if (playerIsOwner && player.hasPermission("mailbox.check.self") || player.hasPermission("mailbox.check.other")) {
              DataHolder sHolder = MailBoxController.getDataHolder(sourceUuid);
              int nLetter = DataManager.getTypeData(sHolder, LetterData.class).size();
              int nItem = DataManager.getTypeData(sHolder, ItemData.class).size();

              if (sourceUuid.equals(player.getUniqueId())) {
                MessageUtils.sendMessage(player, MessageLevel.INFO, LangManager.getValue("result_command_check_self", nLetter, nItem));

              } else {
                MessageUtils.sendMessage(player, MessageLevel.INFO, LangManager.getValue("result_command_check_other", args[1], nLetter, nItem));
              }

            } else {
              MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_permission_needed"));
            }

          } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_player_not_found", args[1]));
          }

        } else if (args[0].equalsIgnoreCase("open")) {
          res = true;
          UUID sourceUuid = PlayerManager.getInstance().getUUID(args[1]);

          if (sourceUuid != null) {
            boolean playerIsOwner = sourceUuid.equals(player.getUniqueId());

            if (playerIsOwner && player.hasPermission("mailbox.openmenu.self") || player.hasPermission("mailbox.openmenu.other")) {
              MailBoxInventory inv = new MailBoxInventory(MailBoxController.getDataHolder(sourceUuid));
              inv.openInventory(player);

            } else {
              MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_permission_needed"));
            }

          } else {
            MessageUtils.sendMessage(player, MessageLevel.ERROR, LangManager.getValue("string_player_not_found", args[1]));
          }

        }
      }

    } else {
      res = true;
      MessageUtils.sendMessage(sender, MessageLevel.ERROR, LangManager.getValue("string_command_player_only"));
    }

    if (!res) {
      MessageUtils.sendMessage(sender, MessageLevel.ERROR, LangManager.getValue("string_command_not_found"));
    }

    return res;
  }
}

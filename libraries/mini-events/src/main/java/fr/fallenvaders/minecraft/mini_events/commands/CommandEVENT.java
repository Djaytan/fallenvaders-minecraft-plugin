package fr.fallenvaders.minecraft.mini_events.commands;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.commands.args.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandEVENT implements CommandExecutor {

  private String incompleteCommand =
      "§cCommande incomplète, utilise la commande §b/event help§c, pour plus d'aide.";
  private String badCommand =
      "§cCommande éronnée, utilise la commande §b/event help§c, pour plus d'aide.";
  private String badStartList =
      "§cCommande éronnée, utilise la commande §b/event startlist§c, pour plus d'aide.";
  private String incompleteStartList =
      "§cCommande incomplète, utilise la commande §b/event start <§7event§b>§c, pour plus d'aide.";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (args.length == 0) {
        player.sendMessage(MiniEvents.PREFIX + incompleteCommand);
        return true;
      }
      if (args.length >= 1) {
        switch (args[0].toLowerCase()) {
          case "forcerun":
            ArgFORCERUN.ArgForceRun(player);
            return true;
          case "help":
            ArgHELP.ArgHelp(player);
            return true;
          case "join":
            ArgJOIN.ArgJoin(player);
            return true;
          case "leave":
            ArgLEAVE.ArgLeave(player);
            return true;
          case "name":
            ArgNAME.ArgName(player, MiniEvents.PREFIX, MiniEvents.getGame().getGameName());
            return true;
          case "participants":
            ArgPARTICIPANTS.ArgParticipants(player);
            return true;
          case "run":
            ArgRUN.ArgRun(player);
            return true;
          case "start":
            if (args.length >= 2) {
              List<GameName> eventNames = Arrays.asList(GameName.values());
              for (int i = 0; i < eventNames.size(); i++) {
                if (eventNames.get(i).getRealName().equals("NONE")) i++;
                if (eventNames.get(i).getRealName().equalsIgnoreCase(args[1])) {
                  ArgSTART.ArgStart(player, eventNames.get(i));
                  return true;
                }
              }
              player.sendMessage(MiniEvents.PREFIX + badStartList);
              return true;
            } else {
              player.sendMessage(MiniEvents.PREFIX + incompleteStartList);
              return true;
            }
          case "startlist":
            ArgSTARTLIST.ArgStartList(MiniEvents.PREFIX, player);
            return true;
          case "state":
            ArgSTATE.ArgState(player, MiniEvents.PREFIX, MiniEvents.getGame().getGameState());
            return true;
          case "stop":
            ArgSTOP.ArgStop(player);
            return true;
          case "teleport":
            ArgTELEPORT.ArgTeleport(player);
            return true;
          default:
            player.sendMessage(MiniEvents.PREFIX + badCommand);
            return true;
        }
      }
    } else {
      sender.sendMessage(
          MiniEvents.PREFIX + "§cTu dois être sur le serveur pour effectuer sur cette commande !");
    }
    return false;
  }
}

package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.OnJoinLeaveCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArgLEAVE {

  public static void ArgLeave(Player player) {

    GameState state = MiniEvents.getGame().getGameState();
    String prefix = MiniEvents.PREFIX;

    if (state == GameState.NOTSTARTED) {
      player.sendMessage(
          prefix + "§cIl y a aucun événement, donc tu ne te trouves dans aucune liste d'attente.");
    } else if ((state == GameState.STARTING
            || state == GameState.PLAYING
            || state == GameState.FINISH)
        && !MiniEvents.getGame().getParticipants().contains(player.getUniqueId())) {
      player.sendMessage(
          prefix + "§cTu ne peux pas quitté un événement pendant qu'il est en cours.");
    } else if (state == GameState.WAITING
        && MiniEvents.getGame().getParticipants().contains(player.getUniqueId())) {
      String eventName = MiniEvents.getGame().getGameName().name().toLowerCase();
      Integer maxPlayers =
          MiniEvents.PLUGIN
              .getConfig()
              .getInt("config-event." + eventName + ".players.max-players");

      MiniEvents.getGame().getParticipants().remove(player.getUniqueId());

      OnJoinLeaveCommand.EventLeave(player);

      player.sendMessage(prefix + "§7Tu as été retiré de la liste des participants !");
      player.sendMessage("  §b/event join §7pour rejoindre la liste.\n");
      player.sendMessage(prefix + "§7Téléportation hors de l'espace de jeu.");

      // Broadcast d'annonce, annonçant que le joueur a quitté l'événement.
      Bukkit.broadcastMessage(
          player.getName()
              + "§7 a quitté l'évévenement §7(§c"
              + MiniEvents.getGame().getParticipants().size()
              + "§7/§c"
              + maxPlayers
              + "§7).");
    } else {
      player.sendMessage(prefix + "§cDésolé mais tu te trouves dans aucune liste d'attente.");
    }
  }
}

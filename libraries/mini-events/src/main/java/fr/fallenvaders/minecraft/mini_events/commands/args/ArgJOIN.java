package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.OnJoinLeaveCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArgJOIN {
  public static void ArgJoin(Player player) {

    GameState state = MiniEvents.getGame().getGameState();
    String prefix = MiniEvents.PREFIX;

    if (state == GameState.NOTSTARTED) {
      player.sendMessage(prefix + "§cIl y a aucun événement de disponible.");
    }

    // On vérifie si un événement est en phase d'attente (où les joueurs peuvent
    // rejoindre).
    else if (state == GameState.WAITING) {
      // On vérifie si le joueur n'est pas déjà dans la liste d'attente des joueurs de
      // l'événement.
      if (!MiniEvents.getGame().getParticipants().contains(player.getUniqueId())) {
        String eventName = MiniEvents.getGame().getGameName().name().toLowerCase();
        Integer maxPlayers =
            MiniEvents.PLUGIN
                .getConfig()
                .getInt("config-event." + eventName + ".players.max-players");
        // On vérifie si le nombre maximal de jouueur n'as pas était atteint.
        if (MiniEvents.getGame().getParticipants().size() < maxPlayers) {

          MiniEvents.getGame().getParticipants().add(player.getUniqueId());

          OnJoinLeaveCommand.EventJoin(player);

          player.sendMessage(MiniEvents.PREFIX + "§7Tu as été ajouté à liste des participants !");
          player.sendMessage("  §b/event leave §7pour quitter la liste.\n");
          player.sendMessage(MiniEvents.PREFIX + "§7Téléportation vers l'événement.");

          // Broadcast d'annonce, annonçant que le joueur à rejoint l'événement.
          Bukkit.broadcastMessage(
              player.getName()
                  + "§7 a rejoint l'évévenement §7(§a"
                  + MiniEvents.getGame().getParticipants().size()
                  + "§7/§a"
                  + maxPlayers
                  + "§7).");
        } else {
          player.sendMessage(
              prefix + "§7Désolé mais l'événement a atteint son nombre maximal de joueurs.");
        }
      } else {
        player.sendMessage(prefix + "§cDésolé mais tu te trouves déjà dans la liste d'attente.");
      }
    } else if (state == GameState.STARTING) {
      player.sendMessage(
          prefix
              + "§cDésolé l'événement que tu veux rejoindre est en cours de lancement, impossible de le rejoindre !");
    } else if (state == GameState.PLAYING) {
      player.sendMessage(
          prefix
              + "§cDésolé l'événement que tu veux rejoindre est en cours de fonctionnement, impossible de le rejoindre !");
    } else if (state == GameState.FINISH) {
      player.sendMessage(
          prefix
              + "§cDésolé l'événement que tu veux rejoindre est terminé, impossible de le rejoindre !");
    }
  }
}

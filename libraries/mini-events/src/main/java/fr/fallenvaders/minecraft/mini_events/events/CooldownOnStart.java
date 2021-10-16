package fr.fallenvaders.minecraft.mini_events.events;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class CooldownOnStart extends BukkitRunnable {

  private int cooldown = 15; // (en secondes)

  @Override
  public void run() {
    List<UUID> participants = MiniEvents.getGame().getParticipants();
    String eventPrefix =
        ("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

    if (MiniEvents.getGame().getGameState() == GameState.STARTING) {

      // La liste des participants contient toujours un joueur minimum:
      if (participants.size() > 0) {
        // ****************** Messages pour les joueurs ****************************//
        for (UUID playerUUID : participants) {
          Player player = Bukkit.getPlayer(playerUUID);
          player.setLevel(cooldown);
          if (cooldown == 15
              || cooldown == 10
              || cooldown == 5
              || cooldown == 4
              || cooldown == 3
              || cooldown == 2) {
            player.sendMessage(
                eventPrefix + "§7Le jeu commence dans §b" + cooldown + " §7secondes !");
            if (cooldown == 15) {
              OnStartEndOfEvent.CooldownStartOnStart(player);
            }
          }
          if (cooldown == 1) {
            player.sendMessage(
                eventPrefix + "§7Le jeu commance dans §b" + cooldown + " §7seconde !");
          }
          if (cooldown == 0) {
            player.sendMessage(eventPrefix + "§7Le jeu commence. Bonne chance à tous !");
            OnStartEndOfEvent.CooldownStartOnEnd(player);
          }
        }
        // *************************************************************************//

        // Lorsque le cooldown se termine et que la liste des participants contient
        // toujours des joueurs, on lance l'événement en le passant à l'état PLAYING:
        if (cooldown == 0 && participants.size() > 0) {
          // Il reste un joueur maximum:

          /*if (participants.size() <= 1) {
          	WhoIsWinner.getWinner(participants, main);
          }*/

          // Sinon on lance l'événement:
          /*else*/
          {
            MiniEvents.getGame().setGameState(GameState.PLAYING);
            WhichEventIs.SelectEvent(); // On essaye de savoir quel événement est lancé.

            // à partir d'ici la phase automatique de lancement s'arrête, le reste dépend
            // simplement du développement des événements eux-mêmes.
            // C'est aussi par cette méthode qu'on lance la première action de lancement de
            // l'événement et qui va engendrer les autres.
          }
          cancel();
        }
      }
      // Si cependant il y a plus aucun joueur:
      else {
        // On envoit un petit message à tous les joueurs du serveur.
        Bukkit.broadcastMessage(
            MiniEvents.PREFIX + "§cIl n'y a plus assez de joueurs pour commencer l'événement.");
        Bukkit.broadcastMessage(
            "  §7Tu peux faire la commande : §b/event join §7pour rejoindre l'event §b"
                + MiniEvents.getGame().getGameName().name().toUpperCase());
        MiniEvents.getGame().setGameState(GameState.WAITING);
        cancel();
      }

      // On décrémente la valeur "cooldown", pour permettre l'avancement de ce dernier
      // pour le lancement de l'événement.
      cooldown--;
    }
    // Si l'état du jeu est différent de STARTING, on arrête cette tâche
    // (Simplement une sécurité)
    else {
      cancel();
    }
  }
}
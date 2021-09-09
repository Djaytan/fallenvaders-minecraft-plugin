package fr.fallenvaders.minecraft.mini_events.events;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerRemaining {
  public static void PlayerLeft() {
    String eventPrefix =
        ("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

    // Permet d'afficher, après l'élimination d'un joueur le nombre restant de
    // joueurs encore en jeu.
    if (MiniEvents.getGame().getGameState() == GameState.STARTING
        || MiniEvents.getGame().getGameState() == GameState.PLAYING) {
      // Permet juste d'éviter d'avoir le message :
      // "Il reste désormais 1 joueur" après l'élimination du dernier joueur avant la
      // fin de l'événement.
      if (MiniEvents.getGame().getParticipants().size() >= 2) {
        for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
          Player players = Bukkit.getPlayer(playerUUID);
          players.sendMessage(
              eventPrefix
                  + "§7Il reste désormais §b"
                  + (MiniEvents.getGame().getParticipants().size())
                  + " joueurs !");
        }
      }
    }
  }
}

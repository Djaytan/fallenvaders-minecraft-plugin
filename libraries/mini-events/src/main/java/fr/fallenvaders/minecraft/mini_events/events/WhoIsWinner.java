package fr.fallenvaders.minecraft.mini_events.events;

import java.util.List;
import java.util.UUID;

public class WhoIsWinner {

  // Méthode permettant de vérifier si l'événement en cours contient un gagnant.
  public static void getWinner(List<UUID> participants) {
    // Si la liste des participants ne contient plus que :

    // 1 joueur, alors nous avons un gagnant :
    if (participants.size() == 1) {
      OnStartEndOfEvent.EventStateFinish();
    }

    // 0 joueur, alors on remet à zéro directement l'événement.
    // Pas besoin de passer par la procédure de fin d'événement.
    if (participants.size() == 0) {
      OnStartEndOfEvent.EventEndAndRAZ();
    }
  }
}

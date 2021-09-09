package fr.fallenvaders.minecraft.mini_events;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

  private List<UUID> participants = new ArrayList<>(); // Liste des participants de l'événement.
  private List<UUID> leaveDuringEvent =
      new ArrayList<>(); // Liste des joueurs déconnectés durant un événement.
  private List<Location> blockLocation =
      new ArrayList<>(); // Liste de location de block (Régénération intélligente).
  private GameState state; // État de l'événement.
  private GameName name; // Nom de l'événement.

  public Game() {
    setGameState(GameState.NOTSTARTED);
    setGameName(GameName.NONE);
  }

  // Modifier l'état de l'event.
  public void setGameState(GameState state) {
    state = state;
  }

  // Récupère l'état de l'event.
  public GameState getGameState() {
    return state;
  }

  // Récupère la liste des joueurs voulant jouer ou qui sont entrain de jouer.
  public List<UUID> getParticipants() {
    return participants;
  }

  // Récupère la liste des joueurs déconnectés durant l'événement.
  public List<UUID> getLeaveDuringEvent() {
    return leaveDuringEvent;
  }

  // Récupère une liste de locations de blocks.
  public List<Location> getBlockLoc() {
    return blockLocation;
  }

  // Modifie le type d'événement.
  public void setGameName(GameName name) {
    this.name = name;
  }

  // Récupère l'événement en cours.
  public GameName getGameName() {
    return this.name;
  }
}

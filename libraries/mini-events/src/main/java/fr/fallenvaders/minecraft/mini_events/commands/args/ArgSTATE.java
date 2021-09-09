package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameState;
import org.bukkit.entity.Player;

public class ArgSTATE {
  ///////////////////////////////////////////////////////
  // Permet de savoir l'état de l'événement en cours.
  // Pratique pour lors de bugs ou de dysfonctionnement.
  ///////////////////////////////////////////////////////
  public static void ArgState(Player player, String pluginPrefix, GameState gameState) {

    player.sendMessage(pluginPrefix + "§7État du jeu : §b" + gameState.name().toUpperCase());
    if (gameState == GameState.NOTSTARTED) {
      player.sendMessage(pluginPrefix + "§7Aucun événement est en cours.");
    } else if (gameState == GameState.WAITING) {
      player.sendMessage(
          pluginPrefix + "§7L'événement est en attente de joueurs avant de commencer.");
    } else if (gameState == GameState.STARTING) {
      player.sendMessage(
          pluginPrefix + "§7L'événement est entrain de commencer (décompte de départ).");
    } else if (gameState == GameState.PLAYING) {
      player.sendMessage(pluginPrefix + "§7L'événement est entrain de se jouer.");
    } else if (gameState == GameState.FINISH) {
      player.sendMessage(
          pluginPrefix + "§7L'événement est entrain de se terminer (décompte de fin).");
    }
  }
}

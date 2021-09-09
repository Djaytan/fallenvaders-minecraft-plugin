package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.CooldownOnStart;
import org.bukkit.entity.Player;

public class ArgRUN {

  public static void ArgRun(Player player) {
    GameState gameState = MiniEvents.getGame().getGameState();
    String pluginPrefix = MiniEvents.PREFIX;

    // On récupère le nom de l'événement en minuscule.
    String eventName = MiniEvents.getGame().getGameName().name().toLowerCase();
    // On récupère le nombre minimum de joueurs.
    Integer minPlayers =
        (MiniEvents.PLUGIN
            .getConfig()
            .getInt("config-event." + eventName + ".players.min-players"));

    if (gameState == GameState.STARTING
        || gameState == GameState.PLAYING
        || gameState == GameState.FINISH) {
      player.sendMessage(
          pluginPrefix + "§7Un événement est déjà en cours, impossible d'en lancer un autre.");
    } else if (gameState == GameState.NOTSTARTED) {
      player.sendMessage(
          pluginPrefix
              + "§cAucun événement en cours, fait la commande §b/event start <§7event§b>§c pour lancer un événement.");
    }
    if (gameState == GameState.WAITING) {
      if (MiniEvents.getGame().getParticipants().size() >= minPlayers) {
        CooldownOnStart start = new CooldownOnStart();
        start.runTaskTimer(MiniEvents.PLUGIN, 0, 20);
      } else {
        int playerNeed = minPlayers - MiniEvents.getGame().getParticipants().size();

        if (playerNeed == 1) {
          player.sendMessage(
              pluginPrefix
                  + "§cIl manque §7"
                  + playerNeed
                  + " §7joueur §cavant de pouvoir lancer l'événement.");
        }
        if (playerNeed > 1) {
          player.sendMessage(
              pluginPrefix
                  + "§cIl manque §7"
                  + playerNeed
                  + " §7joueurs §cavant de pouvoir lancer l'événement.");
        }
      }
    }
  }
}

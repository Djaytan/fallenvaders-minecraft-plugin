package fr.fallenvaders.minecraft.mini_events.events.spleef.tasks;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.GetEventWorld;
import fr.fallenvaders.minecraft.mini_events.events.PlayerElimination;
import fr.fallenvaders.minecraft.mini_events.events.PlayerRemaining;
import fr.fallenvaders.minecraft.mini_events.events.WhoIsWinner;
import fr.fallenvaders.minecraft.mini_events.events.spleef.SpleefActions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class SpleefDetection extends BukkitRunnable {

  @Override
  public void run() {
    GameName name = MiniEvents.getGame().getGameName();
    GameState state = MiniEvents.getGame().getGameState();
    Integer waterLayer =
        MiniEvents.PLUGIN.getConfig().getInt("config-event.spleef.water-detection.y");

    String eventPrefix =
        ("§f[" + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase() + "§f] ");

    if (name == GameName.SPLEEF && state == GameState.PLAYING) {
      if (MiniEvents.getGame().getParticipants().size() > 0) {
        for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
          Player player = Bukkit.getPlayer(playerUUID);

          if (player.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
            if (player.getLocation().getBlockY() <= waterLayer) {
              player.sendMessage(eventPrefix + "§7Tu as été éliminé, tu es tombé dans l'eau !");
              PlayerElimination.EventEliminationTP(player);

              // On envoit à tous les joueurs dans le monde d'événement le message
              // d'élimination.
              for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
                if (pls.getWorld().getName().equalsIgnoreCase(GetEventWorld.getName())) {
                  SpleefActions.EventElimationMessageWater(pls, player);
                }
              }

              // On supprime le joueur des participants et on regarde si il y a un gagnant.
              MiniEvents.getGame().getParticipants().remove(player.getUniqueId());

              PlayerRemaining.PlayerLeft();
              WhoIsWinner.getWinner(MiniEvents.getGame().getParticipants());
            }
          }
        }
      } else {
        cancel();
      }
    } else {
      // Si l'état du jeu n'est plus en "PLAYING", alors on éteint cette boucle de
      // détection.
      cancel();
    }
  }
}

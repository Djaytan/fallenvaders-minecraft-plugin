package fr.fallenvaders.minecraft.mini_events.commands.args;

import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArgPARTICIPANTS {
  public static void ArgParticipants(Player player) {
    if (!(MiniEvents.getGame().getGameState() == GameState.NOTSTARTED)) {
      if (MiniEvents.getGame().getParticipants().size() >= 1) {

        String participantsList = "";
        for (int i = 0; i < MiniEvents.getGame().getParticipants().size(); i++) {
          Player participant = Bukkit.getPlayer(MiniEvents.getGame().getParticipants().get(i));
          if (i == 0) {
            participantsList = ("§f" + participant.getName());
          } else {
            participantsList = (participantsList + "§7, §f" + participant.getName());
          }
        }
        player.sendMessage(MiniEvents.PREFIX + "§7Liste des participants : " + participantsList);
      } else {
        player.sendMessage(MiniEvents.PREFIX + "§cLa liste des participants est vide.");
      }
    } else {
      player.sendMessage(
          MiniEvents.PREFIX + "§cAucun événement est en cours, aucune liste de participants.");
    }
  }
}

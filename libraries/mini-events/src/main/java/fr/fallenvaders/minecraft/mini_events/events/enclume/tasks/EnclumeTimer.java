package fr.fallenvaders.minecraft.mini_events.events.enclume.tasks;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EnclumeTimer extends BukkitRunnable {

  private int waveNumber;

  public EnclumeTimer(int waveNumber) {
    this.waveNumber = waveNumber;
  }

  // On laisse juste deux secondes de plus à la fin de la vague
  private int timerS = 5;
  private int timerDS = 0;

  @Override
  public void run() {
    GameName name = MiniEvents.getGame().getGameName();
    GameState state = MiniEvents.getGame().getGameState();
    if (name == GameName.ENCLUME && state == GameState.PLAYING) {
      if (timerS <= 5) {
        for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
          Player player = Bukkit.getPlayer(playerUUID);
          if (timerS > 1) {
            player
                .spigot()
                .sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(
                        "§cProchaine vague: §b" + timerS + "." + timerDS + " secondes §f!"));
          } else {
            player
                .spigot()
                .sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(
                        "§cProchaine vague: §b" + timerS + "." + timerDS + " seconde §f!"));
          }
        }
      }
      if (timerS == 0 && timerDS == 0) {
        waveNumber++;
        EnclumeMiniWavesTimer start = new EnclumeMiniWavesTimer(waveNumber);
        start.runTaskTimer(MiniEvents.PLUGIN, 0, 1);
        cancel();
      }

      if (!(timerDS == 0)) {
        timerDS--;
      } else {
        timerDS = 9;
        timerS--;
      }
    } else {
      cancel();
    }
  }
}

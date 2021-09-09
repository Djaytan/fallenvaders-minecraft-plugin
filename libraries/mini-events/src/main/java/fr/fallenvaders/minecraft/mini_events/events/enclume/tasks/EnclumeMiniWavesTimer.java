package fr.fallenvaders.minecraft.mini_events.events.enclume.tasks;

import fr.fallenvaders.minecraft.mini_events.GameName;
import fr.fallenvaders.minecraft.mini_events.GameState;
import fr.fallenvaders.minecraft.mini_events.MiniEvents;
import fr.fallenvaders.minecraft.mini_events.events.enclume.EnclumeGeneration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EnclumeMiniWavesTimer extends BukkitRunnable {

  private Integer waveNumber;
  private int timer = 20;
  private int totalMiniWaves = 2;

  public EnclumeMiniWavesTimer(int waveNumber) {
    this.waveNumber = waveNumber;
  }

  @Override
  public void run() {
    GameName name = MiniEvents.getGame().getGameName();
    GameState state = MiniEvents.getGame().getGameState();
    if (name == GameName.ENCLUME && state == GameState.PLAYING) {
      if (totalMiniWaves == 2 && timer == 20) {
        String eventPrefix =
            ("§f["
                + MiniEvents.getGame().getGameName().getEventColoredPrefix().toUpperCase()
                + "§f] ");
        for (UUID playerUUID : MiniEvents.getGame().getParticipants()) {
          Player player = Bukkit.getPlayer(playerUUID);
          player.sendMessage(
              eventPrefix + "§7La vague d'enclumes §b§ln°" + waveNumber + " §7arrive !!");
        }
      }

      if (totalMiniWaves > 0) {
        if (timer < 20) {
          timer++;
        } else {
          EnclumeGeneration.GenerateAnvilLocation(waveNumber);
          timer = 0;
          totalMiniWaves--;
        }
      } else {
        EnclumeTimer startTimer = new EnclumeTimer(waveNumber);
        startTimer.runTaskTimer(MiniEvents.PLUGIN, 0, 2);
        cancel();
      }
    } else {
      cancel();
    }
  }
}
